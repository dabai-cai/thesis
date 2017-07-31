package cn.zttek.thesis.modules.holder;

import cn.zttek.thesis.modules.model.User;
import gnu.trove.map.hash.TLongObjectHashMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @描述: 用户数据持有量
 * @作者: Pengo.Wen
 * @日期: 2016-09-21 11:46
 * @版本: v1.0
 */
@Component
public class UserHolder {

    public ConcurrentMap<Long, TLongObjectHashMap<User>> puserMap = new ConcurrentHashMap<>();

    public void addUser(Long projid, User user){
        if(!puserMap.containsKey(projid)){
            puserMap.put(projid, new TLongObjectHashMap<>());
        }
        puserMap.get(projid).put(user.getId(), user);
    }

    public void updateUser(User user){
        for (Map.Entry<Long, TLongObjectHashMap<User>> entry : puserMap.entrySet()){
            TLongObjectHashMap<User> userMap = entry.getValue();
            if(userMap.containsKey(user.getId())){
                userMap.put(user.getId(), user);
                break;
            }
        }

    }

    public void deleteUser(User user){
        for (Map.Entry<Long, TLongObjectHashMap<User>> entry : puserMap.entrySet()){
            TLongObjectHashMap<User> userMap = entry.getValue();
            if(userMap.containsKey(user.getId())){
                userMap.remove(user.getId());
                break;
            }
        }
    }

    public void addUsers(Long projid, List<User> users){
        if(!puserMap.containsKey(projid)){
            puserMap.put(projid, new TLongObjectHashMap<>());
        }

        for (int i = 0; i < users.size(); i++) {
            puserMap.get(projid).put(users.get(i).getId(), users.get(i));
        }
    }

    public void deleteUsers(Long projid) {
        if(puserMap.containsKey(projid)){
           puserMap.get(projid).clear();
        }
    }

}
