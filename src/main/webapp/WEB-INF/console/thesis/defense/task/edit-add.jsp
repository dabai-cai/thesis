<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/18
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>${id eq null ?"查看答辩任务":"编辑答辩任务"}</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div style="padding:10px 10px 10px 10px">
    <h2 class="StepTitle">添加参与当前答辩任务的${type eq 'student'?"学生":"教师"}</h2>
    <div id="ui-toolbar1">
        <div class="ui-toolbar-search">
            <c:if test="${type eq 'student'}">
                <select id="major" name="major" ></select>
                <select id="grade" name="grade" style="margin-left: 5px;"></select>
                <select id="clazz" name="clazz" style="margin-left: 5px;"></select>
                <label>学号：</label><input class="wu-text easyui-textbox" id="studentid" style="width:90px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doStudentSearch()">开始检索</a>
            </c:if>
            <c:if test="${type eq 'teacher'}">
                <label>选择职称：</label>
                <select id="title" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">
                    <option value="-1">请选择级别</option>
                    <c:forEach items="${titles}" var="title">
                        <option value="${title.ordinal()}">${title.label}</option>
                    </c:forEach>
                </select>
                <label>工号：</label><input class="wu-text easyui-textbox" id="teacherid" style="width:80px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doTeacherSearch()">开始检索</a>
            </c:if>
        </div>
    </div>
    <table id="dg1" class="easyui-datagrid"
           data-options="url: '${ctx}/console/thesis/defense/task/${type}-list.json?id=${defenseTask.id}',
                            method: 'get',
                            <c:if test="${type eq 'student'}">
                            idField: 'studentid',
                            </c:if>
                            <c:if test="${type eq 'teacher'}">
                            idField: 'teacherid',
                            </c:if>
                            toolbar: '#ui-toolbar1',
                            <%--fit:true,--%>
                            fitColumns:true,
                            height: '350',
                            width: '935',
                            pagination:true,
                            rownumbers:true,
                            pageNumber:1,
                            pageSize : 10,
                            pageList : [ 10, 20, 30, 40, 50 ],
                            singleSelect:false">
        <thead>
        <tr>
            <c:if test="${type eq 'student'}">
                <th data-options="field:'studentid', checkbox:true" , width="50">ID</th>
                <th data-options="field:'stuno'," width="130">学生学号</th>
                <th data-options="field:'stuname'" width="130">学生姓名</th>
                <th data-options="field:'clazz'" width="130">年级班级</th>
                <th data-options="field:'defenseStatus'" width="130">论文答辩类型</th>
            </c:if>
            <c:if test="${type eq 'teacher'}">
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
    var name="学生";
    <c:if test="${type eq 'teacher'}">
    name="教师";
    </c:if>
    var Form = {
        submitForm: function(){
            var rows = $("#dg1").datagrid("getSelections");
            if(rows==null||rows==""){
                $.messager.alert("提示", "请至少选中一位"+name+"！");
                return ;
            }
            progressLoad();
            $.post("${ctx}/console/thesis/defense/task/edit-add-${type}?id=${defenseTask.id}",{"jsondata":JSON.stringify(rows)} , function(data){
                progressClose();
                if(data.status == 200){
                    $.messager.alert("提示", data.msg, "info", function(){
                        $("#dlg").dialog("close");
                        $("#dg-${type}").datagrid("reload");
                        window.top.FlashTab("答辩任务管理");
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
        if($('#major').combobox("getValue") != ""){
            params.major = $('#major').combobox("getValue");
        }
        if($('#grade').combobox("getValue") != ""){
            params.grade = $('#grade').combobox("getValue");
        }
        if($('#clazz').combobox("getValue") != ""){
            params.clazz = $('#clazz').combobox("getValue");
        }
        params.stuno =$("#studentid").val();
        $("#dg1").datagrid("load", params);
        return false;
    }
    function doTeacherSearch() {
        var params = {};
        params.teacher = $("#teacherid").val();
        if ($('#title').combobox("getValue") != "") {
            params.titleLevel = $('#title').combobox("getValue");
        }
        $("#dg1").datagrid("load", params);
        return false;
    }
    $('#major').combobox({
        url:'${ctx}/console/user/listMajor.json?pid=' + ${currentProj.id},
        valueField:'id',
        textField:'text',
        width:150,
        loadFilter: function(data){
            var value = [];
            var empty = {};
            empty.id = "";
            empty.text="请选择专业";
            value.push(empty);
            for (var i = 0; i < data.length; i++) {
                value.push(data[i]);
            }
            return value;
        },
        onSelect: function(record){
            var major = record.id;
            if(major != ""){
                $("#grade").combobox("reload",
                    '${ctx}/console/user/listGradeByMajor.json?pid=' + ${currentProj.id} + '&major=' + major).combobox('clear');
            }else{
                $("#grade").combobox("loadData",{}).combobox('clear');
                $("#clazz").combobox("loadData",{}).combobox('clear');
            }
        }
    });
    $('#grade').combobox({
        valueField:'id',
        textField:'text',
        width:120,
        loadFilter: function(data){
            var value = [];
            var empty = {};
            empty.id = "";
            empty.text="请选择年级";
            value.push(empty);
            for (var i = 0; i < data.length; i++) {
                value.push(data[i]);
            }
            return value;
        },
        onSelect: function(record){
            var grade = record.id;
            if(grade != ""){
                var major = $('#major').combobox("getValue");
                $("#clazz").combobox("reload",
                    '${ctx}/console/user/listClazzByMajorAndGrade.json?pid=' + ${currentProj.id} + '&major=' + major + '&grade=' + grade).combobox('clear');
            }else{
                $("#clazz").combobox("loadData",{}).combobox('clear');
            }
        }
    });
    $('#clazz').combobox({
        valueField:'id',
        textField:'text',
        width:150,
        loadFilter: function(data){
            var value = [];
            var empty = {};
            empty.id = "";
            empty.text="请选择班级";
            value.push(empty);
            for (var i = 0; i < data.length; i++) {
                value.push(data[i]);
            }
            return value;
        },
    });
</script>
</body>
</html>


