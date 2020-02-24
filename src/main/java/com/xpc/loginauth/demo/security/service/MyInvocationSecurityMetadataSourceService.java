package com.xpc.loginauth.demo.security.service;

import com.xpc.loginauth.demo.common.entity.Permission;
import com.xpc.loginauth.demo.common.repository.PermissionRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    @Resource
    private PermissionRepository permissionRepository;
    /**
     * 每一个资源所需要的角色 ,Collection<ConfigAttribute>决策器会用到,用Map作缓存,避免每次请求都去查库
     */
    private static HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 获取决策器DecisionManager所需要的当前请求对应的role
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (null == map) {
            loadResourceDefine();
        }
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();

        for (Iterator<String> it = map.keySet().iterator(); it.hasNext(); ) {
            String url = it.next();
            if (new AntPathRequestMatcher(url).matches(request)) {
                //这里返回的就是当前请求的url所需要的roleNameList
                return map.get(url);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 将permission表中的url对应的权限通过role_permission表与role关联并存入map
     */
    private void loadResourceDefine() {
        map = new HashMap<>(16);
        List<Permission> permissions = permissionRepository.findAll();
        for (Permission permission : permissions) {
            String url = permission.getUrl();
            StringBuilder sb = new StringBuilder();
            permission.getRoles().forEach(r->{
                sb.append(r.getName());
            });
            String name = sb.toString();
            ConfigAttribute configAttribute = new SecurityConfig(name);
            if (map.containsKey(url)) {
                map.get(url).add(configAttribute);
            } else {
                List<ConfigAttribute> list = new ArrayList<>();
                list.add(configAttribute);
                map.put(url, list);
            }
        }
    }
}
