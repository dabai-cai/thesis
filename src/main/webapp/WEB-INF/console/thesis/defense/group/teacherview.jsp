<%--
  Created by IntelliJ IDEA.
  User: 大白菜
  Date: 2017/8/25 0025
  Time: 下午 2:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>答辩任务详情</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'west',split:true, border:false,title:'答辩分组基本信息'" width="26%">
    <div style="padding:10px 10px 10px 10px">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">
            自身分组信息
        </div>
        <c:if test="${teacher.size() gt 0}">
            <c:forEach items="${teacher}" var="denfenseGroup">
                <table width="100%" cellpadding="5" class="form-table">
                    <tbody>
                    <tr id="newTopic">
                        <td width="60" style="text-align: left"><label>答辩组数</label></td>
                        <td>
                            <span>第${denfenseGroup.groupno}组</span>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: left"><label>答辩组长</label></td>
                        <td>
                            <span class="president">${denfenseGroup.leaderName}</span>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: left"><label>答辩秘书</label></td>
                        <td>
                            <span class="contact">${denfenseGroup.secretaryName}</span>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: left"><label>答辩时间</label></td>
                        <td>
                            <span>
                                <fmt:formatDate value="${denfenseGroup.defensetime}" pattern="yyyy.MM.dd"/>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: left"><label>答辩地点</label></td>
                        <td><span>
                                ${denfenseGroup.defenseroom}
                        </span>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: left"><label>答辩类型</label></td>
                        <td><span>
                                ${denfenseGroup.grouptype.label}
                        </span>
                        </td>
                    </tr>
                    <tr><td style="text-align: left"><label>操作</label></td>
                        <td>
                               <a href="#" onclick="more(${denfenseGroup.id});" class="easyui-linkbutton" iconCls="myicon-zoom">查看详情</a>
                          <c:if test="${denfenseGroup.secretaryid == user.id}">
                              <a href="#" onclick="score(${denfenseGroup.id})" class="easyui-linkbutton" iconCls="icon-edit">录入成绩</a>
                          </c:if>
                        </td></tr>
                    </tbody>
                </table>
                <br>
            </c:forEach>
        </c:if>

    </div>
</div>
<div data-options="region:'center',split:true, border:false,title:'指导学生分组情况'" width="40%">
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
            <th data-options="field:'id',hidden:'true'" , width="40">ID</th>
            <th data-options="field:'topic'" width="150">论文</th>
            <th data-options="field:'stuname'" width="40">学生姓名</th>
            <th data-options="field:'account'," width="80">学生学号</th>
            <th data-options="field:'groupno'" width="30">小组</th>
            <th data-options="field:'leader'" width="50">答辩组长</th>
            <th data-options="field:'secretary'" width="50">答辩秘书</th>
            <th data-options="field:'defenseStatus'" >答辩类型</th>
            <th data-options="field:'defenseTime'">答辩时间</th>
            <th data-options="field:'defenseroom'">答辩地点</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${students}" var="stuGroup">
            <tr>
                <td>
                        ${stuGroup.id}
                </td>
                <td><a href="#" onclick="viewTopic(${stuGroup.thesisid}, event);">${stuGroup.topic}</a></td>
                <td><a href="#" onclick="viewStudent(${stuGroup.id});">${stuGroup.stuname}</a></td>
                <td>${stuGroup.account}</td>
                <td>${stuGroup.groupno}</td>
                <td>${stuGroup.leader}</td>
                <td>${stuGroup.secretary}</td>
                <td>${stuGroup.defenseType}</td>
                <td>
                    <fmt:formatDate value="${stuGroup.defenseTime}" pattern="yyyy.MM.dd"/>
                </td>
                <td>${stuGroup.defenseRoom}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="dlg"></div>
</body>
</html>
<script>

    function score(id) {
        window.top.addTab("录入成绩", '${ctx}/console/tscore/list3?groupid='+id, null, true);
        event.stopPropagation();
        return false
    }


    function more(id) {
        window.top.addTab("分组详情", '${ctx}/console/thesis/defense/group/view?id='+id, null, true);
        event.stopPropagation();
        return false
    }
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


