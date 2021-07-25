<%--
  Created by IntelliJ IDEA.
  User: 大白菜
  Date: 2017/8/25 0025
  Time: 下午 2:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>答辩任务详情</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'center',split:true, border:false,title:'参加学生'" width="40%">
    <table id="dg-student" class="easyui-datagrid"
           data-options="
                        idField: 'studentid',
                        <%--fit:true,--%>

                        height:'100%',
                        fitColumns:true,
                        rownumbers:true,


                        singleSelect:true
                    ">
        <thead>
        <tr>
            <th data-options="field:'studentid',hidden:'true'" , width="40">ID</th>
            <th data-options="field:'thesisid',hidden:'true'">论文id</th>
            <th data-options="field:'topic'"  width="200" >论文</th>
            <th data-options="field:'stuname'" width="50">学生姓名</th>
            <th data-options="field:'stuno'," width="80">学生学号</th>
            <th data-options="field:'clazz'" width="100">年级班级</th>
            <th data-options="field:'defenseStatus'" >论文答辩类型</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${students}" var="student">
            <tr>
                <td>${student.studentid}</td>
                <td>${student.thesisid}</td>
                <td><a href="#" onclick="viewTopic(${student.thesisid}, event);">${student.topic}</a></td>
                <td><a href="#" onclick="viewStudent(${student.studentid});">${student.stuname}</a></td>
                <td>${student.stuno}</td>
                <td>${student.clazz}</td>
                <td>${defenseGroup.grouptype.label}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div data-options="region:'east',split:true, border:false,title:'参加教师'" width="30%">
    <table id="dg-teacher" class="easyui-datagrid"
           data-options="
                        idField: 'teacherid',
                        <%--fit:true,--%>
                        height:'100%',
                        fitColumns:true,
                        rownumbers:true,
                        singleSelect:true
                    ">
        <thead>
        <tr>
            <th data-options="field:'teacherid',hidden:'true'" , width="40" >ID</th>
            <th data-options="field:'account' " width="100">工号</th>
            <th data-options="field:'userName'" width="100">姓名</th>
            <th data-options="field:'titleName'" width="100">职称</th>
            <th data-options="field:'titleLevel'" width="100">等级</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${teachers}" var="teacher">
            <tr>
                <td>${teacher.teacherid}</td>
                <td>${teacher.account}</td>
                <td>${teacher.userName}</td>
                <td>${teacher.titleName}</td>
                <td>${teacher.titleLevel.label}</td>
            </tr>

        </c:forEach>
        </tbody>

    </table>
</div>
<div id="dlg"></div>
</body>
</html>
<script>
    function viewTopic(id, event){
        window.top.addTab("论文题目详情", '${ctx}/console/thesis/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }
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

    $(document).ready(function (){
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
    })




</script>

