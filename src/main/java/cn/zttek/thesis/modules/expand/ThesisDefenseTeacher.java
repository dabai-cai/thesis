package cn.zttek.thesis.modules.expand;

import cn.zttek.thesis.modules.model.Title;

import java.util.*;

/**
 * Created by Mankind on 2017/8/15.
 */
public class ThesisDefenseTeacher implements Comparable<ThesisDefenseTeacher>{
    /**
     * ID、工号、姓名、职称名字、职称等级
     */
    private Long teacherid;
    private String account;
    private String userName;
    private Title title;

    public Long getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(Long teacherid) {
        this.teacherid = teacherid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
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
        ThesisDefenseTeacher other = (ThesisDefenseTeacher) that;
        return (this.getTeacherid() == null ? other.getTeacherid() == null : this.getTeacherid().equals(other.getTeacherid()))
                && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
                && (this.getTitle()== null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
                && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getTeacherid() == null) ? 0 : getTeacherid().hashCode());
        return result;
    }
    @Override
    public int compareTo(ThesisDefenseTeacher o) {
        return this.account.compareTo(o.getAccount());
    }

}
