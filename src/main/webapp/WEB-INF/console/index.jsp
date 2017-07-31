<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-08-14
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>选择组织机构和论文工作</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<form id="myform" action="${ctx}/console/index" method="post">
    <input type="hidden" id="orgid" name="orgid" />
    <input type="hidden" id="projid" name="projid" />
    <div id="win" class="easyui-window" title="选择组织机构和论文工作" style="width:500px;height:395px;padding:5px;"
         data-options="iconCls:'icon-save', modal:true, closed:true,maximizable:false,minimizable:false, collapsible:false, closable: false">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'">
                <table id="dg" class="easyui-datagrid"
                       data-options="
                                idField: 'id',
                                fit:true,
                                fitColumns:true,
                                rownumbers:true,
                                singleSelect:true,
                                onSelect: orgSelected">
                    <thead>
                    <tr>
                        <th field="id" width="50" hidden="true">ID</th>
                        <th field="name" width="200">组织机构</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${orgs}" var="org">
                        <tr>
                            <td>${org.id}</td>
                            <td>${org.name}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div data-options="region:'east',split:true" style="width: 250px;">
                <table id="dg2"></table>
            </div>
            <div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 0;">
                <a class="easyui-linkbutton" href="#" onclick="submitForm();" data-options="iconCls:'icon-ok'" style="width:80px">确定</a>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    var isSuper = '${currentUser.type.ordinal() eq 0}';
    var dg2;
    var orgid = 0;
    $(function(){
        $("#win").window("open");

        dg2=$('#dg2').datagrid({
            method: "get",
            url:'${ctx}/console/listProject.json',
            queryParams:{orgid: orgid},
            fit : true,
            fitColumns : true,
            border : false,
            idField : 'id',
            animate:true,
            rownumbers:true,
            singleSelect:true,
            striped:true,
            columns:[[
                {field:'id',title:'工作ID', hidden:true, width:40},
                {field:'title',title:'论文工作',width:300},
            ]],
            dataPlain: true
        });
    });


    function orgSelected(index, row){
        orgid = row.id;
        dg2.datagrid('reload',{orgid:orgid});
    }

    function submitForm(){
        var orgid = getSelectedId($("#dg"));
        if(!orgid && isSuper=='false'){
            $.messager.alert("错误提示信息","请选择组织机构！");
            return false;
        }else{
            $("#orgid").val(orgid);
        }
        var projid = getSelectedId($("#dg2"));
        if(!projid && isSuper=='false'){
            $.messager.confirm('确认','您当前未选择论文工作，进入系统后将只能修改个人资料，不能开展论文工作，是否继续？',function(r){
                if (r){
                    $("#projid").val(projid);
                    $("#myform").submit();
                }else{
                    return false;
                }
            });
        }else{
            $("#projid").val(projid);
            $("#myform").submit();
        }
        return false;
    }
</script>
</body>
</html>
