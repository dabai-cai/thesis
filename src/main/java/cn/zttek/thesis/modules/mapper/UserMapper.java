package cn.zttek.thesis.modules.mapper;


import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.expand.GuideStudent;
import cn.zttek.thesis.modules.model.Role;
import cn.zttek.thesis.modules.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     *  查询用户信息
     * @param keywords
     * @param type
     * @param orgid
     * @return
     * @throws Exception
     */
    List<User> search(@Param("keywords") String keywords, @Param("type") UserType type, @Param("orgid")Long orgid) throws Exception;


    Long getSuperRoleId() throws Exception;

    /**
     * 查询角色有无被某个用户使用
     * @param rid
     * @return
     * @throws Exception
     */
    Long countUsed(@Param("rid") Long rid) throws Exception;

    /**
     * 根据用户账号查询用户基本信息
     * @param account
     * @return
     */
    User getByAccount(@Param("account") String account) throws Exception;

    /**
     * 根据用户id获取分配的角色
     * @param uid
     * @return
     */
    List<Role> getRoles(@Param("uid") Long uid) throws Exception;
    /**
     * 删除用户关联的角色
     * @param uid
     * @throws Exception
     */
    void deleteUserRoles(@Param("uid")Long uid) throws Exception;

    /**
     * 插入用户与角色的关联关系
     * @param uid
     * @param rids
     * @throws Exception
     */
    void addUserRoles(@Param("uid")Long uid, @Param("list")List<Long> rids) throws Exception;

    /**
     * 多条件查询用户详细信息
     * @param keywords 查询关键词
     * @param type 用户类型
     * @param orgid 组织机构id
     * @param projid 论文工作id
     * @param major 专业
     * @param grade 年级
     * @param clazz 班级
     * @param selected 是否已被选择
     * @return
     * @throws Exception
     */
    List<User> searchDetail(@Param("keywords") String keywords, @Param("type")UserType type,
                            @Param("orgid")Long orgid, @Param("projid") Long projid,
                            @Param("major") String major, @Param("grade") Integer grade,
                            @Param("clazz") String clazz, @Param("selected")Boolean selected) throws Exception;

    /**
     * 插入用户与论文工作的关联关系
     * @param projid
     * @param uids
     * @param type
     * @throws Exception
     */
    void addProjectUsers(@Param("projid")Long projid, @Param("uids")List<Long> uids, @Param("type")UserType type) throws Exception;

    /**
     * 删除用户与论文工作的关联关系
     * @param projid
     * @param uids
     * @param type
     */
    void deleteProjectUsers(@Param("projid")Long projid, @Param("uids")List<Long> uids, @Param("type")String type) throws Exception;

    /**
     * 根据学号在当前论文工作中查询学生
     * @param projid
     * @param stuno
     */
    User getByProjectAndAccount(@Param("projid") Long projid, @Param("stuno") String stuno) throws Exception;




    /**
     * 查询指导老师指导学生的列表
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    List<GuideStudent> listStudentByGuide(@Param("projid")Long projid, @Param("teacherid") Long teacherid) throws Exception;

}