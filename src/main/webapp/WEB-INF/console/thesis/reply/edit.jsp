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
    <form id="replyForm" method="post" action="${ctx}/console/treply/edit?id=${apply.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">回复意见</div>
        <input type="hidden" name="id" value="${apply.id}"/>
        <input type="hidden" name="confirm" value="false"/>
        <table width="100%" cellpadding="5" class="form-table">
            <tbody>
            <tr>
                <td width="120"><label>论文标题</label></td>
                <td colspan="3">${thesis.topic}</td>
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
                <td><label>申请学生</label></td>
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
                <td><label>申请理由</label></td>
                <td colspan="5">
                    <pre>${apply.applyInfo}</pre>
                </td>
            </tr>
            <tr>
                <td><label>回复意见</label></td>
                <td colspan="5">
                    <input class="easyui-textbox" data-options="multiline:true, required:true" id="replyInfo" name="replyInfo" value="${apply.replyInfo}"  style="width:100%;height:120px" />
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton easyui-tooltip" title="点击将题目直接指定给学生[${student.username}]" onclick="replyForm.submitForm(true)" iconCls="icon-ok">保存并确定学生</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="replyForm.submitForm(false)" iconCls="icon-ok">仅保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="replyForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var replyForm = {
        submitForm: function(confirm){
            $("input[name='confirm']").val(confirm);
            if(!$("#replyForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#replyForm").attr("action"), $("#replyForm").serialize(), function(data){
                progressClose();
                if(data.status == 200){
                    $.messager.alert("提示", data.msg, "info", function(){
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
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