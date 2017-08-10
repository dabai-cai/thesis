package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.modules.expand.ThesisResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @描述: 论文题目选择结果数据访问接口
 * @作者: Pengo.Wen
 * @日期: 2016-10-14 20:27
 * @版本: v1.0
 */
@Repository
public interface ThesisResultMapper {


    /**
     * 获取论文工作学生总人数
     * @param projid
     * @return
     * @throws Exception
     */
    Long getStudentCount(@Param("projid")Long projid) throws Exception;

    /**
     * 获取论文工作总题目数量
     * @param projid
     * @return
     * @throws Exception
     */
    Long getThesisCount(@Param("projid")Long projid) throws Exception;

    /**
     * 获取论文题目最终选题数量
     * @param projid
     * @return
     * @throws Exception
     */
    Long getResultCount(@Param("projid")Long projid) throws Exception;


    /**
     * 查询论文工作的选题结果，以学生为基准
     * @param projid
     * @param selected
     * @param stuno
     * @param stuname
     * @param major
     * @param grade
     * @param clazz
     * @param teacher
     * @return
     * @throws Exception
     */
    List<ThesisResult> listResult(@Param("projid")Long projid, @Param("selected") Boolean selected,
                                  @Param("stuno")String stuno, @Param("stuname")String stuname,
                                  @Param("major") String major, @Param("grade") Integer grade,
                                  @Param("clazz") String clazz, @Param("teacher")String teacher) throws Exception;

}
