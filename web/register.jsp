<%--
  Created by IntelliJ IDEA.
  User: wzzz
  Date: 2019/3/17
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="myApp">
<head>
    <title>注册页面</title>
    <link rel="stylesheet" href="dist/css/bootstrap.css">
    <link rel="stylesheet" href="dist/css/login.css">
    <script src="dist/js/jquery-3.3.1.js"></script>
    <script src="dist/js/bootstrap.js"></script>
    <script src="https://cdn.staticfile.org/angular.js/1.4.6/angular.min.js"></script>
</head>
<body ng-controller="siteCtrl">
<div class="container">
    <div class="row  clearfix">
        <div class="col-md-3 col-sm-3">

        </div>
        <div class="col-md-6 col-sm-6 upload">
            <form class="bs-example bs-example-form" role="form" action="<%=request.getContextPath()%>/login"
                  name="form"
                  method="post">
                <br>
                <span>用户注册</span>
                <hr>
                <input class="form-control" type="text" name="userName" ng-model="userName" required
                       ng-pattern="/^([\u4e00-\u9fa5]|\w){6,8}$/" ng-change="validateUserName()">
                <span style="color:red" ng-show="form.userName.$dirty && form.userName.$invalid">请输入6-8位字母数字中文或它们的组合
                    <%--<span ng-show="form.userName.$error.required">用户名是必须的。</span>--%>
                </span>
                <span class="warning" ng-show="repeat&&!form.userName.$invalid">用户名重复</span>
                <br><br><br>
                <input class="form-control" type="password" name="userPsd" ng-model="userPsd" required ng-pattern="/^\w{6,8}$/">
                <span style="color:red" ng-show="form.userPsd.$dirty && form.userPsd.$invalid">请输入6-8位字母数字或它们的组合
                    <%--<span ng-show="form.userPsd.$error.required">密码是必须的。</span>--%>
                </span>
                <br><br>
                <input type="hidden" name="action" value="register">
                <input type="submit" class="btn btn-primary pull-right" ng-disabled="repeat || form.userName.$invalid ||
                form.userPsd.$invalid " value="提交">
                <br>
            </form>
        </div>
    </div>
</div>
</body>
<script>
    var app = angular.module('myApp', []);
    app.controller('siteCtrl', function ($scope, $http) {
        $scope.repeat = false;
        $scope.validateUserName = function () {
            $http({
                method: 'POST',
                url: '/login',
                data: {userName: $scope.userName, action: "validate"},
            }).then(function successCallback(response) {
                if (response.data.toString().substring(0, 7) == "success") {
                    $scope.repeat = false;
                } else {
                    $scope.repeat = true;
                }
            }, function errorCallback(response) {
                // 请求失败执行代码
            });
        };
    });
</script>
</html>
