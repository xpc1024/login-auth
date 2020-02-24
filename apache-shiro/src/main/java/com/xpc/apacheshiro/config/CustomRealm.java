package com.xpc.apacheshiro.config;

import com.xpc.apacheshiro.entity.Permission;
import com.xpc.apacheshiro.entity.Role;
import com.xpc.apacheshiro.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    /**
     * 处理授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String name = principalCollection.getPrimaryPrincipal().toString();
        User user = getUserByName(name);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        user.getRoles().forEach(role -> {
            //添加角色
            authorizationInfo.addRole(role.getRoleName());
            //添加权限
            role.getPermission().forEach(permission -> {
                authorizationInfo.addStringPermission(permission.getPermissionName());
            });
        });
        return authorizationInfo;
    }

    /**
     * 处理认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String name = authenticationToken.getPrincipal().toString();
        User user = getUserByName(name);
        return new SimpleAuthenticationInfo(name, user.getPassword(), getName());
    }

    private User getUserByName(String name) {
        //模拟数据库查询
        Permission permission1 = new Permission(1L, "common");
        Permission permission2 = new Permission(2L, "private");
        Set<Permission> permissionSet1 = new HashSet<>();
        permissionSet1.add(permission1);
        Set<Permission> permissionSet2 = new HashSet<>();
        permissionSet2.add(permission1);
        permissionSet2.add(permission2);
        Role role1 = new Role(1L, "ordinary", permissionSet1);
        Role role2 = new Role(2L, "admin", permissionSet2);
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role1);
        Set<Role> roleSet2 = new HashSet<>();
        roleSet2.add(role1);
        roleSet2.add(role2);
        User user1 = new User(1L, "user", "123456", "abc", roleSet1);
        User user2 = new User(2L, "admin", "123456", "def", roleSet2);
        Map<String, User> map = new HashMap<>(3);
        map.put(user1.getUsername(), user1);
        map.put(user2.getUsername(), user2);
        return map.get(name);
    }
}
