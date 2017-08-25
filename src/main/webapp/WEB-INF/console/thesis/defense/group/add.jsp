<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/21
  Time: 8:44
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
                           答辩小组参数<br/>
                           <small>输入答辩小组的基本参数</small>
                        </span>
                        </a>
                    </li>
                    <li>
                        <a href="#step-2">
                            <label class="stepNumber">2</label>
                            <span class="stepDesc">
                            确认参加学生<br/>
                            <small>添加参与当前答辩小组的学生</small>
                        </span>
                        </a>
                    </li>
                    <li>
                        <a href="#step-3">
                            <label class="stepNumber">3</label>
                            <span class="stepDesc">
                           确认参加教师<br/>
                           <small>添加参与当前答辩小组的教师</small>
                        </span>
                        </a>
                    </li>
                    <li>
                        <a href="#step-4">
                            <label class="stepNumber">4</label>
                            <span class="stepDesc">
                           确认最终结果<br/>
                           <small>确认答辩小组的信息</small>
                        </span>
                        </a>
                    </li>
                </ul>
                <div id="step-1">
                    <h2 class="StepTitle">Step 1 输入答辩小组的基本参数</h2>
                    <div class="easyui-layout" style="height: 375px">
                        <div data-options="region:'west',split:true, border:true,title:'答辩任务基本信息'" width="30%" height="100px">
                            <table style="margin-top: 20px; margin-left: auto;margin-right:auto;" cellpadding="5" cellspacing="10">
                                <form id="form-left">
                                    <tr>
                                        <td align="right"><label>答辩类型：</label></td>
                                        <td>
                                            <select id="grouptype" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">
                                                <option value="0">正常答辩</option>
                                                <option value="2">争优答辩</option>
                                            </select>
                                        </td>
                                    </tr>
                                    </tr>
                                    <tr>
                                        <td align="right"><label>答辩地点：</label></td>
                                        <td>
                                            <input type="text" id="defenseroom" class="easyui-textbox" style="width:150px;">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right"><label>答辩时间：</label></td>
                                        <td>
                                            <input id="defensetime" class="easyui-datebox" style="width:150px;" data-options="editable:false">
                                        </td>
                                    </tr>
                                </form>

                            </table>
                        </div>
                        <div data-options="region:'center',split:true, border:true,title:'请选择答辩组长'" width="35%" height="100%">
                            <%--<div id="ui-toolbar-leader">--%>
                                <%--<div class="ui-toolbar-search">--%>
                                    <%--<label>选择职称：</label>--%>
                                    <%--<select id="leader-title" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">--%>
                                        <%--<option value="">请选择职称</option>--%>
                                        <%--<c:forEach items="${titles}" var="title">--%>
                                            <%--<option value="${title.ordinal()}">${title.label}</option>--%>
                                        <%--</c:forEach>--%>
                                    <%--</select>--%>
                                    <%--<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doTeacherSearch()">开始检索</a>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <table id="dg-leader" class="easyui-datagrid"
                                   data-options="url: '${ctx}/console/thesis/defense/group/teacher-list.json?taskid=${defenseTask.id}',
                            method: 'get',
                            idField: 'teacherid',
                            toolbar: '#ui-toolbar-leader',
                            <%--fit:true,--%>
                            fitColumns:true,
                            height: '375',
                            width: '100%',
                            rownumbers:true,
                            singleSelect:true">
                                <thead>
                                <tr>
                                    <th data-options="field:'teacherid', checkbox:true" , width="40">ID</th>
                                    <th data-options="field:'account'" width="70">工号</th>
                                    <th data-options="field:'userName'" width="50">姓名</th>
                                    <th data-options="field:'titleName'" width="70">职称</th>
                                    <th data-options="field:'titleLevel'" width="70">等级</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                        <div  data-options="region:'east',split:true, border:true,title:'请选择答辩秘书'" width="35%" height="100%">
                            <%--<label>选择职称：</label>--%>
                            <%--<select id="secretary-title" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">--%>
                                <%--<option value="">请选择职称</option>--%>
                                <%--<c:forEach items="${titles}" var="title">--%>
                                    <%--<option value="${type.ordinal()}">${type.label}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>
                            <%--<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doTeacherSearch()">开始检索</a>--%>
                            <table id="dg-secretary" class="easyui-datagrid"
                                   data-options="url: '${ctx}/console/thesis/defense/group/teacher-list.json?taskid=${defenseTask.id}',
                            method: 'get',
                            idField: 'teacherid',
                            <%--fit:true,--%>
                            fitColumns:true,
                            height: '375',
                            width: '100%',
                            rownumbers:true,
                            singleSelect:true">
                                <thead>
                                <tr>
                                    <th data-options="field:'teacherid', checkbox:true" , width="40">ID</th>
                                    <th data-options="field:'account'" width="70">工号</th>
                                    <th data-options="field:'userName'" width="50">姓名</th>
                                    <th data-options="field:'titleName'" width="70">职称</th>
                                    <th data-options="field:'titleLevel'" width="70">等级</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
                <div id="step-2">
                    <h2 class="StepTitle">Step 2 添加参与当前答辩小组的学生</h2>
                    <div id="ui-toolbar1">
                        <div class="ui-toolbar-search">
                            <label>学号：</label><input class="wu-text easyui-textbox" id="studentid" style="width:90px">
                            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doStudentSearch()">开始检索</a>
                        </div>
                    </div>
                    <table id="dg1" class="easyui-datagrid">
                        <thead>
                        <tr>
                            <th data-options="field:'studentid', checkbox:true" , width="50">ID</th>
                            <th data-options="field:'stuno'," width="230">学生学号</th>
                            <th data-options="field:'stuname'" width="230">学生姓名</th>
                            <th data-options="field:'clazz'" width="230">年级班级</th>
                            <th data-options="field:'defenseStatus'" >论文答辩类型</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div id="step-3">
                    <h2 class="StepTitle">Step 3 添加参与当前答辩小组的教师</h2>
                    <div id="ui-toolbar2">
                        <div class="ui-toolbar-search">
                            <label>选择职称：</label>
                            <select id="title" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">
                                <option value="">请选择职称</option>
                                <c:forEach items="${titles}" var="title">
                                    <option value="${title.ordinal()}">${title.label}</option>
                                </c:forEach>
                            </select>
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
                    <h2 class="StepTitle">Step 4 确认答辩小组的信息</h2>
                    <div style="padding:10px 10px 10px 10px">
                        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">
                            答辩小组信息
                        </div>
                        <input type="hidden" id="form-taskid" name="taskid" value="${defenseTask.id}"/>
                        <input type="hidden" id="form-grouptype" name="grouptype"/>
                        <input type="hidden" id="form-leaderid" name="leaderid"/>
                        <input type="hidden" id="form-secretaryid" name="secretaryid"/>
                        <input type="hidden" id="form-defensetime" name=" defensetime"/>
                        <input type="hidden" id="form-defenseroom" name="defenseroom"/>
                        <input type="hidden" id="form-students" name="students"/>
                        <input type="hidden" id="form-teachers" name="teachers"/>
                        <table width="100%" cellpadding="5" class="form-table" >
                            <tbody>
                            <tr id="newTopic">
                                <td colspan="2"  style="text-align: center"><label>所属答辩任务名称</label></td>
                                <td colspan="4"  style="text-align: center">
                                    <span class="defensename">${defenseTask.name}</span>
                                </td>
                            </tr>
                            <tr>
                                <td><label>答辩组长</label></td>
                                <td>
                                    <span class="leader"></span>
                                </td>
                                <td><label>答辩秘书</label></td>
                                <td>
                                    <span class="secretary"></span>
                                </td>
                                <td> <label>答辩主席</label></td>
                                <td>
                                    <span class="president">${defenseTask.president}</span>
                                </td>
                            </tr>
                            <tr>
                                <td width="120"><label>答辩类型</label></td>
                                <td width="170">
                                    <span class="grouptype"></span>
                                </td>
                                <td width="120"><label>答辩时间</label></td>
                                <td width="170">
                                    <span class="defensetime"></span>
                                </td>
                                <td width="120"><label>答辩地点</label></td>
                                <td width="170">
                                    <span class="defenseroom"></span>
                                </td>
                            </tr>
                            <tr>
                                <td><label>总共选择学生人数</label></td>
                                <td>
                                    <span class="studentnum"></span>
                                </td>
                                <td><label>学生详情</label></td>
                                <td colspan="3">
                                    <span class="studentclass">
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td><label>参加教师人数</label></td>
                                <td>
                                    <span class="teachernum"></span>
                                </td>
                                <td><label>教师详情</label></td>
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
<div id="dlg"></div>
<script type="text/javascript">
    var leader;
    var secretary;
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
        //绑定事件
        $("#leader").next("span").click(function(){
            add("leader");
        });
        $("#secretary").next("span").blur(function(){
            add("secretary");
        });
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
                $('#dg1').datagrid({ loadFilter: pagerFilter }).datagrid({
                    url:'${ctx}/console/thesis/defense/group/student-list.json?taskid=${defenseTask.id}'+"&defenseStatus="+$("#grouptype").combobox("getValue"),
                    method: 'get',
                    idField: 'studentid',
                    toolbar: '#ui-toolbar1',
                    <%--fit:true,--%>
                    fitColumns:true,
                    height: '350',
                    width: '965',
                    pagination:true,
                    rownumbers:true,
                    pageNumber:1,
                    pageSize : 10,
                    pageList : [ 10, 20, 30, 40, 50 ],
                    singleSelect:false
                });
            }
            else if(context.toStep==3){
                $("#dg2").datagrid("resize");
                $('#dg2').datagrid({ loadFilter: pagerFilter }).datagrid({
                    url:'${ctx}/console/thesis/defense/group/teacher-list.json?taskid=${defenseTask.id}'+"&leaderJSON="+JSON.stringify(leader)+"&secretaryJSON="+JSON.stringify(secretary),
                    method: 'get',
                    idField: 'teacherid',
                    toolbar: '#ui-toolbar2',
                    <%--fit:true,--%>
                    fitColumns:false,
                    height: '350',
                    width: '948',
                    pagination:true,
                    rownumbers:true,
                    pageNumber:1,
                    pageSize : 10,
                    pageList : [ 10, 20, 30, 40, 50 ],
                    singleSelect:false
                });
            }
        }

        function leaveAStepCallback(obj, context) {
            return validateSteps(context.fromStep);
        }

        function validateSteps(stepnumber) {
            var isStepValid = true;
            // validate step 1
            if (stepnumber == 1) {
                var grouptype = $("#grouptype").textbox("getValue");
                var defensetime=$("#defensetime").datebox("getValue");
                var defenseroom=$("#defenseroom").textbox('getValue');
                leader=$("#dg-leader").datagrid("getSelected");
                secretary=$("#dg-secretary").datagrid("getSelected");
                if (grouptype == ""||leader==""||secretary==""||defensetime==""||defenseroom=="") {
                    // set isStepValid = false if has errors
                    isStepValid = false;
                    $('#wizard').smartWizard('showError', stepnumber);
                    $('#wizard').smartWizard('showMessage', '存在未填写的字段,请填写完毕后再继续！');
                }
                else if(JSON.stringify(leader) == JSON.stringify(secretary)){
                    isStepValid = false;
                    $('#wizard').smartWizard('showError', stepnumber);
                    $('#wizard').smartWizard('showMessage', '答辩组长与答辩秘书不得相同！');
                }else{
                    $('#wizard').smartWizard('hideError', stepnumber);
                    $('#wizard').smartWizard('hideMessage');
                    $("#form-grouptype").val(grouptype==0?"正常答辩":"争优答辩");
                    $("#form-leaderid").val(leader.teacherid);
                    $("#form-secretaryid").val(secretary.teacherid);
                    $("#form-defensetime").val(defensetime);
                    $("#form-defenseroom").val(defenseroom);
                    $(".grouptype").text(grouptype==0?"正常答辩":"争优答辩");
                    $(".leader").text(leader.userName);
                    $(".secretary").text(secretary.userName);
                    $(".defensetime").text(defensetime);
                    $(".defenseroom").text(defenseroom);
                }
            } else if (stepnumber == 2) {
                var rows = $("#dg1").datagrid("getSelections");
                if (rows == null||rows=="") {
                    isStepValid = false;
                    $('#wizard').smartWizard('showError', stepnumber);
                    $('#wizard').smartWizard('showMessage', '请为答辩小组添加参与学生！');
                } else {
                    $('#wizard').smartWizard('hideError', stepnumber);
                    $('#wizard').smartWizard('hideMessage');
                    var studentclass="";
                    var j=0;
                    for(var i=0;i<rows.length;i++){
                        studentclass+=rows[i].stuno+""+rows[i].stuname+"   ";
                        j+=1;
                        if(j%3==0){
                            studentclass+="</br>";
                        }
                    }
                    $(".studentclass").html(studentclass);
                    $(".studentnum").text(rows.length+"名");
                    $("#form-students").val(JSON.stringify(rows));
                    //检验是否为json
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
                    var teacherclass=""
                    var j=0;
                    for(var i=0;i<rows.length;i++){
                        teacherclass+=rows[i].userName+ " "+rows[i].titleLevel;
                        if(i!=rows.length-1){teacherclass+=","}
                        j+=1;
                        if(j%3==0){
                            studentclass+="</br>";
                        }
                    }
                    $(".teacherclass").html(teacherclass);
                    $(".teachernum").text(rows.length+"名");
                    $("#form-teachers").val(JSON.stringify(rows));
                }
            }
            return isStepValid;
        }

        function onFinish(objs) {
            var taskid = $('#form-taskid').val();
            var grouptype = $('#form-grouptype').val();
            var leaderid =$("#form-leaderid").val();
            var secretaryid =$("#form-secretaryid").val();
            var defensetime=$("#form-defensetime").val();
            var defenseroom=$("#form-defenseroom").val();
            var students=$("#form-students").val();
            var teachers=$("#form-teachers").val();
            alert("taskid="+taskid+";grouptype="+grouptype+"leaderid="+leaderid+
                "secretaryid="+secretaryid+"defensetime"+defensetime+"defenseroom"+
            "students"+students+"teachers"+teachers)
            if (grouptype == null || grouptype == ""
                ||leaderid==null || leaderid==""
                ||secretaryid==null || secretaryid==""
                ||defensetime==null||defensetime==""
                ||defenseroom==null||defenseroom=="") {
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
            var params = {'taskid': taskid, 'grouptype': grouptype,
                'leaderid':leaderid,'secretaryid':secretaryid,'defensetime':defensetime,
                'defenseroom':defenseroom,'students':students,'teachers':teachers};
            $.ajaxSetup({
                async: false
            });
            $.post("${ctx}/console/thesis/defense/group/add", params, function (data) {
                if (data.status == 200) {
                    $('#wizard').smartWizard('showMessage', data.msg);
                    $.messager.alert("提示", data.msg, undefined, function () {
                        location.reload();
                        window.top.FlashTab("答辩小组管理");
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

    function add(op){
        d=$("#dlg").dialog({
            title: '添加' + getLabel(op),
            width: 965,
            height: 500,
            href:'${ctx}/console/thesis/defense/group/edit-add-'+op+"?id=${defenseGroup.id}",
            maximizable:true,
            modal:true
        });
    }
    function getLabel(op){
        switch (op) {
            case "student":
                return "学生";
            case "teacher":
                return "教师";
            case "leader":
                return "答辩组长";
            default:
                return "答辩秘书";
        }
    }
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
        params.stuno =$("#studentid").val();
        $("#dg1").datagrid("load", params);
        return false;
    }
    function doTeacherSearch() {
        var params = {};
        params.teacher = $("#teacherid").val();
        if ($('#title').combobox("getValue") != "") {
            params.titleLevel = $('#title').combobox("getValue");
        }
        $("#dg2").datagrid("load", params);
        return false;
    }
</script>
</body>
</html>
