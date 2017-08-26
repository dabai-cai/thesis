package cn.zttek.thesis.modules.expand;

import cn.zttek.thesis.modules.enums.DefenseStatus;

/**
 * Created by Mankind on 2017/8/15.
 */
public class ThesisDefenseStudent implements Comparable<ThesisDefenseStudent>{
    /**
     * ID、学号、姓名、班级、答辩类型
     */
    private Long studentid;
    private String stuno;
    private String stuname;
    private String clazz;
    private DefenseStatus defenseStatus;
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

    public DefenseStatus getDefenseStatus() {
        return defenseStatus;
    }

    public void setDefenseStatus(DefenseStatus defenseStatus) {
        this.defenseStatus = defenseStatus;
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
                && (this.getDefenseStatus() == null ? other.getDefenseStatus() == null : this.getDefenseStatus().equals(other.getDefenseStatus()))
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
        result = prime * result + ((getDefenseStatus() == null) ? 0 : getDefenseStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ThesisDefenseStudent{" +
                "studentid=" + studentid +
                ", stuno='" + stuno + '\'' +
                ", stuname='" + stuname + '\'' +
                ", clazz='" + clazz + '\'' +
                ", defenseStatus=" + defenseStatus +
                '}';
    }

    @Override
    public int compareTo(ThesisDefenseStudent o) {
        return this.stuno.compareTo(o.getStuno());
    }
}
