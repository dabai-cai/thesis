package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.expand.ThesisDefenseTeacher;
import cn.zttek.thesis.modules.model.DefenseGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface DefenseGroupMapper extends BaseMapper<DefenseGroup> {
    /**
     * 根据答辩任务id删除所有答辩小组
     * @param id
     * @throws Exception
     */
    void deleteByTask(@Param("taskid") Long id) throws Exception;

    /**
     * 根据答辩任务id获取所有答辩小组
     * @param id
     * @throws Exception
     */
    List<DefenseGroup> selectByTask(@Param("taskid") Long id) throws Exception;

    /**
     * 用于查找答辩秘书和答辩组长
     * @param id
     * @return
     * @throws Exception
     */
    ThesisDefenseTeacher selectTeacherByUserId(@Param("userid") Long id)throws  Exception;


    /**
     * 查询答辩教师在当前论文工作下的答辩成绩列表
     * @param projid
     * @param secretaryid
     * @return
     * @throws Exception
     */
    DefenseGroup listBySecretary(@Param("projid") Long projid, @Param(("secretaryid")) Long secretaryid) throws Exception;

}