package cn.zttek.thesis.modules.model;

import cn.zttek.thesis.common.base.BaseModel;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public class DefenseGroup extends BaseModel {
    private Long projectid;

    private Long taskid;

    private Integer groupno;

    private String grouptype;

    private Long leaderid;

    private Long secretaryid;

    private Timestamp defensetime;

    private String defenseroom;

    private String teachers;

    private String students;

    /**
     * 前端显示答辩组长名字用
     */
    private String leaderName;
    /**
     * 前端显示答辩秘书名字用
     */
    private String secretaryName;

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getSecretaryName() {
        return secretaryName;
    }

    public void setSecretaryName(String secretaryName) {
        this.secretaryName = secretaryName;
    }

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public Integer getGroupno() {
        return groupno;
    }

    public void setGroupno(Integer groupno) {
        this.groupno = groupno;
    }

    public String getGrouptype() {
        return grouptype;
    }

    public void setGrouptype(String grouptype) {
        this.grouptype = grouptype == null ? null : grouptype.trim();
    }

    public Long getLeaderid() {
        return leaderid;
    }

    public void setLeaderid(Long leaderid) {
        this.leaderid = leaderid;
    }

    public Long getSecretaryid() {
        return secretaryid;
    }

    public void setSecretaryid(Long secretaryid) {
        this.secretaryid = secretaryid;
    }

    public Timestamp getDefensetime() {
        return defensetime;
    }

    public void setDefensetime(Timestamp defensetime) {
        this.defensetime = defensetime;
    }

    public String getDefenseroom() {
        return defenseroom;
    }

    public void setDefenseroom(String defenseroom) {
        this.defenseroom = defenseroom == null ? null : defenseroom.trim();
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers == null ? null : teachers.trim();
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students == null ? null : students.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectid=").append(projectid);
        sb.append(", taskid=").append(taskid);
        sb.append(", groupno=").append(groupno);
        sb.append(", grouptype=").append(grouptype);
        sb.append(", leader=").append(leaderid);
        sb.append(", secretary=").append(secretaryid);
        sb.append(", defensetime=").append(defensetime);
        sb.append(", defenseroom=").append(defenseroom);
        sb.append(", teachers=").append(teachers);
        sb.append(", students=").append(students);
        sb.append("]");
        return sb.toString();
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
        DefenseGroup other = (DefenseGroup) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProjectid() == null ? other.getProjectid() == null : this.getProjectid().equals(other.getProjectid()))
                && (this.getTaskid() == null ? other.getTaskid() == null : this.getTaskid().equals(other.getTaskid()))
            && (this.getGroupno() == null ? other.getGroupno() == null : this.getGroupno().equals(other.getGroupno()))
            && (this.getGrouptype() == null ? other.getGrouptype() == null : this.getGrouptype().equals(other.getGrouptype()))
            && (this.getLeaderid() == null ? other.getLeaderid() == null : this.getLeaderid().equals(other.getLeaderid()))
            && (this.getSecretaryid() == null ? other.getSecretaryid() == null : this.getSecretaryid().equals(other.getSecretaryid()))
            && (this.getDefensetime() == null ? other.getDefensetime() == null : this.getDefensetime().equals(other.getDefensetime()))
            && (this.getDefenseroom() == null ? other.getDefenseroom() == null : this.getDefenseroom().equals(other.getDefenseroom()))
            && (this.getCdate() == null ? other.getCdate() == null : this.getCdate().equals(other.getCdate()))
            && (this.getTeachers() == null ? other.getTeachers() == null : this.getTeachers().equals(other.getTeachers()))
            && (this.getStudents() == null ? other.getStudents() == null : this.getStudents().equals(other.getStudents()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProjectid() == null) ? 0 : getProjectid().hashCode());
        result = prime * result + ((getTaskid() == null) ? 0 : getTaskid().hashCode());
        result = prime * result + ((getGroupno() == null) ? 0 : getGroupno().hashCode());
        result = prime * result + ((getGrouptype() == null) ? 0 : getGrouptype().hashCode());
        result = prime * result + ((getLeaderid() == null) ? 0 : getLeaderid().hashCode());
        result = prime * result + ((getSecretaryid() == null) ? 0 : getSecretaryid().hashCode());
        result = prime * result + ((getDefensetime() == null) ? 0 : getDefensetime().hashCode());
        result = prime * result + ((getDefenseroom() == null) ? 0 : getDefenseroom().hashCode());
        result = prime * result + ((getCdate() == null) ? 0 : getCdate().hashCode());
        result = prime * result + ((getTeachers() == null) ? 0 : getTeachers().hashCode());
        result = prime * result + ((getStudents() == null) ? 0 : getStudents().hashCode());
        return result;
    }
}