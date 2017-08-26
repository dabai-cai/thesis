package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.expand.ThesisCountExpand;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.mapper.*;
import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.Thesis;
import cn.zttek.thesis.modules.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述: 论文题目相关业务逻辑处理类
 * @作者: Pengo.Wen
 * @日期: 2016-09-14 11:01
 * @版本: v1.0
 */
@Service("thesisService")
public class ThesisService extends BaseService<Thesis>{

    @Autowired
    private ThesisMapper thesisMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TaskbookMapper taskbookMapper;
    @Autowired
    private MidcheckMapper midcheckMapper;
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private UserService userService;

    /**
     * 分页查询教师的论文题目
     * @param page
     * @param rows
     * @param orgid
     * @param projid
     * @param teacherid
     * @param keywords
     * @param isCurrentProj
     * @return
     * @throws Exception
     */
    public PageInfo<Thesis> listByPage(Integer page, Integer rows, Long orgid, Long projid, Long teacherid, String keywords, boolean isCurrentProj) throws Exception{
        log.info("===分页查询教师的论文题目===");
        PageHelper.startPage(page, rows);
        List<Thesis> list;
        if(projid == null){
            list = thesisMapper.listByOrg(orgid, teacherid, keywords);
            List<Project> projects = projectMapper.listByOrg(orgid);
            Map<Long, String> projMap = new HashMap<>();
            projects.forEach(project -> projMap.put(project.getId(), project.getTitle()));

            list.forEach(thesis -> thesis.setProject(projMap.get(thesis.getProjid())));
        }else{
            list = thesisMapper.listByProject(projid, teacherid, keywords);
            Project project = projectMapper.selectByPrimaryKey(projid);
            if(isCurrentProj){
                //如果是查询当前论文工作下的论文题目,需要查询学生姓名
                for(Thesis thesis : list){
                    if(thesis.getStudentid() != null && thesis.getStudentid() > 0) {
                        User student = userMapper.selectByPrimaryKey(thesis.getStudentid());
                        if(student != null){
                            thesis.setStudent(student.getUsername() + "[" + student.getAccount() + "]");
                        }
                    }
                }
            }else{
                list.forEach(thesis -> thesis.setProject(project.getTitle()));
            }

        }
        PageInfo<Thesis> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 检查论文题目在当前论文工作下是否存在
     * @param projid
     * @param thesis
     * @return
     * @throws Exception
     */
    public boolean checkTopic(Long projid, Thesis thesis) throws Exception{
        log.info("===检查论文题目在当前论文工作下是否存在===");
        Thesis temp = thesisMapper.getByTopic(projid, thesis.getTopic());
        //if(temp != null && !temp.getId() != thesis.getId()) Long型比较时，大于127时，会一直不相等
        if(temp != null && !temp.getId().equals(thesis.getId())){
            return true;
        }
        return false;
    }

    /**
     * 检查教师申报题目是否已达到论文工作设定的最大出题数
     * @param project
     * @return
     */
    public boolean checkMaxAllowed(Project project, Long teacherid) throws Exception{
        log.info("===检查教师申报题目是否已达到论文工作设定的最大出题数===");
        Long cnt = thesisMapper.getThesisCount(project.getId(), teacherid);
        if(cnt != null && cnt >= project.getSetmax()){
            return true;
        }
        return false;
    }

    /**
     * 保存新的论文题目信息
     * @param org
     * @param project
     * @param teacher
     * @param thesis
     * @return
     */
    public Thesis insert(Org org, Project project, User teacher,Thesis thesis) throws Exception{
        log.info("===保存新的论文题目[" + thesis.getTopic() + "]信息===");
        thesis.setOrgid(org.getId());
        thesis.setProjid(project.getId());
        thesis.setTeacherid(teacher.getId());
        return this.insert(thesis);
    }

    /**
     * 检查学生是否被其他论文题目指定
     * @param projid
     * @param id 当前论文题目id
     * @param studentid
     * @return
     */
    public boolean checkStudent(Long projid, Long id, Long studentid) throws Exception{
        log.info("===检查学生是否被其他论文题目指定===");
        Thesis temp = thesisMapper.getByStudent(projid, studentid);
        if(temp != null && !temp.getId().equals(id)){
            return true;
        }
        return false;
    }

    /**
     * 查询教师出题数量
     * @param projid
     * @return
     * @throws Exception
     */
    public List<ThesisCountExpand> listThesisCount(Long projid) throws Exception{
        log.info("===查询教师出题数量===");
        List<ThesisCountExpand> list = thesisMapper.listThesisCount(projid, null);
        return list;
    }

    /**
     * 删除某个论文题目，在删除前需要检查
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteOnCheck(Long id) throws Exception{
        log.info("===删除某个论文题目[" + id + "]，在删除前需要检查===");
        String msg = "";

        Thesis thesis = this.queryById(id);

        //校验有没有指定学生
        if(thesis.getStudentid() != null && thesis.getStudentid() > 0){
            msg = "删除失败，论文题目[" + thesis.getTopic() + "]已指定给学生，请先联系学生去除关联后再删除！";
            return msg;
        }
        //TODO 校验有没有下达过任务书
        //TODO 校验有没有出过期中检查
        /*Long cnt = projectMapper.countUsed(id);
        if(cnt > 0){
            msg = "删除失败, 论文工作[" + project.getName() + "]已被使用，请先在论文工作中解除对该论文工作的使用！";
            return msg;
        }*/
        thesisMapper.deleteByPrimaryKey(id);
        msg = "论文题目[" + thesis.getTopic() + "]删除成功！";
        return msg;
    }

    /**
     * 分页查询教师出题数量
     * @param page
     * @param rows
     * @param projid
     * @param keywords
     * @param sort
     * @param order
     * @return
     * @throws Exception
     */
    public PageInfo<ThesisCountExpand> listThesisCount(Integer page, Integer rows, Long projid, String keywords, String sort, String order) throws Exception{
        log.info("===分页查询教师出题数量===");
        PageHelper.startPage(page, rows);
        PageHelper.orderBy(sort + " " + order);
        List<ThesisCountExpand> list = thesisMapper.listThesisCount(projid, keywords);
        PageInfo<ThesisCountExpand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 分页查询论文题目的审核状态
     * @param page
     * @param rows
     * @param projid
     * @param checked
     * @param keywords
     * @return
     * @throws Exception
     */
    public PageInfo<Thesis> listThesisByCheck(Integer page, Integer rows, Long projid, Boolean checked, String keywords) throws Exception{
        log.info("===分页查询论文题目的审核状态===");
        PageHelper.startPage(page, rows);
        List<Thesis> list = this.listThesisByCheck(projid, checked, keywords);
        list.forEach(thesis -> {thesis.setTeacher(thesis.getProfile()); thesis.setProfile("");});
        PageInfo<Thesis> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    private List<Thesis> listThesisByCheck(Long projid, Boolean checked, String keywords) throws Exception{
        return thesisMapper.listThesisByCheck(projid, checked, keywords);
    }

    private void saveCheck(List<Thesis> thesises, boolean isCheck) throws Exception{
        log.info("===通用更新论文题目的审核状态===");
        List<Long> tids = CommonUtils.getIds(thesises);
        thesisMapper.saveCheck(tids, isCheck);
    }

    public String saveQuery(Long projid, Boolean checked, String keywords, boolean isCheck) throws Exception{
        log.info("===保存符合条件的论文题目的审核状态===");
        List<Thesis> list = this.listThesisByCheck(projid, checked, keywords);
        //为防止一次审核数据太多，此处进行分段保存
        int total = list.size();
        int cnt = total / 20 ;
        for (int i = 0; i < cnt; i++) {
            List<Thesis> tempList = list.subList(i*20, (i+1)*20);
            saveCheck(tempList, isCheck);
        }
        if(total % 20 > 0){
            List<Thesis> tempList = list.subList(cnt * 20, total);
            saveCheck(tempList, isCheck);
        }
        String msg = String.format("您本次选择%s的论文题目一共有%d条，操作成功。", isCheck?"审核":"回退", total);

        return msg;
    }

    /**
     * 保存在前台选择的论文题目的审核状态
     * @param tidArry
     * @param isCheck
     * @return
     * @throws Exception
     */
    public String saveSelect(List<Long> tidArry, boolean isCheck) throws Exception{
        log.info("===保存在前台选择的论文题目的审核状态===");
        thesisMapper.saveCheck(tidArry, isCheck);
        String msg = String.format("您本次选择%s的论文题目一共有%d条，操作成功。", isCheck?"审核":"回退", tidArry.size());
        return msg;
    }

    /**
     * 查询当前论文工作下未被指定学生的论文题目
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    public PageInfo<Thesis> listNotSelect(Integer page, Integer rows, Long projid, Long teacherid, String keywords) throws Exception{
        log.info("===查询教师在当前论文工作下的题目入选结果===");
        PageHelper.startPage(page, rows);
        List<Thesis> list = thesisMapper.listNotSelected(projid, teacherid, keywords);
        PageInfo<Thesis> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询学生在当前论文工作的选题结果
     * @param projid
     * @param studentid
     * @return
     * @throws Exception
     */
    public Thesis getStudentThesis(Long projid, Long studentid) throws Exception{
        log.info("===查询学生在当前论文工作的选题结果===");
        return thesisMapper.getByStudent(projid, studentid);
    }

    /**
     * 调整学生选题结果
     * @param thesisid
     * @param studentid
     * @throws Exception
     */
    public void updateStudentThesis(Long thesisid, Long studentid) throws Exception{
        log.info("===调整学生[" + studentid + "]选题结果[" + thesisid + "]===");
        //再次判断论文题目没有被分配给其他学生
        Thesis thesis = this.queryById(thesisid);
        if(thesis.getStudentid() != null && thesis.getStudentid() > 0){
            throw new Exception("论文题目[" + thesis.getTopic() + "]已经被分配给其他学生，请选择其他论文题目！");
        }

        //将学生旧的选题关联去掉并更新
        Thesis old = this.getStudentThesis(thesis.getProjid(), studentid);
        if(old != null){
            old.setStudentid(null);
            this.update(old);
        }

        //更新学生的新选题关联
        thesis.setStudentid(studentid);
        this.update(thesis);


    }

    /**
     * 为论文题目取消学生
     * @param thesisid
     */
    public void deleleStudent(Long thesisid) throws Exception{
        log.info("===为论文题目[" + thesisid + "]取消学生===");
        Thesis thesis = this.queryById(thesisid);
        if(thesis != null){
            thesis.setStudentid(null);
            this.update(thesis);
            taskbookMapper.deleteByThesis(thesisid);
            midcheckMapper.deleteByThesis(thesisid);
            scoreMapper.deleteByThesis(thesisid);
        }
    }


    /**
     * 查询指导教师在当前论文工作下的上传论文列表
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    public List<ThesisExpand> listByTeacher(Long projid, Long teacherid) throws Exception{
        log.info("===查询指导教师在当前论文工作下的上传列表===");
        List<ThesisExpand> list = thesisMapper.listByTeacher(projid, teacherid);
        for (ThesisExpand te : list){
            User viewer = userService.queryById(te.getViewerid());
            if(viewer != null) te.setViewer(viewer.getUsername());
            Thesis thesis=thesisMapper.selectByPrimaryKey(te.getId());
        }
        return list;
    }

}
