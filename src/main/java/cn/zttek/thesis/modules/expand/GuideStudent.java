package cn.zttek.thesis.modules.expand;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.sql.Timestamp;

public class GuideStudent implements Serializable {
    private Long id;
    private Long thesisid;
    private String topic;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String account;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String stuname;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String leader;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String secretary;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  String defenseType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp defenseTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String defenseRoom;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer groupno;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long groupid;

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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getSecretary() {
        return secretary;
    }

    public void setSecretary(String secretary) {
        this.secretary = secretary;
    }

    public String getDefenseType() {
        return defenseType;
    }

    public void setDefenseType(String defenseType) {
        this.defenseType = defenseType;
    }

    public Timestamp getDefenseTime() {
        return defenseTime;
    }

    public void setDefenseTime(Timestamp defenseTime) {
        this.defenseTime = defenseTime;
    }

    public String getDefenseRoom() {
        return defenseRoom;
    }

    public void setDefenseRoom(String defenseRoom) {
        this.defenseRoom = defenseRoom;
    }

    public Integer getGroupno() {
        return groupno;
    }

    public void setGroupno(Integer groupno) {
        this.groupno = groupno;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }
}
