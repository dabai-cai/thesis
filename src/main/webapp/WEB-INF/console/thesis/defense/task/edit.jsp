<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/18
  Time: 10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>编辑答辩任务</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'west',split:true, border:false,title:'答辩任务基本信息'" width="30%">
    <div style="padding:10px 10px 10px 10px">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">
            答辩任务信息
        </div>
        <input type="hidden" id="form-defensename" name="name"/>
        <input type="hidden" id="form-president" name="president"/>
        <input type="hidden" id="form-contact" name="contact"/>
        <input type="hidden" id="form-nums" name=" nums"/>
        <input type="hidden" id="form-remark" name="remark"/>
        <input type="hidden" id="form-defensetime" name="defensetime"/>
        <input type="hidden" id="form-students" name="students"/>
        <input type="hidden" id="form-teachers" name="teachers"/>
        <table width="100%" cellpadding="5" class="form-table">
            <tbody>
            <form id="form1">
            <input type="hidden" name="id" value="${defenseTask.id}" />
            <tr id="newTopic">
                <td width="120"><label>答辩任务名称</label></td>
                <td>
                    <input type="text" name="name" value="${defenseTask.name}"/>
                </td>
            </tr>
            <tr>
                <td><label>答辩主席</label></td>
                <td>
                    <input type="text" name="president" value="${defenseTask.president}"/>
                </td>
            </tr>
            <tr>
                <td><label>联系人</label></td>
                <td>
                    <input type="text" name="contact" value="${defenseTask.contact}"/>
                </td>
            </tr>
            <tr>
                <td><label>答辩时间</label></td>
                <td>
                    <input id="defensetime" class="easyui-datebox"  data-options="editable:false">

                </td>
            </tr>
            <tr>
                <td><label>答辩小组组数</label></td>
                <td>
                    <input type="text" name="nums" value="${defenseTask.nums}">
                </td>
            </tr>
            <tr>
                <td><label>备注</label></td>
                <td>
                    <textarea name="remark" id="remark" cols="30" rows="10">${defenseTask.remark}</textarea>
                </td>
            </tr>
            <tr>
                <td><label>操作</label></td>
                <td align="center">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存修改</a>
                </td>
            </tr>
            </form>
            </tbody>
        </table>
    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'参加学生'" width="37%">
    <div id="ui-toolbar-student">
        <div class="ui-toolbar-button">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add('student')">添加学生</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del('student')">删除学生</a>
        </div>
    </div>
    <table id="dg-student" class="easyui-datagrid"
           data-options="url: '${ctx}/console/thesis/defense/task/student-showlist.json?id=${defenseTask.id}',
                        method: 'get',
                        idField: 'studentid',
                        toolbar: '#ui-toolbar-student',
                        <%--fit:true,--%>
                        height:'100%',
                        loadFilter: pagerFilter ,
                        fitColumns:true,
                        rownumbers:true,
                        pagination:true,
                        pageNumber:1,
                        pageSize : 10,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        singleSelect:false
                    ">
        <thead>
        <tr>
            <th data-options="field:'studentid', checkbox:true" , width="40">ID</th>
            <th data-options="field:'stuno'," width="120">学生学号</th>
            <th data-options="field:'stuname'" width="100">学生姓名</th>
            <th data-options="field:'clazz'" width="100">年级班级</th>
            <th data-options="field:'defenseStatus'" >论文答辩类型</th>
        </tr>
        </thead>
    </table>
</div>
<div data-options="region:'east',split:true, border:false,title:'参加教师'" width="33%">
    <div id="ui-toolbar-teacher">
        <div class="ui-toolbar-button">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add('teacher')">添加教师</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del('teacher')">删除教师</a>
        </div>
    </div>
    <table id="dg-teacher" class="easyui-datagrid"
           data-options="url: '${ctx}/console/thesis/defense/task/teacher-showlist.json?id=${defenseTask.id}',
                        method: 'get',
                        idField: 'teacherid',
                        toolbar: '#ui-toolbar-teacher',
                        <%--fit:true,--%>
                        height:'100%',
                        fitColumns:true,
                        rownumbers:true,
                        singleSelect:false
                    ">
        <thead>
            <tr>
                <th data-options="field:'teacherid', checkbox:true" , width="40">ID</th>
                <th data-options="field:'account'" width="100">工号</th>
                <th data-options="field:'userName'" width="100">姓名</th>
                <th data-options="field:'titleName'" width="100">职称</th>
                <th data-options="field:'titleLevel'" width="100">等级</th>
            </tr>
        </thead>
    </table>
</div>
<div id="dlg">

</div>
<script>
    // 分页数据的操作
    function pagerFilter(data) {
        if (typeof data.length == 'number' && typeof data.splice == 'function') {   // is array
            data = {
                total: data.length,
                rows: data
            }
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
            onSelectPage: function (pageNum, pageSize) {
                opts.pageNumber = pageNum;
                opts.pageSize = pageSize;
                pager.pagination('refresh', {
                    pageNumber: pageNum,
                    pageSize: pageSize
                });
                dg.datagrid('loadData', data);
            }
        });
        if (!data.originalRows) {
            data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
    }
    function save(){
        alert($("#form1").serialize());
        $.post("${ctx}/console/thesis/defense/task/edit",$("#form1").serialize(),function(data){
            if(data.status==200){
                $.messager.alert('提示', data.msg, undefined, function(){
                    window.top.FlashTab("答辩任务管理");
                });
            }else{
                $.messager.alert('错误', data.msg);
            }
        })
    }
    function add(op){
        d=$("#dlg").dialog({
            title: '添加' + getLabel(op),
            width: 965,
            height: 500,
            href:'${ctx}/console/thesis/defense/task/edit-add-'+op+"?id=${defenseTask.id}",
            maximizable:true,
            modal:true
        });
    }

    function edit(op){
        var id = getSelectedId($("#dg-" + op));
        if(id == undefined || id == "" || id == null){
            $.messager.alert('提示','必须选择一个' + getLabel(op) + '才能编辑!');
            return ;
        }
        d=$("#dlg").dialog({
            title: '编辑' + getLabel(op),
            width: 400,
            height: 220,
            href:'${ctx}/console/attr/edit-' + op + '?id=' + id,
            maximizable:true,
            modal:true
        });
    }

    function del(op){
        var rows=$("#dg-" + op).datagrid("getSelections");
        //var id = getSelectedIds($("#dg-" + op));
        var jsondata =JSON.stringify(rows);
        if(rows.length == 0){
            $.messager.alert('提示','未选中要删除的' + getLabel(op) + '！');
            return ;
        }
        $.messager.confirm('确认','确定要删除选中的' + getLabel(op) + '吗？',function(r){
            if (r){
                var params = {"jsondata":jsondata,"id":${defenseTask.id}};
                $.post("${ctx}/console/thesis/defense/task/delete-" +op, params, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示', data.msg, undefined, function(){
                            $("#dg-" + op).datagrid("reload");
                            window.top.FlashTab("答辩任务管理");
                        });
                    }else{
                        $.messager.alert('错误', data.msg);
                    }
                });
            }
        });
        return false;
    }

    function getLabel(op){
        switch (op) {
            case "student":
                return "学生";
            case "teacher":
                return "教师";
            default:
                return "";
        }
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
    $(function(){
        $("#defensetime").datebox('setValue', '${defenseTask.defensetime}');
    });

</script>
</body>
</html>
