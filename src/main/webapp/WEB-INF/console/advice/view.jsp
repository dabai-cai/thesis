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
            <style>
                .wrap{margin:0 auto;width:760px;}
            </style>
        </head>
        <body >

            <input type="hidden" name="id" value="${advice.id}"/>
            <div style="text-align: center" >
                <h2>${advice.topic}</h2>
                <div>
                    编辑:&nbsp;${user.username} &nbsp;&nbsp;发布时间:<fmt:formatDate value="${advice.cdate}"/>
                </div>
            </div>
            <hr/>
            <div class="wrap">
                <p>${advice.content}</p>
            </div>
        </body>