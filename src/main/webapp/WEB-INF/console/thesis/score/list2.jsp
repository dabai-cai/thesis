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
    <title>评阅教师评分</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'north',split:true, border:false" height="60px">
    <div id="p" class="easyui-panel" title="管理提示" style="padding:5px;" iconCls="myicon-information">
        <span class="myicon-tick" style="width: 16px; height: 16px;display: inline-block;margin-right: 4px;">&nbsp;&nbsp;</span>
        ${currentProj.title}，你一共有<strong>&nbsp;${expands.size()}&nbsp;</strong>道论文题目需要评定成绩
    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'论文成绩列表', iconCls:'myicon-table-go'">
    <table id="dg" class="easyui-datagrid"
           data-options="
                        idField: 'id',
                        fit:true,
                        fitColumns:true,
                        rownumbers:true,
                        onLoadSuccess:onLoadSuccess,
                        singleSelect:false">
        <thead>
        <tr>
            <th data-options="field:'id'" hidden="true">论文ID</th>
            <th data-options="field:'topic'" width="200">论文题目</th>
            <th data-options="field:'stuname'" width="80">选题学生</th>
            <th data-options="field:'stuno'" width="60" hidden="true">学生学号</th>
            <th data-options="field:'teacherid'" width="60" hidden="true">指导老师ID</th>
            <th data-options="field:'teacher'" width="100">指导老师</th>
            <th data-options="field:'scoreid'" width="60" hidden="true">成绩ID</th>
            <th data-options="field:'mark'" width="60">评阅老师评分</th>
            <th data-options="field:'action'"  align="left" width="200">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${expands}" var="thesisEx">
            <tr>
                <td>${thesisEx.id}</td>
                <td><a href="#" onclick="viewTopic(${thesisEx.id},event);">${thesisEx.topic}</a></td>
                <td><a href="#" onclick="viewStudent(${thesisEx.studentid});">${thesisEx.stuname}</a></td>
                <td>${thesisEx.stuno}</td>
                <td>${thesisEx.teacherid}</td>
                <td>${thesisEx.teacher}</td>
                <td>${thesisEx.scoreid}</td>
                <td>${thesisEx.mark}</td>
                <td>
                    <c:if test="${currentProj.mark2allowed}">
                        <a name="edit" href="#" onclick="edit('${thesisEx.id}', '${thesisEx.scoreid}')">编辑评阅成绩</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<input type="hidden" id="teacherid" value="${currentUser.id}" />
<div id="dlg"></div>
<script>
    function onLoadSuccess(data){
        <c:if test="${currentProj.mark2allowed}">
        $("a[name='edit']").linkbutton({text:'编辑评阅成绩',plain:true, iconCls:'icon-edit', width:100});
        </c:if>
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

    function edit(thesisid, scoreid){
        d=$("#dlg").dialog({
            title: '编辑自评成绩',
            width: 860,
            height: 500,
            href:'${ctx}/console/tscore/edit-mark2?id=' + scoreid + '&thesisid=' + thesisid,
            maximizable:true,
            modal:true
        });
    }
</script>
</body>
</html>
