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
        <div title="快捷菜单" data-options="iconCls:'icon-application-cascade'" style="padding:5px;">
            <ul class="easyui-tree ui-side-tree">
                <li><a href="${ctx}/console/info/edit">编辑个人资料</a></li>
                <c:if test="${currentUser.type.ordinal() eq 3}">
                    <li><a href="${ctx}/console/tapply/list">论文题目申请</a></li>
                    <li><a href="${ctx}/console/tapply/result">查看选题结果</a></li>
                    <li><a href="${ctx}/console/twork/index">论文事务管理</a></li>
                </c:if>
                <c:if test="${currentUser.type.ordinal() eq 2}">
                    <li><a href="${ctx}/console/thesis/listAll">历年题目管理</a></li>
                    <li><a href="${ctx}/console/thesis/list">题目申报管理</a></li>
                    <li><a href="${ctx}/console/treply/list">确定选题学生</a></li>
                    <li><a href="${ctx}/console/ttask/list">论文任务书</a></li>
                    <li><a href="${ctx}/console/tmidcheck/list">论文中期检查</a></li>
                    <li><a href="${ctx}/console/tscore/list1">指导教师自评</a></li>
                    <li><a href="${ctx}/console/tscore/list2">评阅教师评分</a></li>
                </c:if>
                <c:if test="${currentUser.type.ordinal() eq 1}">
                    <li><a href="${ctx}/console/project/list">论文工作管理</a></li>
                    <li><a href="${ctx}/console/project/users">参与用户管理</a></li>
                    <li><a href="${ctx}/console/tcount/list">教师出题情况</a></li>
                    <li><a href="${ctx}/console/tcheck/list">论文题目审核</a></li>
                    <li><a href="${ctx}/console/tadjust/list">学生选题调整</a></li>
                    <li><a href="${ctx}/console/tresult/list">论文选题结果</a></li>
                </c:if>
                <c:if test="${currentUser.type.ordinal() le 1}">

                    <li><a href="${ctx}/console/attr/list">基础数据管理</a></li>
                    <li><a href="${ctx}/console/arch/list-admin">管理员管理</a></li>
                    <li><a href="${ctx}/console/arch/list-teacher">教师管理</a></li>
                    <li><a href="${ctx}/console/arch/list-student">学生管理</a></li>
                </c:if>
                <c:if test="${currentUser.type.ordinal() eq 0}">
                    <li><a href="${ctx}/console/title/list">职称管理</a></li>
                    <li><a href="${ctx}/console/org/list">机构管理</a></li>
                    <li><a href="${ctx}/console/user/list">用户管理</a></li>
                    <li><a href="${ctx}/console/role/list">角色管理</a></li>
                    <li><a href="${ctx}/console/perm/list">权限管理</a></li>
                    <li><a href="${ctx}/console/resource/list">资源管理</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
<!-- end of sidebar -->
<!-- begin of main -->
<div class="ui-main" data-options="region:'center'">
    <div id="ui-tabs" class="easyui-tabs" data-options="border:false,fit:true">
        <div title="首页" data-options="border:false, closable:false,iconCls:'icon-tip',cls:'pd3'">
            <h1>管理控制台</h1>
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
    $(function(){
        $('.ui-side-tree a').bind("click",function(){
            var title = $(this).text();
            var url = $(this).attr('href');
            var iconCls = $(this).attr('data-icon');
            addTab(title,url,iconCls, true);
            return false;
        });
    })

    /**
     * Name 载入树形菜单
     */
    $('#ui-side-tree').tree({
        onClick:function(node){
            var url = node.attributes['url'];
            if(url==null || url == ""){
                return false;
            }
            else{
                addTab(node.text, url, '', true);
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



    function reloadTabGrid(title) {
        removeTab();
        if ($("#ui-tabs" ).tabs('exists', title)) {
            $( '#ui-tabs').tabs('select' , title);
            window.top.reload_thesis_list.call();
        }
    }
</script>
</body>
</html>
