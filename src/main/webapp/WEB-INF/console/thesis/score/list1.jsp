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
    <title>指导教师自评</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'north',split:true, border:false" height="60px">
    <div id="p" class="easyui-panel" title="管理提示" style="padding:5px;" iconCls="myicon-information">
        <span class="myicon-tick" style="width: 16px; height: 16px;display: inline-block;margin-right: 4px;">&nbsp;&nbsp;</span>
        ${currentProj.title}，你一共有<strong>&nbsp;${expands.size()}&nbsp;</strong>道论文题目需要评定成绩
    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'论文成绩列表', iconCls:'myicon-table-go'">
    <div id="ui-toolbar">
        <div class="ui-toolbar-button">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="assignViewer()" id="addThesis">指定评阅教师</a>
        </div>
    </div>
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
            <th data-options="field:'mark'" width="60">指导教师自评分</th>
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
                    <td>${thesisEx.mark}</td>
                    <td>
                        <c:if test="${currentProj.mark1allowed}">
                            <a name="edit" href="#" onclick="edit('${thesisEx.id}', '${thesisEx.scoreid}')">编辑自评成绩</a>
                        </c:if>
                        <c:if test="${thesisEx.scoreid gt 0}">
                            &nbsp;&nbsp;|&nbsp;&nbsp;<a name="view" href="${ctx}/console/tscore/view?id=${thesisEx.scoreid}" target="_blank">查看成绩单</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<input type="hidden" id="teacherid" value="${currentUser.id}" />
<div id="dlg"></div>
<div id="dlg-dlg" style="display: none;">
    <div id="ui-toolbar2">
        <div class="ui-toolbar-search">
            <div class="ui-toolbar-search">
                <label>关键词：</label><input class="wu-text easyui-textbox" id="keywords" style="width:100px">
                <a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
            </div>
        </div>
    </div>
    <table id="dg2">
    </table>
    <div style="padding:5px;" class="dialog-button">
        <a href="javascript:void(0)" id="ok" class="easyui-linkbutton" onclick="saveSelected()" iconCls="icon-ok">确定选择</a>
        <a href="javascript:void(0)" id="cancel" class="easyui-linkbutton" onclick="cancelSelect()" iconCls="icon-cancel">取消选择</a>
    </div>
</div>
<script>
    function onLoadSuccess(data){
        <c:if test="${currentProj.mark1allowed}">
            $("a[name='edit']").linkbutton({text:'编辑自评成绩',plain:true, iconCls:'icon-edit', width:100});
        </c:if>
        $("a[name='view']").linkbutton({text:'查看成绩单',plain:true, iconCls:'icon-search', width:100});
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

    function edit(thesisid, scoreid){
        d=$("#dlg").dialog({
            title: '编辑自评成绩',
            width: 860,
            height: 500,
            href:'${ctx}/console/tscore/edit-mark1?id=' + scoreid + '&thesisid=' + thesisid,
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

    function assignViewer(){
        var selected = getSelectedIds($("#dg"));
        if(selected.length == 0){
            $.messager.alert('提示','未选择要指定评阅教师的论文题目!');
            return ;
        }
        var sels = $("#dg").datagrid("getSelections");
        var topics = [];
        for(var i in sels){
            if(sels[i].viewerid > 0){
                topics.push(sels[i].topic);
            }
        }
        var msg = "确定要为选择的论文题目指定评阅教师？";
        if(topics.length > 0){
            var list = topics.join("<br>");
            msg = msg +  "以下题目的评阅教师将被重新指定：<br><br>" + list;
        }
        $.messager.confirm('提示', msg, function(r) {
            if (r) {
                dlg_dlg.dialog("open");
            }
        });
        return false;
    }

    function openDlg(){
        $("#search").linkbutton({text:'开始检索',iconCls:'icon-search', width:100});
        $("#ok").linkbutton({text:'确定选择',iconCls:'icon-ok', width:100});
        $("#cancel").linkbutton({text:'取消选择',iconCls:'icon-cancel', width:100});
        $("#keywords").textbox();
        $("#keywords").textbox("setValue","");
        if(dg2 == null){
            $("#dg2").datagrid({
                url: '${ctx}/console/project/users-teacher.json',
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
                singleSelect:true,
                striped:true,
                columns:[[
                    {field:'id',title:'选择', checkbox: true, width:40},
                    {field:'account',title:'工号',width:150},
                    {field:'username',title:'姓名',width:150},
                    {field:'info.title',title:'职称',width:150, formatter:function(v,r,i){return formatColumn('info.title',v,r,i);}}
                ]],
                dataPlain: true
            });
        }else{
            $("#dg2").datagrid("clearSelections");
            $("#dg2").datagrid("load", {});
        }
    }

    function closeDlg(){
        $("#dg2").datagrid('loadData', { total: 0, rows: [] });
    }

    function doSearch(){
        $("#dg2").datagrid("load",{
            keywords:$("#keywords").val()
        });
    }

    function saveSelected(){
        var sel = $("#dg2").datagrid("getSelected");
        if(sel){
            var viewerid = sel.id;
            if(viewerid == $("#teacherid").val()){
                $.messager.alert("错误", "不能选择自己本人作为评阅教师！");
                return ;
            }
            var ids = getSelectedIds($("#dg"));
            $.post("${ctx}/console/tscore/assign", {"viewerid": viewerid, "ids":ids}, function(data){
                if(data.status == 200){
                    $("#dlg-dlg").dialog('close');
                    location.reload();
                }else{
                    $.messager.alert("提示", data.msg, "error");
                }
            });
        }else{
            $.messager.alert("提示", "请先选择一个教师！");
            return ;
        }

    }

    function cancelSelect(){
        $("#dlg-dlg").dialog("close");
    }
</script>
</body>
</html>
