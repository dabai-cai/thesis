<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-09-01
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/inc/dialog.jsp" %>
<c:set var="role" value="管理员" />
<c:if test="${type eq 'teacher'}">
    <c:set var="role" value="教师" />
</c:if>
<c:if test="${type eq 'student'}">
    <c:set var="role" value="学生" />
</c:if>
<div style="padding:10px 10px 10px 10px">
    <form id="userForm" method="post" action="${ctx}/console/arch/edit-${type}?id=${user.id}">
        <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;">${role}信息</div>
        <input type="hidden" name="id" value="${user.id}"/>
        <input type="hidden" name="userinfo.id" value="${user.info.id}"/>
        <table width="100%" cellpadding="5" class="form-table">
            <tr>
                <td><label>用户名称</label></td>
                <td><input class="easyui-textbox" type="text" name="username" value="${user.username}" data-options="required:true" style="width: 200px;" /></td>
                <td rowspan="${type eq 'admin' ? 6 : type eq 'teacher'? 10 : 12}" style="width: 200px;">
                    <table id="dg2"></table>
                    <input type="hidden" id="rids" name="rids"/>
                </td>
            </tr>
            <tr>
                <td><label>用户账号</label></td>
                <td><input class="easyui-textbox" type="text" name="account" value="${user.account}" data-options="required:true" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>用户密码</label></td>
                <td><input type="password" class="easyui-passwordbox" id="pwd" name="pwd" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>确认密码</label></td>
                <td><input type="password" class="easyui-passwordbox" name="rpassword" validType="equals['#pwd']" style="width: 200px;" /></td>
            </tr>
            <tr>
                <td><label>联系电话</label></td>
                <td><input class="easyui-textbox" type="text" name="userinfo.phone" value="${user.info.phone}" /></td>
            </tr>
            <tr>
                <td><label>电子邮件</label></td>
                <td><input class="easyui-textbox" type="text" name="userinfo.email" value="${user.info.email}" /></td>
            </tr>
            <c:if test="${type ne 'admin'}">
                <c:if test="${type eq 'teacher'}">
                    <tr>
                        <td><label>教师职称</label></td>
                        <td>
                            <select id="title" class="easyui-combobox" name="userinfo.tid" editable="false" style="width:200px;">
                                <c:forEach items="${titles}" var="t">
                                    <option value="${t.id}">${t.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td><label>用户性别</label></td>
                    <td>
                        <select id="gender" class="easyui-combobox" name="userinfo.gender" editable="false" style="width:200px;">
                            <option value="男">男</option>
                            <option value="女">女</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>即时通信号码</label></td>
                    <td><input class="easyui-textbox" type="text" name="userinfo.inscomm" value="${user.info.inscomm}" /></td>
                </tr>
                <c:if test="${type eq 'student'}">
                    <tr>
                        <td><label>专&nbsp;&nbsp;&nbsp;&nbsp;业</label></td>
                        <td><input class="easyui-textbox" type="text" name="userinfo.major" value="${user.info.major}" data-options="required:true" /></td>
                    </tr>
                    <tr>
                        <td><label>班&nbsp;&nbsp;&nbsp;&nbsp;级</label></td>
                        <td><input class="easyui-textbox" type="text" name="userinfo.clazz" value="${user.info.clazz}" data-options="required:true" /></td>
                    </tr>
                    <tr>
                        <td><label>年&nbsp;&nbsp;&nbsp;&nbsp;级</label></td>
                        <td><input class="easyui-textbox" type="text" name="userinfo.grade" value="${user.info.grade}" data-options="required:true" /></td>
                    </tr>
                </c:if>
                <tr>
                    <td><label>${type eq 'teacher' ? "办公室" : "学生宿舍"}</label></td>
                    <td><input class="easyui-textbox" type="text" name="userinfo.room" value="${user.info.room}" /></td>
                </tr>
            </c:if>
        </table>
    </form>
</div>
<div style="padding:5px;" class="dialog-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="userForm.submitForm()" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="userForm.clearForm()" iconCls="icon-cancel">取消</a>
</div>
<script type="text/javascript">
    $("#gender").combobox({onLoadSuccess: function(){
        $("#gender").combobox("select",'${user.info.gender}');
    }});
    $("#title").combobox({onLoadSuccess: function(){
        $("#title").combobox("select",'${user.info.tid}');
    }});
    dg2=$('#dg2').datagrid({
        method: "get",
        url:'${ctx}/console/role/list.json',
        fit : true,
        fitColumns : true,
        border : false,
        idField : 'id',
        rownumbers:true,
        striped:true,
        columns:[[
            {field:'id',title:'角色ID',checkbox:true,width:40},
            {field:'name',title:'角色名称',width:100},
            {field:'code',title:'角色编码', hidden:true, width:100}
        ]],
        loadFilter: function(data){
            var value = {
                total:data.total,
                rows:[]
            } ;
            for (var i = 0; i < data.rows.length; i++) {
                if(data.rows[i].code != "super"){
                    value.rows.push(data.rows[i]);
                }
            }
            return value;
        },
        dataPlain: true
    });

    var userRoleData;
    function getRole(userId){
        uid = userId;
        if(userRoleData){
            $("#dg2").datagrid("clearChecked");
        }
        $.ajax({
            type:'get',
            url:"${ctx}/console/role/listByUser.json",
            data:{uid: userId},
            success: function(data){
                userRoleData=data.rows;
                for(var i=0,j=userRoleData.length; i<j; i++){
                    $("#dg2").datagrid('selectRecord',userRoleData[i].id);
                }
            }
        });
    }

    <c:if test="${user.id ne null}">
        getRole(${user.id});
    </c:if>

    var userForm = {
        submitForm: function(){
            if(!$("#userForm").form("validate")){
                $.messager.alert("提示", "表单还未填写完成！");
                return ;
            }
            //DONE 检测有没有设置角色,并将选择的角色作为参数提交
            var sels = $("#dg2").datagrid("getChecked");
            if(sels.length == 0){
                $.messager.alert('提示','角色还未设置!');
                return ;
            }
            var rids = [];
            for(var i in sels){
                rids.push(sels[i].id);
            }
            $("#rids").val(rids);
            progressLoad();
            $.post($("#userForm").attr("action"), $("#userForm").serialize(), function(data){
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
            $("#dlg").dialog("close");
        }
    };
</script>