<%--
  Created by IntelliJ IDEA.
  User: 大白菜
  Date: 2017/8/14 0014
  Time: 上午 11:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/inc/dialog.jsp" %>

<div style="padding:10px 10px 10px 10px">

    <form id="applyForm" method="post" action="/console/gooddelay/student?id=${goodDelay.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">
            提示:申请一旦通过院系通过或者被老师拒绝则不能再次申请
        </div>
        <input type="hidden" name="id" value="${goodDelay.id}"/>
        <input type="hidden" name="applytype" value="${goodDelay.applytype}"/>
        <input type="hidden" name="studentconf" id="studentconf"/>
        <input type="hidden" name="teacherconf" value="0"/>
        <input type="hidden" name="orgconf" value="0"/>
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
                <td><label>申请类型</label></td>
                <td>
                 ${goodDelay.applytype eq 1 ? "争优":"延期"}
                </td>
                <td><label>申请日期</label></td>
                <td> <fmt:formatDate value="${goodDelay.cdate}" pattern="yyyy.MM.dd"/></td>
            </tr>
            <tr>
                <td><label>学生申请理由</label></td>
                <td colspan="5">
                    <input class="easyui-textbox" data-options="multiline:true, required:true" id="reason" name="reason"  style="width:100%;height:140px" />
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="applyForm.saveForm()" iconCls="icon-ok">仅保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="applyForm.submitForm()" iconCls="icon-ok">保存并确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="applyForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>


<script type="text/javascript">
    var applyForm = {
        submitForm: function(){
            if(!$("#applyForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            $("#studentconf").val(1);
            progressLoad();
            $.post($("#applyForm").attr("action"), $("#applyForm").serialize(), function(data){
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
        },
        saveForm: function(){
            if(!$("#applyForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            $("#studentconf").val(0);
            progressLoad();
            $.post($("#applyForm").attr("action"), $("#applyForm").serialize(), function(data){
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
        }
    }
</script>