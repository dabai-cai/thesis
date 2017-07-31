package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.holder.UserHolder;
import cn.zttek.thesis.modules.mapper.UserInfoMapper;
import cn.zttek.thesis.modules.mapper.UserMapper;
import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.model.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import gnu.trove.map.hash.TLongObjectHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @描述: 用户管理业务处理类
 * @作者: Pengo.Wen
 * @日期: 2016-08-11 17:16
 * @版本: v1.0
 */
@Service
public class UserService extends BaseService<User> {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserHolder userHolder;

    /**
     * 分页查询用户信息
     * @param page
     * @param rows
     * @param keywords
     * @param type
     * @return
     * @throws Exception
     */
    public PageInfo<User> listByPage(int page, int rows, String keywords, UserType type) throws Exception{
        PageHelper.startPage(page, rows);
        List<User> list = userMapper.search(keywords, type, null);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 分页查询用户详细信息
     * @param page
     * @param rows
     * @param keywords
     * @param type
     * @return
     * @throws Exception
     */
    public PageInfo<User> listDetailByPage(int page, int rows, String keywords, UserType type, Long orgid) throws Exception{
        PageHelper.startPage(page, rows);
        List<User> list = userMapper.search(keywords, type, orgid);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        for (User user : pageInfo.getList()){
            UserInfo info = userInfoMapper.queryByUid(user.getId());
            if(info == null){
                info = new UserInfo();
            }
            user.setInfo(info);
        }
        return pageInfo;
    }


    /**
     * 获得超级管理员所属的角色id
     * @return
     * @throws Exception
     */
    public Long getSuperRoleId()throws Exception{
        return userMapper.getSuperRoleId();
    }
    
    /**
     * 检查用户账号是否已存在
     * @param user
     * @return
     * @throws Exception
     */
    public boolean checkAccount(User user) throws Exception{
        log.info("===检查用户账号[" + user.getAccount() + "]是否已存在===");
        User temp = userMapper.getByAccount(user.getAccount());
        if(temp != null && !temp.getId().equals(user.getId())){
            return true;
        }
        return false;
    }

    /**
     * 删除某个用户，在删除前需要检查
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteOnCheck(Long id) throws Exception{
        log.info("===删除某个用户[" + id + "]，在删除前需要检查===");
        String msg = "";

        User user = this.queryById(id);

        //TODO 使用具体业务进行校验用户能否被删除
        /*Long cnt = userMapper.countUsed(id);
        if(cnt > 0){
            msg = "删除失败, 用户[" + user.getName() + "]已被使用，请先在用户中解除对该用户的使用！";
            return msg;
        }*/
        userMapper.deleteUserRoles(id);
        userMapper.deleteByPrimaryKey(id);
        msg = "用户[" + user.getUsername() + "]删除成功！";
        return msg;
    }

    /**
     * 更新用户的角色关联
     * @param uid
     * @param rids
     */
    public void updateRoles(Long uid, List<Long> rids) throws Exception{
        log.info("===更新用户的角色关联===");
        //先删除用户之前的角色关联
        userMapper.deleteUserRoles(uid);
        //后插入所有分配的角色
        userMapper.addUserRoles(uid, rids);
    }

    /**
     * 根据用户id获得用户详细信息
     * @param id
     * @return
     * @throws Exception
     */
    public User getDetail(Long id) throws Exception{
        log.info("===根据用户id[" + id + "]获得用户详细信息===");
        User user = userMapper.selectByPrimaryKey(id);
        UserInfo info = userInfoMapper.queryByUid(user.getId());
        user.setInfo(info);
        return user;
    }

    /**
     * 根据账号获得用户详细信息
     * @param account
     * @return
     * @throws Exception
     */
    public User getByAccount(String account) throws Exception{
        log.info("===根据账号[" + account + "]获得用户详细信息===");
        User user = userMapper.getByAccount(account);
        if(user != null){
            UserInfo info = userInfoMapper.queryByUid(user.getId());
            user.setInfo(info);
        }
        return user;
    }

    /**
     * 更新用户详细信息、角色设置
     * @param user
     * @param ridArry
     * @throws Exception
     */
    public void updateUser(User user, Long[] ridArry) throws Exception{
        log.info("===更新用户详细信息、角色设置===");
        this.update(user);
        UserInfo info = user.getInfo();
        info.setUid(user.getId());
        info.setOrgid(user.getOrgid());
        info.setValid(true);
        if(info.getId() != null && info.getId() > 0){
            userInfoMapper.updateByPrimaryKey(info);
        }else{
            userInfoMapper.insert(info);
        }
        //最后更新角色信息
        if(ridArry != null && ridArry.length > 0) {
            this.updateRoles(user.getId(), Arrays.asList(ridArry));
        }
        userHolder.updateUser(user);
    }

    /**
     * 保存用户详细信息、角色设置
     * @param user
     * @param ridArry
     * @throws Exception
     */
    public void saveUser(User user, Long[] ridArry) throws Exception{
        log.info("===保存用户详细信息、角色设置===");
        this.insert(user);
        UserInfo info = user.getInfo();
        info.setUid(user.getId());
        info.setOrgid(user.getOrgid());
        info.setValid(true);
        userInfoMapper.insert(info);
        //最后更新角色信息
        this.updateRoles(user.getId(), Arrays.asList(ridArry));
    }


    /**
     * 批量添加用户信息
     * @param users
     * @param ridArry
     * @return
     * @throws Exception
     */
    public List<String> batchAddUser(List<User> users, Long[] ridArry) throws Exception{
        log.info("===批量添加用户信息===");
        List<String> msgs = new ArrayList<>();

        for (User user : users){
            User temp = userMapper.getByAccount(user.getAccount());
            if(temp != null && !temp.getId().equals(user.getId())){
                msgs.add("系统已存在用户账号[" + user.getAccount() + "],添加被忽略！");
            }else{
                this.saveUser(user, ridArry);
                msgs.add("用户账号[" + user.getAccount() + "],添加成功！");
            }
        }

        return msgs;
    }


    /**
     * 根据组织机构查询所有专业
     * @param orgid
     * @param projid 如果不为null，则只查询论文工作下的学生专业
     * @return
     * @throws Exception
     */
    public List<String> getMajors(Long orgid, Long projid) throws Exception{
        log.info("===根据组织机构查询所有专业===");
        return userInfoMapper.getMajors(orgid, projid);
    }

    /**
     * 根据组织机构和专业查询所有年级
     * @param orgid
     * @param projid 如果不为null，则只查询论文工作下的学生专业
     * @param major
     * @return
     * @throws Exception
     */
    public List<Integer> getGrade(Long orgid, Long projid, String major) throws Exception{
        log.info("===根据组织机构和专业查询所有年级===");
        return userInfoMapper.getGrade(orgid, projid, major);
    }

    /**
     * 根据组织机构、专业和年级查询所有年级
     * @param orgid
     * @param projid 如果不为null，则只查询论文工作下的学生专业
     * @param major
     * @param grade
     * @return
     * @throws Exception
     */
    public List<String> getClazz(Long orgid, Long projid, String major, Integer grade) throws Exception{
        log.info("===根据组织机构、专业和年级查询所有年级===");
        return userInfoMapper.getClazz(orgid, projid, major, grade);
    }

    /**
     * 分页多条件查询论文工作中的教师或学生的详细信息
     * @param page
     * @param rows
     * @param org
     * @param project
     * @param keywords
     * @param type
     * @param args
     * @param selected
     * @return
     * @throws Exception
     */
    public PageInfo<User> listByPage(Integer page, Integer rows, Org org, Project project, String keywords, UserType type, List<String> args, Boolean selected) throws Exception{
        log.info("===分页多条件查询论文工作中的教师或学生的详细信息===");
        PageHelper.startPage(page, rows);
        List<User> list = this.listUsers(org, project, keywords, type, args, selected);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 多条件查询用户详细信息的通用方法
     * @param org
     * @param project
     * @param keywords
     * @param type
     * @param args
     * @param selected
     * @return
     * @throws Exception
     */
    private List<User> listUsers(Org org, Project project, String keywords, UserType type, List<String> args, Boolean selected)throws Exception{
        List<User> list;
        if(type == UserType.TEACHER){
            list = userMapper.searchDetail(keywords, type, org.getId(), project.getId(), null, null, null, selected);
        }else{
            String major = args.get(0);
            Integer grade = args.get(1) == null ? null : Integer.parseInt(args.get(1));
            String clazz = args.get(2);
            list = userMapper.searchDetail(keywords, type, org.getId(), project.getId(), major, grade, clazz, selected);
        }
        return list;
    }

    /**
     * 保存被选择用户与论文工作的关联关系
     * @param org
     * @param project
     * @param type
     * @param uids
     * @return
     * @throws Exception
     */
    public String saveSelect(Org org, Project project, UserType type, List<Long> uids) throws Exception{
        log.info("===保存被选择用户与论文工作的关联关系===");
        //即将要加入到论文工作的用户
        List<Long> uids2 = new ArrayList<>();
        //已经加入到论文工作的用户
        List<User> selectedUsers = listUsers(org, project, null, type, Arrays.asList(null, null, null), true);
        Map<Long, User> userMap = new HashMap<>(100);
        selectedUsers.forEach(user -> userMap.put(user.getId(), user));
        int cnt = 0;
        for (Long uid : uids){
            if(userMap.containsKey(uid)){
                cnt ++;
            }else{
                uids2.add(uid);
            }
        }
        userMapper.addProjectUsers(project.getId(), uids2, type);
        userHolder.addUsers(project.getId(), selectedUsers);
        String msg = String.format("您选择的%s一共有%d人：<br>1、其中%d人已经参与到该论文工作中；<br>2、一共有%d人成功添加到该论文工作中。",
                type.getLabel(), uids.size(), cnt, uids2.size());

        return msg;
    }

    /**
     * 保存符合条件的用户与论文工作的关联关系
     * @param org
     * @param project
     * @param keywords
     * @param type
     * @param args
     * @param selected
     * @return
     * @throws Exception
     */
    public String saveQuery(Org org, Project project, String keywords, UserType type, List<String> args, Boolean selected) throws Exception{
        log.info("===保存符合条件的用户与论文工作的关联关系===");
        //准备要加入到论文工作的用户
        List<User> list = listUsers(org, project, keywords, type, args, selected);
        List<Long> uids = new ArrayList<>();
        //已经加入到论文工作的用户
        List<User> selectedUsers = listUsers(org, project, null, type, Arrays.asList(null, null, null), true);
        Map<Long, User> userMap = new HashMap<>(100);
        selectedUsers.forEach(user -> userMap.put(user.getId(), user));
        int cnt = 0;
        for (User user : list){
            if(userMap.containsKey(user.getId())){
                cnt ++;
            }else{
                uids.add(user.getId());
            }
        }
        //为防止一次添加关联数据太多，此处进行分段保存
        int total = uids.size();
        int count = total / 20 ;
        for (int i = 0; i < count; i++) {
            List<Long> tempList = uids.subList(i*20, (i+1)*20);
            userMapper.addProjectUsers(project.getId(), tempList, type);
        }
        if(total % 20 > 0){
            List<Long> tempList = uids.subList(count * 20, total);
            userMapper.addProjectUsers(project.getId(), tempList, type);
        }
        //userMapper.addProjectUsers(project.getId(), uids, type);
        userHolder.addUsers(project.getId(), selectedUsers);
        String msg = String.format("您选择的%s一共有%d人：<br>1、其中%d人已经参与到该论文工作中；<br>2、一共有%d人成功添加到该论文工作中。",
                type.getLabel(), list.size(), cnt, uids.size());

        return msg;
    }

    /**
     * 删除用户与论文工作的关联
     * @param project
     * @param uids
     */
    public void deleteProjectUsers(Project project, List<Long> uids, UserType type) throws Exception{
        log.info("===删除用户与论文工作的关联===");
        userMapper.deleteProjectUsers(project.getId(), uids, type.name().toLowerCase());
        userHolder.deleteUsers(project.getId());
    }


    public TLongObjectHashMap<User> getProjectUsers(Org org, Project project) throws Exception{
        if(userHolder.puserMap.containsKey(project.getId())){
            if(!userHolder.puserMap.get(project.getId()).isEmpty()){
                return userHolder.puserMap.get(project.getId());
            }
        }
        List<User> teachers = this.listUsers(org, project, null, UserType.TEACHER, null, true);
        List<User> students = this.listUsers(org, project, null, UserType.STUDENT, new ArrayList<>(3), true);
        userHolder.addUsers(project.getId(), teachers);
        userHolder.addUsers(project.getId(), students);
        return userHolder.puserMap.get(project.getId());
    }

    /**
     * 根据学号在当前论文工作中查询学生
     * @param projid
     * @param stuno
     * @return
     */
    public User getByAccount(Long projid, String stuno) throws Exception{
        log.info("===根据学号[" + stuno + "]在当前论文工作[" + projid + "]中查询学生===");
        return userMapper.getByProjectAndAccount(projid, stuno);
    }
}
