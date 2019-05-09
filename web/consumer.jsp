<%--
  Created by IntelliJ IDEA.
  User: wzzz
  Date: 2019/3/17
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html ng-app="myApp">
<head>
    <title>在线问卷</title>
    <link rel="stylesheet" href="dist/css/bootstrap.css">
    <link rel="stylesheet" href="dist/css/questionnaire.css">
    <script src="dist/js/jquery-3.3.1.js"></script>
    <script src="dist/js/bootstrap.js"></script>
    <script src="https://cdn.staticfile.org/angular.js/1.4.6/angular.min.js"></script>
</head>
<body ng-controller="siteCtrl">
<jsp:include page="top.jsp"></jsp:include>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-3 col-sm-3">
            <div class="groups">
                <div class="item-top">
                    我的群组
                </div>
                <c:forEach var="group" items="${requestScope.groups}">
                    <div class="group">
                        <span class="glyphicon glyphicon-link"></span>
                        <span ng-click="myDis(${group.groupId})">${group.name}</span>
                    </div>
                </c:forEach>
            </div>
            <div class="back">
                <a data-toggle="modal" data-target="#myModal" href="">
                    <span class="glyphicon glyphicon-plus"></span>
                    <span>加入群组</span>
                </a>
            </div>
        </div>
        <div class="col-md-9 col-sm-9">
            <div ng-repeat="ques in questionnaire" class="questionnaire">
                <div ng-if="ques.isPublished==0">
                    <div class="item-top-outOfDate">
                        {{ ques.queTitle}}(超出截止时间)
                    </div>
                    <div class="item-bot">

                    </div>
                </div>
                <div ng-if="ques.isPublished==1">
                    <div class="item-top">
                        {{ ques.queTitle}}
                        <div class="pull-right">
                            截止时间：{{ques.timestamp.time | date:'yyyy-MM-dd HH:mm:ss'}}
                        </div>
                    </div>
                    <div class="item-bot">
                        <div>
                            <span class="glyphicon glyphicon-file"></span>
                            <a href="/question?questionnaireId={{ques.questionnaireId}}&groupId={{groupId}}&action=fill">填写</a>
                        </div>
                    </div>
                </div>
                <div ng-if="ques.isPublished==2">
                    <div class="item-top">
                        {{ ques.queTitle}}
                    </div>
                    <div class="item-bot">
                        <div>
                            <span class="glyphicon glyphicon-file"></span>
                            <a href="/question?questionnaireId={{ques.questionnaireId}}&groupId={{groupId}}&action=check">查看</a>
                        </div>
                    </div>
                </div>
            </div>
            <div ng-if="questionnaire==null" class="noQues">
                <span>请选择一个群组查看问卷</span>
            </div>
            <div ng-if="questionnaire.length==0" class="noQues">
                <span>此群组暂无问卷</span>
            </div>
        </div>
    </div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    加入群组
                </h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
            </div>
            <div class="modal-body">
                <div>创建者:<input type="text" class="form-control" name="creatorName" ng-model="creatorName"></div>
                <div>群组名称<input type="text" class="form-control" name="groupName" ng-model="groupName"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
                <a class="btn btn-primary" ng-click="joinGroup()" data-dismiss="modal">
                    加入群组
                </a>

            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<script src="dist/js/consumer.js"></script>
</body>

</html>
