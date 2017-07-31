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
<%@page import="cn.zttek.thesis.modules.enums.UserType" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>用户管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout" style="height:100%; width: 100%;">
<div data-options="region:'center',split:true, border:false,title:'用户列表'">
    <div id="ui-toolbar">
        <div class="ui-toolbar-search">
            <label>用户类型：</label>
            <select id="type" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">
                <option value="-1">请选择用户类型</option>
                <c:forEach items="${types}" var="type">
                    <option value="${type.ordinal()}">${type.label}</option>
                </c:forEach>
            </select>
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
           data-options="url: '${ctx}/console/user/list.json',
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
                        singleSelect:true,
                        onBeforeSelect:onBeforeSelect
                    ">
        <thead>
        <tr>
            <th data-options="field:'id'" width="60">用户ID</th>
            <th data-options="field:'account'" width="200" align="left">用户账号</th>
            <th data-options="field:'username'" width="200">用户名称</th>
            <th data-options="field:'type'" width="150">用户类型</th>
            <th data-options="field:'mdate',formatter:formatDate" width="220">修改时间</th>
            <th data-options="field:'action', formatter: action" width="150">操作</th>
        </tr>
        </thead>
    </table>
</div>
<div data-options="region:'east',split:true,border:false,title:'角色列表'" style="width: 425px">

    <div id="tg_tb" style="padding:5px;height:auto">
        <div>
            <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveRole();">保存授权</a>
            <span class="toolbar-item dialog-tool-separator"></span>
            <a href="#" class="easyui-linkbutton" iconCls="icon-undo" onclick="resetRole()">恢复授权</a>
        </div>
    </div>
    <table id="dg2"></table>
</div>
<div id="dlg"></div>
<script>
    var d;
    var supertype ="<%=UserType.SUPER%>";
    function onBeforeSelect(index, row) {

    }

    function doSearch(){
        $("#dg").datagrid("load",{
            type:$("#type").combobox("getValue"),
            keywords:$("#keywords").val()
        });
        return false;
    }

    var dg2;
    $(function(){
        dg2=$('#dg2').datagrid({
            method: "get",
            url:'${ctx}/console/role/list.json',
            fit : true,
            fitColumns : true,
            border : false,
            idField : 'id',
            rownumbers:true,
            striped:true,
            columns:[[
                {field:'id',title:'角色ID',checkbox:true,width:40},
                {field:'name',title:'角色名称',width:100},
                {field:'code',title:'角色编码',width:100}
            ]],
            toolbar:'#tg_tb',
            dataPlain: true
        });
    });

    var d;

    function add(){
        d=$("#dlg").dialog({
            title: '添加用户',
            width: 380,
            height: 380,
            href:'${ctx}/console/user/add',
            maximizable:true,
            modal:true
        });
    }

    function edit(){
        var id = getSelectedId($("#dg"));
        if(id == undefined || id == "" || id == null){
            $.messager.alert('提示','必须选择一个用户才能编辑!');
            return ;
        }
        d=$("#dlg").dialog({
            title: '编辑用户',
            width: 380,
            height: 380,
            href:'${ctx}/console/user/edit?id=' + id,
            maximizable:true,
            modal:true
        });
    }

    function del(){
        var ids = getSelectedIds($("#dg"));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要删除的用户!');
            return ;
        }
        $.messager.confirm('确认','确定删除ID为 '+ids+' 的用户吗？',function(r){
            if (r){
                var params = {"ids":ids};
                $.post("${ctx}/console/user/delete",params, function(data){
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



    function action(value, row, index){
        if(row.type == supertype){
            return  "";
        }
        return '<a href="#" onclick="getRole('+ row.id +');"><div class="icon-lock" style="width:16px;height:16px">&nbsp;&nbsp;&nbsp;&nbsp;授权</div></a>';
    }

    var userRoleData;
    var uid;

    function getRole(userId){
        $("#dg").datagrid("clearSelections");
        uid = userId;
        $("#dg").datagrid("selectRecord", uid);
        if(userRoleData){
            $("#dg2").datagrid("clearChecked");
        }
        $.ajax({
            type:'get',
            url:"${ctx}/console/role/listByUser.json",
            data:{uid: userId},
            success: function(data){
                userRoleData=data.rows;
                for(var i=0,j=userRoleData.length; i<j; i++){
                    $("#dg2").datagrid('selectRecord',userRoleData[i].id);
                }
            }
        });
    }

    function saveRole(){
        var sels = $("#dg2").datagrid("getChecked");
        $.messager.confirm('提示', "确认要保存当前用户的授权？", function(r){
            if(r){
                var rids = [];
                for(var i in sels){
                    rids.push(sels[i].id);
                }
                if(uid == null){
                    $.messager.alert({ title : "提示",msg: "请选择用户！"});
                    return;
                }
                $.ajax({
                    type:'POST',
                    data:JSON.stringify(rids),
                    contentType:'application/json;charset=utf-8',
                    url:"${ctx}/console/user/updateRole/" + uid,
                    success: function(data){
                        if(data.status == 200){
                            $.messager.alert('提示', data.msg);
                        }else{
                            $.messager.alert('错误', data.msg);
                        }
                    }
                });
            }
        });
    }
    //恢复权限选择
    function resetRole(){
        if(uid == null){
            $.messager.alert({ title : "提示",msg: "请选择用户！"});
            return;
        }
        getRole(uid);
    }

</script>
</body>
</html>
