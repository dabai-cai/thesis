package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.model.Permission;
import cn.zttek.thesis.modules.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询权限有无被某个角色使用
     * @param pid
     * @return
     * @throws Exception
     */
    Long countUsed(@Param("pid") Long pid) throws Exception;

    /**
     * 为超级管理员角色增加权限
     * @param pid
     * @param rid
     * @throws Exception
     */
    void addSuperPerm(@Param("pid") Long pid, @Param("rid") Long rid) throws Exception;

    /**
     * 根据角色编码查询记录
     * @param code
     * @return
     */
    Role getByCode(@Param("code") String code) throws Exception;

    /**
     * 根据角色获得其对应的权限列表
     * @param rid
     * @return
     * @throws Exception
     */
    List<Permission> getPerms(@Param("rid") Long rid) throws Exception;

    /**
     * 删除角色关联的权限
     * @param rid
     * @throws Exception
     */
    void  deleteRolePerms(@Param("rid") Long rid) throws Exception;



    /**
     * 插入角色与权限的关联关系
     * @param rid
     * @param list
     * @throws Exception
     */
    void addRolePerms(@Param("rid") Long rid, @Param("list") List<Long> list)throws Exception;
}