<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-09-15
  Time: 00:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>论文题目审核</title>
    <%@include file="/inc/header.jsp" %>
</head>
<body>
<div id="ui-toolbar">
    <div class="ui-toolbar-search">
        <select id="checked" class="easyui-combobox" pageHeight="auto" editable="false" style="width: 250px;">
            <option value="">审核状态</option>
            <option value="true">已审核</option>
            <option value="false">未审核</option>
        </select>
        <label>教师姓名：</label><input class="wu-text easyui-textbox" id="keywords" style="width:200px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">开始检索</a>
    </div>
    <div class="ui-toolbar-button">
        <a href="#" class="easyui-linkbutton easyui-tooltip" title="点击将审核符合查询条件下的所有论文题目" iconCls="icon-ok" onclick="checkQuery(true)">审核所有</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="checkSelect(true)">审核选择</a>
        <a href="#" class="easyui-linkbutton easyui-tooltip" title="点击将回退符合查询条件下的所有论文题目" iconCls="icon-no" onclick="checkQuery(false)">回退所有</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-undo" onclick="checkSelect(false)">回退选择</a>
    </div>
</div>
<table id="dg" class="easyui-datagrid"
       data-options="url: '${ctx}/console/tcheck/list.json',
                        method: 'get',
                        idField: 'id',
                        toolbar: '#ui-toolbar',
                        fit:true,
                        fitColumns:true,
                        pagination:true,
                        rownumbers:true,
                        pageNumber:1,
                        pageSize : 20,
                        pageList : [ 10, 20, 30, 40, 50 ],
                        singleSelect:false">
    <thead>
    <tr>
        <th data-options="field:'id', checkbox:true", width="100">ID</th>
        <th data-options="field:'view', formatter: formatView", width="100">查看详细</th>
        <th data-options="field:'teacher'" width="100">指导教师</th>
        <th data-options="field:'topic'" width="300">论文题目</th>
        <th data-options="field:'direction'" width="100">研究方向</th>
        <th data-options="field:'source'" width="80">论文来源</th>
        <th data-options="field:'property'" width="80">论文性质</th>
        <th data-options="field:'check', formatter:formatCheck" width="80">审核状态</th>
        <th data-options="field:'studentid', formatter:formatState" width="80">是否被选择</th>
    </tr>
    </thead>
</table>
<script>
    function doSearch(){
        $("#dg").datagrid("load",{
            checked:$("#checked").combobox("getValue"),
            keywords:$("#keywords").val()
        });
        return false;
    }

    function formatView(val, row){
        return '<a href="#" class="notselect" onclick="return view('+ row.id +',event);"><div class="myicon-zoom-in" style="width:16px;height:16px">&nbsp;&nbsp;&nbsp;&nbsp;点击查看</div></a>';
    }
    function formatState(val, row){
        if(val || val > 0){
            return "<span style='background-color: #009cff; padding: 4px;'>已选择</span>";
        }else{
            return "<span style='background-color: #efff00;padding: 4px;'>未选择</span>";
        }
    }

    function formatCheck(val, row){
        if(val || val == "true"){
            return "<span style='background-color: #00ff32;padding: 4px;'>已审核</span>";
        }else{
            return "<span style='background-color: #ff032c; padding: 4px;'>未审核</span>";
        }
    }

    function view(id, event){
        window.top.addTab("论文题目详情", '${ctx}/console/thesis/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }

    $(function(){
        //console.log($("a.notselect"));
        $(document).on('click',"a.notselect",function(e){
            //如果提供了事件对象，则这是一个非IE浏览器
            if (e && e.stopPropagation){
                //因此它支持W3C的stopPropagation()方法
                e.stopPropagation();
                e.stopImmediatePropagation();
                alert( e.isImmediatePropagationStopped() );
            }
            else{
                //否则，我们需要使用IE的方式来取消事件冒泡
                window.event.cancelBubble = true;
            }
        });
    });

    function checkQuery(isCheck){
        var params = {
            checked:$("#checked").combobox("getValue"),
            keywords:$("#keywords").val(),
            isCheck:isCheck
        };
        $.messager.confirm('确认','确定要' + (isCheck?"审核通过":"回退") + '符合查询条件的所有论文题目？',function(r){
            if (r){
                $.post("${ctx}/console/tcheck/saveQuery", params, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示', data.msg, undefined, function(){
                            $("#dg").datagrid("reload");
                        });
                    }else{
                        $.messager.alert('错误', data.msg);
                    }
                });
            }
        });
        return false;
    }

    function checkSelect(isCheck){
        var ids = getSelectedIds($("#dg"));
        if(ids.length == 0){
            $.messager.alert('提示','未选中要' + (isCheck?"审核通过":"回退") + '的论文题目!');
            return ;
        }

        $.messager.confirm('确认','确定要' + (isCheck?"审核通过":"回退") + '选择的论文题目吗？',function(r){
            if (r){
                var params = {"ids":ids, "isCheck":isCheck};
                $.post("${ctx}/console/tcheck/saveSelect", params, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示', data.msg, undefined, function(){
                            $("#dg").datagrid("reload");
                        });
                    }else{
                        $.messager.alert('错误', data.msg);
                    }
                });
            }
        });
        return false;
    }

</script>
</body>
</html>
