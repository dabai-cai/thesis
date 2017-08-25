package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.model.Project;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * 查询组织机构下所有的论文工作
     * @param orgid
     * @return
     * @throws Exception
     */
    List<Project> listByOrg(@Param("orgid") Long orgid) throws Exception;

    /**
     * 根据标题和年份获得论文工作
     * @param title
     * @param year
     * @return
     * @throws Exception
     */
    Project getByTitleAndYear(@Param("title") String title, @Param("year") Integer year) throws Exception;

    /**
     * 根据组织机构id获得活跃论文工作数量
     * @param orgid
     * @return
     * @throws Exception
     */
    Long getActiveCount(@Param("orgid") Long orgid) throws Exception;

    /**
     * 根据组织机构id和用户id获得相应的活跃论文工作
     * @param orgid
     * @param uid
     * @return
     * @throws Exception
     */
    List<Project> listByUser(@Param("orgid") Long orgid, @Param("uid") Long uid, @Param("type") String type) throws Exception;



    /**
     * 根据组织机构id和年份获得相应的活跃论文工作
     * @param orgid
     * @param year
     * @return
     * @throws Exception
     */
    List<Project> listByOrgAndYear(@Param("orgid") Long orgid,@Param("year")Integer year) throws Exception;



}