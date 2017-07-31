<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-09-17
  Time: 00:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>论文题目详情</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
    <div style="padding:10px 10px 10px 10px">
    <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">论文题目信息</div>
    <input type="hidden" name="id" value="${thesis.id}"/>
    <table width="100%" cellpadding="5" class="form-table">
        <tbody>
        <tr>
            <td width="120"><label>论文标题</label></td>
            <td colspan="3">
                ${thesis.topic}
            </td>
            <td><label>指导教师</label></td>
            <td>
                ${teacher.username}&nbsp;&nbsp;${teacher.info.title}
            </td>
        </tr>
        <tr>
            <td><label>研究方向</label></td>
            <td>
                ${thesis.direction}
            </td>
            <td width="80"><label>论文来源</label></td>
            <td>
                ${thesis.source}
            </td>
            <td width="80"><label>论文性质</label></td>
            <td>
                ${thesis.property}
            </td>
        </tr>
        <c:if test="${student ne null}">
            <tr>
                <td><label>选题学生</label></td>
                <td>
                        ${student.username}
                </td>
                <td><label>学生学号</label></td>
                <td>
                        ${student.account}
                </td>
                <td><label>专业班级</label></td>
                <td>${student.info.grade}级${student.info.clazz}</td>
            </tr>
        </c:if>
        <tr>
            <td><label>论文上传日期</label></td>
            <td>
                <fmt:formatDate value="${thesis.uploadtime}"/>
            </td>
        </tr>
        <tr>
            <td><label>题目简介</label></td>
            <td colspan="5">
                ${thesis.profile}
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>
