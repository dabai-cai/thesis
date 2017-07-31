package cn.zttek.thesis.modules.model;


import cn.zttek.thesis.common.base.BaseModel;

@SuppressWarnings("serial")
public class Permission extends BaseModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.keystr
     *
     * @mbggenerated
     */
    private String keystr;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.resid
     *
     * @mbggenerated
     */
    private Long resid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.name
     *
     * @return the value of permission.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.name
     *
     * @param name the value for permission.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.keystr
     *
     * @return the value of permission.keystr
     *
     * @mbggenerated
     */
    public String getKeystr() {
        return keystr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.keystr
     *
     * @param keystr the value for permission.keystr
     *
     * @mbggenerated
     */
    public void setKeystr(String keystr) {
        this.keystr = keystr == null ? null : keystr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.resid
     *
     * @return the value of permission.resid
     *
     * @mbggenerated
     */
    public Long getResid() {
        return resid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.resid
     *
     * @param resid the value for permission.resid
     *
     * @mbggenerated
     */
    public void setResid(Long resid) {
        this.resid = resid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table permission
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", name=").append(name);
        sb.append(", keystr=").append(keystr);
        sb.append(", resid=").append(resid);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table permission
     *
     * @mbggenerated
     */
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
        Permission other = (Permission) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getKeystr() == null ? other.getKeystr() == null : this.getKeystr().equals(other.getKeystr()))
            && (this.getResid() == null ? other.getResid() == null : this.getResid().equals(other.getResid()))
            && (this.getValid() == null ? other.getValid() == null : this.getValid().equals(other.getValid()))
            && (this.getCdate() == null ? other.getCdate() == null : this.getCdate().equals(other.getCdate()))
            && (this.getMdate() == null ? other.getMdate() == null : this.getMdate().equals(other.getMdate()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table permission
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getKeystr() == null) ? 0 : getKeystr().hashCode());
        result = prime * result + ((getResid() == null) ? 0 : getResid().hashCode());
        result = prime * result + ((getValid() == null) ? 0 : getValid().hashCode());
        result = prime * result + ((getCdate() == null) ? 0 : getCdate().hashCode());
        result = prime * result + ((getMdate() == null) ? 0 : getMdate().hashCode());
        return result;
    }
}