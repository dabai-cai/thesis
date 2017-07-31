package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.mapper.ScoreMapper;
import cn.zttek.thesis.modules.model.Score;
import cn.zttek.thesis.modules.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 批量为论文题目指定评阅老师
     * @param viewerid
     * @param idsArry
     */
    public void saveAssign(Long viewerid, List<Long> idsArry) throws Exception{
        log.info("===批量为论文题目指定评阅老师===");
        scoreMapper.saveAssign(viewerid, idsArry);
    }
}
