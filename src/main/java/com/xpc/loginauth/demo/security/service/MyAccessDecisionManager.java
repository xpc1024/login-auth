package com.xpc.loginauth.demo.security.service;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        if (CollectionUtils.isEmpty(collection)) {
            return;
        } else {
            String needRole;
            for (Iterator<ConfigAttribute> iter = collection.iterator(); iter.hasNext(); ) {
                needRole = iter.next().getAttribute();
                for (GrantedAuthority ga : authentication.getAuthorities()) {
                    if (needRole.contains(ga.getAuthority())) {
                        //当前请求所需角色列表包含当前登陆人的角色,允许访问
                        return;
                    }
                }
            }
            throw new AccessDeniedException("当前访问没有权限");
        }
    }
    /**
     * 表示此AccessDecisionManager是否能够处理传递的ConfigAttribute呈现的授权请求
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }
    /**
     * 表示当前AccessDecisionManager实现是否能够为指定的安全对象（方法调用或Web请求）提供访问控制决策
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
