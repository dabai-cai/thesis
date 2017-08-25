package cn.zttek.thesis.modules.mapper;

/**
 * Created by Mankind on 2017/8/10.
 */
import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.model.Advice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdviceMapper extends BaseMapper<Advice> {
    /**
     * 查询管理员或者超级管理员的公告管理列表
     * @param orgid
     * @param keywords
     * @return
     */
    List<Advice> listByOrgAndKeywords(@Param("orgid") Long orgid, @Param("keywords") String keywords);

    /**
     * 根据用户类型查询首页显示的公告列表
     * @param orgid
     * @param keywords
     * @param type
     * @return
     */
        List<Advice> listByUserType(@Param("orgid") Long orgid, @Param("keywords") String keywords, @Param("type") UserType type);

}