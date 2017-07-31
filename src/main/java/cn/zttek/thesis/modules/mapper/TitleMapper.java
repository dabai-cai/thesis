package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.model.Title;
import org.apache.ibatis.annotations.Param;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface TitleMapper extends BaseMapper<Title> {

    Title getByName(@Param("name") String name) throws Exception;

}