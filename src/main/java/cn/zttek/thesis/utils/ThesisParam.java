package cn.zttek.thesis.utils;

import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.User;

import java.io.Serializable;

/**
 * @描述: 用于与当前用户、组织机构、论文工作等相差的数据操作进行设置
 * @作者: Pengo.Wen
 * @日期: 2016-08-30 15:08
 * @版本: v1.0
 */
public class ThesisParam implements Serializable {

    public static ThreadLocal<User> currentUser = new ThreadLocal<>();
    public static ThreadLocal<Org> currentOrg = new ThreadLocal<>();
    public static ThreadLocal<Project> currentProj = new ThreadLocal<>();


    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentUser(User currentUser) {
        ThesisParam.currentUser.set(currentUser);
    }

    public static Org getCurrentOrg(){
        return currentOrg.get();
    }

    public static void setCurrentOrg(Org currentOrg){
        ThesisParam.currentOrg.set(currentOrg);
    }

    public static Project getCurrentProj(){
        return currentProj.get();
    }

    public static void setCurrentProj(Project currentProj){
        ThesisParam.currentProj.set(currentProj);
    }
}
