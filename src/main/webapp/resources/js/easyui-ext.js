/**
 * Created by wenpeng23 on 2016-08-14.
 */
/**
 *
 * @requires jQuery
 *
 * 当页面加载完毕关闭加载进度
 * **/
$(window).load(function(){
    $("#loading").fadeOut();
});

/**
 *
 * @requires jQuery
 *
 * 页面加载加载进度条启用
 * **/
function progressLoad(){
    $("<div class=\"datagrid-mask\" style=\"position:absolute;z-index: 9999;\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
    $("<div class=\"datagrid-mask-msg\" style=\"position:absolute;z-index: 9999;\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
}

/**
 *
 * @requires jQuery
 *
 * 页面加载加载进度条关闭
 * **/
function progressClose(){
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}



/**
 *
 * @requires jQuery
 *
 * 防止退格键导致页面回退
 */
$(document).keydown(function (e) {
    var doPrevent;
    if (e.keyCode == 8) {
        var d = e.srcElement || e.target;
        if (d.tagName.toUpperCase() == 'INPUT' || d.tagName.toUpperCase() == 'TEXTAREA') {
            doPrevent = d.readOnly || d.disabled;
        }else{
            doPrevent = true;
        }
    }else{
        doPrevent = false;
    }
    if (doPrevent)
        e.preventDefault();
});

// 密码框验证
$.extend($.fn.validatebox.defaults.rules, {
    equals: {
        validator: function(value,param){
            if($.trim($(param[0]).val()) != ""){
                return value == $(param[0]).val();
            }
            return true;
        },
        message: '两次密码输入不一致！'
    }
});
//格式化日期显示
function formatDate(val, row){
    if(val == null || val == "null"){
        return "";
    }
    var datetime = new Date();
    datetime.setTime(val);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
}

//格式化日期显示
function formatShortDate(val, row){
    if(val == null || val == "null"){
        return "";
    }
    var datetime = new Date();
    datetime.setTime(val);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "-" + month + "-" + date;
}

//让Datagrid支持子属性
function formatColumn(colName, value, row, index) {
    return eval("row."+colName);
}

//获取编辑的id
function getSelectedId(selector){
    var sels = selector.datagrid("getSelections");
    var ids = [];
    for(var i in sels){
        ids.push(sels[i].id);
    }
    return ids[0];
}

//获取需要删除的全部id
function getSelectedIds(selector){
    var sels = selector.datagrid("getSelections");
    var ids = [];
    for(var i in sels){
        ids.push(sels[i].id);
    }
    ids = ids.join(",")
    return ids;
}

function convert(rows) {
    var scope = this;
    var nodes = [];
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        //判断rows中的数据是否为父节点
        if (!exists(rows, row.pid)) {
            var data = {
                id: row.id,
                text: row.text,
                attributes: row.attributes
            };
            if (row.iconCls != "")data.iconCls = row.iconCls;
            nodes.push(data);
        }
    }
    var toDo = [];
    for (var i = 0; i < nodes.length; i++) {
        toDo.push(nodes[i]);
    }
    //查询父节点的子节点
    while (toDo.length) {
        var node = toDo.shift();
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            if (row.pid == node.id) {
                var child = {id: row.id, text: row.text, attributes: row.attributes, isLeft: true};
                if (row.iconCls != "") child.iconCls = row.iconCls;
                if (node.children) {
                    node.children.push(child);
                } else {
                    node.children = [child];
                }
                toDo.push(child);
            }
        }
    }
    return nodes;
}
//查询rows里面id是否为pid
function exists(rows, parentid) {
    for (var i = 0; i < rows.length; i++) {
        if (rows[i].id == parentid) {
            return true;
        }
    }
    return false;
}
