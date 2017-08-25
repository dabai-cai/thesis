package cn.zttek.thesis.modules.service;
import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.expand.ApplyExtend;
import cn.zttek.thesis.modules.mapper.GoodDelayMapper;
import cn.zttek.thesis.modules.model.GoodDelay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodDelayService extends BaseService<GoodDelay> {

   @Autowired
    private GoodDelayMapper goodDelayMapper;


    /**
     * 查询学生已经选择的争优/延期申请
     * @param thesisid
     * @return
     * @throws Exception
     */
   public GoodDelay queryByThesisId(Long thesisid) throws Exception{
       log.info("===查询学生已经选择的争优/延期申请===");
       return goodDelayMapper.queryByThesisId(thesisid);
   }

    /**
     * 查询学生是否已有申请
     * @param thesisid
     * @return
     * @throws Exception
     */
    public boolean checkByThesisid(Long thesisid) throws Exception{
        log.info("===查询学生是否已有申请===");
        GoodDelay goodDelay=goodDelayMapper.checkByThesisId(thesisid);
        if(null!=goodDelay){
            return true;
        }
        return false;
    }


    /**
     * 查询老师需要确认的申请
     * @param teacherid
     * @return
     * @throws Exception
     */
    public List<ApplyExtend> queryByTeacher(Long teacherid) throws Exception{
        log.info("===查询老师需要确认的申请===");
        return goodDelayMapper.listByTeacher(teacherid);
    }



    /**
     * 查询组织需要确认的申请列表
     * @param orgid
     * @return
     * @throws Exception
     */
    public List<ApplyExtend> queryByOrg(Long orgid) throws Exception{
        log.info("===查询组织需要确认的申请列表===");
        return goodDelayMapper.listByOrg(orgid);
    }


    /**
     * 查询老师需要确认的申请数量
     * @param teacherid
     * @return
     * @throws Exception
     */
    public Integer countUnConfByTeacher(Long teacherid) throws Exception{
        log.info("===查询老师需要确认的申请数量===");
        return goodDelayMapper.countUnConfByTeacher(teacherid);
    }



}
