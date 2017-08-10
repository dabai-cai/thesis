package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.modules.model.Project;
import cn.zttek.thesis.modules.model.User;
import cn.zttek.thesis.modules.service.TaskbookService;
import cn.zttek.thesis.utils.ThesisParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @描述: 论文上传
 * @作者: 大白菜
 * @日期: 2017-08-10 14:55
 */
@Controller
@RequestMapping(value = "/console/tupload")
public class ThesisUploadController {

    @Autowired
    private TaskbookService taskbookService;


    @RequestMapping(value = "/upload")
    public void upload(@RequestParam MultipartFile uploadFile) throws Exception {
        String filename = uploadFile.getOriginalFilename();
        String path = "C://";
        File file = new File(path, filename);
        uploadFile.transferTo(file);
    }


    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(Model model) throws Exception{
        Project project = ThesisParam.getCurrentProj();
        User teacher = ThesisParam.getCurrentUser();
        model.addAttribute("expands", taskbookService.listOfTask(project.getId(), teacher.getId()));
        return "console/thesis/upload/";
    }
    public String A(Model model){
        System.out.println("A");
        return "A";
    }
}
