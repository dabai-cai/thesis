<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-08-12
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/inc/dialog.jsp" %>
<div style="padding:10px 10px 10px 10px">
    <form id="titleForm" method="post" action="${ctx}/console/title/edit?id=${title.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">职称信息</div>
        <input type="hidden" name="id" value="${title.id}"/>
        <table width="100%" cellpadding="5">
            <tr>
                <td><label>职称名称</label></td>
                <td>
                    <input class="easyui-textbox" type="text" name="name" value="${title.name}" data-options="required:true" style="width: 200px;"/>
                </td>
            </tr>
            <tr>
                <td><label>职称级别</label></td>
                <td>
                    <select id="cc" class="easyui-combobox" name="level" editable="false" style="width:200px;">
                        <c:forEach items="${levels}" var="level">
                            <option value="${level.ordinal()}" ${title.level.ordinal() eq level.ordinal() ? "selected" : ""}>${level.label}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="titleForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="titleForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var titleForm = {
        submitForm: function(){
            if(!$("#titleForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#titleForm").attr("action"), $("#titleForm").serialize(), function(data){
                progressClose();
                if(data.status == 200){
                    $.messager.alert("提示", data.msg, "info", function(){
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    });
                }else{
                    $.messager.alert("提示", data.msg);
                }
            });
        },
        clearForm: function(){
            $(".panel-tool-close").click();
        }
    };
</script>
