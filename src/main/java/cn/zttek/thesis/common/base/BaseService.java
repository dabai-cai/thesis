package cn.zttek.thesis.common.base;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @描述: 业务逻辑层基类
 * @作者: Pengo.Wen
 * @日期: 2016-08-11 15:58
 * @版本: v1.0
 */
public class BaseService<T extends BaseModel> {


    @Autowired
    BaseMapper<T> mapper;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public List<T> listAll() throws Exception{
        log.info("===查询所有实体记录===");
        return mapper.selectAll();
    }

    public void delete(Long id) throws Exception{
        log.info("===删除实体记录===");
        T record = queryById(id);
        record.setMdate(new Timestamp(System.currentTimeMillis()));
        record.setValid(false);
        mapper.updateByPrimaryKey(record);
    }

    public T insert(T record) throws Exception{
        log.info("===保存实体记录===");
        record.setCdate(new Timestamp(System.currentTimeMillis()));
        record.setMdate(new Timestamp(System.currentTimeMillis()));
        record.setValid(true);
        mapper.insert(record);
        return record;
    }

    public T update(T record) throws Exception{
        log.info("===更新实体记录===");
        record.setMdate(new Timestamp(System.currentTimeMillis()));
        if (record.getId() == null) {
            record.setCdate(new Timestamp(System.currentTimeMillis()));
            mapper.insert(record);
        } else {
            mapper.updateByPrimaryKey(record);
        }
        return record;
    }
    
    @SuppressWarnings("unchecked")
    public T queryById(Long id) throws Exception{
        log.info("===根据id查询实体记录===");
        T record = mapper.selectByPrimaryKey(id);
        return record;
    }

}
