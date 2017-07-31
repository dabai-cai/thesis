package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.mapper.TitleMapper;
import cn.zttek.thesis.modules.mapper.UserInfoMapper;
import cn.zttek.thesis.modules.model.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述: 职称相关业务处理类
 * @作者: Pengo.Wen
 * @日期: 2016-08-25 10:55
 * @版本: v1.0
 */
@Service
public class TitleService extends BaseService<Title> {

    @Autowired
    private TitleMapper titleMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    /**
     * 检查职称名称是否已存在
     * @param title
     * @return
     * @throws Exception
     */
    public boolean checkName(Title title)throws Exception{
        log.info("===检查职称名称[" + title.getName() + "]是否已存在===");
        Title temp = titleMapper.getByName(title.getName());
        if(temp != null && !temp.getId().equals(title.getId())){
            return true;
        }
        return false;
    }

    /**
     * 删除某个职称，在删除前需要检查
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteOnCheck(Long id) throws Exception{
        log.info("===删除某个职称[" + id + "]，在删除前需要检查===");
        String msg = "";

        Title title = this.queryById(id);

        Long cnt = userInfoMapper.countUsed(id);
        if(cnt > 0){
            msg = "删除失败, 职称[" + title.getName() + "]已被使用，请先在教师资料中解除对该职称的使用！";
            return msg;
        }

        titleMapper.deleteByPrimaryKey(id);
        msg = "职称[" + title.getName() + "]删除成功！";
        return msg;
    }


    /**
     * 将职称列表转换成map集合，以方便使用, key为职称名称
     * @return
     */
    public Map<String, Title> getTitleMap() throws Exception{
        Map<String, Title> titleMap = new HashMap<>(10);
        List<Title> titles = this.listAll();
        titles.forEach(title -> titleMap.put(title.getName(), title));
        return titleMap;
    }

}
