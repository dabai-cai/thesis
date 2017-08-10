package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.model.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {


    Resource getByName(@Param("name") String name) throws Exception;

    Long countChildren(@Param("pid") Long pid) throws Exception;
}