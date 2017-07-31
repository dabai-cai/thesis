package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.mapper.PermissionMapper;
import cn.zttek.thesis.modules.mapper.ResourceMapper;
import cn.zttek.thesis.modules.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @描述: 资源管理业务逻辑处理类
 * @作者: Pengo.Wen
 * @日期: 2016-08-12 14:19
 * @版本: v1.0
 */
@Service
public class ResourceService extends BaseService<Resource> {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 检查资源名称是否已存在
     * @param id
     * @param name
     * @return 如果已存在，返回true，否则返回false
     * @throws Exception
     */
    public boolean checkName(Long id, String name) throws Exception{
        log.info("===检查资源名称[" + name + "]是否已存在===");
        Resource resource = resourceMapper.getByName(name);
        if(resource != null && !resource.getId().equals(id)){
            return true;
        }
        return false;
    }

    /**
     * 删除某个资源，在删除前需要检查
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteOnCheck(Long id) throws Exception{
        log.info("===删除某个资源[" + id + "]，在删除前需要检查===");
        String msg = "";

        Resource resource = this.queryById(id);
        Long cnt = resourceMapper.countChildren(id);
        if(cnt > 0){
            msg = "删除失败, 资源[" + resource.getName() + "]拥有子资源，不能被删除！";
            return msg;
        }

        Long pcnt = permissionMapper.countPerm(id);
        if(pcnt > 0){
            msg = "删除失败, 资源[" + resource.getName() + "]已被权限引用，不能被删除！";
            return msg;
        }

        resourceMapper.deleteByPrimaryKey(id);
        msg = "资源[" + resource.getName() + "]删除成功！";
        return msg;
    }
}
