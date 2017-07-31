<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-09-01
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/inc/dialog.jsp" %>
<c:set var="role" value="教师" />
<c:if test="${type eq 'student'}">
    <c:set var="role" value="学生" />
</c:if>
<div style="padding:10px 10px 10px 10px">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">${role}信息</div>
        <table width="100%" cellpadding="5" class="form-table">
            <tr>
                <td width="80"><label>${role}账号</label></td>
                <td>${user.account}</td>
            </tr>
            <tr>
                <td width="80"><label>${role}名称</label></td>
                <td>${user.username}</td>
            </tr>
            <tr>
                <td><label>${role}性别</label></td>
                <td>${user.info.gender}</td>
            </tr>
            <c:if test="${type eq 'teacher'}">
                <tr>
                    <td><label>教师职称</label></td>
                    <td>${user.info.title}</td>
                </tr>
            </c:if>
            <tr>
                <td><label>联系电话</label></td>
                <td>${user.info.phone}</td>
            </tr>
            <tr>
                <td><label>电子邮件</label></td>
                <td>${user.info.email}</td>
            </tr>
            <tr>
                <td><label>即时通信号码</label></td>
                <td>${user.info.inscomm}</td>
            </tr>
            <c:if test="${type eq 'student'}">
                <tr>
                    <td><label>专&nbsp;&nbsp;&nbsp;&nbsp;业</label></td>
                    <td>${user.info.major}</td>
                </tr>
                <tr>
                    <td><label>班&nbsp;&nbsp;&nbsp;&nbsp;级</label></td>
                    <td>${user.info.clazz}</td>
                </tr>
                <tr>
                    <td><label>年&nbsp;&nbsp;&nbsp;&nbsp;级</label></td>
                    <td>${user.info.grade}</td>
                </tr>
            </c:if>
            <tr>
                <td><label>${type eq 'teacher' ? "办公室" : "学生宿舍"}</label></td>
                <td>${user.info.room}</td>
            </tr>
        </table>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeView()" iconCls="icon-cancel">关闭</a>
</div>
<script type="text/javascript">
    function closeView(){
        $("#dlg").dialog("close");
    }
</script>