package cn.zttek.thesis.common.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @描述: 论文研究方向、论文来源、论文属性等基础映射类
 * @作者: Pengo.Wen
 * @日期: 2016-09-12 16:11
 * @版本: v1.0
 */
public interface AttrMapper<E extends BaseModel> extends BaseMapper<E> {


    List<E> listByOrg(@Param("orgid")Long orgid) throws Exception;

    E getByValue(@Param("value") String value, @Param("orgid") Long orgid) throws Exception;

}
