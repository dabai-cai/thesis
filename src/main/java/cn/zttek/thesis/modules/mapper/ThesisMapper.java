package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.expand.ThesisCountExpand;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.expand.ThesisResult;
import cn.zttek.thesis.modules.model.Thesis;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface ThesisMapper extends BaseMapper<Thesis> {

    /**
     * 根据组织机构查询教师题目
     * @param orgid
     * @param teacherid
     * @param keywords
     * @return
     * @throws Exception
     */
    List<Thesis> listByOrg(@Param("orgid")Long orgid, @Param("teacherid") Long teacherid, @Param("keywords")String keywords) throws Exception;

    /**
     * 根据论文工作查询教师申报题目列表
     * @param projid
     * @param teacherid
     * @param keywords
     * @return
     * @throws Exception
     */
    List<Thesis> listByProject(@Param("projid")Long projid, @Param("teacherid") Long teacherid, @Param("keywords")String keywords) throws Exception;

    /**
     * 根据论文工作及标题查询论文题目
     * @param projid
     * @param topic
     * @return
     * @throws Exception
     */
    Thesis getByTopic(@Param("projid")Long projid, @Param("topic") String topic) throws Exception;

    /**
     * 根据论文工作ID获得教师的出题数量
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    Long getThesisCount(@Param("projid") Long projid, @Param("teacherid") Long teacherid) throws Exception;

    /**
     * 根据论文工作ID获得学生所入选的论文题目
     * @param projid
     * @param studentid
     * @return
     * @throws Exception
     */
    Thesis getByStudent(@Param("projid")Long projid, @Param("studentid")Long studentid) throws Exception;

    /**
     * 查询教师出题数量
     * @param projid
     * @param keywords
     * @return
     * @throws Exception
     */
    List<ThesisCountExpand> listThesisCount(@Param("projid")Long projid, @Param("keywords") String keywords) throws Exception;

    /**
     * 根据审核状态查询教师的论文题目
     * @param projid
     * @param checked
     * @param keywords
     * @return
     * @throws Exception
     */
    List<Thesis> listThesisByCheck(@Param("projid")Long projid, @Param("checked") Boolean checked, @Param("keywords") String keywords)throws Exception;


    /**
     * 保存论文题目的审核状态
     * @param tids
     * @param isCheck
     * @throws Exception
     */
    void saveCheck(@Param("tids") List<Long> tids, @Param("isCheck") boolean isCheck) throws Exception;

    /**
     * 查询可以选择的论文题目
     * @param projid
     * @param teacherid
     * @param keywords
     * @return
     * @throws Exception
     */
    List<Thesis> listNotSelected(@Param("projid")Long projid, @Param("teacherid") Long teacherid, @Param("keywords") String keywords) throws Exception;


    /**
     * 查询老师在当前论文工作下的上传论文列表
     * @param  projid
     * @param  teacherid
     * *@return
     * @throws Exception
     */
    List<ThesisExpand> listByTeacher(@Param("projid") Long projid, @Param("teacherid") Long teacherid) throws Exception;






}