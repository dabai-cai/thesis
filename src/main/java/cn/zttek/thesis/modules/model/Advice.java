package cn.zttek.thesis.modules.model;

import cn.zttek.thesis.common.base.BaseModel;
import cn.zttek.thesis.modules.enums.AdviceTarget;

import java.util.Date;
public class Advice extends BaseModel {
    private Long orgid;
    private Long creator;
    private String topic;
    private String content;
    private AdviceTarget target;
    private Boolean top;

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AdviceTarget getTarget() {
        return target;
    }

    public void setTarget(AdviceTarget target) {
        this.target = target;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }
}