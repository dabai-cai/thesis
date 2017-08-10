package cn.zttek.thesis.shiro.realm;

import cn.zttek.thesis.modules.mapper.PermissionMapper;
import cn.zttek.thesis.modules.mapper.ResourceMapper;
import cn.zttek.thesis.modules.mapper.RoleMapper;
import cn.zttek.thesis.modules.mapper.UserMapper;
import cn.zttek.thesis.modules.model.Permission;
import cn.zttek.thesis.modules.model.Role;
import cn.zttek.thesis.modules.model.User;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by ztcms on 2015/10/15.
 */
@Service("myRealm")
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     *  userRealm.cachingEnabled：启用缓存，默认false；
     *  userRealm.authenticationCachingEnabled：启用身份验证缓存，即缓存AuthenticationInfo信息，默认false；
     *  userRealm.authenticationCacheName：缓存AuthenticationInfo信息的缓存名称；
     *  userRealm. authorizationCachingEnabled：启用授权缓存，即缓存AuthorizationInfo信息，默认false；
     *  userRealm. authorizationCacheName：缓存AuthorizationInfo信息的缓存名称；
     */
    public MyRealm() {
        this.setCachingEnabled(true);
        this.setAuthenticationCachingEnabled(true);
        this.setAuthenticationCacheName("authenticationCache");
        this.setAuthorizationCachingEnabled(true);
        this.setAuthorizationCacheName("authorizationCache");
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //查出是否有此用户
        User user;
        try {
            user = userMapper.getByAccount(token.getUsername());
        } catch (Exception e) {
            user = null;
        }
        if (user == null) {
//            错误的帐号，没有这个账号
            throw new UnknownAccountException("账号或者密码错误");
        }
        if (!user.getPassword().equals(new String(token.getPassword()))) {
            throw new IncorrectCredentialsException("账号或者密码错误");
        }

        if (user != null) {
            //若存在，将此用户存放到登录认证info中
            return new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), getName());
        }
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取登录时输入的用户名
        String loginName = (String) principals.fromRealm(getName()).iterator().next();
        if (StringUtils.isBlank(loginName)) {
            return null;
        }
        User user;

        try {
            user = userMapper.getByAccount(loginName);
        } catch (Exception e) {
            user = null;
        }
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            List<Role> roles = null;
            try {
                roles = userMapper.getRoles(user.getId());
            } catch (Exception e) {
                roles = null;
            }
            if (roles != null) {
                roles.forEach(role -> info.addRole(role.getCode()));
            }
            List<Permission> permissionList = new ArrayList<>(50);
            try {
                for (Role role : roles){
                    permissionList.addAll(roleMapper.getPerms(role.getId()));
                }
            } catch (Exception e) {
                permissionList = null;
            }
            if (permissionList != null) {
                permissionList.forEach(permission -> {
                    if (StringUtils.isNotBlank(permission.getKeystr()))
                        info.addStringPermission(permission.getKeystr());
                });
            }
            return info;
        }
        return null;
    }

    /**
     * 清除某个用户的账号密码缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    /**
     * 清除某个用户的角色权限缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除某个用户的所有缓存
     *
     * @param principals
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 清除所有用户的账号密码缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 清除所有用户的角色权限缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 清除所有用户缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
