<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/15
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>答辩任务管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div id="ui-toolbar">
    <div class="ui-toolbar-button">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit()">修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del()">删除</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="group()">查看答辩小组</a>
    </div>
</div>
<table id="dg" class="easyui-datagrid"
       data-options="url: '${ctx}/console/thesis/defense/task/list.json',
                        method: 'get',
                        idField: 'id',
                        toolbar: '#ui-toolbar',
                        fit:true,
                        fitColumns:true,
                        pagination:true,
                        rownumbers:true,
                        pageNumber:1,
                        pageSize : 10,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        singleSelect:true
                    ">
    <thead>
    <tr>
        <th data-options="field:'id'" width="60" >ID</th>
        <th data-options="field:'name', formatter: formatView", width="200">答辩任务名称</th>
        <th data-options="field:'president'" width="100" align="left">答辩主席</th>
        <th data-options="field:'contact'" width="80">联系人</th>
        <th data-options="field:'nums',formatter:formatGroup" width="80">答辩小组数</th>
        <th data-options="field:'defenseNum',formatter:formatNum" width="80">答辩总人数</th>
        <th data-options="field:'allotNum',formatter:formatNum" width="80">已分组人数</th>
        <th data-options="field:'defensetime',formatter:formatShortDate" width="220">答辩时间</th>
    </tr>
    </thead>
</table>
<div id="dlg"></div>
<script>
    function formatView(val, row){
        return '<a href="#" class="notselect" onclick="return view('+ row.id +',event);"><div class="myicon-zoom-in" style="width:16px;height:16px">&nbsp;&nbsp;&nbsp;&nbsp;'+val+'</div></a>';
    }
    function formatNum(val, row){
        return val+"名";
    }
    function formatGroup(val, row){
        return val+"组";
    }
    //    function formatDate(val, row){
    //        return val.split(" ")[0];
    //    }
    function view(id, event){
        window.top.addTab("答辩任务详情", '${ctx}/console/thesis/defense/task/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }

    $(function(){

    });

    var d;

    function add(){
        window.top.addTab("新增答辩任务", '${ctx}/console/thesis/defense/task/add', null, true);
        event.stopPropagation();
        return false;
    }

    function edit(){
        var id = getSelectedId($("#dg"));
        if(id == undefined || id == "" || id == null){
            $.messager.alert('提示','必须选择一个答辩任务才能编辑!');
            return ;
        }
        window.top.addTab("编辑答辩任务", '${ctx}/console/thesis/defense/task/edit?id='+id, null, true);
        event.stopPropagation();
        return false;
    }

    function del(){
        var ids = getSelectedIds($("#dg"));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要删除的答辩任务!');
            return ;
        }
        $.messager.confirm('确认','确定删除ID为 '+ids+' 的答辩任务及其所属的答辩小组吗？',function(r){
            if (r){
                var params = {"ids":ids};
                $.post("${ctx}/console/thesis/defense/task/delete", params, function(data){
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
    function group(){
        var id = getSelectedIds($("#dg"));
        if(id.length == 0){
            $.messager.alert('提示','未选中要查看的答辩任务!');
            return ;
        }
        window.top.addTab("答辩小组管理", '${ctx}/console/thesis/defense/group/list?taskid='+id, null, true);
        event.stopPropagation();
        return false;
    }
</script>
</body>
</html>