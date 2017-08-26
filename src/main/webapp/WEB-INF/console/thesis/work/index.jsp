<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-11-01
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>论文事务管理</title>
    <%@include file="/inc/header.jsp" %>
    <style type="text/css">
        table.mini-table{
            border-collapse: collapse;
        }
        table.mini-table>thead>tr>th, table.mini-table>tbody>tr>td{
            border: 1px solid #95B8E7;
            padding: 6px;
        }
        table.mini-table>thead>tr>th{
            background-color: #E0ECFF;
            /*background-color: #95B8E7;*/
        }
    </style>
</head>
<body class="easyui-layout">
    <div data-options="region:'west',split:true, iconCls:'myicon-information', border:false,title:'论文题目信息'" width="50%">
        <c:if test="${thesis ne null}">
            <table width="95%" cellpadding="5" class="form-table" style="margin: 20px auto;">
                <tbody>
                <tr>
                    <td width="120"><label>论文标题</label></td>
                    <td colspan="3">
                            ${thesis.topic}
                    </td>
                    <td><label>指导教师</label></td>
                    <td>
                            ${teacher.username}&nbsp;&nbsp;${teacher.info.title}
                    </td>
                </tr>
                <tr>
                    <td><label>研究方向</label></td>
                    <td>
                            ${thesis.direction}
                    </td>
                    <td width="80"><label>论文来源</label></td>
                    <td>
                            ${thesis.source}
                    </td>
                    <td width="80"><label>论文性质</label></td>
                    <td>
                            ${thesis.property}
                    </td>
                </tr>
                <tr>
                    <td><label>论文上传日期</label></td>
                    <td>
                        <fmt:formatDate value="${thesis.uploadtime}"/>
                    </td>
                </tr>
                <tr>
                    <td><label>题目简介</label></td>
                    <td colspan="5">
                            ${thesis.profile}
                    </td>
                </tr>
                </tbody>
            </table>
        </c:if>
        <div class="easyui-accordion" style="width: 95%; margin: 20px auto;">

                <div title="答辩分组信息" data-options="iconCls:'myicon-group',collapsed:false,collapsible:false" style="padding:10px; height: 150px;">
                    <table class="mini-table" style="width: 98%">
                        <thead>
                        <tr>
                            <th>答辩组长</th>
                            <th>答辩秘书</th>
                            <th>答辩时间</th>
                            <th>答辩地点</th>
                            <th>其他操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                          <td></td>
                            <td></td>
                            <td>
                                <fmt:formatDate value="${}" pattern="yyyy.MM.dd"/>
                            </td>
                            <td></td>
                            <td>
                                <a href="" class="easyui-linkbutton" iconCls="icon-download">下载分组表</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
    </div>
    </div>



    <div data-options="region:'center',split:true, iconCls:'myicon-arrow-right', border:false, title:'论文事务处理'" width="50%">
        <c:if test="${thesis ne null}">

        <div class="easyui-accordion" style="width: 95%; margin: 20px auto;">

            <div title="论文任务书" data-options="iconCls:'myicon-page',collapsed:false,collapsible:false" style="padding:10px; height: 150px;">
                <table class="mini-table" style="width: 98%">
                    <thead>
                        <tr>
                            <th>下达状态</th>
                            <th>下达日期</th>
                            <th>教师确认</th>
                            <th>学生确认</th>
                            <th>其他操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${taskbook ne null ? "已下达" : "未下达"}</td>
                            <td>
                                <fmt:formatDate value="${taskbook.cdate}" pattern="yyyy.MM.dd"/>
                            </td>
                            <td>${taskbook.tconfirm ? "已确认" : "未确认"}</td>
                            <td>
                                <c:if test="${taskbook ne null}">
                                    <c:if test="${taskbook.sconfirm}">已确认</c:if>
                                    <c:if test="${not taskbook.sconfirm}">
                                        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="return confirmTask('${taskbook.id}')">确认任务书</a>
                                    </c:if>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${taskbook ne null}">
                                    <a target="_blank" href="${ctx}/console/ttask/view?id=${taskbook.id}" class="easyui-linkbutton" iconCls="myicon-zoom">查看任务书</a>
                                </c:if>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div title="中期报告书" data-options="iconCls:'myicon-report',collapsed:false,collapsible:false" style="padding:10px; height: 150px;">
                <table class="mini-table" style="width: 98%">
                    <thead>
                    <tr>
                        <th>填写状态</th>
                        <th>检查日期</th>
                        <th>教师确认</th>
                        <th>学生确认</th>
                        <th>其他操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${midcheck ne null ? "已填写" : "未填写"}</td>
                        <td>
                            <fmt:formatDate value="${midcheck.cdate}" pattern="yyyy.MM.dd"/>
                        </td>
                        ${midchec}
                        <td>${midcheck.tconfirm ? "已确认" : "未确认"}</td>
                        <td>
                            <c:if test="${midcheck ne null}">
                                <c:if test="${midcheck.sconfirm}">已确认</c:if>
                                <c:if test="${not midcheck.sconfirm}">
                                    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="return confirmMidcheck('${midcheck.id}')">确认报告书</a>
                                </c:if>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${midcheck ne null}">
                                <a target="_blank" href="${ctx}/console/tmidcheck/view?id=${midcheck.id}" class="easyui-linkbutton" iconCls="myicon-zoom">查看报告书</a>
                            </c:if>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div title="论文上传" data-options="iconCls:'myicon-report',collapsed:false,collapsible:false" style="padding:10px; height: 150px;">
                <form method="post" id="uploadForm" action="/console/tupload/upload"  enctype="multipart/form-data">
                    <table width="100%" cellpadding="5" class="form-table">
                        <tbody>
                        <tr>
                            <td><label>论文上传格式</label></td>
                            <td colspan="5">
                                <label>${project.doctype } </label>
                            </td>
                            <td>最近上传日期</td>
                            <td>操作</td>
                        </tr>
                        <tr>
                            <td>
                                <label>选择论文文件上传</label>
                            </td>
                            <td colspan="5">
                                <input  class="easyui-filebox" name="uploadFile"  />
                            </td>
                            <td>
                                <fmt:formatDate value="${upload.mdate}" pattern="yyyy.MM.dd"/>
                            </td>
                            <td>
                                <c:if test="${upload ne null}">
                                    <a href="/console/tupload/download?thesisid=${thesis.id}" class="easyui-linkbutton" iconCls="icon-download">论文下载</a>
                                </c:if>
                                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="doUpload()" iconCls="icon-ok">上传论文</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <div title="答辩相关事务"  data-options="iconCls:'myicon-group',collapsed:false,collapsible:false" style="padding:10px;">
                <tr>
                    <c:if test="${goodDelay eq null}">
                    <td>
                            <a name="edit" href="#" onclick="edit('${goodDelay.id}','${thesis.id}',1)" class="easyui-linkbutton" iconCls="icon-add">争优申请</a>
                    </td>
                    <td>
                            <a name="edit" href="#" onclick="edit('${goodDelay.id}','${thesis.id}',-1)" class="easyui-linkbutton" iconCls="icon-add">延期申请</a>
                    </td>
                    </c:if>
                </tr>
                <table class="mini-table" style="width: 100%">
                    <thead>
                    <tr>
                        <th>申请类型</th>
                        <th>申请日期</th>
                        <th>学生确认</th>
                        <th>教师确认</th>
                        <th>院系确认</th>
                        <th>其他操作</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                            <c:if test="${goodDelay ne null}">
                               ${goodDelay.applytype eq 1 ? "争优" : "延期"}
                            </c:if>
                            </td>
                            <td>
                                <fmt:formatDate value="${goodDelay.cdate}" pattern="yyyy.MM.dd"/>
                            </td>
                            <td>
                                <c:if test="${goodDelay ne null}">
                                    ${goodDelay.studentconf eq 1 ? "已确认":"未确认"}
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${goodDelay ne null}">
                                    <c:if test="${goodDelay.teacherconf eq 1}">
                                        已确认
                                    </c:if>
                                    <c:if test="${goodDelay.teacherconf eq -1}">
                                        拒绝申请
                                    </c:if>
                                    <c:if test="${goodDelay.teacherconf eq 0}">
                                        未确认
                                    </c:if>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${goodDelay ne null}">
                                    <c:if test="${goodDelay.orgconf eq 1}">
                                        已确认
                                    </c:if>
                                    <c:if test="${goodDelay.orgconf eq -1}">
                                        拒绝申请
                                    </c:if>
                                    <c:if test="${goodDelay.orgconf eq 0}">
                                        未确认
                                    </c:if>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${goodDelay ne null}">
                                    <a  href="#" onclick="view('${goodDelay.id}')" class="easyui-linkbutton" iconCls="myicon-zoom">查看申请</a>
                                <c:if test="${goodDelay.studentconf eq 0}">
                                    <a href="#" onclick="editapply('${goodDelay.id}')" class="easyui-linkbutton" iconCls="myicon-zoom">编辑申请</a>
                                </c:if>
                                </c:if>
                            </td>
                        </tr>

                    </tbody>
                </table>
            </div>

        </div>

        </c:if>
    </div>
    <div id="dlg"></div>
    <script type="text/javascript">

        function confirmTask(taskid){
            return confirm("你确定已阅读论文任务书并进行确认？", "${ctx}/console/ttask/confirm", {"taskid":taskid});
        }

        function confirmMidcheck(midcheckid){
            return confirm("你确定已阅读论文中期检查报告书并进行确认？", "${ctx}/console/tmidcheck/confirm", {"midcheckid":midcheckid});
        }

        function edit(applyid,thesisid,applytype){
            d=$("#dlg").dialog({
                title: '申请表',
                width: 860,
                height: 500,
                href:'${ctx}/console/gooddelay/edit-studentapply?id='+applyid+'&thesisid='+thesisid+'&applytype='+applytype,
                maximizable:true,
                modal:true
            });
        }

        function view(applyid){
            d=$("#dlg").dialog({
                title: '申请表',
                width: 860,
                height: 500,
                href:'${ctx}/console/gooddelay/view?id='+applyid,
                maximizable:true,
                modal:true
            });
        }


        function confirm(confirmMsg, url, param){
            $.messager.confirm('提示', confirmMsg, function(r) {
                if (r) {
                    $.post(url, param, function(data){
                        if(data.status == 200){
                            $.messager.alert('提示', data.msg, undefined, function(){
                                location.reload();
                            });
                        }else{
                            $.messager.alert('错误', data.msg,"error");
                        }
                    });
                }
            });
            return false;
        }
        function doUpload() {
            var formData = new FormData($( "#uploadForm" )[0]);
            $.ajax({
                url: '${ctx}/console/tupload/upload?id=${upload.id}'+'&thesisid='+${thesis.id},
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if(data.status==200){
                        $.messager.alert("提示", data.msg, "info", function(){
                            location.reload();
                        });
                    }else{
                        $.messager.alert("提示错误", data.msg, "error");
                    }
                },
                error: function (data) {
                    $.messager.alert("提示", data.msg, "error");
                }
            });
        }

        function editapply(goodDlayid) {
            d=$("#dlg").dialog({
                title: '申请表',
                width: 860,
                height: 500,
                href:'${ctx}/console/gooddelay/edit-studentapply?id='+goodDlayid,
                maximizable:true,
                modal:true
            });
        }
    </script>
</body>
</html>
