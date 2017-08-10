package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 根据用户id查询用户详细信息
     * @param uid
     * @return
     * @throws Exception
     */
    UserInfo queryByUid(@Param("uid") Long uid) throws Exception;

    /**
     * 根据职称id查询用户数量
     * @param tid
     * @return
     */
    Long countUsed(@Param("tid") Long tid) throws Exception;

    /**
     * 根据组织机构查询所有专业
     * @param orgid
     * @param projid 如果不为null，则只查询论文工作下的学生专业
     * @return
     * @throws Exception
     */
    List<String> getMajors(@Param("orgid") Long orgid, @Param("projid")Long projid) throws Exception;

    /**
     * 根据组织机构和专业查询所有年级
     * @param orgid
     * @param projid 如果不为null，则只查询论文工作下的学生专业
     * @param major
     * @return
     * @throws Exception
     */
    List<Integer> getGrade(@Param("orgid") Long orgid, @Param("projid")Long projid, @Param("major") String major) throws Exception;

    /**
     * 根据组织机构、专业和年级查询所有年级
     * @param orgid
     * @param projid 如果不为null，则只查询论文工作下的学生专业
     * @param major
     * @param grade
     * @return
     * @throws Exception
     */
    List<String> getClazz(@Param("orgid") Long orgid, @Param("projid")Long projid, @Param("major") String major, @Param("grade") Integer grade) throws Exception;
}