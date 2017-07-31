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
    <form id="scoreForm" method="post" action="${ctx}/console/tscore/edit?id=${score.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">指导教师自评</div>
        <input type="hidden" name="id" value="${score.id}"/>
        <input type="hidden" name="thesisid" value="${thesis.id}"/>
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
                <td><label>指导教师批阅成绩</label></td>
                <td>
                    <input class="easyui-numberbox" id="mark1" name="mark1" precision="2" min="0" max="100" required="true" value="${score.mark1}" style="width:100px;">
                </td>
                <td><label>评分日期</label></td>
                <td><input type="text" id="mark1date" name="mark1date" class="easyui-datebox" value="<fmt:formatDate value="${score.mark1date}" pattern="yyyy-MM-dd" />" data-options="required:true" /></td>
            </tr>
            <tr>
                <td><label>指导教师评语</label></td>
                <td colspan="5">
                    <input class="easyui-textbox" data-options="multiline:true, required:true" id="comment1" name="comment1" value="${score.comment1}"  style="width:100%;height:140px" />
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="scoreForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="scoreForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var scoreForm = {
        submitForm: function(){
            if(!$("#scoreForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#scoreForm").attr("action"), $("#scoreForm").serialize(), function(data){
                progressClose();
                if(data.status == 200){
                    $.messager.alert("提示", data.msg, "info", function(){
                        $("#dlg").dialog("close");
                        location.reload();
                    });
                }else{
                    $.messager.alert("提示", data.msg, "error");
                }
            });
        },
        clearForm: function(){
            $("#dlg").dialog("close");
        }
    }
</script>