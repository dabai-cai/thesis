package cn.zttek.thesis.modules.model;

import cn.zttek.thesis.common.base.BaseModel;
import java.util.Date;

public class Upload extends BaseModel {
    private Long id;
    private Long thesisid;

    public Long getThesisid() {
        return thesisid;
    }

    public void setThesisid(Long thesisid) {
        this.thesisid = thesisid;
    }

    private String path;



    private Long studentid;

    private Long teacherid;

    private Long projectid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }

    public Long getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(Long teacherid) {
        this.teacherid = teacherid;
    }

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }
}