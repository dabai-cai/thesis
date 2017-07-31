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

<div data-options="region:'north',split:true, border:false" height="60px">
    <div id="p" class="easyui-panel" title="选题提示" style="padding:5px;">
        当前论文工作允许学生最大选题数量为${currentProj.getmax}题，你已选择${applies.size()}题
    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'论文题目选择'" width="67%">
    <div id="ui-toolbar">
        <div class="ui-toolbar-search">
            <label>选择指导教师：</label>
            <select id="teacherid" class="easyui-combogrid" name="teacherid" style="width:100px" data-options="
                                panelWidth: 400,
                                panelHeight:300,
                                singleSelect: true,
                                pagination:true,
                                rownumbers:true,
                                pageNumber:1,
                                pageSize : 20,
                                pageList : [ 10, 20, 30, 40, 50 ],
                                idField: 'id',
                                textField: 'username',
                                url: '${ctx}/console/project/users-teacher.json',
                                method: 'get',
                                columns: [[
                                    {field:'id',title:'教师ID',checkbox:true,width:40},
                                    {field:'account',title:'工号',width:100},
                                    {field:'username',title:'姓名',width:100},
                                    {field:'info.title',title:'职称', formatter:formatTitle, width:50}
                                ]],
                                fitColumns: true
                            ">
            </select>
            <label>关键词：</label><input class="wu-text easyui-textbox" id="keywords" style="width:200px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
        </div>
    </div>
    <table id="dg" class="easyui-datagrid"
           data-options="url: '${ctx}/console/tapply/list.json',
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
                        onLoadSuccess:onLoadSuccess,
                        singleSelect:true">
        <thead>
        <tr>
            <th data-options="field:'teacher', formatter:formatTeacher" width="80">指导教师</th>
            <th data-options="field:'topic', formatter:formatTopic" width="200">论文题目</th>
            <th data-options="field:'direction'" width="100">研究方向</th>
            <th data-options="field:'source'" width="80">题目来源</th>
            <th data-options="field:'property'" width="80">题目性质</th>
            <th data-options="field:'id', formatter:formatAction" align="center" width="80">操作</th>
        </tr>
        </thead>
    </table>
</div>
<div data-options="region:'east',split:true, border:false, title:'本人选题列表'" width="33%">
    <div id="ui-toolbar2">
        <div class="ui-toolbar-button">
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit()">修改申请</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del()">删除申请</a>
        </div>
    </div>
    <table id="dg2" class="easyui-datagrid"
           data-options="idField: 'id',
                        fit:true,
                        fitColumns:true,
                        singleSelect:true,
                        toolbar:'#ui-toolbar2',
                        rownumbers:true">
        <thead>
        <tr>
            <th field="id" width="50" hidden="true">申请ID</th>
            <th field="teacher" width="100">指导教师</th>
            <th field="thesisid" width="50" hidden="true">题目ID</th>
            <th field="topic" width="200">论文题目</th>
            <th field="applyTime" width="100">申请日期</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${applies}" var="apply">
            <tr>
                <td>${apply.id}</td>
                <td><a href="#" onclick="viewTeacher(${apply.teacherid});">${apply.teacher}</a></td>
                <td>${apply.thesisid}</td>
                <td><a href="#" onclick="viewTopic(${apply.thesisid});">${apply.topic}</a></td>
                <td><fmt:formatDate value="${apply.applyTime}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="dlg"></div>
<script>
    function formatTitle(v, r, i){
        return formatColumn('info.title',v,r,i);
    }

    function formatTeacher(val, row){
        return "<a href='#' onclick='viewTeacher(" + row.teacherid + ")'>" + val + "</a>";
    }

    function formatTopic(val, row){
        return '<a href="#" onclick="viewTopic('+ row.id +', event);">'+ val + '</a>';
    }

    function formatAction(val, row){
        <c:if test="${currentProj.getallowed}">
            return '<a href="javascript:void(0)" name="apply" class="easyui-linkbutton" onclick="apply(' + val + ')" iconCls="icon-ok">申请</a>';
        </c:if>
    }

    function doSearch(){
        $("#dg").datagrid("load",{
            teacherid: $("#teacherid").combogrid("getValue"),
            keywords:$("#keywords").val()
        });
        return false;
    }

    function onLoadSuccess(data){
        $("a[name='apply']").linkbutton({text:'申请',plain:true,iconCls:'icon-ok', width:80});
    }
    var d;
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

    function viewTopic(id, event){
        window.top.addTab("论文题目详情", '${ctx}/console/thesis/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }

    function apply(id){
        var selected = $("#dg2").datagrid("getData").rows;
        for(var i in selected){
            if(id == selected[i].thesisid){
                $.messager.alert("警告","你已申请题目[" + selected[i].topic + "]，不能重复申请！");
                return false;
           }
        }
        d=$("#dlg").dialog({
            title: '申请题目',
            width: 760,
            height: 400,
            href:'${ctx}/console/tapply/edit?thesisid=' + id,
            maximizable:true,
            modal:true
        });
        return false;
    }

    function edit(){
        var row = $("#dg2").datagrid("getSelected");
        var id = row.id;
        if(id == undefined || id == "" || id == null){
            $.messager.alert('提示','必须选择一个申请才能编辑!');
            return ;
        }
        d=$("#dlg").dialog({
            title: '修改申请',
            width: 760,
            height: 400,
            href:'${ctx}/console/tapply/edit?id=' + id + '&thesisid=' + row.thesisid,
            maximizable:true,
            modal:true
        });
    }

    function del(){
        var ids = getSelectedIds($("#dg2"));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要删除的论文题目申请!');
            return ;
        }
        $.messager.confirm('确认','确定删除申请选择的论文题目吗？',function(r){
            if (r){
                var params = {"ids":ids};
                $.post("${ctx}/console/tapply/delete", params, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示', data.msg, undefined, function(){
                            location.reload();
                        });
                    }else{
                        $.messager.alert('错误', data.msg);
                    }
                });
            }
        });
    }
</script>
</body>
</html>
