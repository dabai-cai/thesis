package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUDataGridResult;
import cn.zttek.thesis.modules.excel.MyExcelView;
import cn.zttek.thesis.modules.expand.ThesisResult;
import cn.zttek.thesis.modules.model.Apply;
import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.Thesis;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.service.ResultService;
import cn.zttek.thesis.utils.ThesisParam;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 论文题目选择结果相关业务控制器
 * @作者: Pengo.Wen
 * @日期: 2016-09-20 17:49
 * @版本: v1.0
 */
@Controller
@RequestMapping("/console/tresult")
public class ThesisResultController extends BaseController {


    @Autowired
    private ResultService resultService;

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(Model model) throws Exception{
        Project project = ThesisParam.getCurrentProj();

        //参与论文工作学生总数
        Long studentCnt = resultService.getStudentCount(project.getId());
        //论文题目总数
        Long thesisCnt = resultService.getThesisCount(project.getId());
        //选题学生数量
        Long resultCnt = resultService.getResultCount(project.getId());
        //未被选择题目
        Long unselectCnt = thesisCnt - resultCnt;
        //未选题学生
        Long lostCnt = studentCnt - resultCnt;
        model.addAttribute("studentCnt", studentCnt);
        model.addAttribute("thesisCnt", thesisCnt);
        model.addAttribute("resultCnt", resultCnt);
        model.addAttribute("unselectCnt", unselectCnt);
        model.addAttribute("lostCnt", lostCnt);
        return "console/thesis/result/list";
    }

    @RequestMapping(value = "/list.json", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDataGridResult list(Integer page, Integer rows, ThesisResult tresult) throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        Project project = ThesisParam.getCurrentProj();
        PageInfo<ThesisResult> pageInfo = resultService.listResultByPage(page, rows, project.getId(), tresult);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @RequestMapping(value = "/exportQuery", method = RequestMethod.GET)
    public ModelAndView exportQuery(ThesisResult tresult) throws Exception{
        Project project = ThesisParam.getCurrentProj();
        List<ThesisResult> results = resultService.listResult(project.getId(), tresult);
        return new ModelAndView(getExcelView(results));
    }

    @RequestMapping(value = "/exportAll", method = RequestMethod.GET)
    public ModelAndView exportAll() throws Exception{
        Project project = ThesisParam.getCurrentProj();
        List<ThesisResult> results = resultService.listAllResult(project.getId());
        return new ModelAndView(getExcelView(results));
    }

    private MyExcelView getExcelView(List<ThesisResult> results) {
        log.info("===创建Excel文件并输出===");
        String name = "选题结果";
        String[] titles = {"学生学号", "学生姓名", "年级班级", "论文题目", "研究方向", "指导教师","教师工号", "教师职称"};
        List<Object[]> list = new ArrayList<>(results.size());
        for (int i = 0; i < results.size(); i++) {
            ThesisResult r = results.get(i);
            Object[] objs = new Object[]{
                r.getStuno(), r.getStuname(), r.getGrade() + "级" + r.getClazz(),
                r.getTopic(), r.getDirection(),
                r.getTeacher(), r.getAccount(), r.getTitle()
            };
            list.add(objs);
        }
        return new MyExcelView(name, titles, list);
    }
}
