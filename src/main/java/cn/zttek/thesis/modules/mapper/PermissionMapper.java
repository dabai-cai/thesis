package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.model.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    Long countPerm(@Param("resid")Long resid) throws Exception;

    List<Permission> listByRes(@Param("resid") Long resid) throws Exception;

    Permission getByKeystr(@Param("keystr") String keystr) throws Exception;

}