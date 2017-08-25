package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.common.utils.JsonUtils;
import cn.zttek.thesis.modules.expand.ThesisDefenseStudent;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.mapper.DefenseGroupMapper;
import cn.zttek.thesis.modules.mapper.ScoreMapper;
import cn.zttek.thesis.modules.mapper.ThesisMapper;
import cn.zttek.thesis.modules.model.DefenseGroup;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.Score;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.utils.ThesisParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 论文成绩相关业务逻辑处理
 * @作者: Pengo.Wen
 * @日期: 2016-10-12 20:00
 * @版本: v1.0
 */
@Service
public class ScoreService extends BaseService<Score>{


    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private DefenseGroupMapper defenseGroupMapper;
    @Autowired
    private ThesisMapper thesisMapper;

    /**
     * 查询指导教师在当前论文工作下的自评成绩列表
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    public List<ThesisExpand> listByTeacher(Long projid, Long teacherid) throws Exception{
        log.info("===查询指导教师在当前论文工作下的自评成绩列表===");
        List<ThesisExpand> list = scoreMapper.listByTeacher(projid, teacherid);
        for (ThesisExpand te : list){
            User viewer = userService.queryById(te.getViewerid());
            if(viewer != null) te.setViewer(viewer.getUsername());
        }
        return list;
    }

    /**
     * 查询评阅教师在当前论文工作下的评阅成绩列表
     * @param projid
     * @param viewerid
     * @return
     * @throws Exception
     */
    public List<ThesisExpand> listByViewer(Long projid, Long viewerid) throws Exception{
        log.info("===查询评阅教师在当前论文工作下的评阅成绩列表===");
        List<ThesisExpand> list = scoreMapper.listByViewer(projid, viewerid);
        for (ThesisExpand te : list){
            User teacher = userService.queryById(te.getTeacherid());
            if(teacher != null) te.setTeacher(teacher.getUsername());
        }
        return list;
    }

    /**
     * 查询答辩秘书在当前论文工作下的录入成绩列表
     * @param projid
     * @param secretaryid
     * @return
     * @throws Exception
     */
    public List<ThesisExpand> listBySecretary(Long projid, Long secretaryid) throws Exception{
        log.info("===查询答辩秘书在当前论文工作下的录入成绩列表===");
        DefenseGroup defenseGroup = defenseGroupMapper.listBySecretary(projid, secretaryid);
        List<ThesisExpand> list=new ArrayList<>();
        if(null!=defenseGroup){
            List<ThesisDefenseStudent> students= JsonUtils.jsonToList(defenseGroup.getStudents(),ThesisDefenseStudent.class);//转换为pojo
            Long[] studentids=new Long[students.size()];
            System.out.println("学生人数"+students.size());
            for(int i=0;i<students.size();i++){
                studentids[i]=students.get(i).getStudentid();
            }
            list=scoreMapper.listByStudent(projid,studentids);
        }
        return list;
    }

    /**
     * 批量为论文题目指定评阅老师
     * @param viewerid
     * @param idsArry
     */
    public void saveAssign(Long viewerid, List<Long> idsArry) throws Exception{
        log.info("===批量为论文题目指定评阅老师===");
        scoreMapper.saveAssign(viewerid, idsArry);
    }


    /**
     * 判断学生是否通过答辩
     * @param score
     */
    public String  agree(Score score) throws Exception{
        log.info("===判断学生是否通过答辩===");
        Integer defense=score.getMark3().intValue();
        if(defense<60) {
            return "不同意";
        }
        return "同意";
    }




    /**
     *计算学生的论文总评分数
     * @param score
     */
    public Double general(Score score) throws Exception{
        log.info("===计算学生的论文总评分数===");
        Project project= ThesisParam.getCurrentProj();
        Double sum=(score.getMark1()*project.getGmpercent()+
                score.getMark2()*project.getRmpercent()+score.getMark3()*project.getAmpercent())/100;
        BigDecimal b   =   new   BigDecimal(sum);
        return b.setScale(2,  BigDecimal.ROUND_HALF_UP).doubleValue();
    }




    /**
     * 判断学生的论文总评成绩等级
     * @param score
     */
    public String thesisLevel(Score score) throws Exception{
        log.info("===判断学生的论文总评成绩等级===");
        Project project= ThesisParam.getCurrentProj();
        Double sum=general(score);
        String level="";
        Integer scorelevel=sum.intValue()/10;
        switch(scorelevel) {
            case 10:
            case 9:level="优";break;
            case 8:level="良";break;
            case 7:level="中";break;
            case 6:level="及格";break;
            default:level="不及格";
        }
        return level;
     }
}
