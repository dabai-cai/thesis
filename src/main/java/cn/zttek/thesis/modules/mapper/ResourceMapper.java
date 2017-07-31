package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.model.Resource;
import org.apache.ibatis.annotations.Param;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface ResourceMapper extends BaseMapper<Resource> {


    Resource getByName(@Param("name") String name) throws Exception;

    Long countChildren(@Param("pid") Long pid) throws Exception;
}