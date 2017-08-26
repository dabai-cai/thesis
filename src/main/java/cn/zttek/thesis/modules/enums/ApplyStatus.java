package cn.zttek.thesis.modules.enums;

import cn.zttek.thesis.common.mybatis.Identifiable;
import cn.zttek.thesis.modules.model.Apply;
import com.fasterxml.jackson.annotation.JsonValue;
public enum ApplyStatus implements Identifiable<Integer> {
    NORMAL(0, "未确认"),
    EXCEL(1, "确认"),
    DELAY(-1, "拒绝");

    private Integer val;
    private String label;


    ApplyStatus(Integer val, String label) {
          this.label=label;
          this.val=val;
    }

    public static ApplyStatus getById(Integer id)
    {
        ApplyStatus[] applyStatuses=ApplyStatus.values();
        for(ApplyStatus applyStatus:applyStatuses) {
            if(id==applyStatus.val) {
                return applyStatus;
            }
        }
        return null;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public Integer getId() {
        return this.val;
    }

    @Override
    public String toString() {
        return label;
    }
}
