package com.xpc.apacheshiro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String salt;
    /**
     * 用户对应的角色集合
     */
    private Set<Role> roles;
}
