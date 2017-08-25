package cn.zttek.thesis.modules.model;

import cn.zttek.thesis.common.base.BaseModel;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public class DefenseTask extends BaseModel {

    private Long projectid;

    private String name;

    private String president;

    private String contact;

    private String remark;

    private Integer nums;

    private Timestamp defensetime;


    private String teachers;

    private String students;

    /**
     * 添加此字段作为前端显示答辩总人数所用
     */
    private Integer defenseNum;

    /**
     * 添加此字段作为前端显示已答辩人数所用
     */
    private Integer AllotNum;

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public Timestamp getDefensetime() {
        return defensetime;
    }

    public void setDefensetime(Timestamp defensetime) {
        this.defensetime = defensetime;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    public Integer getDefenseNum() {
        return defenseNum;
    }

    public void setDefenseNum(Integer defenseNum) {
        this.defenseNum = defenseNum;
    }

    public Integer getAllotNum() {
        return AllotNum;
    }

    public void setAllotNum(Integer allotNum) {
        AllotNum = allotNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectid=").append(projectid);
        sb.append(", name=").append(name);
        sb.append(", president=").append(president);
        sb.append(", contact=").append(contact);
        sb.append(", remark=").append(remark);
        sb.append(", nums=").append(nums);
        sb.append(", defensetime=").append(defensetime);
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
        DefenseTask other = (DefenseTask) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProjectid() == null ? other.getProjectid() == null : this.getProjectid().equals(other.getProjectid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getPresident() == null ? other.getPresident() == null : this.getPresident().equals(other.getPresident()))
            && (this.getContact() == null ? other.getContact() == null : this.getContact().equals(other.getContact()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getNums() == null ? other.getNums() == null : this.getNums().equals(other.getNums()))
            && (this.getDefensetime() == null ? other.getDefensetime() == null : this.getDefensetime().equals(other.getDefensetime()))
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
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPresident() == null) ? 0 : getPresident().hashCode());
        result = prime * result + ((getContact() == null) ? 0 : getContact().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getNums() == null) ? 0 : getNums().hashCode());
        result = prime * result + ((getDefensetime() == null) ? 0 : getDefensetime().hashCode());
        result = prime * result + ((getCdate() == null) ? 0 : getCdate().hashCode());
        result = prime * result + ((getTeachers() == null) ? 0 : getTeachers().hashCode());
        result = prime * result + ((getStudents() == null) ? 0 : getStudents().hashCode());
        return result;
    }
}