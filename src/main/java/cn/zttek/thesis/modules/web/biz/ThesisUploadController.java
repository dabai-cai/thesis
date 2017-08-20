package cn.zttek.thesis.modules.web.biz;

import cn.zttek.thesis.common.base.BaseController;
import cn.zttek.thesis.common.easyui.EUResult;
import cn.zttek.thesis.common.utils.TimeUtils;
import cn.zttek.thesis.modules.expand.ThesisExpand;
import cn.zttek.thesis.modules.holder.TitleHolder;
import cn.zttek.thesis.modules.model.*;
import cn.zttek.thesis.modules.service.*;
import cn.zttek.thesis.utils.ThesisParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 论文上传
 * @作者: 大白菜
 * @日期: 2017-08-10 14:55
 */
@Controller
@RequestMapping(value = "/console/tupload")
public class ThesisUploadController extends BaseController {

    @Autowired
    private UploadService uploadService;
    @Autowired
    private ThesisService thesisService;
    @Autowired
    private UserService userService;


    @ModelAttribute
    public Upload get(@RequestParam(required = false)Long id) throws Exception{
        if(id != null && id > 0){
            return uploadService.queryById(id);
        }else {
            return new Upload();
        }
    }

    @RequestMapping(value = "/list", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String list(Model model) throws Exception {
        Project project = ThesisParam.getCurrentProj();
        User teacher = ThesisParam.getCurrentUser();
        List<ThesisExpand> students=uploadService.listByTeacher(project.getId(),teacher.getId());
        model.addAttribute("expands",students);
        return "console/thesis/upload/list";
    }

    //获取上传表单
    @RequestMapping(value = {"/upload"}, produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String edit(@ModelAttribute Upload upload, Model model) throws Exception {
        loadData(upload,model);
        return "console/thesis/upload/upload";
    }



    //上传论文
    @RequestMapping(value = "/upload", produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    @ResponseBody
    public EUResult form(@ModelAttribute Upload upload, MultipartFile uploadFile, HttpServletRequest request) {
        EUResult result = new EUResult();
        Org currentOrg=ThesisParam.getCurrentOrg();
        User currentUser=ThesisParam.getCurrentUser();
        Project currentProject=ThesisParam.getCurrentProj();
        //  计算机/2017//建军节论文工作/张大斌教师/
        try {

            //构建上传路径
            String rootpath=this.getDocRoot(request)+"/";
            String path = currentOrg.getDocroot() + "/" + currentProject.getYear() + "/"
                    +currentProject.getTitle()+"/"+currentUser.getUsername()+"/";
            String filename = uploadFile.getOriginalFilename();
            int limitsize=currentOrg.getUploadlimit();//论文上传限制大小
            Long filesize=uploadFile.getSize();


            //验证表单
            if(filesize==0){throw  new Exception("请先选择文件!");}
            else{
                boolean flag=limit(filesize,limitsize);
                if(flag==true) {
                    throw new Exception("文件大小超过限制!请选择低于"+limitsize+"M的文件");
                }
            }
            String filetype=filename.substring(filename.lastIndexOf('.')+1);
            String types=currentProject.getDoctype();
            if(types.indexOf(filetype)==-1){throw new Exception("文件格式不符合,请选择正确格式文件!");}
            System.out.println("文件格式:"+filetype);


            //保存文件到服务器
            File file = new File(rootpath+path, TimeUtils.currentTime()+filename);
            if(!file.exists()) {
                file.mkdirs();
            }
            uploadFile.transferTo(file);


            //更新上传表
            if(upload.getId() != null && upload.getId() > 0){
                //学生先上传，老师这边才会显示，所以不存在插入问题，只是更新论文
                uploadService.update(upload);
            }else{
                //学生先上传，教师才可见，所以插入数据的是学生
                upload.setPath(rootpath+path);
                upload.setProjectid(ThesisParam.getCurrentProj().getId());
                upload.setStudentid(ThesisParam.getCurrentUser().getId());
                Long thesisid=Long.parseLong(request.getParameter("thesisid"));
                upload.setTeacherid(thesisService.queryById(thesisid).getTeacherid());
                upload.setThesisid(thesisid);
                uploadService.insert(upload);
            }
            result.setStatus(EUResult.OK);
            result.setMsg("文件上传成功!");
        } catch (Exception ex) {
            result.setStatus(EUResult.FAIL);
            result.setMsg(ex.getMessage());
        }
        return result;
    }

    //加载上传论文表单信息
    private void loadData(Upload upload, Model model) throws Exception {
        Thesis thesis = thesisService.queryById(upload.getThesisid());
        if (thesis.getViewerid() != null && thesis.getViewerid() > 0) {
            //评阅老师资料
            User viewer = userService.getDetail(thesis.getViewerid());
            viewer.getInfo().setTitle(TitleHolder.getTitle(viewer.getInfo().getTid()));
            model.addAttribute("viewer", viewer);
        }
        //学生信息
        User student = userService.getDetail(thesis.getStudentid());
        model.addAttribute("thesis", thesis);
        model.addAttribute("student", student);
        Project project = ThesisParam.getCurrentProj();
        model.addAttribute("project", project);
    }
    //判断文件是否超出限制
    private boolean limit(Long size,int limit) {
        size = size / 1024;//换成KB
        size = size / 1024;//换成M
        if(size.intValue()>limit){
            return true;
        }
        return false;
    }
}
