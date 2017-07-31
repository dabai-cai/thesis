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
    <title>教师出题情况</title>
    <%@include file="/inc/header.jsp" %>
    <script src="${ctx}/resources/echarts/echarts.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',split:true, border:false,title:'教师出题数量'" width="50%">
    <div id="ui-toolbar">
        <div class="ui-toolbar-search">
            <label>关键词：</label><input class="wu-text easyui-textbox" id="keywords" style="width:200px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
        </div>
    </div>
    <table id="dg" class="easyui-datagrid"
           data-options="url: '${ctx}/console/tcount/list.json',
                        method: 'get',
                        <%--idField: 'id',--%>
                        toolbar: '#ui-toolbar',
                        fit:true,
                        fitColumns:true,
                        sortName:'account',
                        sortOrder:'asc',
                        pagination:true,
                        rownumbers:true,
                        pageNumber:1,
                        pageSize : 20,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        singleSelect:true">
        <thead>
        <tr>

            <th data-options="field:'account'" width="100" sortable="true">教师工号</th>
            <th data-options="field:'username'" width="100">教师姓名</th>
            <th data-options="field:'cnt'" width="80" sortable="true">出题数量</th>
        </tr>
        </thead>
    </table>
</div>
<div data-options="region:'east',split:true, border:false, title:'教师出题情况'" width="50%">
    <div id="main" style="width: 50%;height:400px;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '教师出题情况'
            },
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data:['人数']
            },
            xAxis: {
                name: "出题数量",
                axisTick: {
                    alignWithLabel: true
                },
                data: [
                    <c:forEach items="${xArry}" var="x" varStatus="s">
                        ${x}${s.last ? "" : ","}
                    </c:forEach>
                ]
            },
            yAxis: {
                name: "所占人数"
            },
            series: [{
                name: '人数',
                type: 'bar',
                data: [
                    <c:forEach items="${yArry}" var="y" varStatus="s">
                        ${y}${s.last ? "" : ","}
                    </c:forEach>
                ]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
</div>

<script>
    function doSearch(){
        $("#dg").datagrid("load",{
            keywords:$("#keywords").val()
        });
        return false;
    }
</script>
</body>
</html>
