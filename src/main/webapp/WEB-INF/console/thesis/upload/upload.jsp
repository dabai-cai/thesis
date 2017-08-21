<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-09-22
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/inc/dialog.jsp" %>

<div style="padding:10px 10px 10px 10px">

    <form id="uploadForm" enctype="multipart/form-data" action="${ctx}/console/tupload/upload?id=${upload.id}"  method="post">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">指导教师上传论文</div>
        <input type="hidden" id="studentid" name="studentid" value="${student.id}">
        <input type="hidden" id="projectid" name="projectid" value="${project.id}">
        <input type="hidden" id="thesisid" name="thesisid" value="${thesis.id}">
        <table width="100%" cellpadding="5" class="form-table">
            <tbody>
            <tr>
                <td width="120"><label>论文标题</label></td>
                <td colspan="5">${thesis.topic}</td>
            </tr>
            <tr>
                <td><label>选题学生</label></td>
                <td>${student.username}</td>
                <td><label>学生学号</label></td>
                <td>
                    ${student.account}
                </td>
                <td><label>专业班级</label></td>
                <td>
                    ${student.info.grade}级${student.info.clazz}
                </td>
            </tr>
            <tr>
                <td>
                    <label>题目简介</label>
                </td>
                <td colspan="5">
                    <label> ${thesis.profile} </label>
                </td>
            </tr>
            <tr>
                <td><label>论文上传格式</label></td>
                <td colspan="5">
                    <label>${project.doctype } </label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>选择论文文件上传</label>
                </td>
                <td colspan="5">
                    <input   class="easyui-filebox" name="uploadFile" id="uploadFile" />
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="doUpload()" iconCls="icon-ok">上传论文</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="uploadForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>

<script type="text/javascript">
    function doUpload() {
        var formData = new FormData($( "#uploadForm" )[0]);
        $.ajax({
            url: '${ctx}/console/tupload/upload?id=${upload.id}' ,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if(data.status==200){
                    $.messager.alert("提示", data.msg, "info", function(){
                        $("#dlg").dialog("close");
                        location.reload();
                    });
                }else{
                    $.messager.alert("提示", data.msg, "error");
                }
            },
            error: function (data) {
                $.messager.alert("提示", data.msg, "error");
            }
        });
    }
    var uploadForm = {
        clearForm: function(){
            $("#dlg").dialog("close");
        }
    }
</script>