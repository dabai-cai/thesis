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

    <form id="applyForm" method="post" action="/console/gooddelay/edit?id=${goodDelay.id}">
        <input type="hidden" name="orgconf" id="orgconf">
        <input type="hidden" name="teacherconf" id="teacherconf">
        <input type="hidden" name="studentconf" id="studentconf">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">学生申请</div>
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
                    <pre>
                        ${goodDelay.reason}
                    </pre>
                </td>
            </tr>
            <tr>
                <td><label>指导老师意见</label></td>
                <td colspan="5">
                    <input class="easyui-textbox" data-options="multiline:true, required:true" id="advice" name="advice"  style="width:100%;height:140px" />
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="applyForm.agreeForm()" iconCls="icon-ok">同意</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="applyForm.returnForm()" iconCls="icon-ok">驳回申请</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="applyForm.refuseForm()" iconCls="icon-ok">拒绝</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="applyForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>


<script type="text/javascript">
    var applyForm = {
        agreeForm: function(){
            if(!$("#applyForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            $("#studentconf").val(1);
            $("#teacherconf").val(1);
            $("#orgconf").val(0);
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
        }, refuseForm: function(){
            if(!$("#applyForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            $("#studentconf").val(1);
            $("#orgconf").val(-1);
            $("#teacherconf").val(-1);
            $.messager.confirm('拒绝提醒','一旦拒绝学生将无法再次申请，可以考虑驳回学生申请！' +
                '点击 [确定] 拒绝，点击 [取消] 返回',function(r){
                if (r){
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
            });

        }, returnForm: function(){
            if(!$("#applyForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            $("#teacherconf").val(0);
            $("#orgconf").val(0);
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