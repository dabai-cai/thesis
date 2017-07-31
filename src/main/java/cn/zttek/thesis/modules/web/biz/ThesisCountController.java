package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.modules.expand.ThesisCountExpand;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.service.ThesisService;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @描述: 处理教师出题数量控制器
 * @作者: Pengo.Wen
 * @日期: 2016-09-18 15:56
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/tcount")
public class ThesisCountController extends BaseController{

    @Autowired
    private ThesisService thesisService;

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(Model model) throws Exception{
        Project project = ThesisParam.getCurrentProj();
        List<ThesisCountExpand> list = thesisService.listThesisCount(project.getId());
        ThesisCountExpand max = list.stream().max((t1, t2) -> (int)(t1.getCnt() - t2.getCnt())).get();
        //柱状图的横向轴值
        int[] xArry = new int[max.getCnt() + 1];
        for (int i = 0; i < xArry.length; i++) {
            xArry[i] = i;
        }

        int maxCnt = 0;
        int[] yArry = new int[max.getCnt() + 1];
        for(int i = 0; i < list.size(); i++){
            int cnt = list.get(i).getCnt();
            yArry[cnt] ++ ;
        }

        model.addAttribute("maxCnt", maxCnt);
        model.addAttribute("xArry", xArry);
        model.addAttribute("yArry", yArry);
        return "console/thesis/count/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Integer page, Integer rows, String sort, String order, String keywords) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        Project project = ThesisParam.getCurrentProj();
        PageInfo<ThesisCountExpand> pageInfo = thesisService.listThesisCount(page, rows, project.getId(), keywords, sort, order);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }
}
