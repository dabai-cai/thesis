package cn.zttek.thesis.modules.model;

import cn.zttek.thesis.common.base.BaseModel;

/**
 * @描述: 论文相关实体的基类
 * @作者: Pengo.Wen
 * @日期: 2016-09-13 15:19
 * @版本: v1.0
 */
public class ThesisModel extends BaseModel{

    private Long thesisid;

    public Long getThesisid() {
        return thesisid;
    }

    public void setThesisid(Long thesisid) {
        this.thesisid = thesisid;
    }
}
