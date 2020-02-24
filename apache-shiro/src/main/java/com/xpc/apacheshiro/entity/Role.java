package com.xpc.apacheshiro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Long id;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<Permission> permission;
}
