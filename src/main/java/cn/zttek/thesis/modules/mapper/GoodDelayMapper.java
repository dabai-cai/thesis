package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.expand.ApplyExtend;
import cn.zttek.thesis.modules.model.GoodDelay;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodDelayMapper extends BaseMapper<GoodDelay>{

 /**
  * 根据论文id查询
  * @param thesisid
  * @return
  * @throws Exception
  */
  GoodDelay queryByThesisId(@Param(value = "thesisid") Long thesisid);



 /**
  * 根据论文id检查学生当前的申请
  * @param thesisid
  * @return
  * @throws Exception
  */
   GoodDelay checkByThesisId(@Param(value = "thesisid") Long thesisid);


 /**
  * 查询老师需要确认的申请列表
  * @param teacherid
  * @return
  * @throws Exception
  */
 List<ApplyExtend> listByTeacher(@Param(value = "teacherid") Long teacherid);


 /**
  * 查询组织需要确认的申请列表
  * @param orgid
  * @return
  * @throws Exception
  */
 List<ApplyExtend> listByOrg(@Param(value = "orgid") Long orgid);


 /**
  * 查询老师需要确认的申请数量
  * @param teacherid
  * @return
  * @throws Exception
  */
 Integer countUnConfByTeacher(@Param(value = "teacherid") Long teacherid);

}