<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-09-08
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>参与用户管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
    <div data-options="region:'center',split:true, border:false,title:'参与论文工作教师组'" width="30%">
        <div id="ui-toolbar">
            <div class="ui-toolbar-search">
                <label>关键词：</label><input class="wu-text easyui-textbox" id="keywords" style="width:100px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
            </div>
            <div class="ui-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addTeacher()">添加教师</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="delTeacher()">删除教师</a>
            </div>
        </div>
        <table id="dg" class="easyui-datagrid"
               data-options="url: '${ctx}/console/project/users-teacher.json',
                        method: 'get',
                        idField: 'id',
                        toolbar: '#ui-toolbar',
                        fit:true,
                        fitColumns:true,
                        pagination:true,
                        rownumbers:true,
                        pageNumber:1,
                        pageSize : 20,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        singleSelect:false
                    ">
            <thead>
            <tr>
                <th data-options="field:'id', checkbox:true" width="40">选择</th>
                <th data-options="field:'account'" width="150" align="left">工号</th>
                <th data-options="field:'username'" width="150">姓名</th>
                <th data-options="field:'info.title'" formatter="(function(v,r,i){return formatColumn('info.title',v,r,i);})" width="150">职称</th>
            </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'east',split:true, border:false,title:'参与论文工作学生组'" width="50%">
        <div id="ui-toolbar2">
            <div class="ui-toolbar-search">
                <select id="major" name="major"></select>
                <select id="grade" name="grade" style="margin-left: 5px;"></select>
                <select id="clazz" name="clazz" style="margin-left: 5px;"></select>
                <label>关键词：</label><input class="wu-text easyui-textbox" id="keywords2" style="width:100px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch2()">开始检索</a>
            </div>
            <div class="ui-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addStudent()">添加学生</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="delStudent()">删除学生</a>
            </div>
        </div>
        <table id="dg2" class="easyui-datagrid"
               data-options="url: '${ctx}/console/project/users-student.json',
                        method: 'get',
                        idField: 'id',
                        toolbar: '#ui-toolbar2',
                        fit:true,
                        fitColumns:true,
                        pagination:true,
                        rownumbers:true,
                        pageNumber:1,
                        pageSize : 20,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        singleSelect:false
                    ">
            <thead>
            <tr>
                <th data-options="field:'id', checkbox:true" width="40">选择</th>
                <th data-options="field:'account'" width="150" align="left">学号</th>
                <th data-options="field:'username'" width="150">姓名</th>
                <th data-options="field:'info.major'" formatter="(function(v,r,i){return formatColumn('info.major',v,r,i);})" width="150">专业</th>
                <th data-options="field:'info.clazz'" formatter="(function(v,r,i){return formatColumn('info.clazz',v,r,i);})" width="150">班级</th>
                <th data-options="field:'info.grade'" formatter="(function(v,r,i){return formatColumn('info.grade',v,r,i);})" width="150">年级</th>
            </tr>
            </thead>
        </table>
    </div>
<div id="dlg"></div>
<script>

    function doSearch(){
        $("#dg").datagrid("load",{
            keywords:$("#keywords").val()
        });
    }
    function doSearch2(){
        var params = {};
        params.keywords = $("#keywords2").val();
        if($('#major').combobox("getValue") != ""){
            params.major = $('#major').combobox("getValue");
        }
        if($('#grade').combobox("getValue") != ""){
            params.grade = $('#grade').combobox("getValue");
        }
        if($('#clazz').combobox("getValue") != ""){
            params.clazz = $('#clazz').combobox("getValue");
        }
        $("#dg2").datagrid("load", params);
    }

    $('#major').combobox({
        url:'${ctx}/console/user/listMajor.json',
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
                $("#grade").combobox("reload", '${ctx}/console/user/listGradeByMajor.json?major=' + major).combobox('clear');
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
                $("#clazz").combobox("reload", '${ctx}/console/user/listClazzByMajorAndGrade.json?major=' + major + '&grade=' + grade).combobox('clear');
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

    function addTeacher(){
        d=$("#dlg").dialog({
            title: '选择参与工作教师',
            width: 780,
            height: 600,
            href:'${ctx}/console/project/select-teacher',
            maximizable:true,
            modal:true
        });
    }

    function addStudent(){
        d=$("#dlg").dialog({
            title: '选择参与工作学生',
            width: 880,
            height: 600,
            href:'${ctx}/console/project/select-student',
            maximizable:true,
            modal:true
        });
    }

    function delUser(dgSelector, role, type){
        var ids = getSelectedIds($(dgSelector));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要从当前论文工作中删除的' + role + '！');
            return ;
        }

        $.messager.confirm('确认','确定从当前论文工作中删除ID为 '+ids+' 的' + role + '吗？',function(r){
            if (r){
                var params = {"ids":ids};
                $.post("${ctx}/console/project/delete-" + type, params, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示', data.msg, undefined, function(){
                            $(dgSelector).datagrid("reload");
                        });
                    }else{
                        $.messager.alert('错误', data.msg);
                    }
                });
            }
        });
        return false;
    }

    function delTeacher(){
        delUser("#dg", "教师", "teacher");
    }

    function delStudent(){
        delUser("#dg2", "学生", "student");
    }

</script>
</body>
</html>
