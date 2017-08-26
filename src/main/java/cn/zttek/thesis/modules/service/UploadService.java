package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.mapper.UploadMapper;
import cn.zttek.thesis.modules.model.Upload;
import cn.zttek.thesis.modules.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadService extends BaseService<Upload> {
    @Autowired
    private UploadMapper uploadMapper;
    @Autowired
    private UserService userService;

    /**
     * 查询指导教师在当前论文工作下的论文上传列表
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    public List<ThesisExpand> listByTeacher(Long projid, Long teacherid) throws Exception{
        log.info("===查询指导教师在当前论文工作下的论文上传列表===");
        List<ThesisExpand> list = uploadMapper.listByTeacher(projid, teacherid);
        for (ThesisExpand te : list){
            User viewer = userService.queryById(te.getViewerid());
            if(viewer != null) te.setViewer(viewer.getUsername());
        }
        return list;
    }

    /**
     * 查询学生的论文上传记录
     * @param studentid
     * @param thesisid
     * @return
     * @throws Exception
     */
    public Upload selectByStudent(@Param("studentid") Long studentid, @Param("thesisid") Long thesisid) throws Exception{
        log.info("===查询学生在当前论文工作下的论文上传状态===");
        Upload upload=uploadMapper.selectByStudent(studentid,thesisid);
        return upload;
    }




    /**
     * 查询论文上传记录
     * @param thesisid
     * @return
     * @throws Exception
     */
    public Upload  selectByThesis(@Param("thesisid") Long thesisid) throws Exception{
        log.info("===查询学生在当前论文工作下的论文上传状态===");
        Upload upload=uploadMapper.selectByThesis(thesisid);
        return upload;
    }


}
