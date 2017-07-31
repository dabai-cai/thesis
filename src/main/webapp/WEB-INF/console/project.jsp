<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-10-09
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>论文工作错误</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div id="win" class="easyui-window" title="错误信息" style="width:300px;height:200px;padding:5px;"
     data-options="iconCls:'icon-save', modal:true, closed:true,maximizable:false,minimizable:false, collapsible:false, closable: false">
    <div style="padding-top: 20px">请重新登录后选择相应的论文工作</div>
</div>
<script type="text/javascript">
    $(function(){
        $("#win").window("open");
    });
</script>
</body>
</html>
