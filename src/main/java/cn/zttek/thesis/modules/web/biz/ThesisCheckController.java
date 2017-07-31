package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.CommonUtils;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.Thesis;
import cn.zttek.thesis.modules.service.ThesisService;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * @描述: 论文题目审核相关业务控制器
 * @作者: Pengo.Wen
 * @日期: 2016-09-19 15:19
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/tcheck")
public class ThesisCheckController extends BaseController {



    @Autowired
    private ThesisService thesisService;

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list() throws Exception{
        return "console/thesis/check/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Integer page, Integer rows, Boolean checked, String keywords) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        Project project = ThesisParam.getCurrentProj();
        PageInfo<Thesis> pageInfo = thesisService.listThesisByCheck(page, rows, project.getId(), checked, keywords);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @RequestMapping(value = "/saveSelect", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult saveSelect(String ids, boolean isCheck) {
        EUResult result = new EUResult();
        if (StringUtils.isNotEmpty(ids)) {
            List<Long> idsArry = Arrays.asList(CommonUtils.getIdsArray(ids));
            try {
                result.setStatus(EUResult.OK);
                String msg = thesisService.saveSelect(idsArry, isCheck);
                result.setMsg(msg);
            } catch (Exception e) {
                result.setStatus(EUResult.FAIL);
                result.setMsg((isCheck?"审核":"回退") + "论文题目时发生异常！" + e.getMessage());
            }
        }else{
            result.setStatus(EUResult.FAIL);
            result.setMsg("请选择要" + (isCheck ? "审核" : "回退")  + "的论文题目！");
        }
        return result;
    }

    @RequestMapping(value = "/saveQuery", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUResult saveQuery(Boolean checked, String keywords, boolean isCheck) {
        EUResult result = new EUResult();
        Project project = ThesisParam.getCurrentProj();
        try {
            result.setStatus(EUResult.OK);
            String msg = thesisService.saveQuery(project.getId(), checked, keywords, isCheck);
            result.setMsg(msg);
        } catch (Exception e) {
            result.setStatus(EUResult.FAIL);
            result.setMsg((isCheck?"审核":"回退") + "论文题目时发生异常！" + e.getMessage());
        }
        return result;
    }

}
