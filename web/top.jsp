<%--
  Created by IntelliJ IDEA.
  User: wzzz
  Date: 2019/3/23
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="header">
    <%--<div class="pull-left">--%>
        <%--<img src="images/dandelion.png">--%>
    <%--</div>--%>
    <div class="userContent">
        <span class="glyphicon glyphicon-user"></span><span class="userName"> ${user.userName}</span>
            <a href="/login?action=logout"><span class="glyphicon glyphicon-off"></span> 登出</a>
    </div>
</div>
<br>

