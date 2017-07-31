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
    <form id="applyForm" method="post" action="${ctx}/console/tapply/edit?id=${apply.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">申请题目信息</div>
        <input type="hidden" name="id" value="${apply.id}"/>
        <input type="hidden" name="thesisid" value="${thesis.id}"/>
        <input type="hidden" name="teacherid" value="${thesis.teacherid}"/>
        <input type="hidden" name="studentid" value="${currentUser.id}"/>
        <table width="100%" cellpadding="5" class="form-table">
            <tbody>
            <tr>
                <td width="120"><label>论文标题</label></td>
                <td colspan="3">${thesis.topic}</td>
                <td><label>指导教师</label></td>
                <td>
                    ${teacher.username}&nbsp;&nbsp;${teacher.info.title}
                </td>
            </tr>
            <tr>
                <td><label>研究方向</label></td>
                <td>
                    ${thesis.direction}
                </td>
                <td width="80"><label>论文来源</label></td>
                <td>
                    ${thesis.source}
                </td>
                <td width="80"><label>论文性质</label></td>
                <td>
                    ${thesis.property}
                </td>
            </tr>
            <tr>
                <td><label>申请理由</label></td>
                <td colspan="3">
                    <input class="easyui-textbox" data-options="multiline:true, required:true" id="applyInfo" name="applyInfo" value="${apply.applyInfo}"  style="width:100%;height:120px" />
                </td>
                <td colspan="2">
                    <p style="width: 100%; text-align: left;"><label>填写对题目的想法等内容</label></p>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="applyForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="applyForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var applyForm = {
        submitForm: function(){
            if(!$("#applyForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
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
        }
    }
</script>