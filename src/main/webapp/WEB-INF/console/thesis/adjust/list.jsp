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
    <title>学生选题调整</title>
    <%@include file="/inc/header.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/smartwizard/styles/smart_wizard.css">
    <script type="text/javascript" src="${ctx}/resources/smartwizard/js/jquery.smartWizard.js"></script>
</head>
<body>
<table align="center" border="0" cellpadding="0" cellspacing="0" style="margin: 20px auto;">
    <tr><td>
        <div id="wizard" class="swMain">
            <ul>
                <li>
                    <a href="#step-1">
                        <label class="stepNumber">1</label>
                        <span class="stepDesc">
                           选择学生<br/>
                           <small>输入学生学号以选择学生</small>
                        </span>
                    </a>
                </li>
                <li>
                    <a href="#step-2">
                        <label class="stepNumber">2</label>
                        <span class="stepDesc">
                            确认学生<br />
                            <small>确认学生当前选题信息</small>
                        </span>
                    </a>
                </li>
                <li>
                    <a href="#step-3">
                        <label class="stepNumber">3</label>
                        <span class="stepDesc">
                           选择题目<br />
                           <small>为学生选择新的论文题目</small>
                        </span>
                    </a>
                </li>
                <li>
                    <a href="#step-4">
                        <label class="stepNumber">4</label>
                        <span class="stepDesc">
                           确认调整<br />
                           <small>确认调整学生的选题信息</small>
                        </span>
                    </a>
                </li>
            </ul>
            <div id="step-1">
                <h2 class="StepTitle">Step 1 输入学生学号以选择学生</h2>
                <table style="margin-top: 20px; margin-left: 20px;"  cellpadding="5" cellspacing="10">
                    <tr>
                        <td><label>请输入学生的完整学号：</label></td>
                        <td>
                            <input type="text" id="stuno" class="easyui-textbox" style="width:200px;" />
                        </td>
                    </tr>
                </table>
            </div>
            <div id="step-2">
                <h2 class="StepTitle">Step 2 确认学生当前选题信息</h2>
                <div style="padding:10px 10px 10px 10px">
                    <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">学生选题信息</div>
                    <input type="hidden" id="isStepValid" name="isStepValid"/>
                    <table width="100%" cellpadding="5" class="form-table">
                        <tbody>
                            <tr id="topic" style="display: none">
                                <td width="120"><label>论文标题</label></td>
                                <td colspan="3">
                                    <span class="topic"></span>
                                </td>
                                <td><label>指导教师</label></td>
                                <td>
                                    <span class="teacher"></span>
                                </td>
                            </tr>
                            <tr id="student" style="display: none">
                                <td><label>学生姓名</label></td>
                                <td>
                                    <span class="stuname"></span>
                                </td>
                                <td><label>学生学号</label></td>
                                <td>
                                    <span class="stuno"></span>
                                </td>
                                <td><label>专业班级</label></td>
                                <td>
                                    <span class="clazz"></span>
                                </td>
                            </tr>
                            <tr id="notopic" style="display: none">
                                <td colspan="6" style="text-align: left;">
                                    <strong>该学生未入选任何论文题目，可以直接为其指定其他论文题目。</strong>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div id="step-3">
                <h2 class="StepTitle">Step 3 为学生选择新的论文题目</h2>
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
                            <%--fit:true,--%>
                            fitColumns:false,
                            height: '350',
                            width: '948',
                            pagination:true,
                            rownumbers:true,
                            pageNumber:1,
                            pageSize : 10,
                            pageList : [ 10, 20, 30, 40, 50 ],
                            singleSelect:true">
                    <thead>
                    <tr>
                        <th data-options="field:'id', checkbox:true", width="100">ID</th>
                        <th data-options="field:'teacher'" width="100">指导教师</th>
                        <th data-options="field:'topic', formatter:formatTopic" width="300">论文题目</th>
                        <th data-options="field:'direction'" width="100">研究方向</th>
                        <th data-options="field:'source'" width="80">题目来源</th>
                        <th data-options="field:'property'" width="80">题目性质</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div id="step-4">
                <h2 class="StepTitle">Step 4 确认调整学生的选题信息</h2>
                <div style="padding:10px 10px 10px 10px">
                    <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">学生新选题信息</div>
                    <input type="hidden" id="thesisid" name="thesisid"/>
                    <input type="hidden" id="studentid" name="studentid"/>
                    <table width="100%" cellpadding="5" class="form-table">
                        <tbody>
                        <tr id="newTopic" style="display: none">
                            <td width="120"><label>论文标题</label></td>
                            <td colspan="3">
                                <span class="topic"></span>
                            </td>
                            <td><label>指导教师</label></td>
                            <td>
                                <span class="teacher"></span>
                            </td>
                        </tr>
                        <tr class="student" style="display: none">
                            <td><label>学生姓名</label></td>
                            <td>
                                <span class="stuname"></span>
                            </td>
                            <td><label>学生学号</label></td>
                            <td>
                                <span class="stuno"></span>
                            </td>
                            <td><label>专业班级</label></td>
                            <td>
                                <span class="clazz"></span>
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
    $(document).ready(function(){
        // Smart Wizard
        var sw = $('#wizard').smartWizard({
            selected:0,
            contentCache:false,
            transitionEffect:"slide",
            labelNext: "下一步",
            labelPrevious:'上一步',
            labelFinish:'确认完成',
            onLeaveStep:leaveAStepCallback,
            onShowStep:showStepCallback,
            onFinish: onFinish
        });

        function showStepCallback(obj, context){
            if(context.toStep == 3){
                $("#dg").datagrid("resize");
            }
        }

        function leaveAStepCallback(obj, context){
            return validateSteps(context.fromStep);
        }

        function validateSteps(stepnumber){
            var isStepValid = true;
            // validate step 1
            if(stepnumber == 1){
                var stuno = $("#stuno").textbox("getValue");
                if(stuno == ""){
                    // set isStepValid = false if has errors
                    isStepValid = false;
                    $('#wizard').smartWizard('showError',stepnumber);
                    $('#wizard').smartWizard('showMessage','请先输入学生的学号！');
                }else{
                    $('#wizard').smartWizard('hideError',stepnumber);
                    $('#wizard').smartWizard('hideMessage');
                    $.ajaxSetup({
                        async : false
                    });
                    $("#student,#topic,#notopic").hide();
                    $.post("${ctx}/console/tadjust/wizard", {"stuno":stuno,"step_number":stepnumber}, function(data){
                        if(data.status == 200){
                            $('#wizard').smartWizard('showMessage', data.msg);
                            var objs = data.data;
                            var student = objs[0];
                            if(student != null){
                                $(".stuname").text(student.username);
                                $(".stuno").text(student.account);
                                $(".clazz").text(student.info.grade + "级" + student.info.clazz);
                                $("#studentid").val(student.id);
                                $("#student").show();
                            }
                            var thesis = objs[1];
                            if(thesis != null){
                                $(".topic").text(thesis.topic);
                                var teacher = objs[2];
                                $(".teacher").text(teacher.username + "  " + teacher.info.title);
                                $("#topic").show();
                            }else{
                                $("#notopic").show();
                            }
                        }else{
                            isStepValid = false;
                            $('#wizard').smartWizard('showError',1);
                            $('#wizard').smartWizard('showMessage', data.msg);
                            //$('#wizard').smartWizard('goToStep',1);
                        }
                    });
                }
            }else if(stepnumber == 3){
                var sel = $("#dg").datagrid("getSelected");
                $("#newTopic, .student").hide();
                $("").hide();
                if(sel == null){
                    isStepValid = false;
                    $('#wizard').smartWizard('showError',stepnumber);
                    $('#wizard').smartWizard('showMessage','请为学生选择新的论文题目！');
                }else{
                    $('#wizard').smartWizard('hideError',stepnumber);
                    $('#wizard').smartWizard('hideMessage');
                    $('#thesisid').val(sel.id);
                    $("#newTopic .topic").text(sel.topic);
                    $("#newTopic .teacher").text(sel.teacher);
                    $("#newTopic,.student").show();
                }
            }
            return isStepValid;
        }

        function onFinish(objs){
            var thesisid = $('#thesisid').val();
            var studentid = $('#studentid').val();
            if(studentid == null || studentid == ""){
                $('#wizard').smartWizard('showError',1);
                $('#wizard').smartWizard('showMessage','您还没有确定学生信息！');
                return false;
            }
            if(thesisid == null || thesisid == ""){
                $('#wizard').smartWizard('showError',3);
                $('#wizard').smartWizard('showMessage','您还没有选择新的论文题目！');
                return false;
            }

            var params = {'thesisid':thesisid, 'studentid':studentid};
            $.ajaxSetup({
                async : false
            });

            $.post("${ctx}/console/tadjust/update", params, function(data){
                if(data.status == 200){
                    $('#wizard').smartWizard('showMessage', data.msg);
                    $.messager.alert("提示", data.msg, undefined, function(){
                        location.reload();
                    });
                }else{
                    $('#wizard').smartWizard('showError',4);
                    $('#wizard').smartWizard('showMessage', data.msg);
                    return false;
                }
            });
            return true;
        }
    });

    function formatTitle(v, r, i){
        return formatColumn('info.title',v,r,i);
    }

    function formatTopic(val, row){
        return '<a href="#" onclick="viewTopic('+ row.id +', event);">'+ val + '</a>';
    }

    function viewTopic(id, event){
        window.top.addTab("论文题目详情", '${ctx}/console/thesis/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }

    function doSearch(){
        $("#dg").datagrid("load",{
            teacherid: $("#teacherid").combogrid("getValue"),
            keywords:$("#keywords").val()
        });
        return false;
    }
</script>
</body>
</html>
