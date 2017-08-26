<%--
  Created by IntelliJ IDEA.
  User: 大白菜
  Date: 2017/8/14 0014
  Time: 下午 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>指导老师确认申请</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'north',split:true, border:false" height="60px">
    <div id="p" class="easyui-panel" title="管理提示" style="padding:5px;" iconCls="myicon-information">
        <span class="myicon-tick" style="width: 16px; height: 16px;display: inline-block;margin-right: 4px;">&nbsp;&nbsp;</span>
        ${currentProj.title}，你一共有<strong>&nbsp;${applys.size()}&nbsp;</strong>个申请需要确认
    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'申请列表', iconCls:'myicon-table-go'">
    <table id="dg" class="easyui-datagrid"
           data-options="
                        idField: 'id',
                        fit:true,
                        fitColumns:true,
                        toolbar:'#ui-toolbar',
                        rownumbers:true,
                        onLoadSuccess:onLoadSuccess,
                        singleSelect:false">
        <thead>
        <tr>
            <th data-options="field:'topic'" width="200">论文题目</th>
            <th data-options="field:'stuname'" width="80">选题学生</th>
            <th data-options="field:'mark'" width="80">申请时间</th>
            <th data-options="field:'action'"  align="left" width="200">操作</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <c:forEach items="${applys}" var="apply">
                <td><a href="#" onclick="viewTopic(${apply.thesisid});">${apply.topic}</a></td>
                <td><a href="#" onclick="viewStudent(${apply.studentid});">${apply.username}</a></td>
                <td><fmt:formatDate value="${apply.cdate}"/></td>
                <td>
                    <a name="edit" href="#"onclick="edit('${apply.id}')" class="easyui-linkbutton">编辑申请</a>
                </td>
            </c:forEach>

        </tr>
        </tbody>
    </table>
</div>
<input type="hidden" id="teacherid" value="${currentUser.id}" />
<div id="dlg"></div>
<script>
    function onLoadSuccess(data){

        $("a[name='edit']").linkbutton({text:'编辑申请',plain:true, iconCls:'icon-add', width:100});

        $("#dg").datagrid("resize");
    }
    var d;
    function viewStudent(studentid){
        d=$("#dlg").dialog({
            title: '查看学生信息',
            width: 320,
            height: 480,
            href:'${ctx}/console/arch/view?id=' + studentid,
            maximizable:true,
            modal:true
        });
        return false;
    }
    function viewTopic(id, event){
        window.top.addTab("论文题目详情", '${ctx}/console/thesis/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }

    function edit(applyid){
        d=$("#dlg").dialog({
            title: '争优/延期申请',
            width: 860,
            height: 500,
            href:'${ctx}/console/gooddelay/edit-orgconf?id='+applyid,
            maximizable:true,
            modal:true
        });
    }


</script>
</body>
</html>
