package cn.zttek.thesis.modules.holder;

import cn.zttek.thesis.common.utils.SpringContextHolder;
import cn.zttek.thesis.modules.model.Title;
import cn.zttek.thesis.modules.service.TitleService;
import gnu.trove.map.hash.TLongObjectHashMap;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @描述: 职称数据持有量
 * @作者: Pengo.Wen
 * @日期: 2016-10-09 14:30
 * @版本: v1.0
 */
public class TitleHolder {

    public static ConcurrentMap<Long, Title> titleMap = new ConcurrentHashMap<>();
    public static Title[] tArry = null;

    static {
        TitleService titleService = SpringContextHolder.getBean(TitleService.class);
        List<Title> titles = null;
        try {
            titles = titleService.listAll();
            //构造数组，以ID为下标访问，速度更快，建议数据库表title的id不超过100
            Title max = titles.stream().max((t1, t2) -> (int)(t1.getId() - t2.getId())).get();
            tArry = new Title[max.getId().intValue() + 1];

            titles.forEach(title -> tArry[title.getId().intValue()] = title);
            //填充map
            titles.forEach(title -> titleMap.put(title.getId(), title));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Title title){
        titleMap.put(title.getId(), title);
    }

    public static void remove(Long tid){
        titleMap.remove(tid);
    }

    public static String getTitle(Long tid){
        return titleMap.get(tid) == null ? "" : titleMap.get(tid).getName();
    }

    public static String getTitleByArry(Long tid){
        return tArry[tid.intValue()] == null ? "" : tArry[tid.intValue()].getName();
    }
}
