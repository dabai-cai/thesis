<%--
  Created by IntelliJ IDEA.
  User: 大白菜
  Date: 2017/8/13 0013
  Time: 下午 7:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/inc/dialog.jsp" %>
<div style="padding:10px 10px 10px 10px" >
    <form id="scoreForm" method="post" action="${ctx}/console/tscore/edit?id=${score.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">评阅教师评分</div>
        <input type="hidden" name="mark3" id="mark3">
        <table width="100%" cellpadding="5" class="form-table" >
            <tbody>
            <tr>
                <td width="120"><label>论文标题</label></td>
                <td colspan="3">${thesis.topic}</td>
                <td><label>指导教师</label></td>
                <td>${teacher.username}&nbsp;&nbsp;${teacher.info.title}</td>
            </tr>
            <tr>
                <td><label>选题学生</label></td>
                <td>${student.username}</td>
                <td><label>学生学号</label></td>
                <td>
                    ${student.account}
                </td>
                <td><label>专业班级</label></td>
                <td>
                    ${student.info.grade}级${student.info.clazz}
                </td>
            </tr>
            <tr>
                <td><label>答辩小组评分</label></td>
                <td>
                    ${score.mark3}
                </td>
                <td><label>评分日期</label></td>
                <td><input type="text" id="mark3date" name="mark3date" class="easyui-datebox" value="<fmt:formatDate value="${score.mark3date}" pattern="yyyy-MM-dd" />" data-options="required:true" /></td>
            </tr>
            </tbody>
        </table>
        <table  width="100%" cellpadding="5" class="form-table">
            <tbody>
            <tr>
                <td rowspan="8" width="20" >答辩委员会意见与成绩评定</td>
                <td  rowspan="2">评分项目</td>
                <td  rowspan="2" >具体要求(A级标准)</td>
                <td  rowspan="2" align="center" width="40">最高分</td>
                <td  colspan="5"  align="center">评分</td>
            </tr>
            <tr><td align="center" width="30">A</td>
                <td align="center"  width="30">B</td>
                <td  width="30">C</td>
                <td  width="30">D</td>
                <td  width="30">E</td>
            </tr>
            <tr>
                <td rowspan="2">论文质量</td>
                <td rowspan="2">论文(设计)结构严谨，逻辑性强;<br>
                    有一定的学术价值或实用价值;<br>
                    文字表达准确流畅；论文格式规范;<br>
                    图表(或图纸)规范、符合要求。</td>
                <td rowspan="2" align="center" width="40">60</td>
                <td  width="30">55-60</td>
                <td  width="30">49-54</td>
                <td  width="30">43-48</td>
                <td  width="30">37-42</td>
                <td  width="30"><=36</td>
            </tr>
            <tr>

                <td colspan="5" > 成绩一&nbsp;
                    <input  class="easyui-numberbox"  id="score1" name="score1"  data-options="required:true"  precision="2" min="0" max="60" style="width:100px;"/></td>
            </tr>
            <tr>
                <td rowspan="2"> 论文报告、讲解</td>
                <td rowspan="2">思路清晰:概念清楚,重点(创新点);<br>
                    突出;语言表达准确;报告时间、<br>
                    节奏掌握好。</td>
                <td rowspan="2" align="center" width="40">20</td>
                <td  width="30">19-20</td>
                <td  width="30">17-18</td>
                <td  width="30">15-16</td>
                <td  width="30">13-14</td>
                <td  width="30"><=12</td>
            </tr>
            <tr>
                <td colspan="5" >成绩二&nbsp;
                    <input  class="easyui-numberbox"  id="score2" name="score2"  data-options="required:true"  precision="2" min="0" max="20" style="width:100px;"/></td>

            </tr>
            <tr>
                <td rowspan="2"> 答辩情况</td>
                <td rowspan="2">答辩态度认真,能准确回答问题;<br>
                    </td>
                <td rowspan="2" align="center" width="40">20</td>
                <td  width="30">19-20</td>
                <td  width="30">17-18</td>
                <td  width="30">15-16</td>
                <td  width="30">13-14</td>
                <td  width="30"><=12</td>
            </tr>
            <tr>
                <td colspan="5">成绩三&nbsp;
                    <input  class="easyui-numberbox"  id="score3" name="score3"  data-options="required:true"  precision="2" min="0" max="20" style="width:100px;"/></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="scoreForm.submitForm()" iconCls="icon-ok">录入成绩</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="scoreForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var scoreForm = {
        submitForm: function(){
            if(!$("#scoreForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            //计算成绩
            var sum=0;
            for (var i=1;i<4;i++) {
                var a=document.getElementById("score"+i).value;
                sum+=parseFloat(a);
            }
            document.getElementById("mark3").value=sum;
            progressLoad();
            $.post($("#scoreForm").attr("action"), $("#scoreForm").serialize(), function(data){
                progressClose();
                if(data.status == 200){
                    $.messager.alert("提示", data.msg, "info", function(){
                        $("#dlg").dialog("close");
                        location.reload();
                    });
                }else{
                    $.messager.alert("提示", data.msg, "error");
                }
            });
        },
        clearForm: function(){
            $("#dlg").dialog("close");
        }
    }
</script>