package cn.zttek.thesis.modules.expand;

import java.io.Serializable;

/**
 * @描述: 教师出题数量扩展实体
 * @作者: Pengo.Wen
 * @日期: 2016-09-18 15:34
 * @版本: v1.0
 */
public class ThesisCountExpand implements Serializable{

    private String account;
    private String username;
    private Integer cnt;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}
