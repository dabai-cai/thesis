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
    <form id="attrForm" method="post" action="${ctx}/console/attr/edit-${op}?id=${id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">${label}信息</div>
        <input type="hidden" name="id" value="${id}"/>
        <table width="100%" cellpadding="5" class="form-table">
            <tbody>
                <tr>
                    <td><label>${label}</label></td>
                    <td><input type="text" class="easyui-textbox" id="value" name="value" value="${value}" data-options="required:true, width:250" /></td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="attrForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="attrForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var attrForm = {
        submitForm: function(){
            if(!$("#attrForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#attrForm").attr("action"), $("#attrForm").serialize(), function(data){
                progressClose();
                if(data.status == 200){
                    $.messager.alert("提示", data.msg, "info", function(){
                        $("#dlg").dialog("close");
                        $("#dg-${op}").datagrid("reload");
                    });
                }else{
                    $.messager.alert("提示", data.msg, "error");
                }
            });
        },
        clearForm: function(){
            $("#dlg").dialog("close");
        }
    };
</script>
