<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-09-02
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="role" value="教师" />
<c:if test="${type eq 'student'}">
    <c:set var="role" value="学生" />
</c:if>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>批量添加${role}</title>
    <%@include file="/inc/header.jsp" %>
    <link href="${ctx}/resources/codemirror/codemirror.css" rel="stylesheet" />
    <script type="text/javascript" src="${ctx}/resources/codemirror/codemirror.js"></script>
    <style type="text/css">
        .CodeMirror {
            border: 1px solid #eee;
            height: 500px;
            width: 80%;
        }
    </style>
</head>
<body class="easyui-layout">
    <div data-options="region:'center',split:true, border:false,title:'批量添加'">
        <form id="batchForm" action="${ctx}/console/arch/batch-${type}" method="post">
            <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc; padding-bottom: 5px;"></div>
            <table width="100%" cellpadding="5" class="form-table">
                <tbody>
                    <tr>
                        <td>选择角色</td>
                        <td>
                            <select id="rids" class="easyui-combogrid" name="rids" style="width:200px" data-options="
                                panelWidth: 200,
                                required:true,
                                multiple: true,
                                idField: 'id',
                                textField: 'name',
                                url: '${ctx}/console/role/list.json',
                                method: 'get',
                                columns: [[
                                    {field:'id',title:'角色ID',checkbox:true,width:40},
                                    {field:'name',title:'角色名称',width:100},
                                    {field:'code',title:'角色编码',width:100}
                                ]],
                                loadFilter: loadFilter,
                                fitColumns: true
                            ">
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>输入提示</td>
                        <td>
                            <p>在下面的输入框中，请按照指定格式粘贴${role}信息，以空格分隔字段:&nbsp;&nbsp;&nbsp;&nbsp;
                                <c:if test="${type eq 'teacher'}">
                                    <strong>工号&nbsp;&nbsp;&nbsp;&nbsp;姓名&nbsp;&nbsp;&nbsp;&nbsp;职称</strong>
                                </c:if>
                                <c:if test="${type eq 'student'}">
                                    <strong>学号&nbsp;&nbsp;&nbsp;&nbsp;姓名&nbsp;&nbsp;&nbsp;&nbsp;专业&nbsp;&nbsp;&nbsp;&nbsp;年级&nbsp;&nbsp;&nbsp;&nbsp;班级</strong>
                                </c:if>
                            </p>

                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <%--<input class="easyui-textbox" name="data" data-options="multiline:true" style="width: 80%; height: 500px; overflow: auto;" />--%>
                            <textarea id="data" name="data" style="width: 80%; height: 500px; overflow: auto;"></textarea>
                            <script>
                                var editor = CodeMirror.fromTextArea(document.getElementById("data"), {  // 标识到textarea
                                    value : "##",  // 文本域默认显示的文本
                                    mode : "text/html",  // 模式
                                    // theme : "",  // CSS样式选择
                                    indentUnit : 0,  // 缩进单位，默认2
                                    smartIndent : false,  // 是否智能缩进
                                    tabSize : 4,  // Tab缩进，默认4
                                    readOnly : false,  // 是否只读，默认false
                                    showCursorWhenSelecting : true,
                                    lineNumbers : true  // 是否显示行号
                                    // .. 还有好多，翻译不完。需要的去看http://codemirror.net/doc/manual.html#config
                                });
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="batchForm.submitForm()" iconCls="icon-ok">保存</a>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="batchForm.clearForm()" iconCls="icon-cancel">重置</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </div>

    <div data-options="region:'east',split:true,border:false,title:'操作结果'" style="width: 425px">
        <div id="p" class="easyui-panel" title="信息" style="width:415px;height:100%;padding:10px;">
            <ul>
            </ul>
        </div>
    </div>
    <script type="text/javascript">
        function loadFilter(data){
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
        }

        var batchForm = {
            submitForm: function(){
                if(!$("#batchForm").form("validate")){
                    $.messager.alert("提示", "表单还未填写完成！");
                    return ;
                }
                var str = editor.getValue();
                if($.trim(str) === ""){
                    $.messager.alert("提示", "请先粘贴数据！");
                    return;
                }
                $("#data").val(str);
                progressLoad();
                $.post($("#batchForm").attr("action"), $("#batchForm").serialize(), function(data){
                    progressClose();
                    if(data.status == 200){
                        $.messager.alert("提示", data.msg, null, function(){
                            $.each(data.data, function(i, item){
                                $("<li class='info' style='display: none;'>" + item + "</li>").appendTo("#p ul").slideDown("slow");
                            });
                        });
                    }else{
                        $.messager.alert("提示", data.msg, null, function(){
                            $.each(data.data, function(i, item){
                                $("<li class='error' style='display: none;'>" + item + "</li>").appendTo("#p ul").slideDown("slow");
                            });
                        });

                    }
                });
            },
            clearForm: function(){
                //$("#rids").combogrid("clear");
                $("#batchForm").form("reset");
                editor.setValue("");
                $("#data").val("");
            }
        };
    </script>
</body>
</html>
