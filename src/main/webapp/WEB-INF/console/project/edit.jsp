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
<%@include file="/inc/dialog.jsp" %>
<div style="padding:10px 10px 10px 10px">
    <form id="projectForm" method="post" action="${ctx}/console/project/edit?id=${project.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">论文工作信息</div>
        <input type="hidden" name="id" value="${project.id}"/>
        <table width="100%" cellpadding="5" class="form-table">
            <tbody>
                <tr>
                    <td><label>论文标题</label></td>
                    <td><input type="text" class="easyui-textbox" id="title" name="title" value="${project.title}" data-options="required:true, width:250" /></td>
                    <td><label for="year">工作年份</label></td>
                    <td><input type="text" id="year" name="year" class="easyui-numberbox" value="${project.year}" data-options="required:true, min:2010, max:2030" /></td>
                    <td>
                        <label>工作状态</label>
                    </td>
                    <td>
                        <input class="easyui-switchbutton" id="close" name="close" value="0" ${project.close ? "" : "checked"} data-options="onText:'开启',offText:'关闭'" />
                        <input type="hidden" name="close" value="1">
                    </td>
                </tr>
                <tr>
                    <td><label for="startdate">开始日期</label></td>
                    <td><input type="text" id="startdate" name="startdate" class="easyui-datebox" value="<fmt:formatDate value="${project.startdate}" pattern="yyyy-MM-dd" />" data-options="required:true"></td>
                    <td><label for="enddate">结束日期</label></td>
                    <td><input type="text" id="enddate" name="enddate" class="easyui-datebox" value="<fmt:formatDate value="${project.enddate}" pattern="yyyy-MM-dd" />" data-options="required:true" /></td>
                    <td><label for="midcheckdate">中期检查日期</label></td>
                    <td><input type="text" id="midcheckdate" name="midcheckdate" class="easyui-datebox" value="<fmt:formatDate value="${project.midcheckdate}" pattern="yyyy-MM-dd" />" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td><label for="submitdate">上传论文日期</label></td>
                    <td><input type="text" id="submitdate" name="submitdate" class="easyui-datebox" value="<fmt:formatDate value="${project.submitdate}" pattern="yyyy-MM-dd" />" data-options="required:true" /></td>
                    <td><label for="defensedate">答辩日期</label></td>
                    <td><input type="text" id="defensedate" name="defensedate" class="easyui-datebox" value="<fmt:formatDate value="${project.defensedate}" pattern="yyyy-MM-dd" />" data-options="required:true" /></td>
                    <td><label for="doctype">上传论文的类型</label></td>
                    <td>
                        <input class="easyui-textbox" id="doctype" name="doctype" value="${project.doctype}" data-options="required:true" />
                        <br><span class="hint">使用#隔开的多个文件扩展，如：doc#docx#pdf</span>
                    </td>
                </tr>
                <tr>
                    <td><label for="setallowed">允许教师出题</label></td>
                    <td>
                        <input id="setallowed" name="setallowed" class="easyui-switchbutton" value="1" ${project.setallowed ? "checked" : ""}
                               data-options="onText:'是',offText:'否'">
                        <input type="hidden" name="setallowed" value="0">
                    </td>
                    <td><label for="getallowed">允许学生选题</label></td>
                    <td>
                        <input id="getallowed" name="getallowed" class="easyui-switchbutton" value="1" ${project.getallowed ? "checked" : ""}
                               data-options="onText:'是',offText:'否'">
                        <input type="hidden" name="getallowed" value="0">
                    </td>
                    <td><label for="postallowed">允许提交论文</label></td>
                    <td>
                        <input id="postallowed" name="postallowed" class="easyui-switchbutton" value="1" ${project.postallowed ? "checked" : ""}
                               data-options="onText:'是',offText:'否'">
                        <input type="hidden" name="postallowed" value="0">
                    </td>
                </tr>
                <tr>
                    <td><label for="mark1allowed">允许指导教师评分</label></td>
                    <td>
                        <input id="mark1allowed" name="mark1allowed" class="easyui-switchbutton" value="1" ${project.mark1allowed ? "checked" : ""}
                               data-options="onText:'是',offText:'否'">
                        <input type="hidden" name="mark1allowed" value="0">
                    </td>
                    <td><label for="mark2allowed">允许评阅教师评分</label></td>
                    <td>
                        <input id="mark2allowed" name="mark2allowed" class="easyui-switchbutton" value="1" ${project.mark2allowed ? "checked" : ""}
                               data-options="onText:'是',offText:'否'">
                        <input type="hidden" name="mark2allowed" value="0">
                    </td>
                    <td><label for="mark3allowed">允许登记答辩分数</label></td>
                    <td>
                        <input id="mark3allowed" name="mark3allowed" class="easyui-switchbutton" value="1" ${project.mark3allowed ? "checked" : ""}
                               data-options="onText:'是',offText:'否'">
                        <input type="hidden" name="mark3allowed" value="0">
                    </td>
                </tr>
                <tr>
                    <td><label for="setmax">教师最大出题数</label></td>
                    <td><input type="text" id="setmax" name="setmax" class="easyui-numberbox" value="${project.setmax}" data-options="required:true, min:0, max:100"></td>
                    <td><label for="setmin">教师最小出题数</label></td>
                    <td>
                        <input class="easyui-numberbox" id="setmin" name="setmin" value="${project.setmin}" data-options="required:true, min:0, max:100" />
                    </td>
                    <td><label for="getmax">学生最大选题数</label></td>
                    <td>
                        <input class="easyui-numberbox" id="getmax" name="getmax" value="${project.getmax}" data-options="required:true, min:0, max:100">
                    </td>
                </tr>
                <tr>
                    <td><label for="gmpercent">指导教师分数比例</label></td>
                    <td class="higher"><input class="easyui-slider" id="gmpercent" name="gmpercent"
                               value="${project.gmpercent}"
                               data-options="showTip:true, width:250,
                               min:0, max:100, step: 1,
                               rule: [0,'|',25,'|',50,'|',75,'|',100]" />
                    </td>
                    <td><label for="rmpercent">评阅教师分数比例</label></td>
                    <td class="higher">
                        <input class="easyui-slider" id="rmpercent" name="rmpercent"
                               value="${project.rmpercent}"
                               data-options="showTip:true, width:250,
                               min:0, max:100, step: 1,
                               rule: [0,'|',25,'|',50,'|',75,'|',100]" />
                    </td>
                    <td><label for="ampercent">答辩分数比例</label></td>
                    <td class="higher">
                        <input class="easyui-slider" id="ampercent" name="ampercent"
                               value="${project.ampercent}"
                               data-options="showTip:true, width:250,
                                   min:0, max:100, step: 1,
                                   rule: [0,'|',25,'|',50,'|',75,'|',100]" />
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="projectForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="projectForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    var projectForm = {
        submitForm: function(){
            if(!$("#projectForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#projectForm").attr("action"), $("#projectForm").serialize(), function(data){
                progressClose();
                if(data.status == 200){
                    $.messager.alert("提示", data.msg, "info", function(){
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    });
                }else{
                    $.messager.alert("提示", data.msg);
                }
            });
        },
        clearForm: function(){
            $(".panel-tool-close").click();
        }
    };
</script>
