package com.springboot.shiro.shiro2spboot.local.redis;

import org.apache.shiro.util.Assert;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot.local.redis
 * @className RedisCacheWriterCustomer
 * @description
 * @date 2019/10/15 13:36
 * @see org.springframework.data.redis.cache.DefaultRedisCacheWriter
 */

public class RedisCacheWriterCustomer implements RedisCacheWriter {


    private final RedisConnectionFactory connectionFactory;

    private final Duration sleepTime;

    private final static String REDIS_EXPIRE_TIME_KEY = "#key_expire_time";


    /**
     * @param connectionFactory must not be {@literal null}.
     */
    public RedisCacheWriterCustomer(RedisConnectionFactory connectionFactory) {
        this(connectionFactory, Duration.ZERO);
    }

    /**
     * @param connectionFactory must not be {@literal null}.
     * @param sleepTime         sleep time between lock request attempts. Must not be {@literal null}. Use {@link Duration#ZERO}
     *                          to disable locking.
     */
    public RedisCacheWriterCustomer(RedisConnectionFactory connectionFactory, Duration sleepTime) {

        Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");
        Assert.notNull(sleepTime, "SleepTime must not be null!");

        this.connectionFactory = connectionFactory;
        this.sleepTime = sleepTime;
    }

    /**
     * @return void
     * @description 重写了put方法，用于设置过期时间
     * @params [name, key, value, ttl]
     * @author Hongyan Wang
     * @date 2019/10/15 16:06
     */
    @Override
    public void put(String name, byte[] key, byte[] value, @Nullable Duration ttl) {

        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        Assert.notNull(value, "Value must not be null!");

        execute(name, (connection) -> {

            //当设置了过期时间，则修改取出
            //@Cacheable(value="user-key#key_expire=1200",key = "#id",condition = "#id != 2")
            //name 对应 value
            //key 对应 value :: key

            //判断name里面是否设置了过期时间，如果设置了则对key进行缓存，并设置过期时间
            int index = name.lastIndexOf(REDIS_EXPIRE_TIME_KEY);
            if (index > 0) {
                //取出对应的时间 1200 index + 1是还有一个=号
                String expireString = name.substring(index + 1 + REDIS_EXPIRE_TIME_KEY.length());
                long expireTime = Long.parseLong(expireString);
                connection.set(key, value, Expiration.from(expireTime, TimeUnit.SECONDS), RedisStringCommands.SetOption.upsert());
            } else if (shouldExpireWithin(ttl)) {
                connection.set(key, value, Expiration.from(ttl.toMillis(), TimeUnit.MILLISECONDS), RedisStringCommands.SetOption.upsert());
            } else {
                connection.set(key, value);
            }
            return "OK";
        });
    }

    /**
     * @description 重新get方法，实现访问刷新过期时间，这里默认设置刷新为600s
     * @params [name, key]
     * @return byte[]
     * @author Hongyan Wang
     * @date 2019/10/15 17:41
     */
    public byte[] get(String name, byte[] key) {
        org.springframework.util.Assert.notNull(name, "Name must not be null!");
        org.springframework.util.Assert.notNull(key, "Key must not be null!");
        return execute(name, (connection) -> {

            int index = name.lastIndexOf(REDIS_EXPIRE_TIME_KEY);
            if (index > 0) {
                //取出对应的时间 1200 index + 1是还有一个=号
                String expireString = name.substring(index + 1 + REDIS_EXPIRE_TIME_KEY.length());
                long expireTime = Long.parseLong(expireString);
                connection.expire(key, expireTime);
            } else {
                connection.expire(key, 600);
            }
            return connection.get(key);
        });
    }

    public byte[] putIfAbsent(String name, byte[] key, byte[] value, @Nullable Duration ttl) {
        org.springframework.util.Assert.notNull(name, "Name must not be null!");
        org.springframework.util.Assert.notNull(key, "Key must not be null!");
        org.springframework.util.Assert.notNull(value, "Value must not be null!");
        return this.execute(name, (connection) -> {
            if (this.isLockingCacheWriter()) {
                this.doLock(name, connection);
            }

            byte[] var6;
            try {
                if (connection.setNX(key, value)) {
                    if (shouldExpireWithin(ttl)) {
                        connection.pExpire(key, ttl.toMillis());
                    }

                    Object var10 = null;
                    return (byte[]) var10;
                }

                var6 = connection.get(key);
            } finally {
                if (this.isLockingCacheWriter()) {
                    this.doUnlock(name, connection);
                }

            }

            return var6;
        });
    }

    public void remove(String name, byte[] key) {
        org.springframework.util.Assert.notNull(name, "Name must not be null!");
        org.springframework.util.Assert.notNull(key, "Key must not be null!");
        this.execute(name, (connection) -> connection.del(new byte[][]{key}));
    }

    public void clean(String name, byte[] pattern) {
        org.springframework.util.Assert.notNull(name, "Name must not be null!");
        org.springframework.util.Assert.notNull(pattern, "Pattern must not be null!");
        this.execute(name, (connection) -> {
            boolean wasLocked = false;

            try {
                if (this.isLockingCacheWriter()) {
                    this.doLock(name, connection);
                    wasLocked = true;
                }

                byte[][] keys = (byte[][]) ((Set) Optional.ofNullable(connection.keys(pattern)).orElse(Collections.emptySet())).toArray(new byte[0][]);
                if (keys.length > 0) {
                    connection.del(keys);
                }
            } finally {
                if (wasLocked && this.isLockingCacheWriter()) {
                    this.doUnlock(name, connection);
                }

            }

            return "OK";
        });
    }

    void lock(String name) {
        this.execute(name, (connection) -> {
            return this.doLock(name, connection);
        });
    }

    void unlock(String name) {
        this.executeLockFree((connection) -> {
            this.doUnlock(name, connection);
        });
    }

    private Boolean doLock(String name, RedisConnection connection) {
        return connection.setNX(createCacheLockKey(name), new byte[0]);
    }

    private Long doUnlock(String name, RedisConnection connection) {
        return connection.del(new byte[][]{createCacheLockKey(name)});
    }

    boolean doCheckLock(String name, RedisConnection connection) {
        return connection.exists(createCacheLockKey(name));
    }

    private boolean isLockingCacheWriter() {
        return !this.sleepTime.isZero() && !this.sleepTime.isNegative();
    }

    private <T> T execute(String name, Function<RedisConnection, T> callback) {
        RedisConnection connection = this.connectionFactory.getConnection();

        T var4;
        try {
            this.checkAndPotentiallyWaitUntilUnlocked(name, connection);
            var4 = callback.apply(connection);
        } finally {
            connection.close();
        }

        return var4;
    }

    private void executeLockFree(Consumer<RedisConnection> callback) {
        RedisConnection connection = this.connectionFactory.getConnection();

        try {
            callback.accept(connection);
        } finally {
            connection.close();
        }

    }

    private void checkAndPotentiallyWaitUntilUnlocked(String name, RedisConnection connection) {
        if (this.isLockingCacheWriter()) {
            try {
                while (this.doCheckLock(name, connection)) {
                    Thread.sleep(this.sleepTime.toMillis());
                }

            } catch (InterruptedException var4) {
                Thread.currentThread().interrupt();
                throw new PessimisticLockingFailureException(String.format("Interrupted while waiting to unlock cache %s", name), var4);
            }
        }
    }

    private static boolean shouldExpireWithin(@Nullable Duration ttl) {
        return ttl != null && !ttl.isZero() && !ttl.isNegative();
    }

    private static byte[] createCacheLockKey(String name) {
        return (name + "~lock").getBytes(StandardCharsets.UTF_8);
    }

}
