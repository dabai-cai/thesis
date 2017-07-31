package cn.zttek.thesis.modules.model;

import cn.zttek.thesis.common.base.BaseModel;

/**
 * @描述: 组织机构下所有实体的基类
 * @作者: Pengo.Wen
 * @日期: 2016-08-27 16:41
 * @版本: v1.0
 */
public class OrgModel extends BaseModel {

    private Long orgid;

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }
}
