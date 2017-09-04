<%--
  Created by IntelliJ IDEA.
  User: wenpeng23
  Date: 2016-08-14
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>管理控制台</title>
    <%@include file="/inc/header.jsp" %>
    <style>
        .wrap{margin:0 auto;width:960px;}
    </style>
</head>
<body class="easyui-layout">
<div id="loading" style="position: fixed;top: -50%;left: -50%;width: 200%;height: 200%;background: #fff;z-index: 100;overflow: hidden;">
    <img src="${ctx}/resources/easyui/themes/metro-blue/images/loading.gif" style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;"/>
</div>
<!-- begin of header -->
<div class="ui-header" data-options="region:'north',border:false,split:true">
    <div class="ui-header-left">
        <h1><img src="${ctx}/resources/img/logo.png" height="40px" style="vertical-align: middle;"/>毕业论文选题系统</h1>
    </div>
    <div align="center" >
        <p><strong>${currentOrg.name}</strong><a href="#" style="color: rgba(0,0,0,1)">[切换]</a><strong>${currentProj.title}</strong><a name="edit" href="#" onclick="switchProj('${currentOrg.id}')" style="color: rgba(0,0,0,1)">[切换]</a></p>
        </h2>
    </div>
    <div class="ui-header-right">
        <p><strong>${currentUser.username}</strong>，欢迎您！
            <c:if test="${currentProj ne null}">
                您正在参与
                <strong class="easyui-tooltip" title="您可以重新登录进行切换">[${currentProj.title}]</strong>
            </c:if>
            | <a href="${ctx}/logout.html">[安全退出]</a></p>
    </div>
</div>
<!-- end of header -->
<!-- begin of sidebar -->
<div class="ui-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'">
    <div class="easyui-accordion" data-options="border:false,fit:true">

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
        <div  id="console" title="首页" data-options="border:false, closable:false,cls:'pd3'">
            <div class="wrap">
                <div id="p" class="easyui-panel" title="公告" style="width: 760px ;height: 560px" data-options=" closable:false,iconCls:'icon-tip'">
                    <table>
                        <c:forEach items="${advices}" var="advice">
                            <tr>
                                <td style="width: 760px;margin: 10px" >
                                    <c:if test="${advice.top eq true}">
                                        <span style='background-color: rgb(255,0,60); padding: 4px;color:#ffffff'>置顶</span>
                                    </c:if>
                                    <a href="#" onclick="view('${advice.id}',null)" style='color:rgb(107,107,107)'>${advice.topic}</a>
                                </td>
                                <td style="float: right">
                                       <span style="float: right">
                                <fmt:formatDate value="${advice.cdate}" pattern="yyyy.MM.dd"/>
                        </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div style="text-align: right">
                        <a href="#"  onclick="more()"  data-options="iconCls:'icon-ok'"  style="color: rgb(0,0,0)">更多>></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end of main -->
<!-- begin of footer -->
<div class="ui-footer" data-options="region:'south',border:true,split:true">
    &copy; 2016 ZhouTian Tech. All Rights Reserved
</div>
<!-- end of footer -->
<div id="dlg">
</div>




<script type="text/javascript">

    $(function(){
        $('.ui-side-tree a').bind("click",function(){
            var title = $(this).text();
            var url = $(this).attr('href');
            var iconCls = $(this).attr('data-icon');
            addTab(title,url,iconCls, true);
            return false;
        });
        //转到首页
        <c:if test="${currentUser.type.ordinal() ne 0}">
        $("#home").click();
        </c:if>
    })
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
    function addTab(title, href, iconCls, iframe){
        var tabPanel = $('#ui-tabs');
        if(!tabPanel.tabs('exists',title)){
            var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:99.7%;"></iframe>';
            if(iframe){
                tabPanel.tabs('add',{
                    title:title,
                    content:content,
                    iconCls:iconCls,
                    fit:true,
                    cls:'pd3',
                    closable:true
                });
            }
            else{
                tabPanel.tabs('add',{
                    title:title,
                    href:href,
                    iconCls:iconCls,
                    fit:true,
                    cls:'pd3',
                    closable:true
                });
            }
        }
        else
        {
            tabPanel.tabs('select',title);
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
    function FlashTab(title){
        var tabPanel = $('#ui-tabs');
        var tabs=tabPanel.tabs('tabs');
        for(var i=0;i<tabs.length;i++){
            if(tabPanel.tabs('getTab',i).panel("options").title==title){
                RefreshTab(tabPanel.tabs('getTab',i))
            }
        }
    }


    function reloadTabGrid(title) {
        removeTab();
        if ($("#ui-tabs" ).tabs('exists', title)) {
            $( '#ui-tabs').tabs('select' , title);
            window.top.reload_thesis_list.call();
        }
    }

    //切换论文工作
    function switchProj(orgid) {
        d=$("#dlg").dialog({
            title: '切换论文工作',
            width: 300,
            height: 380,
            href:'${ctx}/console/switchproj?orgid='+orgid,
            maximizable:true,
            modal:true
        });
    }


    //切换组织机构
    function switchOrg(){
        d=$("#dlg").dialog({
            title: '切换组织机构',
            width: 300,
            height: 400,
            href:'${ctx}/console/switchorg',
            maximizable:true,
            modal:true
        });
    }







    /**
     *公告
     */
    function formatTopic(value, row, index){
        if(row.top==true){
            return '<a href="#" class="notselect" onclick="return view('+ row.id +',event);"><div class="myicon-zoom-in" style="width:16px;height:16px">&nbsp;&nbsp;&nbsp;&nbsp;'+"【置顶】"+value+'</div></a>';
        }else if(row.top==false){
            return '<a href="#" class="notselect" onclick="return view('+ row.id +',event);"><div class="myicon-zoom-in" style="width:16px;height:16px">&nbsp;&nbsp;&nbsp;&nbsp;'+value+'</div></a>';
        }
    }
    function formatView(val, row){
        return '<a href="#" class="notselect" onclick="return view('+ row.id +',event);"><div class="myicon-zoom-in" style="width:16px;height:16px">&nbsp;&nbsp;&nbsp;&nbsp;点击查看</div></a>';
    }
    function view(id, event){
        window.top.addTab("公告详情", '${ctx}/console/advice/view?id=' + id, null, true);
        event.stopPropagation();
        return false;
    }

    function doSearch(){
        $("#dg").datagrid("load",{
            keywords:$("#keywords").val()
        });
        return false;
    }

    /**
     *查看更多公告
     */
    function more() {
        window.top.addTab("公告", '${ctx}/console/home', null, true);
        event.stopPropagation();
        return false
    }

    $(function(){

    });


</script>
</body>
</html>
