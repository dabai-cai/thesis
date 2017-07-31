<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-09-05
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>论文工作管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div id="ui-toolbar">
    <div class="ui-toolbar-button">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">开启新工作</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit()">修改工作参数</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del()">删除论文工作</a>
    </div>
</div>
<table id="dg" class="easyui-datagrid"
       data-options="url: '${ctx}/console/project/list.json',
                        method: 'get',
                        idField: 'id',
                        toolbar: '#ui-toolbar',
                        fit:true,
                        fitColumns:true,
                        pagination:true,
                        rownumbers:true,
                        pageNumber:1,
                        pageSize : 20,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        singleSelect:true">
    <thead>
    <tr>
        <th data-options="field:'id'" width="60">工作ID</th>
        <th data-options="field:'year'" width="60">工作年份</th>
        <th data-options="field:'title'" width="200" align="left">工作标题</th>
        <th data-options="field:'startdate',formatter:formatShortDate" width="150">开始日期</th>
        <th data-options="field:'enddate',formatter:formatShortDate" width="150">结束日期</th>
        <th data-options="field:'setmax'"width="100">教师最大出题数</th>
        <th data-options="field:'setmin'"width="100">教师最少出题数</th>
        <th data-options="field:'getmax'"width="100">学生最大选题数</th>
        <th data-options="field:'close', formatter: formatState" width="100" align="center">工作状态</th>
        <th data-options="field:'mdate',formatter:formatDate" width="150">修改时间</th>
    </tr>
    </thead>
</table>
<div id="dlg"></div>
<script>
    function formatState(val, row){
        if(val || val == "true"){
            return "<span style='background-color: #ff032c; padding: 4px;'>已关闭</span>";
        }else{
            return "<span style='background-color: #00ff32;padding: 4px;'>进行中</span>";
        }
    }
    var d;
    function add(){
        d=$("#dlg").dialog({
            title: '添加论文工作',
            width: 1280,
            height: 580,
            href:'${ctx}/console/project/add',
            maximizable:true,
            modal:true
        });
    }

    function edit(){
        var id = getSelectedId($("#dg"));
        if(id == undefined || id == "" || id == null){
            $.messager.alert('提示','必须选择一个论文工作才能编辑!');
            return ;
        }
        d=$("#dlg").dialog({
            title: '编辑论文工作',
            width: 1280,
            height: 580,
            href:'${ctx}/console/project/edit?id=' + id,
            maximizable:true,
            modal:true
        });
    }

    function del(){
        var ids = getSelectedIds($("#dg"));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要删除的论文工作!');
            return ;
        }
        $.messager.confirm('确认','确定删除ID为 '+ids+' 的论文工作吗？',function(r){
            if (r){
                var params = {"ids":ids};
                $.post("${ctx}/console/project/delete",params, function(data){
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
