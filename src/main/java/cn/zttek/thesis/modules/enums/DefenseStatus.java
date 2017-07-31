package cn.zttek.thesis.modules.enums;

import cn.zttek.thesis.common.mybatis.Identifiable;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @描述: 答辩状态
 * @作者: Pengo.Wen
 * @日期: 2016-09-13 15:23
 * @版本: v1.0
 */
public enum DefenseStatus implements Identifiable<Integer>{
    NORMAL(0, "正常答辩"),
    EXCEL(2, "争优答辩"),
    DELAY(4, "延期答辩");

    private Integer val;
    private String label;

    DefenseStatus(Integer val, String label) {
        this.val = val;
        this.label = label;
    }

    public static DefenseStatus getById(int id){
        DefenseStatus[] statuses = DefenseStatus.values();
        for(DefenseStatus status : statuses){
            if(status.val == id){
                return status;
            }
        }
        return null;
    }



    @Override
    public Integer getId() {
        return this.val;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
