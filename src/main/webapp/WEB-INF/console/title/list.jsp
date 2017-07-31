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
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>职称管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div id="ui-toolbar">
    <div class="ui-toolbar-button">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit()">修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del()">删除</a>
    </div>
</div>
<table id="dg" class="easyui-datagrid"
       data-options="url: '${ctx}/console/title/list.json',
                        method: 'get',
                        idField: 'id',
                        toolbar: '#ui-toolbar',
                        fit:true,
                        fitColumns:true,
                        rownumbers:true,
                        showFooter:true,
                        singleSelect:true">
    <thead>
    <tr>
        <th data-options="field:'id'" width="60">职称ID</th>
        <th data-options="field:'name'" width="200" align="left">职称名称</th>
        <th data-options="field:'level'" width="200">职称级别</th>
        <th data-options="field:'mdate',formatter:formatDate" width="220">修改时间</th>
    </tr>
    </thead>
</table>
<div id="dlg"></div>
<script>
    var d;
    function add(){
        d=$("#dlg").dialog({
            title: '添加职称',
            width: 380,
            height: 380,
            href:'${ctx}/console/title/add',
            maximizable:true,
            modal:true
        });
    }

    function edit(){
        var id = getSelectedId($("#dg"));
        if(id == undefined || id == "" || id == null){
            $.messager.alert('提示','必须选择一个职称才能编辑!');
            return ;
        }
        d=$("#dlg").dialog({
            title: '编辑职称',
            width: 380,
            height: 380,
            href:'${ctx}/console/title/edit?id=' + id,
            maximizable:true,
            modal:true
        });
    }

    function del(){
        var ids = getSelectedIds($("#dg"));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要删除的职称!');
            return ;
        }
        $.messager.confirm('确认','确定删除ID为 '+ids+' 的职称吗？',function(r){
            if (r){
                var params = {"ids":ids};
                $.post("${ctx}/console/title/delete",params, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示', data.msg, undefined, function(){
                            $("#dg").datagrid("reload");
                        });
                    }else{
                        $.messager.alert('错误', data.msg);
                    }
                });
            }
        });
    }
</script>
</body>
</html>
