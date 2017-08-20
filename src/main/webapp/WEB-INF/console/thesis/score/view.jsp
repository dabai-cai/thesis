<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-10-10
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <title>本科毕业设计（论文）成绩单</title>
    <style type="text/css">
        table{
            border-collapse: collapse;
        }
        table > tbody > tr > td{
            border: 1px solid black;
            padding-left: 4px;
            padding-right: 4px;
        }
        pre {
            white-space: pre-wrap;
            white-space: -moz-pre-wrap;
            white-space: -o-pre-wrap;
            word-wrap: break-word;
            height: 100px;
            font-size: 14px;
        }
    </style>
</head>
<body>
<table align="center">
    <tr>
        <th align="center">
            <font size="4">华南农业大学${currentOrg.name}</font>
            <br/>
            <font size="5">本科毕业设计（论文）成绩单</font>
        </th>
    </tr>
</table>
<table width="700" align="center" border="1" cellspacing="0" bordercolor="#666666">
    <tr>
        <td colspan="3" height="40" align="justify" width="40">学号</td>
        <td colspan="2" height="40" align="left" width="80">${student.account}</td>
        <td colspan="2" height="40" align="justify" width="60">姓名</td>
        <td colspan="2" height="40" align="left" width="80">${student.username}</td>
        <td colspan="3" height="40" align="justify" width="40">专业</td>
        <td colspan="2" height="40" align="left" width="80">${student.info.grade}级${student.info.clazz}</td>
    </tr>
    <tr>
        <td colspan="3" height="40" align="justify" width="80">论文题目</td>
        <td colspan="11" height="40" align="left" width="340">${thesis.topic}</td>
    </tr>
    <tr>
        <td colspan="3" height="40" align="justify" width="90">指导老师</td>
        <td colspan="2" height="40" align="left" width="80">${teacher.username}</td>
        <td colspan="2" height="40" align="justify" width="60">职 称</td>
        <td colspan="2" height="40" align="left" width="80">${teacher.info.title}</td>
        <td colspan="2" height="40" align="justify" width="100">评分</td>
        <td colspan="3" height="40" align="justify" width="140">
            ${score.mark1}
        </td>
    </tr>
    <tr>
        <td colspan="3" height="40" align="justify" width="90">评阅老师</td>
        <td colspan="2" height="40" align="left" width="80">${viewer.username}</td>
        <td colspan="2" height="40" align="justify" width="60">职 称</td>
        <td colspan="2" height="40" align="left" width="80">${viewer.info.title}</td>
        <td colspan="2" height="40" align="justify" width="100">评分</td>
        <td colspan="3" height="40" align="justify" width="140">
            ${score.mark2}
        </td>
    </tr>
    <tr>
        <td colspan="3" height="40" align="justify" width="90">答辩组长</td>
        <td colspan="2" height="40" align="justify" width="60"></td>
        <td colspan="2" height="40" align="justify" width="60">职 称</td>
        <td colspan="2" height="40" align="left" width="80">${viewer.info.title}</td>
        <td colspan="2" height="40" align="justify" width="100">评分</td>
        <td colspan="3" height="40" align="justify" width="140">
            ${score.mark3}
        </td>
    </tr>
    <tr>
        <td colspan="3" height="40" align="justify" width="90">是否通过答辩</td>
        <td colspan="11" height="40" align="justify" width="340">${agree}</td>
    </tr>
    <tr>
        <td colspan="3" height="40" align="justify" width="120">论文总评分数</td>
        <td colspan="4" height="40" align="left" width="80">${general}</td>
        <td colspan="3" height="40" align="justify" width="120">论文成绩等级</td>
        <td colspan="4" height="40" align="left" width="80">${level}</td>
    </tr>

    <tr>
        <td colspan="16" width="640" align="left">
            <span style="font-weight:bold;">指导教师评语</span><br/>
            <pre>${score.comment1}</pre>
        </td>
    </tr>
    <tr>
        <td colspan="16" width="640" align="left">
            <span style="font-weight:bold;">评阅教师评语</span><br/>
            <pre>${score.comment2}</pre>
        </td>
    </tr>
</table>
</body>
</html>
