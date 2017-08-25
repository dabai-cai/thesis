package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.enums.TitleLevel;
import cn.zttek.thesis.modules.expand.ThesisDefenseStudent;
import cn.zttek.thesis.modules.expand.ThesisDefenseTeacher;
import cn.zttek.thesis.modules.model.DefenseTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface DefenseTaskMapper extends BaseMapper<DefenseTask> {

    /**
     * 根据论文工作id找到所有的答辩任务
     * @param projid
     * @return
     * @throws Exception
     */
    List<DefenseTask> listByProj(@Param("projid") Long projid) throws Exception;

    /**
     * 通过答辩任务名称查找答辩任务，用于查重
     * @param name
     * @return
     */
    DefenseTask selectByName(@Param("name") String name) ;

    /**
     * 通过论文工作id和条件查询论文工作下符合条件的学生列表
     * @param projid
     * @param major
     * @param grade
     * @param clazz
     * @param stuno
     * @return
     * @throws Exception
     */
    List<ThesisDefenseStudent> studentlistByProj(@Param("projid") Long projid,
                                                 @Param("major") String major,
                                                 @Param("grade") Integer grade,
                                                 @Param("clazz") String clazz,
                                                 @Param("studentno") String stuno
    ) throws Exception;

    /**
     * 通过论文工作id和条件查询论文工作下符合条件的教师列表
     * @param projid
     * @param titlename
     * @param account
     * @return
     * @throws Exception
     */
    List<ThesisDefenseTeacher> teacherlistByProj(@Param("projid") Long projid,
                                                 @Param("titleLevel") TitleLevel titleLevel,
                                                 @Param("account") String account) throws Exception;


}