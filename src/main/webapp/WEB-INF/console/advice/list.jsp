<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/11
  Time: 8:57
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
    <title>公告管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div id="ui-toolbar">
    <div class="ui-toolbar-search">
        <c:if test="${type eq 'super'}">
        <label>用户类型：</label>
        <select id="org" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">
            <option value="-1">请选择机构</option>
            <c:forEach items="${orgs}" var="org">
                <option value="${org.id}">${org.name}</option>
            </c:forEach>
        </select>
        </c:if>
        <label>关键词：</label><input class="wu-text easyui-textbox" id="keywords" style="width:100px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
    </div>
    <div class="ui-toolbar-button">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit()">修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del()">删除</a>
    </div>
</div>
<table id="dg" class="easyui-datagrid"
       data-options="url: '${ctx}/console/advice/list.json',
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
        <%--<th data-options="field:'id'" width="60">ID</th>--%>
        <th data-options="field:'view', formatter: formatView", width="100">查看详细</th>
        <th data-options="field:'topic'" width="200" align="left">公告标题</th>
        <th data-options="field:'valid',formatter:format01" width="80">发布状态</th>
        <th data-options="field:'top',formatter:format01" width="80">是否置顶</th>
        <th data-options="field:'target'" width="80">公告对象</th>
        <th data-options="field:'mdate',formatter:formatDate" width="220">修改时间</th>
    </tr>
    </thead>
</table>
<div id="dlg"></div>
<script>
    function formatView(val, row){
        return '<a href="#" class="notselect" onclick="return view('+ row.id +',event);"><div class="myicon-zoom-in" style="width:16px;height:16px">&nbsp;&nbsp;&nbsp;&nbsp;点击查看</div></a>';
    }
    function view(id, event){
        window.top.addTab("公告详情", '${ctx}/console/advice/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }

    function format01(val, row){
        if(val ==true){
            return "<span style='padding: 4px;'>是</span>";
        }else{
            return "<span style='padding: 4px;'>否</span>";
        }
    }
    function doSearch(){
        $("#dg").datagrid("load",{
            <c:if test="${type eq 'super'}">
            orgid:$("#org").combobox("getValue"),
            </c:if>
            keywords:$("#keywords").val()
        });
        return false;
    }

    $(function(){

    });

    var d;

    function add(){
        d=$("#dlg").dialog({
            title: '添加公告',
            width: 620,
            <%--height: ${type eq 'student'? 680 : 590},--%>
            height:  490 ,
            href:'${ctx}/console/advice/add',
            maximizable:true,
            modal:true,
            resizable:true
        });
    }

    function edit(){
        var id = getSelectedId($("#dg"));
        if(id == undefined || id == "" || id == null){
            $.messager.alert('提示','必须选择一个公告才能编辑!');
            return ;
        }
        d=$("#dlg").dialog({
            title: '编辑公告',
            width: 620,
            <%--height: ${type eq 'student'? 680 : 590},--%>
            height: 490 ,
            href:'${ctx}/console/advice/edit?id=' + id,
            maximizable:true,
            modal:true
        });
    }

    function del(){
        var ids = getSelectedIds($("#dg"));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要删除的公告!');
            return ;
        }
        $.messager.confirm('确认','确定删除ID为 '+ids+' 的公告吗？',function(r){
            if (r){
                var params = {"ids":ids};
                $.post("${ctx}/console/advice/delete", params, function(data){
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