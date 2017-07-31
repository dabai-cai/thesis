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
    <form id="userForm" method="post" action="${ctx}/console/user/edit?id=${user.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">用户信息</div>
        <input type="hidden" name="id" value="${user.id}"/>
        <table width="100%" cellpadding="5">
            <tr>
                <td><label>用户名称</label></td>
                <td><input class="easyui-textbox" type="text" name="username" value="${user.username}" data-options="required:true" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>用户账号</label></td>
                <td><input class="easyui-textbox" type="text" name="account" value="${user.account}" data-options="required:true" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>用户密码</label></td>
                <td><input type="password" class="easyui-validatebox" id="pwd" name="pwd" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>确认密码</label></td>
                <td><input type="password" class="easyui-validatebox" name="rpassword" validType="equals['#pwd']" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>用户类型</label></td>
                <td>
                    <select id="cc" class="easyui-combobox" name="type" editable="false" style="width:200px;">
                        <c:forEach items="${types}" var="type">
                            <option value="${type.ordinal()}" ${user.type.ordinal() eq type.ordinal() ? "selected" : ""}>${type.label}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="userForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="userForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var userForm = {
        submitForm: function(){
            if(!$("#userForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#userForm").attr("action"), $("#userForm").serialize(), function(data){
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
