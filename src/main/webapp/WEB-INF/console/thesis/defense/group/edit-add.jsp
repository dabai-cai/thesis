<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/21
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<c:set var="role" value="答辩秘书" />
<c:if test="${type eq 'leader'}">
    <c:set var="role" value="答辩组长" />
</c:if>
<c:if test="${type eq 'teacher'}">
    <c:set var="role" value="教师" />
</c:if>
<c:if test="${type eq 'student'}">
    <c:set var="role" value="学生" />
</c:if>
<head>
    <meta charset="utf-8"/>
    <title>编辑${role}</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>

<div style="padding:10px 10px 10px 10px">
    <h2 class="StepTitle">添加参与当前答辩小组的${role}</h2>
    <div id="ui-toolbar1">
        <div class="ui-toolbar-search">
            <c:if test="${type eq 'student'}">
                <label>学号：</label><input class="wu-text easyui-textbox" id="studentid" style="width:90px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doStudentSearch()">开始检索</a>
            </c:if>
            <c:if test="${type ne 'student'}">
                <label>选择职称：</label>
                <select id="title" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">
                    <option value="">请选择职称</option>
                    <c:forEach items="${titles}" var="title">
                        <option value="${title.ordinal()}">${title.label}</option>
                    </c:forEach>
                </select>
                <label>工号：</label><input class="wu-text easyui-textbox" id="teacherid" style="width:80px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doTeacherSearch()">开始检索</a>
            </c:if>
        </div>
    </div>
    <table id="dg-add" class="easyui-datagrid">
        <thead>
        <tr>
            <c:if test="${type eq 'student'}">
                <th data-options="field:'studentid', checkbox:true" , width="40">ID</th>
                <th data-options="field:'stuno'," width="120">学生学号</th>
                <th data-options="field:'stuname'" width="100">学生姓名</th>
                <th data-options="field:'clazz'" width="100">年级班级</th>
                <th data-options="field:'defenseStatus'" >论文答辩类型</th>
            </c:if>
            <c:if test="${type ne 'student'}">
                <th data-options="field:'teacherid', checkbox:true" , width="100">ID</th>
                <th data-options="field:'account'" width="230">工号</th>
                <th data-options="field:'userName'" width="230">姓名</th>
                <th data-options="field:'titleName'"  width="230">职称</th>
                <th data-options="field:'titleLevel'" width="220">等级</th>
            </c:if>
        </tr>
        </thead>
    </table>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="Form.submitForm()" iconCls="icon-ok">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="Form.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var Form = {
        submitForm: function(){
            var rows = $("#dg-add").datagrid("getSelections");
            if(rows==null||rows==""){
                $.messager.alert("提示", "请至少选中一位"+'${role}'+"！");
                return ;
            }
            progressLoad();
            $.post("${ctx}/console/thesis/defense/group/edit-add-${type}?id=${defenseGroup.id}",{"jsondata":JSON.stringify(rows)} , function(data){
                progressClose();
                if(data.status == 200){
                    $.messager.alert("提示", data.msg, "info", function(){
                        $("#dlg").dialog("close");
                        $("#dg-${type}").datagrid("reload");
                        window.top.FlashTab("答辩小组管理");
                    });
                }else{
                    $.messager.alert("提示", data.msg, "error");
                }
            });
        },
        clearForm: function(){
            $("#dlg").dialog("close");
        }
    };
    function doStudentSearch(){
        var params = {};
        params.stuno =$("#studentid").val();
        $("#dg-add").datagrid("load", params);
        return false;
    }
    function doTeacherSearch() {
        var params = {};
        params.teacher = $("#teacherid").val();
        if ($('#title').combobox("getValue") != "") {
            params.title = $('#title').combobox("getValue");
        }
        $("#dg-add").datagrid("load", params);
        return false;
    }
    $(function(){
        $("#dg-add").datagrid({
            method: 'get',
            <c:if test="${type eq 'student'}">
            url: '${ctx}/console/thesis/defense/group/${type}-list.json?id=${defenseGroup.id}',
            idField: 'studentid',
            </c:if>
            <c:if test="${type ne 'student'}">
            url: '${ctx}/console/thesis/defense/group/${type}-list.json?id=${defenseGroup.id}'+"&secretaryJSON="+$("#secretaryJSON").val()+"&leaderJSON="+$("#leaderJSON").val(),
            idField: 'teacherid',
            </c:if>
            toolbar: '#ui-toolbar1',
            <%--fit:true,--%>
            fitColumns:true,
            height: '350',
            width: '965',
            pagination:true,
            rownumbers:true,
            pageNumber:1,
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50 ],
            singleSelect:false
        })
    })
</script>
</body>
</html>
