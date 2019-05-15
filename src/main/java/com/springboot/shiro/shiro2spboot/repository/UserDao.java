package com.springboot.shiro.shiro2spboot.repository;

import com.springboot.shiro.shiro2spboot.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);


    //     使用sql语句查询时，需要设置nativeQuery=true
    @Query(nativeQuery = true, value = "SELECT uid,name,password,salt,state,username FROM user " +
//             IF(条件，真执行，假执行)
            "where IF(:username is not null,username LIKE CONCAT('%',:username,'%'),1=1 ) ")
//     在执行sql的同时，亦可通过Pageable织入分页和排序
    List<User> findUser(@Param("username") String username, Pageable pageable);

}
