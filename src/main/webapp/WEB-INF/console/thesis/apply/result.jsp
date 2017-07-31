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
    <title>论文题目选择</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">

<div data-options="region:'north',split:true, border:false, title:'选题结果', hideCollapsedContent: false" height="${thesis eq null ? '60px' : '320px'}">
    <div id="p" class="easyui-panel" data-options="collapsible:true" style="padding:5px;">
        <c:if test="${thesis eq null}">
            你还未入选任何论文题目
        </c:if>
        <c:if test="${thesis ne null}">
            <table width="100%" cellpadding="5" class="form-table">
                <tbody>
                <tr>
                    <td width="120"><label>论文标题</label></td>
                    <td colspan="3"><a href="#" onclick="viewTopic(${thesis.id});">${thesis.topic}</a></td>
                    <td><label>指导教师</label></td>
                    <td>
                        <a href="#" onclick="viewTeacher(${teacher.id});">${teacher.username}</a>&nbsp;&nbsp;${teacher.info.title}
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
                <tr>
                    <td><label>申请理由</label></td>
                    <td colspan="3">
                        <pre>${apply.applyInfo}</pre>
                    </td>
                    <td><label>申请时间</label></td>
                    <td><fmt:formatDate value="${apply.applyTime}"/></td>
                </tr>
                <tr>
                    <td><label>回复意见</label></td>
                    <td colspan="3">
                        <pre>${apply.replyInfo}</pre>
                    </td>
                    <td><label>回复时间</label></td>
                    <td><fmt:formatDate value="${apply.replyTime}"/></td>
                </tr>
                <tr>
                    <td><label>题目简介</label></td>
                    <td colspan="5">
                        ${thesis.profile}
                    </td>
                </tr>
                </tbody>
            </table>

        </c:if>
    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'我的其他申请'">
    <table id="dg2" class="easyui-datagrid"
           data-options="idField: 'id',
                        fit:true,
                        fitColumns:true,
                        singleSelect:true,
                        onLoadSuccess:onLoadSuccess,
                        rownumbers:true">
        <thead>
        <tr>
            <th field="id" width="50" hidden="true">申请ID</th>
            <th field="teacher" width="100">指导教师</th>
            <th field="thesisid" width="50" hidden="true">题目ID</th>
            <th field="topic" width="200">论文题目</th>
            <th field="applyInfo" width="250">申请理由</th>
            <th field="applyTime" width="100">申请日期</th>
            <th field="replyInfo" width="250">回复意见</th>
            <th field="replyTime" width="100">申请日期</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${applies}" var="apply">
            <tr>
                <td>${apply.id}</td>
                <td><a href="#" onclick="viewTeacher(${apply.teacherid});">${apply.teacher}</a></td>
                <td>${apply.thesisid}</td>
                <td><a href="#" onclick="viewTopic(${apply.thesisid},event);">${apply.topic}</a></td>
                <td><span class="easyui-tooltip" title="${apply.applyInfo}">${apply.applyInfo}</span></td>
                <td><fmt:formatDate value="${apply.applyTime}"/></td>
                <td><span class="easyui-tooltip" title="${apply.replyInfo}">${apply.replyInfo}</span></td>
                <td><fmt:formatDate value="${apply.replyTime}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="dlg"></div>
<script>
    var d;
    function viewTeacher(teacherid){
        d=$("#dlg").dialog({
            title: '查看教师信息',
            width: 320,
            height: 480,
            href:'${ctx}/console/arch/view?id=' + teacherid,
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

    function onLoadSuccess(){
        $("span.easyui-tooltip").each(function(){
            $(this).tooltip({
                position: 'top',
                content:$(this).text(),
                onShow: function () {
                    $(this).tooltip('tip').css({
                        borderColor: '#000'
                    });
                }
            });
        });
    }

</script>
</body>
</html>
