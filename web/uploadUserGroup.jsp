<%--
  Created by IntelliJ IDEA.
  User: wzzz
  Date: 2019/3/22
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传用户</title>
    <link rel="stylesheet" href="dist/css/bootstrap.css">
    <link rel="stylesheet" href="dist/css/login.css">
    <script src="dist/js/jquery-3.3.1.js"></script>
    <script src="dist/js/bootstrap.js"></script>
</head>
<body>
<div class="container">
    <div class="row  clearfix">
        <div class="col-md-3 col-sm-3">

        </div>
        <div class="col-md-6 col-sm-6 upload">
            <form class="bs-example bs-example-form" role="form" action="/upload" enctype="multipart/form-data" method="post">
                <br>
                <span>请选择拓展名为.xlsx的文件:</span>
                <hr>
                <input type="hidden" name="action" value="userGroup">
                <input type="hidden" name="groupId" value="${param.groupId}">
                <input id="file" type="file" name="file" value="选择文件"/>
                <br>

                <input type="submit" class="btn btn-primary pull-right" value="提交">
                <br>
            </form>
        </div>
    </div>
</div>
</body>
</html>
