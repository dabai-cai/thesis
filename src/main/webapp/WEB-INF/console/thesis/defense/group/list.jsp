<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/19
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>答辩小组管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div id="ui-toolbar">
    <div class="ui-toolbar-button">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">添加答辩小组</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit()">修改答辩小组</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del()">删除答辩小组</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="autogroup()">自动分组</a>
    </div>
</div>
<table id="dg" class="easyui-datagrid"></table>
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
        window.top.addTab("答辩小组详情", '${ctx}/console/thesis/defense/task/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }

    $(function(){
        $("#dg").datagrid({
            title:"答辩小组列表",
            fitColumns:true,
            columns:[[
                {field:'id',width:'80',title:"ID",checkbox:true},
                {field:"groupno",width:"100",title:"分组编号"},
                {field:"grouptype",width:"100",title:"分组类型"},
                {field:"leaderName",width:"120",title:"答辩组长"},
                {field:"secretaryName",width:"120",title:"答辩秘书"},
                {field:"students",width:"120",title:"小组学生人数",
                    formatter: function(value,row,index){
                        return JSON.parse(value).length +"名";
                    }},
                {field:"teachers",width:"120",title:"小组教师人数",
                    formatter: function(value,row,index){
                        return JSON.parse(value).length +"名";
                    }},
                {field:"defensetime",width:"160",title:"答辩时间"},
                {field:"defenseroom",width:"160",title:"答辩地点"}
            ]]
    });
        $.ajax({
            url: '${ctx}/console/thesis/defense/group/list?taskid=${taskid}',
            type:'get',
            dataType:'json',
            success:function (data) {
                $("#dg").datagrid('loadData',data);
            }
        })
    });

    var d;

    function add(){
        window.top.addTab("新增答辩小组", '${ctx}/console/thesis/defense/group/add?taskid=${taskid}', null, true);
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
</script>
</body>
</html>

