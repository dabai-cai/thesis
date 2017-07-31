package cn.zttek.thesis.modules.enums;

import cn.zttek.thesis.common.mybatis.Identifiable;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @描述: 职称级别
 * @作者: Pengo.Wen
 * @日期: 2016-08-24 21:16
 * @版本: v1.0
 */
public enum TitleLevel implements Identifiable<String> {
    PRIMARY("初级"),
    JUNIOR("中级"),
    /**
     * Deputy Senior
     */
    DSENIOR("副高级"),
    SENIOR("高级");

    TitleLevel(String label) {
        this.label = label;
    }

    private String label;

    @Override
    public String getId() {
        return label;
    }

    public static TitleLevel getById(long id){
        TitleLevel[] values = TitleLevel.values();
        for(TitleLevel level : values){
            if(level.ordinal() == id){
                return level;
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
