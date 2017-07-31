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
    <form id="resourceForm" method="post" action="${ctx}/console/resource/edit?id=${resource.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">资源信息</div>
        <input type="hidden" name="id" value="${resource.id}"/>
        <table width="100%" cellpadding="5">
            <tr>
                <td><label>上级资源</label></td>
                <td>
                    <input type="text" id="parent" name="pid" value="${resource.pid}" class="easyui-combotree" style="width: 200px;">
                </td>
            </tr>
            <tr>
                <td><label>资源名称</label></td>
                <td><input class="easyui-textbox" type="text" name="name" value="${resource.name}" data-options="required:true" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>资源URL</label></td>
                <td><input class="easyui-textbox" type="text" name="url" value="${resource.url}" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>显示顺序</label></td>
                <td><input class="easyui-textbox" type="text" name="num" value="${resource.num}" style="width: 200px;" /></td>
            </tr>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="resourceForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="resourceForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    $(function () {
        var data = [
            <c:forEach items="${resources}" var="r" varStatus="s">
            {id: "${r.id}", text: "${r.name}", pid: "${r.pid}"}${s.last?"":","}
            </c:forEach>
        ];
        $('#parent').combotree({
            required: true
        });
        $('#parent').combotree('loadData', convert(data));


    });
    var resourceForm = {
        submitForm: function(){
            if(!$("#resourceForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#resourceForm").attr("action"), $("#resourceForm").serialize(), function(data){
                progressClose();
                if(data.status == 200){
                    $.messager.alert("提示", data.msg, "info", function(){
                        $("#dlg").dialog("close");
                        $("#tg").treegrid("reload");
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
