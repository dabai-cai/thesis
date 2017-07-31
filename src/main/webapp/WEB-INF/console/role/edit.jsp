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
    <form id="roleForm" method="post" action="${ctx}/console/role/edit?id=${role.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">角色信息</div>
        <input type="hidden" name="id" value="${role.id}"/>
        <table width="100%" cellpadding="5">
            <tr>
                <td><label>角色名称</label></td>
                <td><input class="easyui-textbox" type="text" name="name" value="${role.name}" data-options="required:true" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>角色编码</label></td>
                <td><input class="easyui-textbox" type="text" name="code" value="${role.code}" data-options="required:true" style="width: 200px;" /></td>
            </tr>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="roleForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="roleForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var roleForm = {
        submitForm: function(){
            if(!$("#roleForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#roleForm").attr("action"), $("#roleForm").serialize(), function(data){
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
