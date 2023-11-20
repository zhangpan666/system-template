package com.gallery.manage.common.config.shiro;

import com.gallery.manage.common.model.Permission;
import com.gallery.manage.common.model.Role;
import com.gallery.manage.common.model.SysUser;
import com.gallery.manage.common.model.SysUserRole;
import com.gallery.manage.common.service.PermissionService;
import com.gallery.manage.common.service.RoleService;
import com.gallery.manage.common.service.SysUserRoleService;
import com.gallery.manage.common.service.SysUserService;
import com.gallery.manage.common.util.MenuUtil;
import com.gallery.manage.common.util.SystemUtil;
import com.gallery.manage.common.constants.ProjectConstant;
import com.light.shiro.config.ByteSourceUtils;
import com.light.shiro.config.RedisCache;
import com.light.shiro.util.ShiroUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    RoleService roleService;

    @Autowired
    RedisCache<String, AuthorizationInfo> redisCache;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String username = principalCollection.getPrimaryPrincipal().toString();
        if (StringUtils.isEmpty(username)) {
            return simpleAuthorizationInfo;
        }
        SysUser sysUser = sysUserService.getUserByUsernameAndAccountType(username, ProjectConstant.UserType.ADMIN.getKey());
        if (sysUser == null) {
            return simpleAuthorizationInfo;
        }
        if (SystemUtil.isAdmin(username)) {
            return getAdminAuthorization(simpleAuthorizationInfo);
        } else {
            Long userId = sysUser.getId();
            SysUserRole sysUserRole = sysUserRoleService.getUserRoleDetailByUserId(userId);
            if (sysUserRole == null) {
                return simpleAuthorizationInfo;
            }
            Long roleId = sysUserRole.getRoleId();
            String roleCode = sysUserRole.getRoleCode();
            if (SystemUtil.isAdminRole(roleCode)) {
                return getAdminAuthorization(simpleAuthorizationInfo);
            }
            simpleAuthorizationInfo.addRole(roleCode);
            List<Permission> permissionList = permissionService.getPermissionListByRoleId(roleId);
            if (CollectionUtils.isEmpty(permissionList)) {
                return simpleAuthorizationInfo;
            }
            Collection<String> stringPermissions = new LinkedHashSet<>();
            permissionList.forEach(permission -> {
                String permissions = permission.getPermission();
                if (!StringUtils.isEmpty(permissions)) {
                    String[] permissionsArray = permissions.split(",");
                    if (permissionsArray.length > 0) {
                        stringPermissions.addAll(Arrays.asList(permissionsArray));
                    }
                }
            });
            simpleAuthorizationInfo.addStringPermissions(stringPermissions);
            return simpleAuthorizationInfo;
        }
    }

    private AuthorizationInfo getAdminAuthorization(SimpleAuthorizationInfo simpleAuthorizationInfo) {
        List<Role> roleList = roleService.list();
        if (CollectionUtils.isEmpty(roleList)) {
            simpleAuthorizationInfo.addRole(SystemUtil.getAdminRole());
        }
        List<String> roles = roleList.stream().map(role -> role.getRoleCode()).collect(Collectors.toList());
        simpleAuthorizationInfo.addRoles(roles);
        Collection<String> allPermissions = MenuUtil.getAllPermissions();
        if (!CollectionUtils.isEmpty(allPermissions)) {
            simpleAuthorizationInfo.addStringPermissions(allPermissions);
        }
        return simpleAuthorizationInfo;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return null;
        }
        SysUser sysUser = sysUserService.getAdminUserByUsername(username);
        if (sysUser == null) {
            return null;
        }
        ByteSource credentialsSalt = ByteSourceUtils.bytes(ShiroUtil.getCredentialsSalt(username));
        SimpleAuthenticationInfo simpleAuthorizationInfo = new SimpleAuthenticationInfo(username, sysUser.getPassword(), credentialsSalt, getName());
        return simpleAuthorizationInfo;
    }


    public boolean hasPermission(String username, String stringPermission) {
        if (StringUtils.isEmpty(username)) {
            return false;
        }
        if (SystemUtil.isAdmin(username)) {
            return true;
        }
        PrincipalCollection principals = new SimplePrincipalCollection(username, getName());
        return this.isPermitted(principals, stringPermission);
    }


    public AuthorizationInfo getCustomAuthorizationInfo(String username) {
        AuthorizationInfo authorizationInfo = redisCache.get(username);
        if (authorizationInfo == null) {
            PrincipalCollection principals = new SimplePrincipalCollection(username, getName());
            authorizationInfo = this.doGetAuthorizationInfo(principals);
            if (authorizationInfo != null) {
                redisCache.put(username, authorizationInfo);
            }
        }
        return authorizationInfo;
    }

    public boolean customHasPermission(String username, String stringPermission) {
        if (StringUtils.isEmpty(username)) {
            return false;
        }
        if (SystemUtil.isAdmin(username)) {
            return true;
        }
        AuthorizationInfo authorizationInfo = this.getCustomAuthorizationInfo(username);
        if (authorizationInfo == null) {
            return false;
        }
        Collection<String> stringPermissions = authorizationInfo.getStringPermissions();
        return !CollectionUtils.isEmpty(stringPermissions)&&stringPermissions.contains(stringPermission);
    }

}

















