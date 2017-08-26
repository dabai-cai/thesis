<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-09-22
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/inc/dialog.jsp" %>
<div style="padding:10px 10px 10px 10px">
    <form id="scoreForm" method="post" action="${ctx}/console/tscore/edit?id=${score.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">评阅教师评分</div>
        <input type="hidden" name="id" value="${score.id}"/>
        <input type="hidden" name="thesisid" value="${thesis.id}"/>
        <table width="100%" cellpadding="5" class="form-table" style="margin:auto;">
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
                <td><label>评阅教师批阅成绩</label></td>
                <td>
                    <span  precision="2" min="0" max="100" required="true" value="${score.mark2}" style="width:100px;" >${score.mark2}</span>
                </td>
                <td><label>评分日期</label></td>
                <td><input type="text" id="mark2date" name="mark2date" class="easyui-datebox" value="<fmt:formatDate value="${score.mark2date}" pattern="yyyy-MM-dd" />" data-options="required:true" /></td>
            </tr>
            <tr>
              <table width="100%"  class="form-table" >
                  <tbody>
                   <tr>
                       <td rowspan="10">成绩评定标准</td>
                       <td colspan="3" align="center" >评分项目</td>
                       <td  align="center" width="30">分值</td>
                       <td  align="center" width="30">得分</td>
                   </tr>
                   <tr>
                       <td rowspan="3" width="30">选题质量20%</td>
                       <td align="right">1</td>
                       <td align="center" >专业培养目标</td>
                       <td align="right">5</td>
                       <td > <input class="easyui-numberbox" id="score1" name="score1"  required="true"  precision="2" min="0" max="5" >
                       </td>
                   </tr>
                   <tr><td>2</td>
                       <td  align="center">课题难易度与工作量</td>
                       <td align="center">10</td>
                       <td><input class="easyui-numberbox"  id="score2" name="score2"   data-options="required:true"  precision="2" min="0" max="10" /></td>
                   </tr>
                   <tr><td>3</td>
                       <td align="center">理论意义或生产实践意义</td>
                       <td align="center">5</td>
                       <td><input class="easyui-numberbox" id="score3" name="score3"   data-options="required:true"   precision="2" min="0" max="5"/></td>
                   </tr>
                   <tr>
                       <td width="30" rowspan="4" >能力水平40%</td>
                       <td align="right">4</td>
                       <td  align="center">查询文献资料与综合运用知识能力</td>
                       <td align="right">10</td>
                       <td><input class="easyui-numberbox"   id="score4" name="score4"   data-options="required:true"  precision="2" min="0" max="10"/></td>

                   </tr>
                   <tr> <td>5</td>
                        <td align="center" >研究方案的设计能力</td>
                       <td align="center">10</td>
                       <td><input class="easyui-numberbox"  id="score5" name="score5"   data-options="required:true"  precision="2" min="0" max="10"/></td>
                   </tr>
                   <tr> <td>6</td>
                       <td align="center" >研究方法和手段的运用能力</td>
                       <td align="center">10</td>
                       <td><input  class="easyui-numberbox"  id="score6" name="score6"  data-options="required:true"  precision="2" min="0" max="10"/></td>
                   </tr>
                   <tr> <td>7</td>
                       <td align="center" >外文应用能力</td>
                       <td align="center">10</td>
                       <td><input  class="easyui-numberbox"  id="score7" name="score7"  data-options="required:true"  precision="2" min="0" max="10" /></td>

                   </tr>
                   <tr>
                       <td rowspan="2" width="30" >成果质量40%</td>
                       <td align="right">8</td>
                       <td align="center" > 写作水平与写作规范</td>
                       <td align="right">20</td>
                       <td><input class="easyui-numberbox" id="score8" name="score8"   data-options="required:true"  precision="2" min="0" max="20"/></td>
                   </tr>
                   <tr>
                       <td>9</td>
                       <td align="center" >研究结果的理论或实际应用价值</td>
                       <td align="center">20</td>
                       <td><input class="easyui-numberbox" id="score9"   name="score9"  data-options="required:true"  precision="2" min="0" max="20"/></td>
                   </tr>
                  <tr>
                      <td><label>评阅教师评语</label></td>
                      <input type="hidden" id="mark2" name="mark2">
                      <td colspan="5">
                          <input class="easyui-textbox" data-options="multiline:true, required:true" id="comment2" name="comment2" value="${score.comment2}"  style="width:100%;height:140px" />
                      </td>
                  </tr>
                  </tbody>
              </table>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="scoreForm.submitForm()" iconCls="icon-ok">评定</a>
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
            for (var i=1;i<10;i++) {
                var a=document.getElementById("score"+i).value;
                sum+=parseFloat(a);
            }
            document.getElementById("mark2").value=sum;
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