package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.model.Apply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface ApplyMapper extends BaseMapper<Apply> {

    /**
     * 查询学生本人的题目申请
     * @param projid
     * @param studentid
     * @return
     * @throws Exception
     */
    List<Apply> listByStudent(@Param("projid") Long projid, @Param("studentid")Long studentid) throws Exception;

    /**
     * 查询学生的题目申请数量
     * @param projid
     * @param studentid
     * @return
     * @throws Exception
     */
    Long getApplyCount(@Param("projid") Long projid, @Param("studentid")Long studentid) throws Exception;

    /**
     * 查询教师本人的论文题目所有申请
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    List<Apply> listByTeacher(@Param("projid") Long projid, @Param("teacherid")Long teacherid) throws Exception;

    /**
     * 更新论文题目其他申请的答复
     * @param thesisid
     * @param applyid
     * @param reply_info
     * @throws Exception
     */
    void updateByThesis(@Param("thesisid") Long thesisid, @Param("applyid") Long applyid, @Param("reply_info") String reply_info) throws Exception;

    /**
     * 更新学生其他申请的答复
     * @param studentid
     * @param applyid
     * @param reply_info
     * @throws Exception
     */
    void updateByStudent(@Param("studentid") Long studentid, @Param("applyid") Long applyid, @Param("reply_info") String reply_info) throws Exception;
}