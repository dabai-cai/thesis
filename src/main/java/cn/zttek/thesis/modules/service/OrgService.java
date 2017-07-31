package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.mapper.OrgMapper;
import cn.zttek.thesis.modules.model.Org;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @描述: 组织机构相关业务处理类
 * @作者: Pengo.Wen
 * @日期: 2016-08-24 16:44
 * @版本: v1.0
 */
@Service
public class OrgService extends BaseService<Org> {

    @Autowired
    private OrgMapper orgMapper;

    /**
     * 分页查询组织机构信息
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    public PageInfo<Org> listByPage(int page, int rows) throws Exception{
        PageHelper.startPage(page, rows);
        List<Org> list = orgMapper.selectAll();
        PageInfo<Org> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 检查机构名称是否已存在
     * @param org
     * @return
     * @throws Exception
     */
    public boolean checkName(Org org)throws Exception{
        log.info("===检查机构名称[" + org.getName() + "]是否已存在===");
        Org temp = orgMapper.getByName(org.getName());
        if(temp != null && !temp.getId().equals(org.getId())){
            return true;
        }
        return false;
    }

    /**
     * 保存组织架构信息，并建立文件保存目录
     * @param org 需要保存的组织机构实体
     * @param docRoot 文件保存根目录
     * @return
     * @throws Exception
     */
    public Org insert(Org org, String docRoot) throws Exception {
        log.info("===保存组织架构信息，并建立文件保存目录===");
        org.setDocroot(org.getName());
        super.insert(org);
        File file = new File(docRoot, org.getDocroot());
        if(!file.exists()){
            file.mkdirs();
        }
        return org;
    }

    /**
     * 删除某个组织机构，在删除前需要检查
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteOnCheck(Long id) throws Exception{
        log.info("===删除某个组织机构[" + id + "]，在删除前需要检查===");
        String msg = "";

        Org org = this.queryById(id);

        //TODO 使用具体业务(论文工作)进行校验组织机构能否被删除
        /*Long cnt = orgMapper.countUsed(id);
        if(cnt > 0){
            msg = "删除失败, 组织机构[" + org.getName() + "]已被使用，请先在组织机构中解除对该组织机构的使用！";
            return msg;
        }*/
        //TODO 待定删除方法
        orgMapper.deleteByPrimaryKey(id);
        msg = "组织机构[" + org.getName() + "]删除成功！";
        return msg;
    }
}
