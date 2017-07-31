package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.expand.ThesisResult;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.mapper.ThesisResultMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 论文选题结果相关业务逻辑处理
 * @作者: Pengo.Wen
 * @日期: 2016-10-14 20:10
 * @版本: v1.0
 */
@Service
public class ResultService{



    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThesisResultMapper thesisResultMapper;

    /**
     * 分页查询论文工作的选题结果
     * @param page
     * @param rows
     * @param projid
     * @param tresult
     * @return
     * @throws Exception
     */
    public PageInfo<ThesisResult> listResultByPage(Integer page, Integer rows, Long projid, ThesisResult tresult) throws Exception{
        log.info("===分页查询论文工作的选题结果===");
        PageHelper.startPage(page, rows);
        List<ThesisResult> list = listResult(projid, tresult);
        PageInfo<ThesisResult> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    /**
     * 查询论文工作的选题结果
     * @param projid
     * @param tresult
     * @return
     * @throws Exception
     */
    public List<ThesisResult> listResult(Long projid, ThesisResult tresult) throws Exception{
        List<ThesisResult> list = thesisResultMapper.listResult(projid, tresult.getSelected(),
                tresult.getStuno(), tresult.getStuname(),
                tresult.getMajor(), tresult.getGrade(),
                tresult.getClazz(), tresult.getTeacher());
        int len = list.size();
        for (int i = 0; i < len; i++) {
            ThesisResult result = list.get(i);
            if(result.getTid() != null && result.getTid() > 0){
                result.setTitle(TitleHolder.getTitleByArry(result.getTid()));
            }
        }
        return list;
    }

    /**
     * 查询论文工作的全部选题结果
     * @param projid
     * @return
     * @throws Exception
     */
    public List<ThesisResult> listAllResult(Long projid) throws Exception{
        log.info("===查询论文工作的全部选题结果===");
        ThesisResult tresult = new ThesisResult();
        tresult.setSelected(null);
        tresult.setStuno(null);
        tresult.setStuname(null);
        tresult.setTeacher(null);
        tresult.setMajor(null);
        tresult.setGrade(null);
        tresult.setClazz(null);
        return listResult(projid, tresult);
    }
/**
     * 查询论文工作的符合条件的选题结果
     * @param projid
     * @param tresult
     * @return
     * @throws Exception
     */
    public List<ThesisResult> listSearchResult(Long projid, ThesisResult tresult) throws Exception{
        log.info("===查询论文工作的符合条件的选题结果===");
        return listResult(projid, tresult);
    }


    /**
     * 获取论文工作学生总人数
     * @param projid
     * @return
     * @throws Exception
     */
    public Long getStudentCount(Long projid) throws Exception{
        log.info("===获取论文工作学生总人数===");
        return thesisResultMapper.getStudentCount(projid);
    }

    /**
     * 获取论文工作总题目数量
     * @param projid
     * @return
     * @throws Exception
     */
    public Long getThesisCount(Long projid) throws Exception{
        log.info("===获取论文工作总题目数量===");
        return thesisResultMapper.getThesisCount(projid);
    }

    /**
     * 获取论文题目最终选题数量
     * @param projid
     * @return
     * @throws Exception
     */
    public Long getResultCount(Long projid) throws Exception{
        log.info("===获取论文题目最终选题数量===");
        return thesisResultMapper.getResultCount(projid);
    }

}
