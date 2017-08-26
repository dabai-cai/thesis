package cn.zttek.thesis.utils;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @描述: 将请求参数封装成对象
 * @作者: Pengo.Wen
 * @日期: 2016-05-19 17:47
 * @版本: v1.0
 */
public class RequestUtil {


    public static <T> T getParamObject(HttpServletRequest request, Class<T> clz){
        Map<String, String[]> reqMap = request.getParameterMap();
        Set<String> keySet = reqMap.keySet();
        T t = null;
        try {
            t = clz.newInstance();
            for(String key : keySet){
                if(key.startsWith(clz.getSimpleName().toLowerCase() + ".")){
                    String[] value = reqMap.get(key);
                    String fieldName = key.split("\\.")[1];
                    if(reqMap.get(key).length == 1){
                        BeanUtils.copyProperty(t, fieldName, value[0]);
                    }else {
                        BeanUtils.copyProperty(t, fieldName, value);
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return t;
    }


    public static <T> List<T> getParamObjectList(HttpServletRequest request, Class<T> clz){
        Map<String, String[]> reqMap = request.getParameterMap();
        Set<String> keySet = reqMap.keySet();
        List<T> list = new ArrayList<T>();
        T t = null;
        try {
            for(String key : keySet){
                if(key.startsWith(clz.getSimpleName().toLowerCase() + ".")){
                    String[] value = reqMap.get(key);
                    String fieldName = key.split("\\.")[1];
                    for (int i = 0; i < reqMap.get(key).length; i++) {
                        try {
                            t = list.get(i);
                        }catch (IndexOutOfBoundsException ex){
                            t = clz.newInstance();
                            list.add(t);
                        }
                        BeanUtils.copyProperty(t, fieldName, value[i]);
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static <T> T setParam(HttpServletRequest request, Class<T> clz){
        Map<String, String[]> reqMap = request.getParameterMap();
        Set<String> keySet = reqMap.keySet();
        T t = null;
        try {
            t = clz.newInstance();
            for(String key : keySet){
                String[] value = reqMap.get(key);
                if(reqMap.get(key).length == 1){
                    BeanUtils.copyProperty(t, key, value[0]);
                }else {
                    BeanUtils.copyProperty(t, key, value);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return t;
    }

}
