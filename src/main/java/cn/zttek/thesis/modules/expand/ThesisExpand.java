package cn.zttek.thesis.modules.expand;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @描述: 论文题目扩展实体类
 * @作者: Pengo.Wen
 * @日期: 2016-09-20 16:04
 * @版本: v1.0
 */
public class ThesisExpand implements Serializable {


    private Long id;
    private String topic;
    private Long teacherid;
    private Long studentid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long viewerid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String teacher;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String stuno;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String stuname;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String viewer;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long taskid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long midcheckid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long scoreid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double mark;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String state;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tconfirm;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sconfirm;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String oconfirm;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long uploadid;

    public Long getUploadid() {
        return uploadid;
    }

    public void setUploadid(Long uploadid) {
        this.uploadid = uploadid;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  Timestamp lastuptime;

    public Timestamp getLastuptime() {
        return lastuptime;
    }

    public void setLastuptime(Timestamp lastuptime) {
        this.lastuptime = lastuptime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(Long teacherid) {
        this.teacherid = teacherid;
    }

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }

    public Long getViewerid() {
        return viewerid;
    }

    public void setViewerid(Long viewerid) {
        this.viewerid = viewerid;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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

    public String getViewer() {
        return viewer;
    }

    public void setViewer(String viewer) {
        this.viewer = viewer;
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public Long getMidcheckid() {
        return midcheckid;
    }

    public void setMidcheckid(Long midcheckid) {
        this.midcheckid = midcheckid;
    }

    public Long getScoreid() {
        return scoreid;
    }

    public void setScoreid(Long scoreid) {
        this.scoreid = scoreid;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTconfirm() {
        return tconfirm;
    }

    public void setTconfirm(String tconfirm) {
        this.tconfirm = tconfirm;
    }

    public String getSconfirm() {
        return sconfirm;
    }

    public void setSconfirm(String sconfirm) {
        this.sconfirm = sconfirm;
    }

    public String getOconfirm() {
        return oconfirm;
    }

    public void setOconfirm(String oconfirm) {
        this.oconfirm = oconfirm;
    }
}
