package cn.zttek.thesis.modules.enums;

import cn.zttek.thesis.common.mybatis.Identifiable;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @描述: 用户类型
 * @作者: Pengo.Wen
 * @日期: 2016-08-11 16:34
 * @版本: v1.0
 */
public enum UserType implements Identifiable<String>{
    SUPER("超级管理员"),
    ADMIN("管理员"),
    TEACHER("教师"),
    STUDENT("学生");

    private String label;

    UserType(String label) {
        this.label = label;
    }

    @Override
    public String getId() {
        return this.label;
    }

    public static UserType getById(long id){
        UserType[] types = UserType.values();
        for(UserType type : types){
            if(type.ordinal() == id){
                return type;
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
    public String toString() {
        return this.label;
    }
}
