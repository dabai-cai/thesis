package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.expand.ThesisDefenseStudent;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.model.Score;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface ScoreMapper extends BaseMapper<Score> {

    /**
     * 查询指导教师在当前论文工作下的自评成绩列表
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    List<ThesisExpand> listByTeacher(@Param("projid") Long projid, @Param("teacherid") Long teacherid) throws Exception;

    /**
     * 查询评阅教师在当前论文工作下的评阅成绩列表
     * @param projid
     * @param viewerid
     * @return
     * @throws Exception
     */
    List<ThesisExpand> listByViewer(@Param("projid") Long projid, @Param("viewerid") Long viewerid) throws Exception;




        /**
         * 批量为论文题目指定评阅老师
         * @param viewerid
         * @param idsArry
         */
    void saveAssign(@Param("viewerid") Long viewerid, @Param("list") List<Long> idsArry) throws Exception;

    /**
     * 删除论文题目关联的成绩单
     * @param thesisid
     * @throws Exception
     */
    void deleteByThesis(@Param("thesisid") Long thesisid)throws Exception;


    /**
     * 查询论文题目关联的成绩单
     * @param thesisid
     * @throws Exception
     */
    void queryByThesis(@Param("thesisid") Long thesisid)throws Exception;


    /**
     * 查询答辩秘书在当前论文工作下需要录入成绩的学生列表
     * @param  projid
     * @param  studentids
     * *@return
     * @throws Exception
     */
    List<ThesisExpand> listByStudent(@Param("projid") Long projid, @Param("studentids") Long[] studentids) throws Exception;
}