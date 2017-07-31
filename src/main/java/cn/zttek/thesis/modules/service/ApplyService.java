package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.mapper.ApplyMapper;
import cn.zttek.thesis.modules.mapper.ThesisMapper;
import cn.zttek.thesis.modules.model.Apply;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.Thesis;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 学生选择论文题目相关业务逻辑处理
 * @作者: Pengo.Wen
 * @日期: 2016-09-21 16:25
 * @版本: v1.0
 */
@Service
public class ApplyService extends BaseService<Apply> {

    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private ThesisMapper thesisMapper;

    /**
     * 查询学生已经选择的论文题目申请
     * @param projid
     * @param studentid
     * @return
     * @throws Exception
     */
    public List<Apply> listByStudent(Long projid, Long studentid) throws Exception{
        log.info("===查询学生已经选择的论文题目申请===");
        List<Apply> list = applyMapper.listByStudent(projid, studentid);
        return list;
    }

    /**
     * 检查学生选择题目是否已达到论文工作设定的最大选题数
     * @param project
     * @param studentid
     * @return
     * @throws Exception
     */
    public boolean checkMaxAllowed(Project project, Long studentid) throws Exception{
        log.info("===检查学生选择题目是否已达到论文工作设定的最大选题数===");
        Long cnt = applyMapper.getApplyCount(project.getId(), studentid);
        if(cnt != null && cnt >= project.getGetmax()){
            return true;
        }
        return false;
    }

    /**
     * 查询教师的论文题目申请列表
     * @param page
     * @param rows
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    public PageInfo<Apply> listByTeacher(Integer page, Integer rows, Long projid, Long teacherid) throws Exception{
        log.info("===查询教师的论文题目申请列表===");
        List<Apply> list = applyMapper.listByTeacher(projid, teacherid);
        PageInfo<Apply> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 保存论文题目申请的答复信息
     * @param apply
     * @param confirm 是否确认该学生入选该题目
     * @throws Exception
     */
    public void update(Apply apply, Boolean confirm) throws Exception{
        log.info("===保存论文题目申请的答复信息===");
        if(confirm){
            //如果确认该学生入选该题目
            //1、保存学生ID到题目中
            Thesis thesis = thesisMapper.selectByPrimaryKey(apply.getThesisid());
            thesis.setStudentid(apply.getStudentid());
            thesisMapper.updateByPrimaryKey(thesis);
            //2、更新当前答复信息
            this.update(apply);
            //3、更新该题目其他申请的答复信息
            applyMapper.updateByThesis(thesis.getId(), apply.getId(), "申请未通过，该题已有同学入选！");
            //4、更新该学生其他申请的答复信息
            String msg = "已入选题目[" + thesis.getTopic() + "]，该申请无效";
            applyMapper.updateByStudent(apply.getStudentid(), apply.getId(), msg);
        }else {
            this.update(apply);
        }

    }

    /**
     * 删除某个论文题目的申请，在删除前需要检查
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteOnCheck(Long id) throws Exception{
        log.info("===删除某个论文题目的申请[" + id + "]，在删除前需要检查===");
        String msg = "";

        Apply apply = this.queryById(id);

        //校验有没有答复
//        if(StringUtils.isNotEmpty(apply.getReplyInfo())){
//            msg = "删除失败，该论文题目申请已经有答复，不能删除！";
//            return msg;
//        }
        applyMapper.deleteByPrimaryKey(id);
        msg = "论文题目申请删除成功！";
        return msg;
    }
}
