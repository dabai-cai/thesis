<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-10-09
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>系统错误</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div id="win" class="easyui-window" title="错误信息" style="width:300px;height:200px;padding:5px;"
     data-options="iconCls:'icon-save', modal:true, closed:true,maximizable:false,minimizable:false, collapsible:false, closable: false">
    <div style="padding-top: 20px;font-size: 14px; color: red;">${message}</div>
    <div style="padding-top: 20px;font-size: 14px; color: red;">点击页面右上角[安全退出]重新登录</div>
</div>
<script type="text/javascript">
    $(function(){
        $("#win").window("open");
        $("#win").window("center");
    });
</script>
</body>
</html>
