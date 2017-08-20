package cn.zttek.thesis.modules.expand;

import cn.zttek.thesis.common.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.sql.Timestamp;

public class ApplyExtend implements Serializable{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Long studentid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long thesisid;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reason;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Byte studentconf;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String advice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer teacherconf;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String review;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer orgconf;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer applytype;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String topic;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;

    public Timestamp getCdate() {
        return cdate;
    }

    public void setCdate(Timestamp cdate) {
        this.cdate = cdate;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp cdate;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThesisid() {
        return thesisid;
    }

    public void setThesisid(Long thesisid) {
        this.thesisid = thesisid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Byte getStudentconf() {
        return studentconf;
    }

    public void setStudentconf(Byte studentconf) {
        this.studentconf = studentconf;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Integer getTeacherconf() {
        return teacherconf;
    }

    public void setTeacherconf(Integer teacherconf) {
        this.teacherconf = teacherconf;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getOrgconf() {
        return orgconf;
    }

    public void setOrgconf(Integer orgconf) {
        this.orgconf = orgconf;
    }

    public Integer getApplytype() {
        return applytype;
    }

    public void setApplytype(Integer applytype) {
        this.applytype = applytype;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ApplyExtend other = (ApplyExtend) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getThesisid() == null ? other.getThesisid() == null : this.getThesisid().equals(other.getThesisid()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getStudentconf() == null ? other.getStudentconf() == null : this.getStudentconf().equals(other.getStudentconf()))
            && (this.getAdvice() == null ? other.getAdvice() == null : this.getAdvice().equals(other.getAdvice()))
            && (this.getTeacherconf() == null ? other.getTeacherconf() == null : this.getTeacherconf().equals(other.getTeacherconf()))
            && (this.getReview() == null ? other.getReview() == null : this.getReview().equals(other.getReview()))
            && (this.getOrgconf() == null ? other.getOrgconf() == null : this.getOrgconf().equals(other.getOrgconf()))
            && (this.getApplytype() == null ? other.getApplytype() == null : this.getApplytype().equals(other.getApplytype()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getThesisid() == null) ? 0 : getThesisid().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getStudentconf() == null) ? 0 : getStudentconf().hashCode());
        result = prime * result + ((getAdvice() == null) ? 0 : getAdvice().hashCode());
        result = prime * result + ((getTeacherconf() == null) ? 0 : getTeacherconf().hashCode());
        result = prime * result + ((getReview() == null) ? 0 : getReview().hashCode());
        result = prime * result + ((getOrgconf() == null) ? 0 : getOrgconf().hashCode());
        result = prime * result + ((getApplytype() == null) ? 0 : getApplytype().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", thesisid=").append(thesisid);
        sb.append(", reason=").append(reason);
        sb.append(", studentconf=").append(studentconf);
        sb.append(", advice=").append(advice);
        sb.append(", teacherconf=").append(teacherconf);
        sb.append(", review=").append(review);
        sb.append(", orgconf=").append(orgconf);
        sb.append(", applytype=").append(applytype);
        sb.append("]");
        return sb.toString();
    }
}