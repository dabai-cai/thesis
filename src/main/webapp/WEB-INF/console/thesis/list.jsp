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
    <title>题目申报管理</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'north',split:true, border:false" height="60px">
    <div id="p" class="easyui-panel" title="申报提示" style="padding:5px;" iconCls="myicon-information">
        <span class="myicon-tick" style="width: 16px; height: 16px;display: inline-block;margin-right: 4px;">&nbsp;&nbsp;</span>
        当前论文工作允许教师最大出题数量为<strong>&nbsp;${currentProj.setmax}&nbsp;</strong>题，你已申报<strong>&nbsp;<span id="total">0</span>&nbsp;</strong>题
    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'论文题目申报', iconCls:'myicon-table-go'">
    <div id="ui-toolbar">
        <div class="ui-toolbar-search">
            <label>关键词：</label><input class="wu-text easyui-textbox" id="keywords" style="width:200px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
        </div>
        <div class="ui-toolbar-button">
            <c:if test="${currentProj.setallowed}">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()" id="addThesis">申报题目</a>
            </c:if>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit()">修改题目</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="del()">删除题目</a>
        </div>
    </div>
    <table id="dg" class="easyui-datagrid"
           data-options="url: '${ctx}/console/thesis/list.json',
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
                        singleSelect:true,
                        onLoadSuccess: onLoadSuccess">
        <thead>
        <tr>
            <th data-options="field:'id'" width="20" hidden="true">ID</th>
            <th data-options="field:'topic', formatter:formatTopic" width="300">论文题目</th>
            <th data-options="field:'direction'" width="100">研究方向</th>
            <th data-options="field:'source'" width="80">论文来源</th>
            <th data-options="field:'property'" width="80">论文性质</th>
            <th data-options="field:'check', formatter:formatState" width="80">审核状态</th>
            <th data-options="field:'studentid'" hidden="true" width="80">学生ID</th>
            <th data-options="field:'student', formatter:formatStudent" width="250">选题学生</th>
            <th data-options="field:'uploadtime',formatter:formatShortDate" width="100">论文上传日期</th>
            <th data-options="field:'cdate',formatter:formatDate" width="130">出题日期</th>
        </tr>
        </thead>
    </table>
</div>

<div id="dlg"></div>
<script>

    var max = "${currentProj.setmax}";

    function doSearch(){
        $("#dg").datagrid("load",{
            keywords:$("#keywords").val()
        });
        return false;
    }


    function onLoadSuccess(data){
        var total = data.total;
        $("#total").text(total);
        if(total >= max){
            $("#addThesis").attr("disabled",true);
        }
        $("a[name='cancel']").linkbutton({text:'取消学生',iconCls:'icon-cancel', width:80});
        $("#dg").datagrid("resize");
    }

    function formatTopic(val, row){
        return '<a href="#" onclick="viewTopic('+ row.id +',event);">' + val + '</a>';
    }

    function formatState(val, row){
        if(val || val == "true"){
            return "<span style='background-color: #00ff32;padding: 4px;'>已审核</span>";
        }else{
            return "<span style='background-color: #ff032c; padding: 4px;'>未审核</span>";
        }
    }

    function formatStudent(val, row){
        if(val){
            var str =  "<a href='#' style='display: inline-block; width: 60%;' onclick='viewStudent(" + row.studentid + ",event)'>" + val + "</a>";
            str = str + "<a href='javascript:void(0)' name='cancel' class='easyui-linkbutton' onclick='cancelStudent(" + row.id + ", event)' iconCls='icon-cancel'>取消学生</a>";
            return str;
        }
        return "";
    }

    var d;
    function viewStudent(studentid,event){
        d=$("#dlg").dialog({
            title: '查看学生信息',
            width: 320,
            height: 480,
            href:'${ctx}/console/arch/view?id=' + studentid,
            maximizable:true,
            modal:true
        });
        event.stopPropagation();
        return false;
    }

    function viewTopic(id,event){
        window.top.addTab("论文题目详情", '${ctx}/console/thesis/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }

    function add(){
        var total = Number($("#total").text());
        if(total >= max){
            $.messager.alert("提示","您的论文题目申报数量已达到最大限制！");
            return false;
        }
        window.top.addTab("申报论文题目",'${ctx}/console/thesis/add', null, true);
    }

    function edit(){
        var id = getSelectedId($("#dg"));
        if(id == undefined || id == "" || id == null){
            $.messager.alert('提示','必须选择一个题目才能编辑!');
            return ;
        }
        window.top.addTab("编辑论文题目",'${ctx}/console/thesis/edit?id=' + id, null, true);
    }

    function del(){
        var ids = getSelectedIds($("#dg"));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要删除的论文题目!');
            return ;
        }
        $.messager.confirm('确认','确定删除ID为 '+ids+' 的论文题目吗？',function(r){
            if (r){
                var params = {"ids":ids};
                $.post("${ctx}/console/thesis/delete", params, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示', data.msg, undefined, function(){
                            $("#dg").datagrid("reload");
                        });
                    }else{
                        $.messager.alert('错误', data.msg);
                    }
                });
            }
        });
    }

    function cancelStudent(id, event){
        var index = $("#dg").datagrid("getRowIndex", id);
        var row = $('#dg').datagrid('getData').rows[index];
        $.messager.confirm('确认','<br><br><br>确定将题目[' + row.topic + ']的学生'+row.student+'取消吗？<br>警告：一旦取消，下发的任务书和填写的期中检查将被删除',function(r){
            if (r){
                var params = {"thesisid":id};
                $.post("${ctx}/console/thesis/unsign", params, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示', data.msg, undefined, function(){
                            $("#dg").datagrid("reload");
                        });
                    }else{
                        $.messager.alert('错误', data.msg);
                    }
                });
            }
        });
        event.stopPropagation();
        return false;
    }

    window.top["reload_thesis_list"]=function(){
        $("#dg").datagrid( "reload");
    };
</script>
</body>
</html>
