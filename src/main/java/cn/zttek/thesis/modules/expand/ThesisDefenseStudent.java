package cn.zttek.thesis.modules.expand;

import cn.zttek.thesis.modules.model.Thesis;

/**
 * Created by Mankind on 2017/8/15.
 */
public class ThesisDefenseStudent {
    /**
     * ID、学号、姓名、班级
     */
    private Long studentid;
    private String stuno;
    private String stuname;
    private String clazz;

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
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
        ThesisDefenseStudent other = (ThesisDefenseStudent) that;
        return (this.getStudentid() == null ? other.getStudentid() == null : this.getStudentid().equals(other.getStudentid()))
                && (this.getClazz() == null ? other.getClazz() == null : this.getClazz().equals(other.getClazz()))
                && (this.getStuname()== null ? other.getStuname() == null : this.getStuname().equals(other.getStuname()))
                && (this.getStuno() == null ? other.getStuno() == null : this.getStuno().equals(other.getStuno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStudentid() == null) ? 0 : getStudentid().hashCode());
        result = prime * result + ((getClazz() == null) ? 0 : getClazz().hashCode());
        result = prime * result + ((getStuname() == null) ? 0 : getStuname().hashCode());
        result = prime * result + ((getStuno() == null) ? 0 : getStuno().hashCode());
        return result;
    }
}
