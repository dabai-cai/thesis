package cn.zttek.thesis.shiro;

import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.shiro.realm.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 * Created by ztcms on 2015/10/19.
 */
public class ShiroUtils {
    private static MyRealm myRealm =
            (MyRealm) ((RealmSecurityManager) SecurityUtils.getSecurityManager()).getRealms().iterator().next();

    /**
     * 清除当前用户的账号密码缓存
     */
    public static void clearCurrentUserCachedAuthenticationInfo() {
        myRealm.clearCachedAuthenticationInfo(SecurityUtils.getSubject().getPrincipals());
    }

    /**
     * 清除当前用户的角色权限缓存
     */
    public static void clearCurrentUserCachedAuthorizationInfo() {
        myRealm.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

    /**
     * 清除当前用户的所有缓存
     */
    public static void clearCurrentUserCache() {
        myRealm.clearCache(SecurityUtils.getSubject().getPrincipals());
    }

    /**
     * 清除所有用户的账号密码缓存
     */
    public static void clearAllCachedAuthenticationInfo() {
        myRealm.clearAllCachedAuthenticationInfo();
    }

    /**
     * 清除所有用户的角色权限缓存
     */
    public static void clearAllCachedAuthorizationInfo() {
        myRealm.clearAllCachedAuthorizationInfo();
    }

    /**
     * 清除所有用户缓存
     */
    public static void clearAllCache() {
        myRealm.clearAllCache();
    }

    /**
     * 清除特定用户的账号密码缓存
     */
    public static void clearTheUserCachedAuthenticationInfo(User user) {
        myRealm.clearCachedAuthenticationInfo(
                new SimplePrincipalCollection(user.getAccount(), myRealm.getName()));
    }

    /**
     * 清除特定用户的角色权限缓存
     *
     * @param user
     */
    public static void clearTheUserAuthorizationInfo(User user) {
        myRealm.clearCachedAuthorizationInfo(
                new SimplePrincipalCollection(user.getAccount(), myRealm.getName()));
    }
}
