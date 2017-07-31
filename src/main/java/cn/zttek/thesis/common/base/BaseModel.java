package cn.zttek.thesis.common.base;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @描述: 基础实体模型
 * @作者: Pengo.Wen
 * @日期: 2016-08-09 16:01
 * @版本: v1.0
 */
public class BaseModel implements Serializable {

    private Long id;
    private Timestamp cdate;
    private Timestamp mdate;
    private Boolean isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCdate() {
        return cdate;
    }

    public void setCdate(Timestamp cdate) {
        this.cdate = cdate;
    }

    public Timestamp getMdate() {
        return mdate;
    }

    public void setMdate(Timestamp mdate) {
        this.mdate = mdate;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}
