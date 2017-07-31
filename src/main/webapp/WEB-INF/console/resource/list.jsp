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
    <title>资源管理</title>
    <%@include file="/inc/header.jsp"%>
</head>
<body>
    <table id="tg" class="easyui-treegrid"
           data-options="
                    url: '${ctx}/console/resource/list.json',
                    method: 'get',
                    idField: 'id',
                    toolbar:toolbar,
                    treeField: 'name',
                    fit:true,
                    fitColumns:true,
                    singleSelect: false,
                    onLoadSuccess:onload,
                    onBeforeSelect:onBeforeSelect
                ">
        <thead>
        <tr>
            <th data-options="field:'id'" width="60">资源ID</th>
            <th data-options="field:'name'" width="220">资源名称</th>
            <th data-options="field:'url'" width="250" align="left">资源URL</th>
            <th data-options="field:'num'" width="150">显示顺序</th>
            <th data-options="field:'mdate',formatter:formatDate" width="150">修改时间</th>
        </tr>
        </thead>
    </table>
    <div id="dlg"></div>
    <script>
        var d;
        function onload(row, data){
            $("#tg").treegrid("collapseAll");
            var rows = data.rows;
            for(var i in rows){
                if(rows[i]._parentId === undefined){
                    $("#tg").treegrid("expand", rows[i].id);
                    break;
                }
            }
        }

        function onBeforeSelect(index, row){
            if(index.id == 1 || index.name === "root"){
                return false;
            }
        }

        var toolbar = [{
            text:'新增',
            iconCls:'icon-add',
            handler:function(){
                d=$("#dlg").dialog({
                    title: '添加资源',
                    width: 380,
                    height: 380,
                    href:'${ctx}/console/resource/add',
                    maximizable:true,
                    modal:true
                });
            }
        },{
            text:'编辑',
            iconCls:'icon-edit',
            handler:function(){
                var id = getSelectedId($("#tg"));
                if(id == undefined || id == "" || id == null){
                    $.messager.alert('提示','必须选择一个资源才能编辑!');
                    return ;
                }
                d=$("#dlg").dialog({
                    title: '编辑资源',
                    width: 380,
                    height: 380,
                    href:'${ctx}/console/resource/edit?id=' + id,
                    maximizable:true,
                    modal:true
                });
            }
        },{
            text:'删除',
            iconCls:'icon-remove',
            handler:function(){
                var ids = getSelectedIds($("#tg"));
                if(ids.length == 0){
                    $.messager.alert('提示','未选中要删除的资源!');
                    return ;
                }
                $.messager.confirm('确认','确定删除ID为 '+ids+' 的资源吗？',function(r){
                    if (r){
                        var params = {"ids":ids};
                        $.post("${ctx}/console/resource/delete",params, function(data){
                            if(data.status == 200){
                                $.messager.alert('提示', data.msg, undefined, function(){
                                    $("#tg").treegrid("reload");
                                });
                            }else{
                                $.messager.alert('错误', data.msg);
                            }
                        });
                    }
                });
            }
        }];


    </script>
</body>
</html>
