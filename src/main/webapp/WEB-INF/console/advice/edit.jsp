<%--
  Created by IntelliJ IDEA.
  User: Mankind
  Date: 2017/8/11
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/inc/dialog.jsp" %>
<div style="padding:10px 10px 10px 10px">
    <form id="adviceForm" method="post" action="${ctx}/console/advice/edit?id=${advice.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">公告信息</div>
        <input type="hidden" name="id" value="${advice.id}"/>
        <table width="100%" cellpadding="5">
            <tr>
                <td><label>公告标题</label></td>
                <td>
                    <input class="easyui-textbox" type="text" name="topic" value="${advice.topic}"
                           data-options="required:true" style="width: 200px;"/>
                    <%--${advice.id ne null && advice.id gt 0 ? "readonly='true'" : ""}--%>
                </td>
            </tr>
            <tr>
                <td><label>公告目标对象</label></td>
                <td>
                    <select id="target" name="target" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">
                        <c:forEach items="${types}" var="type">
                            <c:choose>
                                <c:when test="${type.ordinal() eq 0}">
                                    <c:if test="${currentUser.type.ordinal() eq 0}">
                                        <option value="${type.ordinal()}" ${advice.target ne null && type.ordinal() eq advice.target.ordinal() ? "selected='selected'" : ""}>${type.label}</option>
                            </c:if>
                        </c:when>
                                <c:otherwise>
                                    <option value="${type.ordinal()}" ${advice.target ne null && type.ordinal() eq advice.target.ordinal() ? "selected='selected'" : ""}>${type.label}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label>是否置顶</label></td>
                <td>
                    <select id="top" name="top" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">
                        <option value="false" ${advice.top == false?"selected='selected'" : ""}>否</option>
                        <option value="true" ${advice.top == true?"selected='selected'" : ""}>是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label>发布状态</label></td>
                <td>
                    <select id="valid" name="valid" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 150px;">
                        <option value="false" ${advice.valid == false?"selected='selected'" : ""}>否</option>
                        <option value="true" ${advice.valid == true?"selected='selected'" : ""}>是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td valign="top"><label>公告正文</label></td>
                <td>
                    <textarea id="content" name="content" >${advice.content}</textarea>
                    <%--<input class="easyui-textbox" data-options="multiline:true, required:true" id="content" name="content" value="${advice.content}"  style="width:100%;height:120px" />--%>
                </td>
            </tr>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="adviceForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="adviceForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script src="${ctx}/resources/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
    var adviceForm = {
        submitForm: function(){
            if(!$("#adviceForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            progressLoad();
            $.post($("#adviceForm").attr("action"), $("#adviceForm").serialize(), function(data){
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
    var ckeditor = CKEDITOR.replace('content', {
        language: 'zh-cn',
        width: '99%',
        height: '135',
        toolbar: 'Basic'
    });
    ckeditor.on('change', function (evt) {
//            console.log(evt.editor.getData());
        $("#content").val(evt.editor.getData());
    });
</script>