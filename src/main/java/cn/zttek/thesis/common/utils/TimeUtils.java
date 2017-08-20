package cn.zttek.thesis.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimeUtils {
    public static String timestampToString(Timestamp timestamp) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
        String time = df.format(timestamp);
        return time;
    }
    public static String currentTime() {
        return new Timestamp(System.currentTimeMillis()).getTime()+"";
    }
}
