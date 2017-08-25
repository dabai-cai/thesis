<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/12
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>公告管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div id="ui-toolbar">
    <div class="ui-toolbar-search">
        <label>关键词：</label><input class="wu-text easyui-textbox" id="keywords" style="width:100px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
</div>
</div>
<table id="dg" class="easyui-datagrid"
       data-options="url: '${ctx}/console/advice/showlist.json',
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
                        singleSelect:true
                    ">
    <thead>
    <tr>
        <%--<th data-options="field:'id'" width="60">ID</th>--%>
        <th data-options="field:'topic', formatter: formatTopic" width="200" align="left" >公告标题</th>
        <th data-options="field:'top'"   hidden="true">是否置顶</th>
            <th data-options="field:'creatorname'",width="150" >编辑人</th>
            <th data-options="field:'mdate',formatter:formatDate" width="220">发布时间</th>
    </tr>
    </thead>
</table>
<script>
    function formatTopic(value, row, index){
        if(row.top==true){
            return '<a href="#" class="notselect" onclick="return view('+ row.id +',event);"><span style="background-color: #ff121a; padding: 5px;">【置顶】</span>'+value+'</a>';
        }else if(row.top==false){
            return '<a href="#" class="notselect" onclick="return view('+ row.id +',event);">'+value+'</a>';
        }
    }
    function formatView(val, row){
        return '<a href="#" class="notselect" onclick="return view('+ row.id +',event);"><div class="myicon-zoom-in" style="width:16px;height:16px">&nbsp;&nbsp;&nbsp;&nbsp;点击查看</div></a>';
    }
    function view(id, event){
        window.top.addTab("公告详情", '${ctx}/console/advice/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }

    function doSearch(){
        $("#dg").datagrid("load",{
            keywords:$("#keywords").val()
        });
        return false;
    }

    $(function(){

    });



</script>
</body>
</html>
