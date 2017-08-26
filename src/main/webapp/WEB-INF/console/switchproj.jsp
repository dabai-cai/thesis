<%--
  Created by IntelliJ IDEA.
  User: 大白菜
  Date: 2017/8/18 0018
  Time: 下午 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/inc/dialog.jsp" %>

<form id="myform" action="${ctx}/console/index" method="post">
    <input type="hidden" id="orgid" name="orgid" />
    <input type="hidden" id="projid" name="projid" />
    <div class="easyui-layout" data-options="fit:true" style="height: 375px">
        <div data-options="region:'center'">
            <table id="dg2"></table>
        </div>
        <div data-options="region:'south'">
            <a class="easyui-linkbutton" href="#" onclick="submitForm();" data-options="iconCls:'icon-ok'" style="width:120px">确定</a>
            <a class="easyui-linkbutton" href="#" onclick="clearForm();" data-options="iconCls:'icon-cancel'" style="width:120px">取消</a>
        </div>
    </div>

</form>
<script type="text/javascript">
    var isSuper = '${currentUser.type.ordinal() eq 0}';
    var dg2;
    var orgid = 0;
    $(function(){
        dg2=$('#dg2').datagrid({
            method: "get",
            url:'${ctx}/console/listProject.json?orgid='+${orgid},
            fit   : true,
           fitColumns : true,
           border : false,
            idField : 'id',
            animate:true,
           rownumbers:true,
          singleSelect:true,
          striped:true,
            columns:[[
                {field:'id',title:'工作ID', hidden:true, width:40},
                {field:'title',title:'论文工作',width:300}
            ]]
           ,
            dataPlain: true
        });
    });


    function orgSelected(index, row){
        orgid = row.id;
        dg2.datagrid('reload',{orgid:orgid});
    }

    function submitForm(){
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

    function clearForm() {
        $("#dlg").dialog("close");
    }
</script>
