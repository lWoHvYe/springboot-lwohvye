package com.springboot.shiro.shiro2spboot.repository;

import com.springboot.shiro.shiro2spboot.entity.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleDao extends CrudRepository<Role, Long> {

    //       使用sql语句查询时，需要设置nativeQuery=true
    @Query(nativeQuery = true, value = "SELECT id,role,description,available FROM role " +
//             IF(条件，真执行，假执行)
            "where IF(:role is not null,role LIKE CONCAT('%',:role,'%'),1=1 ) ")
//     在执行sql的同时，亦可通过Pageable织入分页和排序
    List<Role> findRole(String role, Pageable pageable);
}
