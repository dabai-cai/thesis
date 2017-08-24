<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/16
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>添加答辩任务</title>
    <%@include file="/inc/header.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/smartwizard/styles/smart_wizard.css">
    <script type="text/javascript" src="${ctx}/resources/smartwizard/js/jquery.smartWizard.js"></script>
</head>
<body>
<table align="center" border="0" cellpadding="0" cellspacing="0" style="margin: 20px auto;">
    <tr>
        <td>
            <div id="wizard" class="swMain">
                <ul>
                    <li>
                        <a href="#step-1">
                            <label class="stepNumber">1</label>
                            <span class="stepDesc">
                           答辩任务参数<br/>
                           <small>输入答辩任务的基本参数</small>
                        </span>
                        </a>
                    </li>
                    <li>
                        <a href="#step-2">
                            <label class="stepNumber">2</label>
                            <span class="stepDesc">
                            确认参加学生<br/>
                            <small>添加参与当前答辩任务的学生</small>
                        </span>
                        </a>
                    </li>
                    <li>
                        <a href="#step-3">
                            <label class="stepNumber">3</label>
                            <span class="stepDesc">
                           确认参加教师<br/>
                           <small>添加参与当前答辩任务的教师</small>
                        </span>
                        </a>
                    </li>
                    <li>
                        <a href="#step-4">
                            <label class="stepNumber">4</label>
                            <span class="stepDesc">
                           确认最终结果<br/>
                           <small>确认答辩任务的信息</small>
                        </span>
                        </a>
                    </li>
                </ul>
                <div id="step-1">
                    <h2 class="StepTitle">Step 1 输入答辩任务的基本参数</h2>
                    <table style="margin-top: 20px; margin-left: auto;margin-right:auto;" cellpadding="5" cellspacing="10">
                        <tr>
                            <td align="right"><label>请输入答辩任务名称：</label></td>
                            <td>
                                <input type="text" id="defensename" class="easyui-textbox" style="width:250px;"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>请输入答辩主席名称：</label></td>
                            <td>
                                <input type="text" id="president" class="easyui-textbox" style="width:250px;">
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>请输入联系人：</label></td>
                            <td>
                                <input type="text" id="contact" class="easyui-textbox" style="width:250px;">
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>请输入答辩小组组数：</label></td>
                            <td>
                                <input type="text" id="nums" class="easyui-textbox" style="width:250px;">
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>请选择答辩时间：</label></td>
                            <td>
                                <input id="defensetime" class="easyui-datebox" style="width:250px;" data-options="editable:false">
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>备注：</label></td>
                            <td>
                                <textarea name="remark" id="remark" cols="38" rows="7"></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="step-2">
                    <h2 class="StepTitle">Step 2 添加参与当前答辩任务的学生</h2>
                    <div id="ui-toolbar1">
                        <div class="ui-toolbar-search">
                            <select id="major" name="major" ></select>
                            <select id="grade" name="grade" style="margin-left: 5px;"></select>
                            <select id="clazz" name="clazz" style="margin-left: 5px;"></select>
                            <label>学号：</label><input class="wu-text easyui-textbox" id="studentid" style="width:90px">
                            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doStudentSearch()">开始检索</a>
                        </div>
                    </div>
                    <table id="dg1" class="easyui-datagrid"
                           data-options="url: '${ctx}/console/thesis/defense/task/student-list.json',
                            method: 'get',
                            idField: 'studentid',
                            toolbar: '#ui-toolbar1',
                            <%--fit:true,--%>
                            loadFilter: pagerFilter ,
                            fitColumns:true,
                            height: '350',
                            width: '965',
                            pagination:true,
                            rownumbers:true,
                            pageNumber:1,
                            pageSize : 10,
                            pageList : [ 10, 20, 30, 40, 50 ],
                            singleSelect:false">
                        <thead>
                        <tr>
                            <th data-options="field:'studentid', checkbox:true" , width="50">ID</th>
                            <th data-options="field:'stuno'," width="230">学生学号</th>
                            <th data-options="field:'stuname'" width="230">学生姓名</th>
                            <th data-options="field:'clazz'" width="230">年级班级</th>
                            <th data-options="field:'defenseStatus'" >论文答辩类型</th>
                            <%--<th data-options="field:'direction'" width="80">研究方向</th>--%>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div id="step-3">
                    <h2 class="StepTitle">Step 3 添加参与当前答辩任务的教师</h2>
                    <div id="ui-toolbar2">
                        <div class="ui-toolbar-search">
                            <label>选择职称：</label>
                            <select id="title" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">
                                <option value="-1">请选择级别</option>
                                <c:forEach items="${titles}" var="title">
                                    <option value="${title.ordinal()}">${title.label}</option>
                                </c:forEach>
                            </select>
                            <label>工号：</label><input class="wu-text easyui-textbox" id="teacherid" style="width:80px">
                            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doTeacherSearch()">开始检索</a>
                        </div>
                    </div>
                    <table id="dg2" class="easyui-datagrid">
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
                <div id="step-4">
                    <h2 class="StepTitle">Step 4 确认答辩任务的信息</h2>
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
                            <tr id="newTopic">
                                <td width="120"><label>答辩任务名称</label></td>
                                <td colspan="3">
                                    <span class="defensename"></span>
                                </td>
                                <td><label>答辩主席</label></td>
                                <td>
                                    <span class="president"></span>
                                </td>
                            </tr>
                            <tr>
                                <td><label>联系人</label></td>
                                <td>
                                    <span class="contact"></span>
                                </td>
                                <td><label>答辩时间</label></td>
                                <td>
                                    <span class="defensetime"></span>
                                </td>
                                <td><label>答辩小组组数</label></td>
                                <td>
                                    <span class="nums"></span>
                                </td>
                            </tr>
                            <tr>
                                <td><label>总共选择学生人数</label></td>
                                <td>
                                    <span class="studentnum"></span>
                                </td>
                                <td><label>班级分布情况</label></td>
                                <td colspan="3">
                                    <span class="studentclass"></span>
                                </td>
                            </tr>
                            <tr>
                                <td><label>总共选择老师人数</label></td>
                                <td>
                                    <span class="teachernum"></span>
                                </td>
                                <td><label>职称情况统计</label></td>
                                <td colspan="3">
                                    <span class="teacherclass"></span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!-- End SmartWizard Content -->

        </td>
    </tr>
</table>
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
    $(document).ready(function () {
        // Smart Wizard
        var sw = $('#wizard').smartWizard({
            selected: 0,
            contentCache: false,
            transitionEffect: "slide",
            labelNext: "下一步",
            labelPrevious: '上一步',
            labelFinish: '确认完成',
            onLeaveStep: leaveAStepCallback,
            onShowStep: showStepCallback,
            onFinish: onFinish
        });

        function showStepCallback(obj, context) {
            if (context.toStep == 2) {
                $("#dg1").datagrid("resize");
            }
            else if(context.toStep==3){
                $("#dg2").datagrid("resize");
            }
        }

        function leaveAStepCallback(obj, context) {
            return validateSteps(context.fromStep);
        }

        function validateSteps(stepnumber) {
            var isStepValid = true;
            // validate step 1
            if (stepnumber == 1) {
                var defensename = $("#defensename").textbox("getValue");
                var president=$("#president").textbox("getValue");
                var contact=$("#contact").textbox("getValue");
                var defensetime=$("#defensetime").datebox("getValue");
                var nums=$("#nums").textbox('getValue');
                var remark=$("#remark").val();
                if (defensename == ""||president==""||contact==""||defensetime==""||nums=="") {
                    // set isStepValid = false if has errors
                    isStepValid = false;
                    $('#wizard').smartWizard('showError', stepnumber);
                    $('#wizard').smartWizard('showMessage', '请先输入答辩任务名称/答辩主席/联系人/答辩小组组数/答辩时间！');
                } else {
                    $('#wizard').smartWizard('hideError', stepnumber);
                    $('#wizard').smartWizard('hideMessage');
                    $.ajaxSetup({
                        async : false
                    });
                    $.post("${ctx}/console/thesis/defense/task/wizard", {"name":defensename,"step_number":stepnumber}, function(data){
                        if(data.status == 200){
                            $("#form-defensename").val(defensename);
                            $("#form-contact").val(contact);
                            $("#form-nums").val(nums);
                            $("#form-defensetime").val(defensetime);
                            $("#form-president").val(president);
                            $("#form-remark").val(remark);
                            $(".defensename").text(defensename);
                            $(".president").text(president);
                            $(".contact").text(contact);
                            $(".defensetime").text(defensetime);
                            $(".nums").text(nums);
                        }else{
                            isStepValid = false;
                            $('#wizard').smartWizard('showError',1);
                            $('#wizard').smartWizard('showMessage', data.msg);
                            //$('#wizard').smartWizard('goToStep',1);
                        }
                    })

                }
            } else if (stepnumber == 2) {
                var rows = $("#dg1").datagrid("getSelections");
                if (rows == null||rows=="") {
                    isStepValid = false;
                    $('#wizard').smartWizard('showError', stepnumber);
                    $('#wizard').smartWizard('showMessage', '请为答辩任务添加参与学生！');
                } else {
                    $('#wizard').smartWizard('hideError', stepnumber);
                    $('#wizard').smartWizard('hideMessage');
                    var total={}
                    for(var i=0;i<rows.length;i++){
                        if(total[rows[i].clazz]==null){
                            total[rows[i].clazz]=1;
                        }else{
                            total[rows[i].clazz]++;
                        }
                    }
                    var studentclass=""
                    for(var key in total){
                        studentclass+=key+":"+total[key]+"名 ";
                    }
                    $(".studentclass").text(studentclass);
                    $(".studentnum").text(rows.length+"名");
                    alert(JSON.stringify(rows));
                    $("#form-students").val(JSON.stringify(rows));
                    //检验是否为json
                    $("#dg2").datagrid({
                        url: '${ctx}/console/thesis/defense/task/teacher-list.json?defensetime='+$("#form-defensetime").val(),
                        method: 'get',
                        idField: 'teacherid',
                        toolbar: '#ui-toolbar',
                        <%--fit:true,--%>
                        loadFilter: pagerFilter ,
                        fitColumns:false,
                        height: '350',
                        width: '948',
                        pagination:true,
                        rownumbers:true,
                        pageNumber:1,
                        pageSize : 10,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        singleSelect:false
                    })
                }
            }else if(stepnumber == 3){
                var rows = $("#dg2").datagrid("getSelections");
                if (rows == null||rows=="") {
                    isStepValid = false;
                    $('#wizard').smartWizard('showError', stepnumber);
                    $('#wizard').smartWizard('showMessage', '请为答辩任务添加参与教师！');
                } else {
                    $('#wizard').smartWizard('hideError', stepnumber);
                    $('#wizard').smartWizard('hideMessage');
                    var total={}
                    for(var i=0;i<rows.length;i++){
                        if(total[rows[i].titleName]==null){
                            total[rows[i].titleName]=1;
                        }else{
                            total[rows[i].titleName]++;
                        }
                    }
                    var teacherclass=""
                    for(var key in total){
                        teacherclass+=key+":"+total[key]+"名 ";
                    }
                    $(".teacherclass").text(teacherclass);
                    $(".teachernum").text(rows.length+"名");
                    alert(JSON.stringify(rows));
                    $("#form-teachers").val(JSON.stringify(rows));
                }
            }
            return isStepValid;
        }

        function onFinish(objs) {
            var defensename = $('#form-defensename').val();
            var president = $('#form-president').val();
            var contact =$("#form-contact").val();
            var remark =$("#form-remark").val();
            var nums=$("#form-nums").val();
            var defensetime=$("#form-defensetime").val();
            var students=$("#form-students").val();
            var teachers=$("#form-teachers").val();
            if (defensename == null || defensename == ""
            ||president==null || president==""
            ||contact==null || contact==""
            ||defensetime==null||defensetime==""
            ||nums==null||nums=="") {
                $('#wizard').smartWizard('showError', 1);
                $('#wizard').smartWizard('showMessage', '您还没有填完整答辩任务基本信息！');
                return false;
            }
            if (students == null || students == "") {
                $('#wizard').smartWizard('showError', 2);
                $('#wizard').smartWizard('showMessage', '您还没有选择参与学生！');
                return false;
            }
            if (teachers == null || teachers == "") {
                $('#wizard').smartWizard('showError', 3);
                $('#wizard').smartWizard('showMessage', '您还没有选择参与教师！');
                return false;
            }
            var params = {'name': defensename, 'president': president,
            'contact':contact,'remark':remark,'nums':nums,
            'defensetime':defensetime,'students':students,'teachers':teachers};
            $.ajaxSetup({
                async: false
            });
            $.post("${ctx}/console/thesis/defense/task/add", params, function (data) {
                if (data.status == 200) {
                    $('#wizard').smartWizard('showMessage', data.msg);
                    $.messager.alert("提示", data.msg, undefined, function () {
                        location.reload();
                        window.top.FlashTab("答辩任务管理");
                    });
                } else {
                    $('#wizard').smartWizard('showError', 4);
                    $('#wizard').smartWizard('showMessage', data.msg);
                    return false;
                }
            });
            return true;
        }
    });

    function formatTitle(v, r, i) {
        return formatColumn('info.title', v, r, i);
    }

    function formatTopic(val, row) {
        return '<a href="#" onclick="viewTopic(' + row.id + ', event);">' + val + '</a>';
    }

    function viewTopic(id, event) {
        window.top.addTab("论文题目详情", '${ctx}/console/thesis/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }

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
    function doTeacherSearch(){
        var params = {};
        params.teacher=$("#teacherid").val();
        if($('#title').combobox("getValue") != ""){
            params.titleLevel = $('#title').combobox("getValue");
        }
        $("#dg2").datagrid("load", params);
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
