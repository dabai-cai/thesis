package cn.zttek.thesis.modules.model;

import cn.zttek.thesis.common.base.BaseModel;

import java.sql.Timestamp;
import java.util.Date;

@SuppressWarnings("serial")
public class DefenseGroup extends BaseModel {
    private Long projectid;

    private Long taskid;

    private String groupno;

    private String grouptype;

    private String leader;

    private String secretary;

    private Timestamp defensetime;

    private String defenseroom;


    private String teachers;

    private String students;

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

    public String getGroupno() {
        return groupno;
    }

    public void setGroupno(String groupno) {
        this.groupno = groupno == null ? null : groupno.trim();
    }

    public String getGrouptype() {
        return grouptype;
    }

    public void setGrouptype(String grouptype) {
        this.grouptype = grouptype == null ? null : grouptype.trim();
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader == null ? null : leader.trim();
    }

    public String getSecretary() {
        return secretary;
    }

    public void setSecretary(String secretary) {
        this.secretary = secretary == null ? null : secretary.trim();
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
        sb.append(", leader=").append(leader);
        sb.append(", secretary=").append(secretary);
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
            && (this.getLeader() == null ? other.getLeader() == null : this.getLeader().equals(other.getLeader()))
            && (this.getSecretary() == null ? other.getSecretary() == null : this.getSecretary().equals(other.getSecretary()))
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
        result = prime * result + ((getLeader() == null) ? 0 : getLeader().hashCode());
        result = prime * result + ((getSecretary() == null) ? 0 : getSecretary().hashCode());
        result = prime * result + ((getDefensetime() == null) ? 0 : getDefensetime().hashCode());
        result = prime * result + ((getDefenseroom() == null) ? 0 : getDefenseroom().hashCode());
        result = prime * result + ((getCdate() == null) ? 0 : getCdate().hashCode());
        result = prime * result + ((getTeachers() == null) ? 0 : getTeachers().hashCode());
        result = prime * result + ((getStudents() == null) ? 0 : getStudents().hashCode());
        return result;
    }
}