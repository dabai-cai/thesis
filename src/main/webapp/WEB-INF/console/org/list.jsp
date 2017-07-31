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
    <title>组织机构管理</title>
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
       data-options="url: '${ctx}/console/org/list.json',
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
        <th data-options="field:'id'" width="60">机构ID</th>
        <th data-options="field:'name'" width="200" align="left">机构名称</th>
        <th data-options="field:'projlimit'" width="200">最多论文工作活跃数量</th>
        <th data-options="field:'studlimit'" width="150">最多参与学生数量</th>
        <th data-options="field:'uploadlimit'"width="220">单个论文上传限制（单位：MB）</th>
        <th data-options="field:'mdate',formatter:formatDate" width="220">修改时间</th>
    </tr>
    </thead>
</table>
<div id="dlg"></div>
<script>
    var d;
    function add(){
        d=$("#dlg").dialog({
            title: '添加组织机构',
            width: 380,
            height: 380,
            href:'${ctx}/console/org/add',
            maximizable:true,
            modal:true
        });
    }

    function edit(){
        var id = getSelectedId($("#dg"));
        if(id == undefined || id == "" || id == null){
            $.messager.alert('提示','必须选择一个组织机构才能编辑!');
            return ;
        }
        d=$("#dlg").dialog({
            title: '编辑组织机构',
            width: 380,
            height: 380,
            href:'${ctx}/console/org/edit?id=' + id,
            maximizable:true,
            modal:true
        });
    }

    function del(){
        var ids = getSelectedIds($("#dg"));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要删除的组织机构!');
            return ;
        }
        $.messager.confirm('确认','确定删除ID为 '+ids+' 的组织机构吗？',function(r){
            if (r){
                var params = {"ids":ids};
                $.post("${ctx}/console/org/delete",params, function(data){
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
