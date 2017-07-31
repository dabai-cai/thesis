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
    <title>论文选题结果</title>
    <%@include file="/inc/header.jsp" %>
    <script src="${ctx}/resources/echarts/echarts.js"></script>
    <style type="text/css">
        .form-table tbody tr td:nth-child(odd){
            background-color: #f5f5f5;
        }
    </style>
</head>
<body class="easyui-layout">
<div data-options="region:'west',split:true, border:false,title:'论文选题结果概览'" width="24%">
    <table class="form-table" width="90%" style="margin: 15px;">
        <tbody>
            <tr>
                <td>论文工作年度</td>
                <td>${currentProj.year}</td>
            </tr>
            <tr>
                <td>论文工作名称</td>
                <td>${currentProj.title}</td>
            </tr>
            <tr>
                <td>参与学生人数</td>
                <td>${studentCnt}</td>
            </tr>
            <tr>
                <td>选题学生人数</td>
                <td>${resultCnt}</td>
            </tr>
            <tr>
                <td>未选学生人数</td>
                <td>${lostCnt}</td>
            </tr>
            <tr>
                <td>论文题目总数</td>
                <td>${thesisCnt}</td>
            </tr>
            <tr>
                <td>未选题目总数</td>
                <td>${unselectCnt}</td>
            </tr>
        </tbody>
    </table>
</div>
<div data-options="region:'center',split:true, border:false, title:'论文选题结果详情'" width="75%">
    <div id="ui-toolbar">
        <div class="ui-toolbar-search">
            <select id="selected" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 100px;">
                <option value="">选题状况</option>
                <option value="true">已选题</option>
                <option value="false">未选题</option>
            </select>
            <select id="major" name="major"></select>
            <select id="grade" name="grade" style="margin-left: 5px;"></select>
            <select id="clazz" name="clazz" style="margin-left: 5px;"></select>
            <br>
            <label>学生学号：</label><input class="wu-text easyui-textbox" id="stuno" style="width:100px">
            <label>学生姓名：</label><input class="wu-text easyui-textbox" id="stuname" style="width:120px">
            <label>指导教师：</label><input class="wu-text easyui-textbox" id="teacher" style="width:100px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
        </div>
        <div class="ui-toolbar-button">
            <a href="#" class="easyui-linkbutton" iconCls="myicon-page-white-excel" onclick="exportQuery()">导出查询结果</a>
            <a href="${ctx}/console/tresult/exportAll" target="_blank" class="easyui-linkbutton" iconCls="myicon-page-excel">导出全部结果</a>
        </div>
    </div>
    <table id="dg" class="easyui-datagrid"
           data-options="url: '${ctx}/console/tresult/list.json',
                        method: 'get',
                        <%--idField: 'id',--%>
                        toolbar: '#ui-toolbar',
                        fit:true,
                        fitColumns:true,
                        pagination:true,
                        rownumbers:true,
                        pageNumber:1,
                        pageSize : 20,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        singleSelect:true">
        <thead>
        <tr>
            <th data-options="field:'stuno'" width="100">学生学号</th>
            <th data-options="field:'stuname', formatter:formatStudent" width="80">学生姓名</th>
            <th data-options="field:'clazz', formatter:formatClazz" width="150">年级班级</th>
            <th data-options="field:'topic', formatter:formatTopic" width="250">论文题目</th>
            <th data-options="field:'direction'" width="80">研究方向</th>
            <th data-options="field:'teacher', formatter:formatTeacher" width="80">指导教师</th>
            <th data-options="field:'account'" width="100">教师工号</th>
            <th data-options="field:'title'" width="60">教师职称</th>
        </tr>
        </thead>
    </table>
    <div id="dlg"></div>
</div>

<script>
    function doSearch(){
        var params = {};
        params.selected = $("#selected").combobox("getValue");
        if($('#major').combobox("getValue") != ""){
            params.major = $('#major').combobox("getValue");
        }
        if($('#grade').combobox("getValue") != ""){
            params.grade = $('#grade').combobox("getValue");
        }
        if($('#clazz').combobox("getValue") != ""){
            params.clazz = $('#clazz').combobox("getValue");
        }
        params.stuno = $("#stuno").val();
        params.stuname = $("#stuname").val();
        params.teacher = $("#teacher").val();
        $("#dg").datagrid("load", params);
        return false;
    }

    function formatStudent(val, row){
        return "<a href='#' onclick='viewStudent(" + row.studentid + ")'>" + val + "</a>";
    }

    function formatClazz(val, row){
        return row.grade + "级" + row.clazz;
    }

    function formatTeacher(val, row){
        if(row.teacherid > 0){
            return "<a href='#' onclick='viewTeacher(" + row.teacherid + ")'>" + val + "</a>";
        }else{
            return "";
        }

    }

    function formatTopic(val, row){
        if(row.thesisid > 0){
            return '<a href="#" onclick="viewTopic('+ row.thesisid +', event);">'+ val + '</a>';
        }else{
            return "";
        }
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

    function viewTeacher(teacherid){
        d=$("#dlg").dialog({
            title: '查看教师信息',
            width: 320,
            height: 400,
            href:'${ctx}/console/arch/view?id=' + teacherid,
            maximizable:true,
            modal:true
        });
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

    function exportQuery(){
        var params = [];
        params.push("selected=" + $("#selected").combobox("getValue"));

        if($('#major').combobox("getValue") != ""){
            params.push("major=" + $("#major").combobox("getValue"));
        }
        if($('#grade').combobox("getValue") != ""){
            params.push("grade=" + $("#grade").combobox("getValue"));
        }
        if($('#clazz').combobox("getValue") != ""){
            params.push("clazz=" + $("#clazz").combobox("getValue"));
        }
        params.push("stuno=" + $("#stuno").val());
        params.push("stuname=" + $("#stuname").val());
        params.push("teacher=" + $("#teacher").val());
        window.open("${ctx}/console/tresult/exportQuery?" + params.join("&"));
        return false;
    }
</script>
</body>
</html>
