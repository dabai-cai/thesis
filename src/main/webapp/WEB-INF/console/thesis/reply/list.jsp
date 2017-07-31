<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-09-15
  Time: 00:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>确定选题学生</title>
    <%@include file="/inc/header.jsp" %>
</head>

<body class="easyui-layout">
<div data-options="region:'north',split:true, border:false" height="60px">
    <div id="p" class="easyui-panel" title="管理提示" style="padding:5px;" iconCls="myicon-information">
        <span class="myicon-tick" style="width: 16px; height: 16px;display: inline-block;margin-right: 4px;">&nbsp;&nbsp;</span>
        你一共有<strong>&nbsp;<span id="total">0</span>&nbsp;</strong>条选题申请信息，请选择回复
    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'学生选题申请列表', iconCls:'myicon-table-go'">
    <table id="dg" class="easyui-datagrid"
           data-options="url: '${ctx}/console/treply/list.json',
                        method: 'get',
                        idField: 'id',
                        fit:true,
                        fitColumns:true,
                        pagination:true,
                        rownumbers:true,
                        pageNumber:1,
                        pageSize : 20,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        onLoadSuccess:onLoadSuccess,
                        singleSelect:true">
        <thead>
        <tr>
            <th data-options="field:'topic', formatter:formatTopic" width="200">论文题目</th>
            <th data-options="field:'student', formatter:formatStudent" width="80">申请学生</th>
            <th data-options="field:'stuno'" width="100">学生学号</th>
            <th data-options="field:'replyInfo'" width="300">回复意见</th>
            <th data-options="field:'id', formatter:formatAction" align="center" width="80">操作</th>
        </tr>
        </thead>
    </table>
</div>
<div id="dlg"></div>
<script>

    function formatStudent(val, row){
        return "<a href='#' onclick='viewStudent(" + row.studentid + ")'>" + val + "</a>";
    }

    function formatTopic(val, row){
        return '<a href="#" onclick="viewTopic('+ row.thesisid +', event);">'+ val + '</a>';
    }

    function formatAction(val, row){
        return '<a href="javascript:void(0)" name="reply" class="easyui-linkbutton" onclick="reply(' + val + ')" iconCls="icon-ok">答复</a>';
    }

    function doSearch(){
        $("#dg").datagrid("load",{
            teacherid: $("#teacherid").combogrid("getValue"),
            keywords:$("#keywords").val()
        });
        return false;
    }

    function onLoadSuccess(data){
        var total = data.total;
        $("#total").text(total);
        $("a[name='reply']").linkbutton({text:'答复',plain:true,iconCls:'icon-ok', width:80});
    }
    var d;
    function viewStudent(studentid){
        d=$("#dlg").dialog({
            title: '查看学生信息',
            width: 320,
            height: 480,
            href:'${ctx}/console/arch/view?id=' + studentid,
            maximizable:true,
            modal:true
        });
        return false;
    }

    function viewTopic(id, event){
        window.top.addTab("论文题目详情", '${ctx}/console/thesis/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }

    function reply(id){
        d=$("#dlg").dialog({
            title: '申请题目',
            width: 820,
            height: 480,
            href:'${ctx}/console/treply/edit?id=' + id,
            maximizable:true,
            modal:true
        });
        return false;
    }

    function edit(){
        var row = $("#dg2").datagrid("getSelected");
        var id = row.id;
        if(id == undefined || id == "" || id == null){
            $.messager.alert('提示','必须选择一个申请才能编辑!');
            return ;
        }
        d=$("#dlg").dialog({
            title: '修改申请',
            width: 760,
            height: 400,
            href:'${ctx}/console/tapply/edit?id=' + id + '&thesisid=' + row.thesisid,
            maximizable:true,
            modal:true
        });
    }
</script>
</body>
</html>
