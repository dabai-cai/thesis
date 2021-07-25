package cn.zttek.thesis.modules.enums;

import cn.zttek.thesis.common.mybatis.Identifiable;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Mankind on 2017/8/24.
 */
public enum     DefenseGroupType implements Identifiable<Integer> {
    NORMAL(0, "正常答辩"),
    EXCEL(2, "争优答辩");
    private Integer val;
    private String label;
    DefenseGroupType(Integer val, String label) {
        this.val = val;
        this.label = label;
    }
    public static DefenseGroupType getById(int id){
        DefenseGroupType[] groupTypes = DefenseGroupType.values();
        for(DefenseGroupType groupType : groupTypes){
            if(groupType.val == id){
                return groupType;
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
