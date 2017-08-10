package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.model.Org;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface OrgMapper extends BaseMapper<Org> {


    /**
     * 根据名称获得组织机构实体
     * @param name
     * @return
     * @throws Exception
     */
    Org getByName(@Param("name") String name) throws Exception;

}