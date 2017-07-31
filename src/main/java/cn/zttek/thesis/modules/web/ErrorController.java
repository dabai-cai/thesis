package cn.zttek.thesis.modules.web;

import cn.zttek.thesis.common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @描述: 错误页面跳转控制器
 * @作者: Pengo.Wen
 * @日期: 2016-10-09 22:42
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/error")
public class ErrorController extends BaseController{

    @RequestMapping(value = "/{type}", produces = "text/html;charset=utf-8")
    public String error(@PathVariable String type) throws Exception{
        return "console/" + type;
    }

}
