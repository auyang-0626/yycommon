package com.yang.yy.common.limitcheck.repository;

import com.yang.yy.common.limitcheck.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,Long> {


    @Modifying
    @Query(value = "update t_resource a inner join (select id,modify_count   from t_resource where id= :id)  b  " +
            "on a.id=b.id  set a.modify_count = b.modify_count + 1 where a.id = :id",nativeQuery = true)
    void increment(@Param("id") Long id);

    Resource findByName(String name);
}
