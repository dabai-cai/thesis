package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.mapper.ProjectMapper;
import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.Project;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @描述: 论文工作相关业务处理类
 * @作者: Pengo.Wen
 * @日期: 2016-09-5 16:08
 * @版本: v1.0
 */
@Service
public class ProjectService extends BaseService<Project> {

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 分页查询论文工作信息
     * @param page
     * @param rows
     * @param org
     * @return
     * @throws Exception
     */
    public PageInfo<Project> listByPage(int page, int rows, Org org) throws Exception{
        PageHelper.startPage(page, rows);
        List<Project> list = projectMapper.listByOrg(org.getId());
        PageInfo<Project> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<Project> listByOrg(Long orgid) throws Exception{
        return projectMapper.listByOrg(orgid);
    }

    /**
     * 检查机构名称是否已存在
     * @param project
     * @return
     * @throws Exception
     */
    public boolean checkTitle(Project project)throws Exception{
        log.info("===检查机构名称[" + project.getTitle() + "]是否已存在===");
        Project temp = projectMapper.getByTitleAndYear(project.getTitle(), project.getYear());
        if(temp != null && !temp.getId().equals(project.getId())){
            return true;
        }
        return false;
    }

    /**
     * 检查组织机构下的活跃论文工作数量是否已达到限制
     * @param org
     * @return
     * @throws Exception
     */
    public boolean checkActiveCount(Org org) throws Exception{
        log.info("===检查组织机构下的活跃论文工作数量是否已达到限制===");
        Long cnt = projectMapper.getActiveCount(org.getId());
        if(cnt != null && cnt >= org.getProjlimit()){
            return true;
        }
        return false;
    }

    /**
     * 保存组织架构信息，并建立文件保存目录
     * @param project 需要保存的论文工作实体
     * @param docRoot 文件保存根目录
     * @return
     * @throws Exception
     */
    public Project insert(Project project, Org org, String docRoot) throws Exception {
        log.info("===保存组织架构信息，并建立文件保存目录===");
        String docDir = org.getDocroot() + "/" + project.getYear();
        project.setOrgid(org.getId());
        super.insert(project);
        File file = new File(docRoot, docDir);
        if(!file.exists()){
            file.mkdirs();
        }
        return project;
    }

    /**
     * 删除某个论文工作，在删除前需要检查
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteOnCheck(Long id) throws Exception{
        log.info("===删除某个论文工作[" + id + "]，在删除前需要检查===");
        String msg = "";

        Project project = this.queryById(id);

        //TODO 使用具体业务进行校验论文工作能否被删除
        /*Long cnt = projectMapper.countUsed(id);
        if(cnt > 0){
            msg = "删除失败, 论文工作[" + project.getName() + "]已被使用，请先在论文工作中解除对该论文工作的使用！";
            return msg;
        }*/
        //TODO 待定删除方法
        projectMapper.deleteByPrimaryKey(id);
        msg = "论文工作[" + project.getTitle() + "]删除成功！";
        return msg;
    }

    /**
     * 加载用户所参与的论文工作
     * @param orgid
     * @param uid
     * @param type {teacher, student}
     * @return
     * @throws Exception
     */
    public List<Project> listByUser(Long orgid, Long uid, String type)throws Exception{
        log.info("===加载用户所参与的论文工作===");
        return projectMapper.listByUser(orgid, uid, type);
    }
}
