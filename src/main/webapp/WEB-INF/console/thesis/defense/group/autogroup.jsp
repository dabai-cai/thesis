<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/24
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/inc/dialog.jsp" %>
<div id="p" class="easyui-panel" title="自动分组提示" style="padding:5px;">
    当前答辩任务总组数为XX组，其中争优组有XX组，总参加学生人数XX人，其中争优学生XX人
</div>
<div style="padding:10px 10px 10px 10px">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">自动分组参数</div>
    <form id="autogroupForm" method="post" action="${ctx}/console/thesis/defense/group/autogroup?taskid=${defenseTask.id}">
    <table width="100%" cellpadding="5" class="form-table">
            <tbody>
                <tr>
                    <input type="hidden" name="taskid" value="${defenseTask.id}"/>
                </tr>
                <tr>
                    <td><label>组长级别要求</label></td>
                    <td>
                        <select id="leader-title" name="leaderLevel" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 250px;">
                            <option value="-1">请选择组长等级</option>
                            <c:forEach items="${titles}" var="title">
                                <option value="${title.ordinal()}">${title.label}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>秘书级别要求</label></td>
                    <td>
                        <select id="secretary-title" name="secretaryLevel" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 250px;">
                            <option value="-1">请选择秘书等级</option>
                            <c:forEach items="${titles}" var="title">
                                <option value="${title.ordinal()}">${title.label}</option>
                            </c:forEach>
                        </select>
                    </td>

                </tr>
                <tr>
                    <td><label>分组类型</label></td>
                    <td>
                        <select id="grouptype" name="grouptype" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 250px;">
                            <option value="0">正常答辩</option>
                            <option value="2">争优答辩</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>答辩时间</label></td>
                    <td>
                        <input  class="easyui-datebox" id="defensetime" name="defensetime" value="${defenseTask.defensetime}" style="width: 250px;" disabled/>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="attrForm.submitForm()" iconCls="icon-ok">开始分组</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="attrForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var attrForm = {
        submitForm: function(){
            $.messager.confirm("确认","自动分组将导致对应分组类型的已经存在的分组被删除，分组后的答辩地点需要手动编辑，是否确认？",function(r){
                if(r){
                    if(!$("#autogroupForm").form("validate")){
                        $.messager.alert("提示", "表单还未填写完成！");
                        return ;
                    }
                    progressLoad();
                    $.post($("#autogroupForm").attr("action"), $("#autogroupForm").serialize(), function(data){
                        progressClose();
                        if(data.status == 200){
                            $.messager.alert("提示", data.msg, "info", function(){
                                $("#dlg").dialog("close");
                                $("#dg").datagrid("reload");
                            });
                        }else{
                            $.messager.alert("提示", data.msg, "error");
                        }
                    });
                }
            })
        },
        clearForm: function(){
            $("#dlg").dialog("close");
        }
    };
    $(function(){
        <%--alert('${defenseTask.defensetime}');--%>
        <%--$("#defensetime").datebox('setValue', '2017-06-05');--%>
    })
</script>

