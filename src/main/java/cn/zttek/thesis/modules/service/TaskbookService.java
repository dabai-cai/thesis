package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.mapper.TaskbookMapper;
import cn.zttek.thesis.modules.model.Taskbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @描述: 任务书相关业务逻辑处理
 * @作者: Pengo.Wen
 * @日期: 2016-09-26 15:43
 * @版本: v1.0
 */
@Service("taskbookService")
public class TaskbookService extends BaseService<Taskbook> {


    @Autowired
    private TaskbookMapper taskbookMapper;

    /**
     * 查询教师在当前论文工作下的任务书下达列表
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    public List<ThesisExpand> listOfTask(Long projid, Long teacherid) throws Exception{
        log.info("===查询教师在当前论文工作下的任务书下达列表===");

        return taskbookMapper.listOfTask(projid, teacherid);
    }


    /**
     * 教师或学生确认任务书
     * @param taskid
     * @param confirmField
     * @throws Exception
     */
    public void updateConfirm(Long taskid, String confirmField) throws Exception{
        log.info("===教师或学生[" + confirmField + "]确认任务书===");
        taskbookMapper.updateConfirm(taskid, confirmField);
    }

    @Override
    public Taskbook insert(Taskbook taskbook) throws Exception {
        taskbook.setMdate(new Timestamp(System.currentTimeMillis()));
        taskbook.setValid(true);
        taskbookMapper.insert(taskbook);
        return taskbook;
    }

    /**
     * 获取论文题目对应的任务书
     * @param thesisid
     * @return
     */
    public Taskbook getByThesis(Long thesisid) throws Exception{
        log.info("===获取论文题目[" + thesisid + "]对应的任务书===");
        return taskbookMapper.getByThesis(thesisid);
    }
}
