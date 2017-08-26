package cn.zttek.thesis.modules.enums;

import cn.zttek.thesis.common.mybatis.Identifiable;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Mankind on 2017/8/10.
 */
public enum AdviceTarget implements Identifiable<Integer> {
    ALL(0, "全体"),
    TEACHER(1, "教师"),
    STUDENT(2, "学生"),
    MANAGER(3,"管理员");


    private Integer val;
    private String label;

    AdviceTarget(Integer val, String label){
        this.val=val;
        this.label=label;
    }

    public static AdviceTarget getById(int id){
        AdviceTarget[] adviceTargets= AdviceTarget.values();
        for(AdviceTarget target: adviceTargets){
            if(target.val==id){
                return target;
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
