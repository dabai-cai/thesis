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
    <title>答辩秘书录入成绩</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/table.css">
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'north',split:true, border:false" height="60px">
    <div id="p" class="easyui-panel" title="管理提示" style="padding:5px;" iconCls="myicon-information">
        <span class="myicon-tick" style="width: 16px; height: 16px;display: inline-block;margin-right: 4px;">&nbsp;&nbsp;</span>
        ${currentProj.title}，你一共有<strong>&nbsp;${expands.size()}&nbsp;</strong>道论文题目需要录入成绩
    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'论文成绩列表', iconCls:'myicon-table-go'">
    <table id="dg" class="easyui-datagrid"
           data-options="
                        idField: 'id',
                        fit:true,
                        fitColumns:true,
                        toolbar:'#ui-toolbar',
                        rownumbers:true,
                        onLoadSuccess:onLoadSuccess,
                        singleSelect:false">
        <thead>
        <tr>
            <th data-options="field:'id', checkbox:true">论文ID</th>
            <th data-options="field:'topic'" width="200">论文题目</th>
            <th data-options="field:'stuname'" width="80">选题学生</th>
            <th data-options="field:'stuno'" width="60" hidden="true">学生学号</th>
            <th data-options="field:'viewerid'" width="60" hidden="true">指导教师ID</th>
            <th data-options="field:'viewer'" width="100">指导教师</th>
            <th data-options="field:'scoreid'" width="60" hidden="true">成绩ID</th>
            <th data-options="field:'mark'" width="80">答辩成绩</th>
            <th data-options="field:'agree'" width="100" >答辩通过</th>
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
                <td>${thesisEx.teacherid}</td>
                <td>${thesisEx.teacher}</td>
                <td>${thesisEx.scoreid}</td>
                <td>${thesisEx.mark}</td>
                <td>
                    <c:if test="${thesisEx.mark ne null}">
                        ${thesisEx.mark lt 60 ? "不通过":"通过" }
                    </c:if>
                </td>
                <td>
                    <c:if test="${currentProj.mark3allowed}">
                        <a name="edit" href="#" onclick="edit('${thesisEx.id}', '${thesisEx.scoreid}')">编辑答辩成绩</a>
                    </c:if>
                    <c:if test="${thesisEx.scoreid gt 0}">
                        &nbsp;&nbsp;|&nbsp;&nbsp;<a name="view" href="${ctx}/console/tscore/view?id=${thesisEx.scoreid}" target="_blank">查看成绩单</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<input type="hidden" id="teacherid" value="${currentUser.id}" />
<div id="dlg"></div>
<div id="dlg-dlg" style="display: none;">
    <div id="ui-toolbar2">
        <div class="ui-toolbar-search">
            <div class="ui-toolbar-search">
                <label>关键词：</label><input class="wu-text easyui-textbox" id="keywords" style="width:100px">
                <a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
            </div>
        </div>
    </div>
    <table id="dg2">
    </table>
    <div style="padding:5px;" class="dialog-button">
        <a href="javascript:void(0)" id="ok" class="easyui-linkbutton" onclick="saveSelected()" iconCls="icon-ok">确定选择</a>
        <a href="javascript:void(0)" id="cancel" class="easyui-linkbutton" onclick="cancelSelect()" iconCls="icon-cancel">取消选择</a>
    </div>
</div>
<script>
    function onLoadSuccess(data){

        <c:if test="${currentProj.mark3allowed}">
        $("a[name='edit']").linkbutton({text:'编辑答辩成绩',plain:true, iconCls:'icon-edit', width:100});
        </c:if>
        $("a[name='view']").linkbutton({text:'查看成绩单',plain:true, iconCls:'icon-search', width:100});
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
            title: '编辑答辩成绩',
            width: 860,
            height: 500,
            href:'${ctx}/console/tscore/edit-mark3?id=' + scoreid + '&thesisid=' + thesisid,
            maximizable:true,
            modal:true
        });
    }



    function doSearch(){
        $("#dg2").datagrid("load",{
            keywords:$("#keywords").val()
        });
    }



    function cancelSelect(){
        $("#dlg-dlg").dialog("close");
    }
</script>
</body>
</html>
