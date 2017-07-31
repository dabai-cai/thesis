package cn.zttek.thesis.common.utils;



import cn.zttek.thesis.common.base.BaseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ztcms on 2015/10/19.
 */
public class CommonUtils {
    /**
     * 将页面传来的id转成long型返回，
     * 如果没有则返回null
     *
     * @param tempId
     * @return
     */
    public static Long getId(String tempId) {
        try {
            Long id = Long.parseLong(tempId);
            if (id != null) {
                return id;
            } else return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * @param ids 表单传来的一组id
     * @return
     */
    public static List<Long> parseIds(String[] ids) {
        if (ids == null || ids.length == 0) {
            return null;
        }
        List<Long> result = new ArrayList<>();
        for (String id : ids) {
            result.add(Long.parseLong(id));
        }
        return result;
    }

    public static List<Long> getIds(Collection<? extends BaseModel> entities) {
        if (entities == null || entities.size() == 0) {
            return null;
        }
        List<Long> ids = entities.stream().map(BaseModel::getId).collect(Collectors.toList());
        return ids;
    }

    public static  Long[] getIdsArray(String ids) {
        List<String> idsList = Arrays.asList(ids.split(","));
        Long[] idsArry = new Long[idsList.size()];
        for (int i = 0; i < idsList.size(); i++) {
            idsArry[i] = Long.valueOf(idsList.get(i));
        }
        return idsArry;
    }

}
