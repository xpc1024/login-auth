package com.xpc.loginauth.demo.common.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Role implements GrantedAuthority, Serializable {
    @Id
    @GeneratedValue
    private long rid;

    private String name;//角色名.

    private String descprtion;//角色描述.

    @Override
    public String getAuthority() {
        return name;
    }
}
