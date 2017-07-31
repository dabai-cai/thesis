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
    <form id="orgForm" method="post" action="${ctx}/console/org/edit?id=${org.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">组织机构信息</div>
        <input type="hidden" name="id" value="${org.id}"/>
        <table width="100%" cellpadding="5">
            <tr>
                <td><label>机构名称</label></td>
                <td>
                    <input class="easyui-textbox" type="text" name="name" value="${org.name}"
                           data-options="required:true" style="width: 200px;"
                           ${org.id ne null && org.id gt 0 ? "readonly='true'" : ""}/>
                </td>
            </tr>
            <tr>
                <td><label>最多工作活跃数量</label></td>
                <td><input type="text" class="easyui-numberbox" name="projlimit" value="${org.projlimit}" data-options="required:true" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>最多参与学生数量</label></td>
                <td><input type="text" class="easyui-numberbox" name="studlimit" value="${org.studlimit}" data-options="required:true" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td valign="top"><label>单个论文上传限制</label></td>
                <td>
                    <input type="text" class="easyui-numberbox" name="uploadlimit" value="${org.uploadlimit}" data-options="required:true" style="width: 200px;" />
                    <br><label>（单位：MB）</label>
                </td>
            </tr>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="orgForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="orgForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var orgForm = {
        submitForm: function(){
            if(!$("#orgForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#orgForm").attr("action"), $("#orgForm").serialize(), function(data){
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
