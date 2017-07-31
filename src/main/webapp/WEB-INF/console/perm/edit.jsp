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
    <form id="permForm" method="post" action="${ctx}/console/perm/edit?id=${perm.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">权限信息</div>
        <input type="hidden" name="id" value="${perm.id}"/>
        <table width="100%" cellpadding="5">
            <tr>
                <td><label>选择资源</label></td>
                <td>
                    <input type="text" id="parent" name="resid" value="${perm.resid}" class="easyui-combotree" style="width: 200px;">
                </td>
            </tr>
            <tr>
                <td><label>权限名称</label></td>
                <td><input class="easyui-textbox" type="text" name="name" value="${perm.name}" data-options="required:true" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>权限编码</label></td>
                <td><input class="easyui-textbox" type="text" name="keystr" value="${perm.keystr}" data-options="required:true" style="width: 200px;" /></td>
            </tr>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="permForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="permForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    $(function () {
        var data = [
            <c:forEach items="${resources}" var="r" varStatus="s">
            {id: "${r.id}", text: "${r.name}", pid: "${r.pid}"}${s.last?"":","}
            </c:forEach>
        ];
        $('#parent').combotree({
            idField:"id",
            textFiled : 'text',
            parentField: "pid",
            required: true,
            onBeforeSelect: function(node){
                //返回树对象
                var tree = $(this).tree;
                //选中的节点是否为叶子节点,如果不是叶子节点,清除选中
                var isLeaf = tree('isLeaf', node.target);
                if (!isLeaf) {
                    return false;
                }
                return true;
            }
        });
        $('#parent').combotree('loadData', convert(data));


    });
    var permForm = {
        submitForm: function(){
            if(!$("#permForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#permForm").attr("action"), $("#permForm").serialize(), function(data){
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
