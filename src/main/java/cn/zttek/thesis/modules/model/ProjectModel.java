package cn.zttek.thesis.modules.model;

/**
 * @描述: 组织机构下论文工作相关实体的基类
 * @作者: Pengo.Wen
 * @日期: 2016-09-13 15:17
 * @版本: v1.0
 */
public class ProjectModel extends OrgModel {

    private Long projid;

    public Long getProjid() {
        return projid;
    }

    public void setProjid(Long projid) {
        this.projid = projid;
    }
}
