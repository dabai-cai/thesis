package cn.zttek.thesis.common.mybatis;

/**
 * @描述: 所有枚举类型都继承此接口，即可自动处理枚举类型的映射
 * @作者: https://github.com/xjs1919/enumhandler
 * @日期: 2016-08-09 15:28
 * @版本: v1.0
 */
public interface Identifiable<K> {

    K getId();

}
