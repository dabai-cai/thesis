package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.common.utils.TimeUtils;
import cn.zttek.thesis.modules.mapper.PermissionMapper;
import cn.zttek.thesis.modules.mapper.ResourceMapper;
import cn.zttek.thesis.modules.mapper.RoleMapper;
import cn.zttek.thesis.modules.mapper.UserMapper;
import cn.zttek.thesis.modules.model.Permission;
import cn.zttek.thesis.modules.model.Resource;
import cn.zttek.thesis.modules.model.Role;
import cn.zttek.thesis.modules.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @描述: 权限管理业务逻辑处理类
 * @作者: Pengo.Wen
 * @日期: 2016-08-16 14:33
 * @版本: v1.0
 */
@Service
public class PermissionService extends BaseService<Permission> {


    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ResourceMapper resourceMapper;


    /**
     * 根据资源获得相应的权限列表
     * @return
     * @throws Exception
     */
    public List<Permission> listByRes(Long resid) throws Exception{
        log.info("===根据资源[" + resid + "]获得相应的权限列表===");
        return permissionMapper.listByRes(resid);
    }

    /**
     * 检查权限编码是否已存在
     * @param permission
     * @return
     * @throws Exception
     */
    public boolean checkKeystr(Permission permission) throws Exception{
        log.info("===检查权限编码[" + permission.getName() + "]是否已存在===");
        Permission perm = permissionMapper.getByKeystr(permission.getKeystr());
        if(perm != null && !perm.getId().equals(permission.getId())){
            return true;
        }
        return false;
    }
    /**
     * 新增权限后，为超级管理员添加此权限
     * @param record
     * @return
     * @throws Exception
     */
    @Override
    public Permission insert(Permission record) throws Exception {
        log.info("===新增权限后，为超级管理员添加此权限===");
        Permission perm =  super.insert(record);
        Long rid = userMapper.getSuperRoleId();
        roleMapper.addSuperPerm(perm.getId(), rid);
        return perm;
    }

    /**
     * 删除某个权限，在删除前需要检查
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteOnCheck(Long id) throws Exception{
        log.info("===删除某个权限[" + id + "]，在删除前需要检查===");
        String msg = "";

        Permission perm = this.queryById(id);

        Long cnt = roleMapper.countUsed(id);
        if(cnt > 0){
            msg = "删除失败, 权限[" + perm.getName() + "]已被使用，请先在角色管理中解除对该权限的使用！";
            return msg;
        }

        permissionMapper.deleteByPrimaryKey(id);
        msg = "权限[" + perm.getName() + "]删除成功！";
        return msg;
    }

    /**
     * 生成资源权限的树结构
     * @return
     * @throws Exception
     */
    public List<Resource> getPermTree()throws Exception{
        log.info("===生成资源权限的树结构===");
        List<Resource> result = new ArrayList<>();


        //权限数据预处理
        Map<Long, List<Permission>> permMap = new HashMap<>(50);
        List<Permission> perms = permissionMapper.selectAll();
        perms.forEach(p -> {
            if(!permMap.containsKey(p.getResid()))
                permMap.put(p.getResid(), new ArrayList<>());
            permMap.get(p.getResid()).add(p);
        });

        //资源数据处理
        Map<Long, Resource> resourceMap = new TreeMap<>();
        List<Resource> resources = resourceMapper.selectAll();
        resources.forEach(r -> {

            //将权限与资源关联起来
            if(permMap.containsKey(r.getId())){
                r.setPerms(permMap.get(r.getId()));
            }
            //清除缓存带来的影响
            r.getSub().clear();

            resourceMap.put(r.getId(), r);



            if(r.getPid() == null || r.getPid() == 0){
                result.add(r);
            }
        });

        //再一次循环构建资源的父子关系
        resources.forEach(r -> {
            if(r.getPid() != null  && resourceMap.containsKey(r.getPid())){
                Resource resource = resourceMap.get(r.getPid());
                resource.getSub().add(r);
            }
        });

        return result;
    }

    /**
     * 根据角色获得相应的权限列表
     * @param rid
     * @return
     * @throws Exception
     */
    public List<Permission> listByRole(Long rid) throws Exception{
        log.info("===根据角色[" + rid + "]获得相应的权限列表===");
        return roleMapper.getPerms(rid);
    }



    /**
     * 生成用户的资源权限的树结构
     * @return
     * @throws Exception
     */
    public List<Resource> getUserPermTree(User currentUser)throws Exception{
        log.info("===生成用户的资源权限的树结构===");
        List<Resource> result = new ArrayList<>();
        //权限数据预处理
        Map<Long, List<Permission>> permMap = new HashMap<>(50);
        List<Role> roles=userMapper.getRoles(currentUser.getId());//获取用户所有角色
        List<Permission> perms=new ArrayList<>();//获取用户所有权限
        for(Role role:roles){
            List<Permission> temp=permissionMapper.listByRole(role.getId());
            perms.addAll(temp);
        }

        perms.forEach(p -> {
            if(!permMap.containsKey(p.getResid()))
                permMap.put(p.getResid(), new ArrayList<>());
            permMap.get(p.getResid()).add(p);
        });


        //资源数据处理
        Map<Long, Resource> resourceMap = new TreeMap<>();
        Set<Long> rids=new HashSet<>();
        for(Permission permission:perms){//根据权限获取资源id列表
            rids.add(permission.getResid());
        }
        CopyOnWriteArrayList<Resource> resources=resourceMapper.listByids(rids);
        for(Resource resource:resources){
            getFRes(resource,resources);//寻找资源的父节点(菜单头)
        }


        resources.forEach(r -> {
            //将权限与资源关联起来
            if(permMap.containsKey(r.getId())){
                r.setPerms(permMap.get(r.getId()));
            }
            //清除缓存带来的影响
            r.getSub().clear();

            resourceMap.put(r.getId(), r);



            if(r.getPid() == null || r.getPid() == 1){
                result.add(r);
            }
        });

        //再一次循环构建资源的父子关系
        resources.forEach(r -> {
            if(r.getPid() != null  && resourceMap.containsKey(r.getPid())){
                Resource resource = resourceMap.get(r.getPid());
                resource.getSub().add(r);
            }
        });


        return result;
    }

    private void getFRes(Resource resource,List<Resource> resources){
        if(resource.getPid()!=null||resource.getPid()!=1){
            Resource father=resourceMapper.selectByPrimaryKey(resource.getPid());
            if(father!=null&&!father.getName().equals("root")){
                if(!resources.contains(father)){
                    resources.add(father);
                }
                getFRes(father,resources);
            }else{
                return;
            }

        }else{
            return;
        }
    }

}
