package com.springboot.shiro.shiro2spboot.repository;

import com.springboot.shiro.shiro2spboot.entity.Permission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PermissionDao extends CrudRepository<Permission, Long> {


    //       使用sql语句查询时，需要设置nativeQuery=true
    @Query(nativeQuery = true, value = "SELECT id,name,parent_id,parent_ids,permission,resource_type,url,available FROM permission " +
//             IF(条件，真执行，假执行)
            "where IF(:name is not null,name LIKE CONCAT('%',:name,'%'),1=1 ) ")
//     在执行sql的同时，亦可通过Pageable织入分页和排序
    List<Permission> findPermission(String name, Pageable pageable);
}
