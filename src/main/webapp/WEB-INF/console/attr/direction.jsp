<%--
  Created by IntelliJ IDEA.
  User: 大白菜
  Date: 2017/8/14 0028
  Time: 上午 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>基础数据管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'center',split:true, border:false,title:'论文研究方向'" width="100%">
    <div id="ui-toolbar-direction">
        <div class="ui-toolbar-button">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add('direction')">添加研究方向</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit('direction')">编辑研究方向</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del('direction')">删除研究方向</a>
        </div>
    </div>
    <table id="dg-direction" class="easyui-datagrid"
           data-options="url: '${ctx}/console/attr/list-direction.json',
                        method: 'get',
                        idField: 'id',
                        toolbar: '#ui-toolbar-direction',
                        fit:true,
                        fitColumns:true,
                        rownumbers:true,
                        singleSelect:true
                    ">
        <thead>
        <tr>
            <th data-options="field:'id'" width="40">ID</th>
            <th data-options="field:'direction'" width="150">论文研究方向</th>
            <th data-options="field:'mdate',formatter:formatDate" width="150">修改时间</th>
        </tr>
        </thead>
    </table>
</div>

<div id="dlg">

</div>
<script>

    function add(op){
        d=$("#dlg").dialog({
            title: '添加' + getLabel(op),
            width: 400,
            height: 220,
            href:'${ctx}/console/attr/add-' + op,
            maximizable:true,
            modal:true
        });
    }

    function edit(op){
        var id = getSelectedId($("#dg-" + op));
        if(id == undefined || id == "" || id == null){
            $.messager.alert('提示','必须选择一个' + getLabel(op) + '才能编辑!');
            return ;
        }
        d=$("#dlg").dialog({
            title: '编辑' + getLabel(op),
            width: 400,
            height: 220,
            href:'${ctx}/console/attr/edit-' + op + '?id=' + id,
            maximizable:true,
            modal:true
        });
    }

    function del(op){
        var ids = getSelectedIds($("#dg-" + op));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要删除的' + getLabel(op) + '！');
            return ;
        }

        $.messager.confirm('确认','确定要删除ID为 '+ids+' 的' + getLabel(op) + '吗？',function(r){
            if (r){
                var params = {"ids":ids};
                $.post("${ctx}/console/attr/delete-" + op, params, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示', data.msg, undefined, function(){
                            $("#dg-" + op).datagrid("reload");
                        });
                    }else{
                        $.messager.alert('错误', data.msg);
                    }
                });
            }
        });
        return false;
    }

    function getLabel(op){
        switch (op) {
            case "direction":
                return "论文研究方向";
            case "source":
                return "论文来源";
            default:
                return "论文属性";
        }
    }


</script>
</body>
</html>
