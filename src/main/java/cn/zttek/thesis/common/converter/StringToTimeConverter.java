package cn.zttek.thesis.common.converter;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guin_guo on 2015/11/20.
 */
public class StringToTimeConverter {
    private static final List<String> formarts = new ArrayList<>(4);
    static{
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy-MM-dd HH:mm:ss");
    }

    public static Timestamp convert(String source) {
        String value = source.trim();
        if ("".equals(value)) {
            return null;
        }
        if (source.contains("T")) {
            source = source.replace("T", " ");
        }
        if(source.length() > 19){
            source = source.substring(0, 19);
            System.out.println("===============>" + source);
        }
        if(source.matches("^\\d{4}-\\d{1,2}$")){
            return parseDate(source, formarts.get(0));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
            return parseDate(source, formarts.get(1));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}$")){
            return parseDate(source, formarts.get(2));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$")){
            return parseDate(source, formarts.get(3));
        }else {
            throw new IllegalArgumentException("Invalid string value '" + source + "'");
        }
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr
     *            String 字符型日期
     * @param format
     *            String 格式
     * @return Timestamp 日期
     */
    public static Timestamp parseDate(String dateStr, String format) {
        Timestamp ts = null;
        Date date;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
            ts = new Timestamp(date.getTime());
        } catch (Exception e) {
        }
        return ts;
    }

    public static void main(String[] args) {
        System.err.println(new StringToTimeConverter().convert("2015-11-21"));
    }

}
