package cn.zttek.thesis.common.base;

import java.util.List;

/**
 * @描述: mybatis基础映射类
 * @作者: Pengo.Wen
 * @日期: 2016-08-09 16:02
 * @版本: v1.0
 */
public interface BaseMapper<T extends BaseModel> {

    List<T> selectAll();

    int deleteByPrimaryKey(Long id);

    T selectByPrimaryKey(Long id);

    int insert(T t);

    int updateByPrimaryKey(T t);


}
