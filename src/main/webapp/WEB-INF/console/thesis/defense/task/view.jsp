<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/18
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>答辩任务详情</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'west',split:true, border:false,title:'答辩任务基本信息'" width="30%">
    <div style="padding:10px 10px 10px 10px">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">
            答辩任务信息
        </div>
        <table width="100%" cellpadding="5" class="form-table">
            <tbody>
            <tr id="newTopic">
                <td width="120"><label>答辩任务名称</label></td>
                <td>
                    <span class="defensename">${defenseTask.name}</span>
                </td>
            </tr>
            <tr>
                <td><label>答辩主席</label></td>
                <td>
                    <span class="president">${defenseTask.president}</span>
                </td>
            </tr>
            <tr>
                <td><label>联系人</label></td>
                <td>
                    <span class="contact">${defenseTask.contact}</span>
                </td>
            </tr>
            <tr>
                <td><label>答辩时间</label></td>
                <td>
                    <span class="defensetime">${defenseTask.defensetime}</span>
                </td>
            </tr>
            <tr>
                <td><label>答辩小组组数</label></td>
                <td>
                    <span class="nums">${defenseTask.nums}组</span>
                </td>
            </tr>
            <tr>
                <td><label>总共选择学生人数</label></td>
                <td>
                    <span class="studentnum"></span>
                </td>
            </tr>
            <tr>
                <td><label>学生分布情况</label></td>
                <td>
                    <span class="studentclass"></span>
                </td>
            </tr>
            <tr>
                <td><label>总共选择老师人数</label></td>
                <td>
                    <span class="teachernum"></span>
                </td>
            </tr>
            <tr>
                <td><label>教师职称情况统计</label></td>
                <td>
                    <span class="teacherclass"></span>
                </td>
            </tr>
            <tr>
                <td><label>备注</label></td>
                <td>
                    <span>${defenseTask.remark}</span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'参加学生'" width="35%">
    <table id="dg-student" class="easyui-datagrid"
           data-options="url: '${ctx}/console/thesis/defense/task/student-showlist.json?id=${defenseTask.id}',
                        method: 'get',
                        idField: 'studentid',
                        <%--fit:true,--%>
                        height:'100%',
                        fitColumns:true,
                        rownumbers:true,
                        pagination:true,
                        pageNumber:1,
                        pageSize : 10,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        singleSelect:true
                    ">
        <thead>
        <tr>
            <th data-options="field:'studentid',hidden:'true'" , width="40">ID</th>
            <th data-options="field:'stuno'," width="100">学生学号</th>
            <th data-options="field:'stuname'" width="100">学生姓名</th>
            <th data-options="field:'clazz'" width="100">年级班级</th>
        </tr>
        </thead>
    </table>
</div>
<div data-options="region:'east',split:true, border:false,title:'参加教师'" width="35%">
    <table id="dg-teacher" class="easyui-datagrid"
           data-options="url: '${ctx}/console/thesis/defense/task/teacher-showlist.json?id=${defenseTask.id}',
                        method: 'get',
                        idField: 'teacherid',
                        <%--fit:true,--%>
                        height:'100%',
                        fitColumns:true,
                        rownumbers:true,
                        pagination:true,
                        pageNumber:1,
                        pageSize : 10,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        singleSelect:true
                    ">
        <thead>
        <tr>
            <th data-options="field:'teacherid',hidden:'true'" , width="40" >ID</th>
            <th data-options="field:'account' " width="100">工号</th>
            <th data-options="field:'userName'" width="100">姓名</th>
            <th data-options="field:'titleName'" width="100">职称</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>
<script>
    var students=JSON.parse('${defenseTask.students}');
    var teachers=JSON.parse('${defenseTask.teachers}');
    var clazznum={};
    var titlenum={};
    for(var i=0;i<students.length;i++){
        if(clazznum[students[i].clazz]==null){
            clazznum[students[i].clazz]=1;
        }else{
            clazznum[students[i].clazz]++;
        }
    }
    for(var i=0;i<teachers.length;i++){
        if(titlenum[teachers[i].titleName]==null){
            titlenum[teachers[i].titleName]=1;
        }else{
            titlenum[teachers[i].titleName]++;
        }
    }
    var stuclass="";
    for(var key in clazznum){
        stuclass+=key+":"+clazznum[key]+"名<br/>";
    }
    var teacherclass="";
    for(var key in titlenum){
        teacherclass+=key+":"+titlenum[key]+"名<br/>";
    }
    $(".studentnum").text(students.length+"名");
    $(".teachernum").text(teachers.length+"名");
    $(".studentclass").html(stuclass);
    $(".teacherclass").html(teacherclass);
    var time='${defenseTask.defensetime}';
    time=time.split(" ")[0];
    $(".defensetime").text(time);
</script>

