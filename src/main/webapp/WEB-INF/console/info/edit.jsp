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
<c:set var="role" value="管理员" />
<c:if test="${type eq 'teacher'}">
    <c:set var="role" value="教师" />
</c:if>
<c:if test="${type eq 'student'}">
    <c:set var="role" value="学生" />
</c:if>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>${role}管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div style="padding:10px 10px 10px 10px">
    <form id="infoForm" method="post" action="${ctx}/console/info/edit">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">个人资料编辑</div>
        <input type="hidden" name="username" value="${user.username}" />
        <input type="hidden" name="userinfo.major" value="${userinfo.major}" />
        <input type="hidden" name="userinfo.grade" value="${userinfo.grade}" />
        <input type="hidden" name="userinfo.clazz" value="${userinfo.clazz}" />
        <table width="50%" cellpadding="5" class="form-table">
            <tr>
                <td><label>${role}账号</label></td>
                <td>${user.account}</td>
            </tr>
            <tr>
                <td><label>${role}姓名</label></td>
                <td><input class="easyui-textbox" type="text" name="username" disabled="disabled" value="${user.username}" data-options="required:true" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>${role}密码</label></td>
                <td><input type="password" class="easyui-passwordbox" id="pwd" name="pwd" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>确认密码</label></td>
                <td><input type="password" class="easyui-passwordbox" name="rpassword" validType="equals['#pwd']" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>联系电话</label></td>
                <td><input class="easyui-textbox" type="text" name="userinfo.phone" value="${user.info.phone}" /></td>
            </tr>
            <tr>
                <td><label>电子邮件</label></td>
                <td><input class="easyui-textbox" type="text" name="userinfo.email" value="${user.info.email}" /></td>
            </tr>
            <c:if test="${type ne 'admin'}">
                <c:if test="${type eq 'teacher'}">
                    <tr>
                        <td><label>教师职称</label></td>
                        <td>
                            <select id="title" class="easyui-combobox" name="userinfo.tid" editable="false" style="width:200px;">
                                <c:forEach items="${titles}" var="t">
                                    <option value="${t.id}">${t.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td><label>用户性别</label></td>
                    <td>
                        <select id="gender" class="easyui-combobox" name="userinfo.gender" editable="false" style="width:200px;">
                            <option value="男">男</option>
                            <option value="女">女</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>即时通信号码</label></td>
                    <td><input class="easyui-textbox" type="text" name="userinfo.inscomm" value="${user.info.inscomm}" /></td>
                </tr>
                <c:if test="${type eq 'student'}">
                    <tr>
                        <td><label>专&nbsp;&nbsp;&nbsp;&nbsp;业</label></td>
                        <td><input class="easyui-textbox" type="text" name="userinfo.major" disabled="disabled" value="${user.info.major}" data-options="required:true" /></td>
                    </tr>
                    <tr>
                        <td><label>班&nbsp;&nbsp;&nbsp;&nbsp;级</label></td>
                        <td><input class="easyui-textbox" type="text" name="userinfo.clazz" disabled="disabled" value="${user.info.clazz}" data-options="required:true" /></td>
                    </tr>
                    <tr>
                        <td><label>年&nbsp;&nbsp;&nbsp;&nbsp;级</label></td>
                        <td><input class="easyui-textbox" type="text" name="userinfo.grade" disabled="disabled" value="${user.info.grade}" data-options="required:true" /></td>
                    </tr>
                </c:if>
                <tr>
                    <td><label>${type eq 'teacher' ? "办公室" : "学生宿舍"}</label></td>
                    <td><input class="easyui-textbox" type="text" name="userinfo.room" value="${user.info.room}" /></td>
                </tr>
            </c:if>
            <tr>
                <td colspan="2" style="text-align: center;">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="infoForm.submitForm()" iconCls="icon-ok" style="width: 100px;">保&nbsp;&nbsp;&nbsp;&nbsp;存</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    $(function(){
        $("#gender").combobox("select",'${user.info.gender}');
        $("#title").combobox("select",'${user.info.tid}');
    });
    var infoForm = {
        submitForm: function(){
            if(!$("#infoForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#infoForm").attr("action"), $("#infoForm").serialize(), function(data){
                progressClose();
                if(data.status == 200){
                    $.messager.alert("提示", data.msg, "info");
                }else{
                    $.messager.alert("提示", data.msg);
                }
            });
        }
    };
</script>
</body>
