package com.xpc.loginauth.demo.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Data
@Entity
public class Permission implements Serializable {
    @Id @GeneratedValue
    private long id;

    private String name;//权限名称.
    private String description;//描述.
    private String url;//地址.
    private long pid;//父id.

    //角色和权限的关系  多对多.
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="role_permission",joinColumns= {@JoinColumn(name="permission_id")},
            inverseJoinColumns= {@JoinColumn(name="role_id")})
    private List<Role> roles;
}
