package cn.zttek.thesis.modules.service;

import cn.zttek.thesis.common.base.AttrMapper;
import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.common.base.BaseModel;
import cn.zttek.thesis.modules.factory.AttrFactory;
import cn.zttek.thesis.modules.model.Org;
import cn.zttek.thesis.modules.model.OrgModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @描述: 论文研究方向、论文来源、论文属性三类数据的业务逻辑处理代理类
 * @作者: Pengo.Wen
 * @日期: 2016-09-12 15:39
 * @版本: v1.0
 */
@Service
public class ProxyService {

    @Autowired
    private AttrFactory attrFactory;

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 根据数据类型及id获得实例
     * @param op
     * @param id
     * @return
     * @throws Exception
     */
    public BaseModel queryById(String op, Long id) throws Exception{
        log.info("===根据数据类型及id获得实例===");
        BaseMapper mapper = attrFactory.getMapper(op);
        return mapper.selectByPrimaryKey(id);
    }

    public List<BaseModel> listAll(String op, Long orgid) throws Exception{
        AttrMapper mapper = (AttrMapper) attrFactory.getMapper(op);
        return mapper.listByOrg(orgid);
    }

    public BaseModel insert(String op, String value, Long orgid) throws Exception{
        BaseMapper mapper = attrFactory.getMapper(op);
        BaseModel model = attrFactory.createModel(op, value);
        ((OrgModel)model).setOrgid(orgid);
        model.setCdate(new Timestamp(System.currentTimeMillis()));
        model.setMdate(new Timestamp(System.currentTimeMillis()));
        model.setValid(true);
        mapper.insert(model);
        return model;
    }

    public void update(String op, Long id, String value, Long orgid) throws Exception{
        BaseMapper mapper = attrFactory.getMapper(op);
        BaseModel model = mapper.selectByPrimaryKey(id);
        ((OrgModel)model).setOrgid(orgid);
        model.setMdate(new Timestamp(System.currentTimeMillis()));
        attrFactory.updateModel(op, model, value);
        mapper.updateByPrimaryKey(model);
    }

    public boolean checkValue(String op, Long id, String value, Long orgid) throws Exception{
        AttrMapper mapper = (AttrMapper) attrFactory.getMapper(op);
        BaseModel temp = mapper.getByValue(value, orgid);
        if(temp != null && !temp.getId().equals(id)){
            return true;
        }
        return false;
    }

    /**
     * 删除论文相关数据
     * @param op
     * @param id
     * @return
     * @throws Exception
     */
    public String deleteOnCheck(String op, Long id) throws Exception{
        BaseMapper mapper = attrFactory.getMapper(op);
        String msg;
        BaseModel model = this.queryById(op, id);
        if(model == null){
            msg = attrFactory.getLabel(op) + "[" + attrFactory.getValue(op, model) + "]删除成功！";
            return msg;
        }
        mapper.deleteByPrimaryKey(id);
        msg = attrFactory.getLabel(op) +"[" + attrFactory.getValue(op, model) + "]删除成功！";
        return msg;
    }
}
