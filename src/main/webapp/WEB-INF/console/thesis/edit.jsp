<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-08-12
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
    <title>编辑论文题目</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div style="padding:10px 10px 10px 10px">
    <form id="thesisForm" method="post" action="${ctx}/console/thesis/edit?id=${thesis.id}">
        <%--<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">论文题目信息</div>--%>
        <input type="hidden" name="id" value="${thesis.id}"/>
        <c:if test="${thesis.id eq null}">
            <input type="hidden" name="checked" value="false"/>
        </c:if>
        <table width="100%" cellpadding="5" class="form-table">
            <tbody>
            <tr>
                <td width="120"><label>论文标题</label></td>
                <td colspan="3"><input type="text" class="easyui-textbox" id="topic" name="topic" value="${thesis.topic}" data-options="required:true, width:450" /></td>
            </tr>
            <tr>
                <td><label>研究方向</label></td>
                <td>
                    <select id="direction" name="direction" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;" required="true">
                        <c:forEach items="${directions}" var="dir">
                            <option value="${dir.direction}" ${dir.direction eq thesis.direction ? "selected" : ""}>${dir.direction}</option>
                        </c:forEach>
                    </select>
                </td>
                <td width="80"><label>论文来源</label></td>
                <td>
                    <select id="source" name="source" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 120px;" required="true">
                        <c:forEach items="${sources}" var="source">
                            <option value="${source.source}" ${source.source eq thesis.source ? "selected" : ""}>${source.source}</option>
                        </c:forEach>
                    </select>
                </td>
                <td width="80"><label>论文性质</label></td>
                <td>
                    <select id="property" name="property" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 120px;" required="true">
                        <c:forEach items="${properties}" var="prop">
                            <option value="${prop.property}" ${prop.property eq thesis.property ? "selected" : ""}>${prop.property}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label>指定学生</label></td>
                <td>
                    <input id="account" class="easyui-textbox"  value="${student.account}" data-options="
                            prompt: '选择学生!',
                            iconWidth: 22,
                            icons: [{
                                iconCls:'icon-search',
                                handler: searchStudent
                            }]
                        ">
                    <span>手工输入后请按回车[Enter]</span>
                </td>
                <td colspan="4">
                    <p style="width: 100%; text-align: left;">
                        <label id="student">${student.username}</label>
                        <input type="hidden" name="studentid" value="${thesis.studentid}">
                    </p>

                </td>
            </tr>
            <tr>
                <td><label>论文上传日期</label></td>
                <td>
                    <input type="text" id="uploadtime" name="uploadtime" class="easyui-datebox" value="<fmt:formatDate value="${thesis.uploadtime}" pattern="yyyy-MM-dd" />" data-options="required:true" />
                </td>
                <td colspan="4">
                    <p style="width: 100%; text-align: left;"><label>日期格式：yyyy-MM-dd，如：2011-05-20</label></p>
                </td>
            </tr>
            <tr>
                <td><label>题目简介</label></td>
                <td colspan="5">
                    <textarea id="profile" name="profile">${thesis.profile}</textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="thesisForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="thesisForm.closeForm()" iconCls="icon-cancel">关闭</a>
</div>
<div id="dlg" style="display: none;">
    <div id="ui-toolbar2">
        <div class="ui-toolbar-search">
            <select id="major" name="major"></select>
            <select id="grade" name="grade" style="margin-left: 5px;"></select>
            <select id="clazz" name="clazz" style="margin-left: 5px;"></select>
            <label>关键词：</label><input class="wu-text easyui-textbox" id="keywords2" style="width:100px">
            <a href="#" id="search" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch2()">开始检索</a>
        </div>
    </div>
    <table id="dg2">
    </table>
    <div style="padding:5px;" class="dialog-button">
        <a href="javascript:void(0)" id="ok" class="easyui-linkbutton" onclick="saveSelected()" iconCls="icon-ok">确定选择</a>
        <a href="javascript:void(0)" id="cancel" class="easyui-linkbutton" onclick="cancelSelect()" iconCls="icon-cancel">取消选择</a>
    </div>
</div>
<script src="${ctx}/resources/ckeditor/ckeditor.js"></script>
<script type="text/javascript">

    $(function () {
        var ckeditor = CKEDITOR.replace('profile', {
            language: 'zh-cn',
            width: '99%',
            height: '265',
            toolbar: 'Basic'
        });

        ckeditor.on('change', function (evt) {
//            console.log(evt.editor.getData());
            $("#profile").val(evt.editor.getData());
        });

    });

    $(function(){
        $('#account').textbox('textbox').on("keypress", function (e) {
            if (e.keyCode == 13) {
                var stuno = $('#account').textbox('getValue');
                if(stuno == "" || stuno == null){
                    $("#student").text("");
                    $("input[name='studentid']").val("");
                    return ;
                }
                var id = $("input[name='id']").val();
                $.post("${ctx}/console/thesis/checkStudent", {"stuno": stuno, "id":id}, function(data){
                    if(data.status == 200){
                        $("#student").text(data.data.username);
                        $("input[name='studentid']").val(data.data.id);
                        dlg.dialog('close');
                    }else{
                        $.messager.alert("提示", data.msg, "error");
                    }
                });
            }
        });
    });

    var dlg =$('#dlg').dialog({
        modal : true,
        title : '选择学生',
        closed: true,
        cache: false,
        width :880,
        height:400,
        onOpen:openDlgDlg,
        onClose:closeDlgDlg
    });

    function searchStudent(){
        $("#dlg").dialog("open");
    }

    var dg2 = null;


    function openDlgDlg(){

        $("#search").linkbutton({text:'开始检索', iconCls:'icon-search', width:100});
        $("#ok").linkbutton({text:'确定选择', iconCls:'icon-ok', width:100});
        $("#cancel").linkbutton({text:'取消选择', iconCls:'icon-cancel', width:100});
        $("#keywords2").textbox();
        $("#keywords2").textbox("setValue","");
        $('#major').combobox("setValue","");
        $('#grade').combobox("setValue","");
        $('#clazz').combobox("setValue","");

        if(dg2 == null){
            dg2 = $("#dg2").datagrid({
                url: '${ctx}/console/project/users-student.json',
                method: 'get',
                idField: 'id',
                toolbar: '#ui-toolbar2',
                fit:true,
                fitColumns:true,
                cache:false,
                pagination:true,
                rownumbers:true,
                pageNumber:1,
                pageSize : 10,
                pageList : [ 10, 20, 30, 40, 50 ],
                singleSelect:true,
                striped:true,
                columns:[[
                    {field:'id',title:'选择', checkbox: true, width:40},
                    {field:'account',title:'学号',width:150},
                    {field:'username',title:'姓名',width:150},
                    {field:'info.major',title:'专业',width:150, formatter:function(v,r,i){return formatColumn('info.major',v,r,i);}},
                    {field:'info.clazz',title:'班级',width:150, formatter:function(v,r,i){return formatColumn('info.clazz',v,r,i);}},
                    {field:'info.grade',title:'年级',width:150, formatter:function(v,r,i){return formatColumn('info.grade',v,r,i);}}
                ]],
                dataPlain: true
            });
        }else{
            $("#dg2").datagrid("clearSelections");
            $("#dg2").datagrid("load", {});
        }

    }

    function closeDlgDlg(){
        //$("#dg2").datagrid('loadData', { total: 0, rows: [] });
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

    function saveSelected(){
        var sel = $("#dg2").datagrid("getSelected");
        if(sel){
            var studentid = sel.id;
            var id = $("input[name='id']").val();
            $.post("${ctx}/console/thesis/checkStudent", {"studentid": studentid, "id":id}, function(data){
                if(data.status == 200){
                    $("#account").textbox("setValue",sel.account);
                    $("#student").text(sel.username);
                    $("input[name='studentid']").val(sel.id);
                    dlg.dialog('close');
                }else{
                    $.messager.alert("提示", data.msg, "error");
                }
            });
        }else{
            $.messager.alert("提示", "请先选择一个学生！");
            return ;
        }

    }

    function cancelSelect(){
        dlg.dialog("close");
    }

    var thesisForm = {
        submitForm: function(){
            if(!$("#thesisForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            var stuno = $('#account').textbox('getValue');
            if(stuno != "" && $("input[name='studentid']").val() == ""){
                $.messager.alert("错误","请输入正确的学号或输入学号后确保按回车键[Enter]！");
                return false;
            }else if(stuno == ""){
                $("input[name='studentid']").val("");
            }
            progressLoad();
            $.post($("#thesisForm").attr("action"), $("#thesisForm").serialize(), function(data){
                progressClose();
                if(data.status == 200){
                    $.messager.alert("提示", data.msg, "info", function(){
                         window.top.reloadTabGrid("题目申报管理");
                         //window.close();
                    });
                }else{
                    $.messager.alert("提示", data.msg, "error");
                }
            });
        },
        closeForm: function(){
            //window.close();
            window.top.removeTab();
        }
    };
</script>
</body>
</html>
