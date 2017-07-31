<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-09-09
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/inc/dialog.jsp" %>
<c:set var="role" value="教师" />
<c:if test="${type eq 'teacher'}">
    <c:set var="role" value="教师" />
</c:if>
<c:if test="${type eq 'student'}">
    <c:set var="role" value="学生" />
</c:if>
<div style="padding:10px 10px 10px 10px; height: 100%;">
    <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">选择参与${role}</div>
    <div class="easyui-layout" style="height: 80%;">
        <div data-options="region:'center', fit:true">
            <div id="dlg-ui-toolbar">
                <div class="ui-toolbar-search">
                    <input type="checkbox" id="selected" name="selected" value="0" checked />${type eq 'teacher'?"未参加本次论文工作":"未参与论文工作"}
                    <c:if test="${type eq 'student'}">
                        <select class="major" name="major"></select>
                        <select class="grade" name="grade" style="margin-left: 5px;"></select>
                        <select class="clazz" name="clazz" style="margin-left: 5px;"></select>
                    </c:if>
                    <label>关键词：</label><input class="wu-text easyui-textbox" id="dlg-keywords" style="width:100px">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
                </div>
            </div>
            <table id="dlg-dg" class="easyui-datagrid"
                   data-options="url: '${ctx}/console/project/select-${type}.json',
                        method: 'get',
                        queryParams:{selected: 0},
                        idField: 'id',
                        toolbar: '#dlg-ui-toolbar',
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
                    <c:if test="${type eq 'teacher'}">
                        <th data-options="field:'info.title'" formatter="(function(v,r,i){return formatColumn('info.title',v,r,i);})" width="150">职称</th>
                    </c:if>
                    <c:if test="${type eq 'student'}">
                        <th data-options="field:'info.major'" formatter="(function(v,r,i){return formatColumn('info.major',v,r,i);})" width="150">专业</th>
                        <th data-options="field:'info.clazz'" formatter="(function(v,r,i){return formatColumn('info.clazz',v,r,i);})" width="150">班级</th>
                        <th data-options="field:'info.grade'" formatter="(function(v,r,i){return formatColumn('info.grade',v,r,i);})" width="150">年级</th>
                    </c:if>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <div style="padding:5px;" class="dialog-button">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveSelected()" iconCls="icon-ok">确定选择</a>
        <a href="javascript:void(0)" class="easyui-linkbutton easyui-tooltip" title="点击将选择符合查询条件下的所有${role}" onclick="saveQuery()" iconCls="myicon-app-form-add">选择所有</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancelSelect()" iconCls="icon-cancel">取消选择</a>
    </div>
</div>
<script type="text/javascript">
    var dgSelector = "#${type eq 'teacher'?'dg' : 'dg2'}";
    function getParam(){
        var params = {};
        params.keywords = $("#dlg-keywords").val();
        if($("#selected").is(':checked')){
            params.selected = 0;
        }
        <c:if test="${type eq 'student'}">
        if($('.major').combobox("getValue") != ""){
            params.major = $('.major').combobox("getValue");
        }
        if($('.grade').combobox("getValue") != ""){
            params.grade = $('.grade').combobox("getValue");
        }
        if($('.clazz').combobox("getValue") != ""){
            params.clazz = $('.clazz').combobox("getValue");
        }
        </c:if>
        return params;
    }
    function doSearch(){
        $("#dlg-dg").datagrid("load", getParam());
    }

    $('.major').combobox({
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
                $(".grade").combobox("reload", '${ctx}/console/user/listGradeByMajor.json?major=' + major).combobox('clear');
            }else{
                $(".grade").combobox("loadData",{}).combobox('clear');
                $(".clazz").combobox("loadData",{}).combobox('clear');
            }
        }
    });
    $('.grade').combobox({
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
                var major = $('.major').combobox("getValue");
                $(".clazz").combobox("reload", '${ctx}/console/user/listClazzByMajorAndGrade.json?major=' + major + '&grade=' + grade).combobox('clear');
            }else{
                $(".clazz").combobox("loadData",{}).combobox('clear');
            }
        }
    });
    $('.clazz').combobox({
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

    function saveSelected(){
        var ids = getSelectedIds($("#dlg-dg"));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要添加到论文工作的${role}!');
            return ;
        }

        $.messager.confirm('确认','确定添加ID为 '+ids+' 的${role}到当前论文工作中吗？',function(r){
            if (r){
                var params = {"ids":ids};
                $.post("${ctx}/console/project/save-${type}", params, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示', data.msg, undefined, function(){
                            $(dgSelector).datagrid("reload");
                            $("#dlg").dialog("close");
                        });
                    }else{
                        $.messager.alert('错误', data.msg);
                    }
                });
            }
        });
        return false;
    }

    function saveQuery(){
        var params = getParam();
        $.messager.confirm('确认','确定将符合查询条件的所有${role}添加到当前论文工作中吗？',function(r){
            if (r){
                $.post("${ctx}/console/project/saveQuery-${type}", params, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示', data.msg, undefined, function(){
                            $(dgSelector).datagrid("reload");
                            $("#dlg").dialog("close");
                        });
                    }else{
                        $.messager.alert('错误', data.msg);
                    }
                });
            }
        });
        return false;
    }

    function cancelSelect(){
        $("#dlg").dialog("close");
    }
</script>
