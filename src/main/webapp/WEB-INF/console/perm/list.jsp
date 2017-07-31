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
    <title>权限管理</title>
    <%@include file="/inc/header.jsp"%>
</head>
<body class="easyui-layout" style="height:100%; width: 100%;">
    <div data-options="region:'west',split:true, border:false,title:'资源列表'"  style="width: 300px;height:100%;">
        <table id="rtg" class="easyui-treegrid"
               data-options="
                    url: '${ctx}/console/resource/list.json',
                    method: 'get',
                    fit:true,
                    border : false,
                    idField: 'id',
                    treeField: 'name',
                    <%--fit:true,--%>
                    fitColumns:true,
                    animate:true,
                    rownumbers:true,
                    singleSelect:true,
                    striped:true,
                    onSelect:onSelect,
                    onBeforeSelect:onBeforeSelect">
            <thead>
                <tr>
                    <%--<th data-options="field:'id',hidden:true">资源ID</th>--%>
                    <th data-options="field:'name'" width="220">资源名称</th>
                </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'center',split:true,border:false,title:'权限列表'"  style="height:100%;">
        <div id="ui-toolbar">
            <div class="ui-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">添加</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit()">修改</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del()">删除</a>
            </div>
        </div>
        <table id="dg"></table>
    </div>
    <div id="dlg"></div>
    <script>
        var dg;
        var resid = 0;
        function onBeforeSelect(index){
            if(index.children){
                return false;
            }
        }

        function onSelect(index){
            resid = index.id;
            dg.datagrid('reload',{resid:resid});
        }

        $(function(){
            dg=$('#dg').datagrid({
                method: "get",
                url:'${ctx}/console/perm/listByRes.json',
                queryParams:{resid: resid},
                fit : true,
                fitColumns : true,
                border : false,
                idField : 'id',
                iconCls: 'icon',
                animate:true,
                rownumbers:true,
                singleSelect:true,
                striped:true,
                columns:[[
                    {field:'id',title:'权限ID', width:40},
                    {field:'name',title:'权限名称',width:100},
                    {field:'keystr',title:'权限编码',width:100},
                    {field:'mdate',title:'修改日期',width:100, formatter:formatDate}
                ]],
                toolbar:'#ui-toolbar',
                dataPlain: true
            });
        });

        var d;

        function add(){
            d=$("#dlg").dialog({
                title: '添加权限',
                width: 380,
                height: 380,
                href:'${ctx}/console/perm/add',
                maximizable:true,
                modal:true
            });
        }

        function edit(){
            var id = getSelectedId($("#dg"));
            if(id == undefined || id == "" || id == null){
                $.messager.alert('提示','必须选择一个权限才能编辑!');
                return ;
            }
            d=$("#dlg").dialog({
                title: '编辑权限',
                width: 380,
                height: 380,
                href:'${ctx}/console/perm/edit?id=' + id,
                maximizable:true,
                modal:true
            });
        }

        function del(){
            var ids = getSelectedIds($("#dg"));
            if(ids.length == 0){
                $.messager.alert('提示','未选中要删除的权限!');
                return ;
            }
            $.messager.confirm('确认','确定删除ID为 '+ids+' 的权限吗？',function(r){
                if (r){
                    var params = {"ids":ids};
                    $.post("${ctx}/console/perm/delete",params, function(data){
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
