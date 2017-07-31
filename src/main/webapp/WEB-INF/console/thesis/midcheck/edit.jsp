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
    <form id="midcheckForm" method="post" action="${ctx}/console/tmidcheck/edit?id=${midcheck.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">中期报告书</div>
        <input type="hidden" name="id" value="${midcheck.id}"/>
        <input type="hidden" name="thesisid" value="${thesis.id}"/>
        <input type="hidden" name="confirm" value="false"/>
        <table width="100%" cellpadding="5" class="form-table">
            <tbody>
            <tr>
                <td width="120"><label>论文标题</label></td>
                <td colspan="3">${thesis.topic}</td>
                <td><label>中期检查日期</label></td>
                <td><input type="text" id="cdate" name="cdate" class="easyui-datebox" value="<fmt:formatDate value="${midcheck.cdate}" pattern="yyyy-MM-dd" />" data-options="required:true" /></td>
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
                <td><label>内容要求，包括技术路线</label></td>
                <td colspan="5">
                    <input class="easyui-textbox" data-options="multiline:true, required:true" id="content" name="content" value="${midcheck.content}"  style="width:100%;height:120px" />
                </td>
            </tr>
            <tr>
                <td><label>进度安排</label></td>
                <td colspan="5">
                    <input class="easyui-textbox" data-options="multiline:true, required:true" id="schedule" name="schedule" value="${midcheck.schedule}"  style="width:100%;height:120px" />
                </td>
            </tr>
            <tr>
                <td><label>参考文献</label></td>
                <td colspan="5">
                    <input class="easyui-textbox" data-options="multiline:true, required:true" id="reference" name="reference" value="${midcheck.reference}"  style="width:100%;height:120px" />
                </td>
            </tr>
            <tr>
                <td><label>指导老师意见与建议</label></td>
                <td colspan="5">
                    <input class="easyui-textbox" data-options="multiline:true, required:true" id="advice" name="advice" value="${midcheck.advice}"  style="width:100%;height:120px" />
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton easyui-tooltip" title="点击将中期报告书下发给学生" onclick="midcheckForm.submitForm(true)" iconCls="icon-ok">保存并确认</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="midcheckForm.submitForm(false)" iconCls="icon-ok">仅保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="midcheckForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var midcheckForm = {
        submitForm: function(confirm){
            $("input[name='confirm']").val(confirm);
            if(!$("#midcheckForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#midcheckForm").attr("action"), $("#midcheckForm").serialize(), function(data){
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