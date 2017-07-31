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
    <title>论文任务书</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'north',split:true, border:false" height="60px">
    <div id="p" class="easyui-panel" title="管理提示" style="padding:5px;" iconCls="myicon-information">
        <span class="myicon-tick" style="width: 16px; height: 16px;display: inline-block;margin-right: 4px;">&nbsp;&nbsp;</span>
        ${currentProj.title}，你一共有<strong>&nbsp;${expands.size()}&nbsp;</strong>道论文题目需要填写任务书
    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'论文任务书列表', iconCls:'myicon-table-go'">
    <table id="dg" class="easyui-datagrid"
           data-options="
                        idField: 'id',
                        fit:true,
                        fitColumns:true,
                        rownumbers:true,
                        onLoadSuccess:onLoadSuccess,
                        singleSelect:true">
        <thead>
        <tr>
            <th data-options="field:'id'" hidden="true">论文ID</th>
            <th data-options="field:'topic'" width="200">论文题目</th>
            <th data-options="field:'stuname'" width="80">选题学生</th>
            <th data-options="field:'stuno'" width="60" hidden="true">学生学号</th>
            <th data-options="field:'taskid'" width="60" hidden="true">任务书ID</th>
            <th data-options="field:'state'" width="60">下达情况</th>
            <th data-options="field:'tconfirm'" width="60">教师确认</th>
            <th data-options="field:'sconfirm'" width="60">学生确认</th>
            <th data-options="field:'action'"  align="left" width="200">操作</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${expands}" var="thesisEx">
                <tr>
                    <td>${thesisEx.id}</td>
                    <td><a href="#" onclick="viewTopic(${thesisEx.id}, event);">${thesisEx.topic}</a></td>
                    <td><a href="#" onclick="viewStudent(${thesisEx.studentid});">${thesisEx.stuname}</a></td>
                    <td>${thesisEx.stuno}</td>
                    <td>${thesisEx.taskid}</td>
                    <td>${thesisEx.state}</td>
                    <td>${thesisEx.tconfirm}</td>
                    <td>${thesisEx.sconfirm}</td>
                    <td>
                        <a name="edit" href="#" onclick="edit('${thesisEx.id}', '${thesisEx.taskid}')">编辑任务书</a>
                        <c:if test="${thesisEx.taskid gt 0}">
                            &nbsp;&nbsp;|&nbsp;&nbsp;<a name="confirm" href="#" onclick="confirm('${thesisEx.taskid}')">确认任务书</a>
                            &nbsp;&nbsp;|&nbsp;&nbsp;<a name="view" href="${ctx}/console/ttask/view?id=${thesisEx.taskid}" target="_blank">查看任务书</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<div id="dlg"></div>
<script>
    function onLoadSuccess(data){
        $("a[name='edit']").linkbutton({text:'编辑任务书',plain:true, iconCls:'icon-edit', width:100});
        $("a[name='confirm']").linkbutton({text:'确认任务书',plain:true, iconCls:'icon-ok', width:100});
        $("a[name='view']").linkbutton({text:'查看任务书',plain:true, iconCls:'icon-search', width:100});
        $("#dg").datagrid("resize");
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

    function edit(thesisid, taskid){
        d=$("#dlg").dialog({
            title: '编辑任务书',
            width: 860,
            height: 600,
            href:'${ctx}/console/ttask/edit?id=' + taskid + '&thesisid=' + thesisid,
            maximizable:true,
            modal:true
        });
    }
    function confirm(taskid){
        $.messager.confirm('提示', "确认当前的论文任务书？", function(r) {
            if (r) {
                $.post("${ctx}/console/ttask/confirm", {"taskid":taskid}, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示', data.msg, undefined, function(){
                            location.reload();
                        });
                    }else{
                        $.messager.alert('错误', data.msg);
                    }
                });
            }
        });
        return false;
    }
</script>
</body>
</html>
