package com.xpc.loginauth.demo.common.repository;

import com.xpc.loginauth.demo.common.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
