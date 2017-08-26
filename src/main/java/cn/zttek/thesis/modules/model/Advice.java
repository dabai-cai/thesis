package cn.zttek.thesis.modules.model;

import cn.zttek.thesis.common.base.BaseModel;
import cn.zttek.thesis.modules.enums.AdviceTarget;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Advice extends BaseModel {
    private Long orgid;
    private Long creator;
    private String topic;
    private String content;
    private AdviceTarget target;
    private Boolean top;



    /**
     * 添加此字段作为前端显示编辑人所用
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String creatorname;

    public String getCreatorname() {
        return creatorname;
    }

    public void setCreatorname(String creatorname) {
        this.creatorname = creatorname;
    }

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