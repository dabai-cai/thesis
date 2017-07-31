package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.model.Taskbook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface TaskbookMapper extends BaseMapper<Taskbook> {

    /**
     * 查询教师在当前论文工作下的任务书下达列表
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    List<ThesisExpand> listOfTask(@Param("projid") Long projid, @Param("teacherid") Long teacherid) throws Exception;

    /**
     * 教师或学生确认任务书
     * @param taskid
     * @param confirmField
     * @throws Exception
     */
    void updateConfirm(@Param("taskid") Long taskid, @Param("confirmField") String confirmField) throws Exception;

    /**
     * 删除论文题目关联的任务书
     * @param thesisid
     * @throws Exception
     */
    void deleteByThesis(@Param("thesisid") Long thesisid)throws Exception;
    /**
     * 获取论文题目关联的任务书
     * @param thesisid
     * @throws Exception
     */
    Taskbook getByThesis(@Param("thesisid") Long thesisid)throws Exception;
}