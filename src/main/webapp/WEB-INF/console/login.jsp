<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8"/>
    <title>毕业论文选题系统</title>
    <script type="text/javascript" src="${ctx}/resources/easyui/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/login.css" />
</head>
<body>
<div>
    <form id="loginForm" action="${ctx}/console/login.html" method="post">
        <input type="hidden" name="loginPath" value="${loginPath}">
        <div class="login_top">
            <div class="login_title">
                毕业论文选题系统登录页
            </div>
        </div>
        <div style="float:left;width:100%;">
            <div class="login_main">
                <div class="login_main_top"></div>
                <div class="login_main_errortip">&nbsp;${message}</div>
                <div class="login_main_ln">
                    <input type="text" id="account" name="account" value="admin" required/>
                </div>
                <div class="login_main_pw">
                    <input type="password" id="password" name="password" value="123456" required />
                </div>
                <div class="login_main_yzm">
                    <div>
                        <input type="text" id="code" name="code" value="11" required />
                        <img id="img_captcha" src="${ctx}/console/code.html" alt="验证码图片" style="height:45px;width:115px;float:right;margin-right:58px;cursor: pointer;"/>
                    </div>
                </div>
                <div class="login_main_submit">
                    <button onclick=""></button>
                </div>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    document.addEventListener("readystatechange", function () {
        if (document.readyState === "complete") {
            /**
             * retrieve the auth code image
             */
            (function () {
                document.getElementById("img_captcha").addEventListener("click", function () {
                    this.src = getAuthCodeSrc(this.src);
                });

                function getAuthCodeSrc(url) {
                    var index = url.indexOf("?");
                    if (index > 0) {
                        url = url.substring(0, index);
                    }
                    url += "?time=" + Date.now();
                    return url;
                }
            })();
        }
    });
</script>

<script type="text/javascript">
//    window.onload = function(){
//        document.getElementById("loginForm").submit();
//    }
</script>
</body>
</html>
