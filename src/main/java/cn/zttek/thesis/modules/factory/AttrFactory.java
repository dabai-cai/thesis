package cn.zttek.thesis.modules.factory;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.common.base.BaseModel;
import cn.zttek.thesis.modules.mapper.PdirectionMapper;
import cn.zttek.thesis.modules.mapper.PpropertyMapper;
import cn.zttek.thesis.modules.mapper.PsourceMapper;
import cn.zttek.thesis.modules.model.Pdirection;
import cn.zttek.thesis.modules.model.Pproperty;
import cn.zttek.thesis.modules.model.Psource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @描述: 论文研究方向、论文来源、论文属性等数据访问层的实例生产工具类
 * @作者: Pengo.Wen
 * @日期: 2016-09-12 15:41
 * @版本: v1.0
 */
@Component("archFactory")
public class AttrFactory {

    @Autowired
    private PdirectionMapper pdirectionMapper;
    @Autowired
    private PsourceMapper psourceMapper;
    @Autowired
    private PpropertyMapper ppropertyMapper;

    public BaseMapper getMapper(String op) {
        switch (op) {
            case "direction":
                return pdirectionMapper;
            case "source":
                return psourceMapper;
            case "property":
                return ppropertyMapper;
            default:
                return pdirectionMapper;
        }
    }

    public void updateModel(String op, BaseModel model,  String value){
        switch (op) {
            case "direction":
                Pdirection pdirection = (Pdirection)model;
                pdirection.setDirection(value);
                break;
            case "source":
                Psource psource = (Psource)model;
                psource.setSource(value);
                break;
            case "property":
                Pproperty property = (Pproperty)model;
                property.setProperty(value);
                break;
            default:
                break;
        }
    }

    public BaseModel createModel(String op, String value){
        switch (op) {
            case "direction":
                return new Pdirection(value);
            case "source":
                return new Psource(value);
            case "property":
                return new Pproperty(value);
            default:
                return new Pdirection(value);
        }
    }

    public String getLabel(String op){
        switch (op) {
            case "direction":
                return "论文研究方向";
            case "source":
                return "论文来源";
            case "property":
                return "论文属性";
            default:
                return "";
        }
    }

    public String getValue(String op, BaseModel model){
        switch (op) {
            case "direction":
                Pdirection pdirection = (Pdirection)model;
                return pdirection.getDirection();
            case "source":
                Psource psource = (Psource)model;
                return psource.getSource();
            case "property":
                Pproperty property = (Pproperty)model;
                return property.getProperty();
            default:
                return "";
        }
    }
}
