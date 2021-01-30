package com.luxoft.videoplayer.dao;

import com.luxoft.videoplayer.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "select * from roles where role = :role", nativeQuery = true)
    Role getByRole(@Param("role") String role);

}
