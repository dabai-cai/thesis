package cn.zttek.thesis.modules.model;

import cn.zttek.thesis.common.base.BaseModel;
import java.util.Date;

public class GoodDelay extends BaseModel {

    private Long thesisid;

    private String reason;

    private Integer studentconf;

    private String advice;

    private Integer teacherconf;

    private String review;

    private Integer orgconf;

    private int applytype;



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

    public Integer getStudentconf() {
        return studentconf;
    }

    public void setStudentconf(Integer studentconf) {
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

    public int getApplytype() {
        return applytype;
    }

    public void setApplytype(int applytype) {
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
        GoodDelay other = (GoodDelay) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getThesisid() == null ? other.getThesisid() == null : this.getThesisid().equals(other.getThesisid()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getStudentconf() == null ? other.getStudentconf() == null : this.getStudentconf().equals(other.getStudentconf()))
            && (this.getAdvice() == null ? other.getAdvice() == null : this.getAdvice().equals(other.getAdvice()))
            && (this.getTeacherconf() == null ? other.getTeacherconf() == null : this.getTeacherconf().equals(other.getTeacherconf()))
            && (this.getReview() == null ? other.getReview() == null : this.getReview().equals(other.getReview()))
            && (this.getOrgconf() == null ? other.getOrgconf() == null : this.getOrgconf().equals(other.getOrgconf()))
            && (this.getCdate() == null ? other.getCdate() == null : this.getCdate().equals(other.getCdate()))
            && (this.getMdate() == null ? other.getMdate() == null : this.getMdate().equals(other.getMdate()));
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
        result = prime * result + ((getCdate() == null) ? 0 : getCdate().hashCode());
        result = prime * result + ((getMdate() == null) ? 0 : getMdate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());

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