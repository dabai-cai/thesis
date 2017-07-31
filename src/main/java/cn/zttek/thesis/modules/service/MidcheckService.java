package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.mapper.MidcheckMapper;
import cn.zttek.thesis.modules.model.Midcheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @描述: 中期检查相关业务逻辑处理
 * @作者: Pengo.Wen
 * @日期: 2016-09-26 15:43
 * @版本: v1.0
 */
@Service("midcheckService")
public class MidcheckService extends BaseService<Midcheck> {


    @Autowired
    private MidcheckMapper midcheckMapper;

    /**
     * 查询教师在当前论文工作下的中期检查填写列表
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    public List<ThesisExpand> listOfMidcheck(Long projid, Long teacherid) throws Exception{
        log.info("===查询教师在当前论文工作下的中期检查填写列表===");

        return midcheckMapper.listOfMidcheck(projid, teacherid);
    }


    /**
     * 教师或学生确认中期检查
     * @param midcheckid
     * @param confirmField
     * @throws Exception
     */
    public void updateConfirm(Long midcheckid, String confirmField) throws Exception{
        log.info("===教师或学生[" + confirmField + "]确认中期检查===");
        midcheckMapper.updateConfirm(midcheckid, confirmField);
    }

    @Override
    public Midcheck insert(Midcheck midcheck) throws Exception {
        midcheck.setMdate(new Timestamp(System.currentTimeMillis()));
        midcheck.setValid(true);
        midcheckMapper.insert(midcheck);
        return midcheck;
    }

    /**
     * 获取论文题目对应的中期检查
     * @param thesisid
     * @return
     */
    public Midcheck getByThesis(Long thesisid) throws Exception{
        log.info("===获取论文题目[" + thesisid + "]对应的中期检查===");
        return midcheckMapper.getByThesis(thesisid);
    }
}
