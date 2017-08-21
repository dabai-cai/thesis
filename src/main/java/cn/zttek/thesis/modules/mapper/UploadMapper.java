package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.model.Upload;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadMapper extends BaseMapper<Upload>{

    /**
     * 查询指导教师在当前论文工作下的论文上传列表
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    List<ThesisExpand> listByTeacher(@Param("projid") Long projid, @Param("teacherid") Long teacherid) throws Exception;

    /**
     * 查询学生的论文上传记录
     * @param studentid
     * @param thesisid
     * @return
     * @throws Exception
     */
    Upload selectByStudent(@Param("studentid") Long studentid, @Param("thesisid") Long thesisid) throws Exception;




    /**
     * 查询论文的上传记录
     * @param thesisid
     * @return
     * @throws Exception
     */
    Upload  selectByThesis(@Param("thesisid") Long thesisid) throws Exception;



}