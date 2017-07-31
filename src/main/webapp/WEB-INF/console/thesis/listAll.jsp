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
    <title>历年题目管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div id="ui-toolbar">
    <div class="ui-toolbar-search">
        <select id="project" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 250px;">
            <option value="0">请选择论文工作</option>
            <c:forEach items="${projects}" var="proj">
                <option value="${proj.id}">${proj.title}</option>
            </c:forEach>
        </select>
        <label>关键词：</label><input class="wu-text easyui-textbox" id="keywords" style="width:200px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
    </div>
</div>
<table id="dg" class="easyui-datagrid"
       data-options="url: '${ctx}/console/thesis/list.json',
                        method: 'get',
                        queryParams:{projid: 0},
                        idField: 'id',
                        toolbar: '#ui-toolbar',
                        fit:true,
                        fitColumns:true,
                        pagination:true,
                        rownumbers:true,
                        pageNumber:1,
                        pageSize : 20,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        singleSelect:true">
    <thead>
    <tr>
        <th data-options="field:'id', formatter: formatView", width="100">查看详细</th>
        <th data-options="field:'topic'" width="300">论文题目</th>
        <th data-options="field:'direction'" width="100">研究方向</th>
        <th data-options="field:'source'" width="80">论文来源</th>
        <th data-options="field:'property'" width="80">论文性质</th>
        <th data-options="field:'project'" width="200">所属论文工作</th>
        <th data-options="field:'studentid', formatter:formatState" width="80">是否被选择</th>
        <th data-options="field:'cdate',formatter:formatDate" width="150">出题日期</th>
    </tr>
    </thead>
</table>
<script>
    function doSearch(){
        $("#dg").datagrid("load",{
            projid:$("#project").combobox("getValue"),
            keywords:$("#keywords").val()
        });
        return false;
    }

    function formatView(val, row){
        return '<a href="#" onclick="view('+ val +', event);"><div class="myicon-zoom-in" style="width:16px;height:16px">&nbsp;&nbsp;&nbsp;&nbsp;点击查看</div></a>';
    }
    function formatState(val, row){
        if(val || val > 0){
            return "<span style='background-color: #009cff; padding: 4px;'>已选择</span>";
        }else{
            return "<span style='background-color: #efff00;padding: 4px;'>未选择</span>";
        }
    }

    function view(id, event){
        window.top.addTab("论文题目详情", '${ctx}/console/thesis/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }
</script>
</body>
</html>
