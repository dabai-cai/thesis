package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.mapper.RoleMapper;
import cn.zttek.thesis.modules.mapper.UserMapper;
import cn.zttek.thesis.modules.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 角色业务处理类
 * @作者: Pengo.Wen
 * @日期: 2016-08-11 16:15
 * @版本: v1.0
 */
@Service("roleService")
public class RoleService extends BaseService<Role> {


    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    /**
     * 检查角色编码是否已存在
     * @param role
     * @return
     * @throws Exception
     */
    public boolean checkCode(Role role) throws Exception{
        log.info("===检查角色编码[" + role.getCode() + "]是否已存在===");

        Role temp = roleMapper.getByCode(role.getCode());

        if(temp != null && !temp.getId().equals(role.getId())){
            return true;
        }
        return false;
    }

    /**
     * 删除某个角色，在删除前需要检查
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteOnCheck(Long id) throws Exception{
        log.info("===删除某个角色[" + id + "]，在删除前需要检查===");
        String msg = "";

        Role role = this.queryById(id);

        Long cnt = userMapper.countUsed(id);
        if(cnt > 0){
            msg = "删除失败, 角色[" + role.getName() + "]已被使用，请先在用户中解除对该角色的使用！";
            return msg;
        }

        roleMapper.deleteByPrimaryKey(id);
        msg = "角色[" + role.getName() + "]删除成功！";
        return msg;
    }

    /**
     * 根据用户获得相应的角色列表
     * @param uid
     * @return
     * @throws Exception
     */
    public List<Role> listByUser(Long uid) throws Exception{
        log.info("===根据角色[" + uid + "]获得相应的角色列表===");
        return userMapper.getRoles(uid);
    }

    /**
     * 更新角色的权限
     * @param rid
     * @param pids
     * @throws Exception
     */
    public void updatePerms(Long rid, List<Long> pids) throws Exception{
        log.info("===更新角色的权限===");
        //先删除角色之前关联的权限
        roleMapper.deleteRolePerms(rid);
        //后插入所有授予的权限
        roleMapper.addRolePerms(rid, pids);
    }

}
