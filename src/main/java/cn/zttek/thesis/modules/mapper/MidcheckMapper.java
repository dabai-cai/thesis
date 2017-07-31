package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.model.Midcheck;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface MidcheckMapper extends BaseMapper<Midcheck> {

    /**
     * 查询教师在当前论文工作下的中期检查下达列表
     * @param projid
     * @param teacherid
     * @return
     * @throws Exception
     */
    List<ThesisExpand> listOfMidcheck(@Param("projid") Long projid, @Param("teacherid") Long teacherid) throws Exception;

    /**
     * 教师或学生确认中期检查
     * @param midcheckid
     * @param confirmField
     * @throws Exception
     */
    void updateConfirm(@Param("midcheckid") Long midcheckid, @Param("confirmField") String confirmField) throws Exception;

    /**
     * 删除论文题目关联的中期检查
     * @param thesisid
     * @throws Exception
     */
    void deleteByThesis(@Param("thesisid") Long thesisid)throws Exception;
    /**
     * 获取论文题目关联的中期检查
     * @param thesisid
     * @throws Exception
     */
    Midcheck getByThesis(@Param("thesisid") Long thesisid)throws Exception;
}