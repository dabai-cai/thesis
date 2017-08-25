package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.BaseService;
import cn.zttek.thesis.modules.enums.UserType;
import cn.zttek.thesis.modules.mapper.AdviceMapper;
import cn.zttek.thesis.modules.model.Advice;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mankind on 2017/8/11.
 */
@Service
public class AdviceService extends BaseService<Advice> {
    @Autowired
    private AdviceMapper adviceMapper;

    /**
     * 超级管理员默认不传入orgid,查找出全部公告，可以传入orgid查找对应机构的公告
     * 管理员默认传入自身orgid,查找机构下公告
     * @param page
     * @param rows
     * @param orgid
     * @param keywords
     * @return
     * @throws Exception
     */
    public PageInfo<Advice> listAdvice(Integer page, Integer rows, Long orgid, String keywords) throws Exception{
        log.info("===查询公告列表===");
        PageHelper.startPage(page, rows);
        List<Advice> list = adviceMapper.listByOrgAndKeywords(orgid,keywords);
        PageInfo<Advice> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 删除公告
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteById(Long id) throws  Exception{
        log.info("===删除某个公告[" + id + "]===");
        String msg = "";
        Advice advice=this.queryById(id);
        adviceMapper.deleteByPrimaryKey(id);
        msg="公告删除成功";
        return msg;
    }

    /**
     * 查询用户首页显示的公告列表，不同用户显示不同的公告
     * @param page
     * @param rows
     * @param orgid
     * @param keywords
     * @param userType
     * @return
     * @throws Exception
     */
    public PageInfo<Advice> listByUserType(Integer page, Integer rows, Long orgid, String keywords, UserType userType) throws Exception{
        log.info("===查询首页的公告列表===");
        PageHelper.startPage(page, rows);
        List<Advice> list = adviceMapper.listByUserType(orgid,keywords,userType);
        PageInfo<Advice> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
