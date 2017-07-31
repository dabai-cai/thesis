<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-08-12
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>角色管理</title>
    <%@include file="/inc/header.jsp"%>
</head>
<body class="easyui-layout" style="height:100%; width: 100%;">
    <div data-options="region:'center',split:true, border:false,title:'角色列表'">
        <div id="ui-toolbar">
            <div class="ui-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">添加</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit()">修改</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del()">删除</a>
            </div>
        </div>
        <table id="dg" class="easyui-datagrid"
               data-options="
                    url: '${ctx}/console/role/list.json',
                    method: 'get',
                    fit:true,
                    border : false,
                    toolbar:'#ui-toolbar',
                    idField: 'id',
                    fitColumns:true,
                    animate:true,
                    rownumbers:true,
                    singleSelect:true,
                    onBeforeSelect:onBeforeSelect,
                    striped:true">
            <thead>
                <tr>
                    <th data-options="field:'id'" width="60">角色ID</th>
                    <th data-options="field:'name'" width="220">角色名称</th>
                    <th data-options="field:'code'" width="220">角色编码</th>
                    <th data-options="field:'mdate',formatter:formatDate" width="150">修改时间</th>
                    <th data-options="field:'action', formatter: action" width="220">操作</th>
                </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'east',split:true,border:false,title:'权限列表'" style="width: 425px">

        <div id="tg_tb" style="padding:5px;height:auto">
            <div>
                <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="savePerm();">保存授权</a>
                <span class="toolbar-item dialog-tool-separator"></span>
                <a href="#" class="easyui-linkbutton" iconCls="icon-undo" onclick="resetPerm()">恢复授权</a>
            </div>
        </div>
        <table id="tg"></table>
    </div>
    <div id="dlg"></div>
    <script>
        function onBeforeSelect(index, row){
            if(row.code == "super"){
                return false;
            }
        }
        var tg;
        $(function(){
            tg=$('#tg').treegrid({
                method: "get",
                url:'${ctx}/console/perm/tree.json',
                fit : true,
                fitColumns : true,
                border : false,
                idField : 'id',
                treeField: 'text',
                checkbox:true,
                animate:true,
                rownumbers:true,
                striped:true,
                columns:[[
                    {field:'id',title:'权限ID', hidden:true, width:40},
                    {field:'text',title:'权限名称',width:100},
                    {field:'leaf', hidden:true}
                ]],
                toolbar:'#tg_tb',
                dataPlain: true
            });
        });

        var d;

        function add(){
            d=$("#dlg").dialog({
                title: '添加角色',
                width: 380,
                height: 380,
                href:'${ctx}/console/role/add',
                maximizable:true,
                modal:true
            });
        }

        function edit(){
            var id = getSelectedId($("#dg"));
            if(id == undefined || id == "" || id == null){
                $.messager.alert('提示','必须选择一个角色才能编辑!');
                return ;
            }
            d=$("#dlg").dialog({
                title: '编辑角色',
                width: 380,
                height: 380,
                href:'${ctx}/console/role/edit?id=' + id,
                maximizable:true,
                modal:true
            });
        }

        function del(){
            var ids = getSelectedIds($("#dg"));
            if(ids.length == 0){
                $.messager.alert('提示','未选中要删除的角色!');
                return ;
            }
            $.messager.confirm('确认','确定删除ID为 '+ids+' 的角色吗？',function(r){
                if (r){
                    var params = {"ids":ids};
                    $.post("${ctx}/console/role/delete",params, function(data){
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
            return '<a href="#" onclick="getPerm('+ row.id +');"><div class="icon-lock" style="width:16px;height:16px">&nbsp;&nbsp;&nbsp;&nbsp;授权</div></a>';
        }

        var rolePerData;
        var rid;

        function getPerm(roleId){
            $("#dg").treegrid("clearSelections");
            rid = roleId;
            $("#dg").treegrid("selectRecord", rid);
            if(rolePerData){
                $("#tg").treegrid("clearChecked");
            }
            $.ajax({
                type:'get',
                url:"${ctx}/console/perm/listByRole.json",
                data:{rid: roleId},
                success: function(data){
                    rolePerData=data.rows;
                    for(var i=0,j=rolePerData.length;i<j;i++){
                        $("#tg").treegrid('checkNode',rolePerData[i].id);
                    }
                }
            });
        }

        function savePerm(){
            var sels = $("#tg").treegrid("getCheckedNodes");
/*            var row = $("#dg").datagrid('getSelected');
            var roleId=row.id;*/
            $.messager.confirm('提示', "确认要保存当前角色的授权？", function(r){
                if(r){
                    var pids = [];
                    for(var i in sels){
                        if(sels[i].leaf){
                            pids.push(sels[i].id);
                        }
                    }
                    if(rid == null){
                        $.messager.alert({ title : "提示",msg: "请选择角色！"});
                        return;
                    }
                    $.ajax({
                        type:'POST',
                        data:JSON.stringify(pids),
                        contentType:'application/json;charset=utf-8',
                        url:"${ctx}/console/role/updatePerm/" + rid,
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
        function resetPerm(){
            if(rid == null){
                $.messager.alert({ title : "提示",msg: "请选择角色！"});
                return;
            }
            getPerm(rid);
        }
    </script>
</body>
</html>
