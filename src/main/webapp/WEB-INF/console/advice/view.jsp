<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/12
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <!doctype html>
        <html>
        <head>
            <meta charset="utf-8"/>
            <title>公告详情</title>
            <%@include file="/inc/header.jsp" %>
        </head>
        <body>
        <div style="padding:10px 10px 10px 10px">
            <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">公告信息</div>
            <input type="hidden" name="id" value="${advice.id}"/>
            <table width="100%" cellpadding="5" class="form-table">
                <tbody>
                <tr>
                    <td width="120"><label>公告标题</label></td>
                    <td colspan="5" align="center">
                            ${advice.topic}
                    </td>
                </tr>
                <c:if test="${currentUser.type.ordinal() eq 0 ||currentUser.type.ordinal() eq 1}">
                    <tr>
                        <td><label>发布状态</label></td>
                        <td >
                                ${advice.valid==true?"已发布":"未发布"}
                        </td>
                        <td width="80"><label>是否置顶</label></td>
                        <td>
                                ${thesis.top==true?"是":"否"}
                        </td>
                        <td width="80"><label>创建者</label></td>
                        <td>
                                ${user.username}
                        </td>
                    </tr>
        </c:if>
        <tr>
            <td><label>创建时间</label></td>
            <td colspan="5" align="center">
                <fmt:formatDate value="${advice.cdate}"/>
            </td>
        </tr>
        <tr>
            <td><label>公告正文</label></td>
            <td colspan="5" align="left">
                ${advice.content}
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>