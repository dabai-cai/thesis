<%@ page import="cn.zttek.thesis.modules.model.Resource" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: 大白菜
  Date: 2017/9/1 0001
  Time: 上午 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>管理控制台</title>
    <style>
        td{
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .iconfont{
            background-image: none!important;
        }
        span.iconfont{
            margin-right: 6px!important;
        }
    </style>
</head>
<body class="easyui-layout">
<div id="loading" style="position: fixed;top: -50%;left: -50%;width: 200%;height: 200%;background: #fff;z-index: 100;overflow: hidden;">
    <img src="${ctx}/static/easyui/themes/metro/images/loading.gif" style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;"/>
</div>
<!-- begin of header -->
<div class="ui-header" data-options="region:'north',border:false,split:true">
    <div class="ui-header-left">
        <h1><img src="${ctx}/static/img/logo.png" height="50px" style="vertical-align: middle; margin-top: -15px;"/></h1>
    </div>
    <div class="ui-header-right">

    </div>
</div>
<!-- end of header -->
<!-- begin of sidebar -->
<div class="ui-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'">
    <div class="easyui-accordion" data-options="border:false,fit:true" >
        <c:forEach items="${menuList}" var="menu">
            <div title="${menu.name}" data-options="iconCls:'icon-application-cascade'"  style="padding:5px;" >
                <ul class="ui-side-tree">
                    <c:forEach items="${menu.sub}" var="subMenu">
                        <c:if test="${subMenu.sub.size() eq 0}">
                            <li ><a href="${ctx}${subMenu.url}">${subMenu.name}</a></li>
                        </c:if>
                        <c:if test="${subMenu.sub.size() ne 0}">
                            <li>
                                <span>${subMenu.name}</span>
                                <ul>
                                    <c:forEach items="${subMenu.sub}" var="sub">
                                        <li ><a href="${ctx}${sub.url}">${sub.name}</a></li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:if>
                    </c:forEach>

                </ul>
            </div>
        </c:forEach>

    </div>
</div>
<!-- end of sidebar -->
<!-- begin of main -->
<div class="ui-main" data-options="region:'center'">
    <div id="ui-tabs" class="easyui-tabs" data-options="border:false,fit:true">
        <div title="首页" data-options="border:false, closable:false,iconCls:'iconfont iconfont-all',href:'${ctx}/console/index'">

        </div>
    </div>
</div>
<!-- end of main -->
<!-- begin of footer -->
<div class="ui-footer" data-options="region:'south',border:true,split:true">
    &copy; 2016 ZhouTian Tech. All Rights Reserved
</div>
<!-- end of footer -->
<script type="text/javascript">


    /**
     * Name 载入树形菜单
     */
    var trees = $('.ui-side-tree').tree({
        onClick:function(node){
            if($('.ui-side-tree').tree('isLeaf', node.target)) {
                console.log(node.target);
                var adom = node.text;
                var url = $(adom).attr("href");
                var title = $(adom).text();
                var iconClass = node.iconCls;
                if (url == null || url == "" || url == undefined) {
                    return false;
                }
                else {
                    addTab(title, url, iconClass, true);
                }
            }else {
                $('.ui-side-tree').tree('toggle', node.target);
            }
        }
    });

    $(function(){
        $('.ui-side-tree a').on("click",function(){
            var target = $(this).closest(".tree-node")[0];
            var iconCls = "";
            for(var i in trees){
                var node = $(trees[i]).tree('getNode', target);
                if(node != null && node != undefined){
                    iconCls = node.iconCls;
                    break;
                }
            }
            var title = $(this).text();
            var url = $(this).attr('href');
            addTab(title,url, true);
            return false;
        });
    });

    //刷新当前标签Tabs
    function RefreshTab(currentTab) {
        currentTab.find("iframe")[0].contentWindow.location.reload();
    }

    /**
     * Name 选项卡初始化
     */
    var tabs = $('#ui-tabs').tabs({
        fit : true,
        border : false,
        tools:[{
            iconCls:'icon-reload',
            border:false,
            handler:function(){
                var tab = $('#ui-tabs').tabs('getSelected');
                RefreshTab(tab);
            }
        }]
    });

    /**
     * Name 添加菜单选项
     * Param title 名称
     * Param href 链接
     * Param iconCls 图标样式
     * Param iframe 链接跳转方式（true为iframe，false为href）
     */
    function addTab(title, href,  iframe){
        var tabPanel = $('#ui-tabs');
        if(!tabPanel.tabs('exists',title)){
            var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:99.0%;"></iframe>';
            if(iframe){
                tabPanel.tabs('add',{
                    title:title,
                    content:content,
                    fit:true,
                    closable:true
                });
            }
            else{
                tabPanel.tabs('add',{
                    title:title,
                    href:href,

                    fit:true,
                    cls:'pd3',
                    closable:true
                });
            }
        }
        else
        {
            tabPanel.tabs('select',title);
            var currentTab = tabPanel.tabs('getTab', title);
            currentTab.find("iframe")[0].contentWindow.location.href= href;
        }
    }
    /**
     * Name 移除菜单选项
     */
    function removeTab(){
        var tabPanel = $('#ui-tabs');
        var tab = tabPanel.tabs('getSelected');
        if (tab){
            var index = tabPanel.tabs('getTabIndex', tab);
            tabPanel.tabs('close', index);
        }
    }



    function reloadTabGrid(title) {
        removeTab();
        if ($("#ui-tabs" ).tabs('exists', title)) {
            $( '#ui-tabs').tabs('select' , title);
            window.top.reload_hse_list.call();
        }
    }
</script>
</body>
</html>
