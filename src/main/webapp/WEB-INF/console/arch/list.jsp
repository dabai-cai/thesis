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
<c:set var="role" value="管理员" />
<c:if test="${type eq 'teacher'}">
    <c:set var="role" value="教师" />
</c:if>
<c:if test="${type eq 'student'}">
    <c:set var="role" value="学生" />
</c:if>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>${role}管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div id="ui-toolbar">
    <div class="ui-toolbar-search">
        <label>关键词：</label><input class="wu-text easyui-textbox" id="keywords" style="width:100px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
    </div>
    <div class="ui-toolbar-button">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit()">修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del()">删除</a>
        <c:if test="${type ne 'admin'}">
            <a href="#" class="easyui-linkbutton" iconCls="myicon-group-add" onclick="batch()">批量添加${role}</a>
        </c:if>
    </div>
</div>
<table id="dg" class="easyui-datagrid"
       data-options="url: '${ctx}/console/arch/list-${type}.json',
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
                        singleSelect:true
                    ">
    <thead>
    <tr>
        <th data-options="field:'id'" width="60">${role}ID</th>
        <th data-options="field:'account'" width="200" align="left">${role}账号</th>
        <th data-options="field:'username'" width="200">${role}名称</th>
        <c:if test="${type eq 'admin'}">
            <th data-options="field:'info.phone'" formatter="(function(v,r,i){return formatColumn('info.phone',v,r,i);})" width="150">联系电话</th>
            <th data-options="field:'info.email'" formatter="(function(v,r,i){return formatColumn('info.email',v,r,i);})"  width="150">电子邮件</th>
        </c:if>
        <c:if test="${type eq 'teacher'}">
            <th data-options="field:'info.gender'" formatter="(function(v,r,i){return formatColumn('info.gender',v,r,i);})" width="150">性别</th>
            <th data-options="field:'info.phone'" formatter="(function(v,r,i){return formatColumn('info.phone',v,r,i);})" width="150">联系电话</th>
            <th data-options="field:'info.email'" formatter="(function(v,r,i){return formatColumn('info.email',v,r,i);})"  width="150">电子邮件</th>
            <th data-options="field:'info.inscomm'" formatter="(function(v,r,i){return formatColumn('info.inscomm',v,r,i);})"  width="150">即时通信号码</th>
            <th data-options="field:'info.room'" formatter="(function(v,r,i){return formatColumn('info.room',v,r,i);})"  width="150">办公地点</th>
        </c:if>
        <c:if test="${type eq 'student'}">
            <th data-options="field:'info.major'" formatter="(function(v,r,i){return formatColumn('info.major',v,r,i);})" width="150">专业</th>
            <th data-options="field:'info.clazz'" formatter="(function(v,r,i){return formatColumn('info.clazz',v,r,i);})" width="150">班级</th>
            <th data-options="field:'info.grade'" formatter="(function(v,r,i){return formatColumn('info.grade',v,r,i);})" width="150">年级</th>
            <th data-options="field:'info.phone'" formatter="(function(v,r,i){return formatColumn('info.phone',v,r,i);})" width="150">联系电话</th>
            <th data-options="field:'info.email'" formatter="(function(v,r,i){return formatColumn('info.email',v,r,i);})"  width="150">电子邮件</th>
        </c:if>
        <th data-options="field:'mdate',formatter:formatDate" width="220">修改时间</th>
    </tr>
    </thead>
</table>
<div id="dlg"></div>
<script>

    function doSearch(){
        $("#dg").datagrid("load",{
            keywords:$("#keywords").val()
        });
        return false;
    }

    $(function(){

    });

    var d;

    function add(){
        d=$("#dlg").dialog({
            title: '添加${role}',
            width: 620,
            height: ${type eq 'student'? 680 : 590},
            href:'${ctx}/console/arch/add-${type}',
            maximizable:true,
            modal:true
        });
    }

    function edit(){
        var id = getSelectedId($("#dg"));
        if(id == undefined || id == "" || id == null){
            $.messager.alert('提示','必须选择一个${role}才能编辑!');
            return ;
        }
        d=$("#dlg").dialog({
            title: '编辑${role}',
            width: 620,
            height: ${type eq 'student'? 680 : 590},
            href:'${ctx}/console/arch/edit-${type}?id=' + id,
            maximizable:true,
            modal:true
        });
    }

    function del(){
        var ids = getSelectedIds($("#dg"));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要删除的${role}!');
            return ;
        }
        $.messager.confirm('确认','确定删除ID为 '+ids+' 的${role}吗？',function(r){
            if (r){
                var params = {"ids":ids};
                $.post("${ctx}/console/arch/delete", params, function(data){
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

    function batch(){
        window.top.addTab("批量添加${role}", "${ctx}/console/arch/batch-${type}", null, true);
    }
</script>
</body>
</html>
