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
    <title>指导老师论文上传</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'north',split:true, border:false" height="60px">
    <div id="p" class="easyui-panel" title="管理提示" style="padding:5px;" iconCls="myicon-information">
        <span class="myicon-tick" style="width: 16px; height: 16px;display: inline-block;margin-right: 4px;">&nbsp;&nbsp;</span>
        ${currentProj.title}，你一共有<strong>&nbsp;${expands.size()}&nbsp;</strong>道论文题目需要上传论文
    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'论文成绩列表', iconCls:'myicon-table-go'">
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
            <th data-options="field:'id', checkbox:true">论文ID</th>
            <th data-options="field:'topic'" width="200">论文题目</th>
            <th data-options="field:'stuname'" width="80">选题学生</th>
            <th data-options="field:'stuno'" width="60" hidden="true">学生学号</th>
            <th data-options="field:'viewerid'" width="60" hidden="true">评阅教师ID</th>
            <th data-options="field:'viewer'" width="100">评阅教师</th>
            <th data-options="field:'scoreid'" width="60" hidden="true">成绩ID</th>
            <th data-options="field:'mark'" width="80">最近上传时间</th>
            <th data-options="field:'action'"  align="left" width="200">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${expands}" var="thesisEx">
            <tr>
                <td>${thesisEx.id}</td>
                <td><a href="#" onclick="viewTopic(${thesisEx.id}, event);">${thesisEx.topic}</a></td>
                <td><a href="#" onclick="viewStudent(${thesisEx.studentid});">${thesisEx.stuname}</a></td>
                <td>${thesisEx.stuno}</td>
                <td>${thesisEx.viewerid}</td>
                <td>${thesisEx.viewer}</td>
                <td>${thesisEx.scoreid}</td>
                <td><fmt:formatDate value="${thesisEx.lastuptime}"/></td>
                <td>
                        <a name="download" href="/console/tupload/download?thesisid=${thesisEx.id}" >下载论文</a>
                        <a name="edit" href="#" onclick="edit('${thesisEx.uploadid}')">上传论文</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<input type="hidden" id="teacherid" value="${currentUser.id}" />
<div id="dlg"></div>
<script>
    function onLoadSuccess(data){

        $("a[name='edit']").linkbutton({text:'上传论文',plain:true, iconCls:'icon-add', width:100});
        $("a[name='download']").linkbutton({text:'下载论文',plain:true, iconCls:'icon-download', width:100});
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

    function edit(uploadid){
        d=$("#dlg").dialog({
            title: '上传论文',
            width: 860,
            height: 500,
            href:'${ctx}/console/tupload/upload?id='+uploadid,
            maximizable:true,
            modal:true
        });
    }
    var dlg_dlg =$('#dlg-dlg').dialog({
        modal : true,
        title : '选择评阅教师',
        width :780,
        height:600,
        closed:true,
        cache: false,
        onOpen:openDlg,
        onClose:closeDlg
    });
    var dg2 = null;


</script>
</body>
</html>
