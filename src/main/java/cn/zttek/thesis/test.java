package cn.zttek.thesis;


import cn.zttek.thesis.common.utils.JsonUtils;
import cn.zttek.thesis.modules.expand.ThesisDefenseStudent;

import java.util.List;

public class test {
    public static void main(String[] args) {
        String students="[{\"studentid\":149,\"stuno\":\"201330850117\",\"stuname\":\"林伟兴\",\"clazz\":\"工业工程1班\"},{\"studentid\":150,\"stuno\":\"201330850118\",\"stuname\":\"刘振\",\"clazz\":\"工业工程1班\"},{\"studentid\":151,\"stuno\":\"201330850119\",\"stuname\":\"罗永强\",\"clazz\":\"工业工程1班\"},{\"studentid\":152,\"stuno\":\"201330850120\",\"stuname\":\"潘荣沃\",\"clazz\":\"工业工程1班\"},{\"studentid\":153,\"stuno\":\"201330850121\",\"stuname\":\"彭永康\",\"clazz\":\"工业工程1班\"},{\"studentid\":154,\"stuno\":\"201330850122\",\"stuname\":\"邱文添\",\"clazz\":\"工业工程1班\"}]";
        if(students.contains("林伟兴!")){
            System.out.println("成功！");
        }
    }
}
