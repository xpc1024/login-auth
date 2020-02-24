package com.xpc.loginauth.demo.common.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class User implements UserDetails, Serializable {
    @Id @GeneratedValue
    private long uid;//主键.

    private String username;//用户名.

    private String password;//密码.

    //省略用户的其它信息,如手机号,邮箱等...

    //用户 - 角色关系. 多对多./
    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中获取.
    @JoinTable(name="user_role",joinColumns= {@JoinColumn(name="uid")},inverseJoinColumns= {@JoinColumn(name="role_id")})
    private List<Role> roles;

    //当前用户的角色列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
    //账号是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //账号是否未被锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //证书是否过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
