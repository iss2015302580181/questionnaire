<%--
  Created by IntelliJ IDEA.
  User: wzzz
  Date: 2019/3/13
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
    <link rel="stylesheet" href="dist/css/bootstrap.css">
    <link rel="stylesheet" href="dist/css/login.css">
    <script src="dist/js/jquery-3.3.1.js"></script>
    <script src="dist/js/bootstrap.js"></script>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-5 col-sm-5">
            <img src="images/timg.jpg"/>
        </div>
        <div class="col-md-7 col-sm-7">
            <div>
                <form class="bs-example bs-example-form" role="form" action="/login" method="post">
                    <input type="hidden" name="action" value="login">
                    <h1 align="center">用户名/密码登录</h1>
                    <br>
                    <br>
                    <div>
                        <input type="text" class="form-control" name="userName" placeholder="username">
                    </div>
                    <br>
                    <div>
                        <input type="password" class="form-control" name="userPsd" placeholder="password">
                    </div>
                    <br>
                    <div>
                        <input type="submit" class="btn btn-primary" id="login" value="登录">
                    </div>
                </form>
            </div>
            <a href="/register.jsp">点我注册~~</a>
        </div>
    </div>
</div>
</body>
</html>
