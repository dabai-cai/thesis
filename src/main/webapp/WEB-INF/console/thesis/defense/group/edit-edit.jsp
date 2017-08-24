<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/22
  Time: 22:51
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
    <h2 class="StepTitle">选择答辩小组的${type eq 'leader'?"答辩组长":"答辩秘书"}</h2>
    <div id="ui-toolbar1">
        <div class="ui-toolbar-search">
                <label>选择职称：</label>
                <select id="title" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">
                    <option value="-1">请选择级别</option>
                    <c:forEach items="${titles}" var="title">
                        <option value="${title.ordinal()}">${title.label}</option>
                    </c:forEach>
                </select>
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doTeacherSearch()">开始检索</a>
        </div>
    </div>
    <table id="dg-edit" class="easyui-datagrid">
        <thead>
        <tr>
            <th data-options="field:'teacherid', checkbox:true" , width="100">ID</th>
            <th data-options="field:'account'" width="230">工号</th>
            <th data-options="field:'userName'" width="230">姓名</th>
            <th data-options="field:'titleName'"  width="230">职称</th>
            <th data-options="field:'titleLevel'" width="220">等级</th>
        </tr>
        </thead>
    </table>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="Form.submitForm()" iconCls="icon-ok">选择</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="Form.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
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
    $(function(){
        $("#dg-edit").datagrid({
            <c:if test="${type eq 'secretary'}">
            url: '${ctx}/console/thesis/defense/group/teacher-list.json?id=${defenseGroup.id}&taskid=${defenseGroup.taskid}'+"&leaderJSON="+$("#leaderJSON").val(),
            </c:if>
            <c:if test="${type eq 'leader'}">
            url: '${ctx}/console/thesis/defense/group/teacher-list.json?id=${defenseGroup.id}&taskid=${defenseGroup.taskid}'+"&secretaryJSON="+$("#secretaryJSON").val(),
            </c:if>
             method: 'get',
            idField: 'teacherid',
            toolbar: '#ui-toolbar1',
            <%--fit:true,--%>
            fitColumns:true,
//            loadFilter: pagerFilter,
            height: '350',
            width: '935',
            pagination:true,
            rownumbers:true,
            pageNumber:1,
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50 ],
            singleSelect:true
        })
    })
    var Form = {
        submitForm: function(){
            var row = $("#dg-edit").datagrid("getSelected");
            if(row==null||row==""){
                $.messager.alert("提示", "请至少选中一位"+name+"！");
                return ;
            }
            <c:if test="${type eq 'secretary'}">
            $("#secretaryid").val(row.teacherid);
            $("#secretaryName").text(row.userName);
            alert(JSON.stringify(row));
            $("#secretaryJSON").val(JSON.stringify(row));
            </c:if>
            <c:if test="${type eq 'leader'}">
            $("#leaderid").val(row.teacherid);
            $("#leaderName").text(row.userName);
            $("#leaderJSON").val(JSON.stringify(row));
            </c:if>
            $("#dlg").dialog("close");
        },
        clearForm: function(){
            $("#dlg").dialog("close");
        }
    };
    function doTeacherSearch() {
        var params = {};
        params.teacher = $("#teacherid").val();
        if ($('#title').combobox("getValue") != "") {
            params.titleLevel = $('#title').combobox("getValue");
        }
        $("#dg-edit").datagrid("load", params);
        return false;
    }
</script>
</body>
</html>
