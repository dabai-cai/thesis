package cn.zttek.thesis.common.easyui;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 自定easyui的combo响应结构
 * @作者: Pengo.Wen
 * @日期: 2016-09-08 15:08
 * @版本: v1.0
 */
public class EUComboResult<T> {

    private T id;
    private String text;

    public static <T> List<EUComboResult> createResults(List<T> list){
        List<EUComboResult> results = new ArrayList<>();
        for (T t : list){
            EUComboResult<T> result = new EUComboResult<>();
            result.setId(t);
            result.setText(t == null ? "" : t.toString());
            results.add(result);
        }
        return results;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
