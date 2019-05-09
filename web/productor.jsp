<%--
  Created by IntelliJ IDEA.
  User: wzzz
  Date: 2019/3/22
  Time: 15:35
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
        <ul id="myTab" class="nav nav-tabs">
            <li class="active"><a href="#questionnaires" data-toggle="tab">
                我的问卷</a>
            </li>
            <li><a href="#groups" data-toggle="tab">我的群组</a></li>
        </ul>
        <hr>
        <div class="tab-content">
            <div class="tab-pane .fade in active" id="questionnaires">
                <div class="questionnaires">
                    <c:forEach var="questionnaire" items="${questionnaires}">
                        <div class="questionnaire" id="questionnaire_${questionnaire.questionnaireId}">
                            <div class="item-top">
                                    ${questionnaire.queTitle}
                                <div class="pull-right">
                                    截止时间：<fmt:formatDate value="${questionnaire.timestamp}"
                                                         pattern="yyyy-MM-dd HH:mm:ss"/>
                                </div>
                            </div>
                            <div class="item-bot">
                                <span class="glyphicon glyphicon-file"></span><a
                                    href="/question?questionnaireId=${questionnaire.questionnaireId}&groupId=0&action=fill">查看</a>
                                <c:if test="${questionnaire.isPublished==0}">
                                    <span class="glyphicon glyphicon-pencil"></span><a
                                        href="/question?questionnaireId=${questionnaire.questionnaireId}&groupId=0&action=modify">修改</a>
                                </c:if>
                                <c:if test="${questionnaire.isOutOfDate==0}">
                                <span class="glyphicon glyphicon-chevron-right"></span><a data-toggle="modal"
                                                                                          data-target="#myModala"
                                                                                          href=""><span
                                    ng-click="publishQues(${questionnaire.questionnaireId})">发布</span> </a>
                                </c:if>
                                <span class="glyphicon glyphicon-trash"></span><a><span
                                    ng-click="deleteQues(${questionnaire.questionnaireId})">删除</span></a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="back">
                    <a href="uploadXsl.jsp">
                        <span class="glyphicon glyphicon-plus"></span>
                        <span>制作新问卷</span>
                    </a>
                </div>
                <br><br><br><br>
            </div>
            <div class="tab-pane .fade" id="groups">
                <div class="col-md-3 col-sm-3">
                    <div class="groups">
                        <div class="item-top">
                            我的群组
                        </div>
                        <c:forEach var="group" items="${requestScope.groups}">
                            <div class="group">
                                <span class="glyphicon glyphicon-link"></span>
                                <a href="javascipt:void(0);" ng-click="myDis(${group.groupId})">${group.name}</a>
                                <a href="uploadUserGroup.jsp?groupId=${group.groupId}">分配用户到群组</a>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="back">
                        <a data-toggle="modal" data-target="#myModal" href="">
                            <span class="glyphicon glyphicon-plus"></span>
                            <span>创建群组</span>
                        </a>
                    </div>
                </div>
                <div class="col-md-9 col-sm-9">
                    <div ng-repeat="ques in questionnaire" class="questionnaire">
                        <div class="item-top">
                            {{ ques.queTitle}}
                        </div>
                        <div class="item-bot">
                            <a href="/question?questionnaireId={{ques.questionnaireId}}&groupId=0&action=fill"><span
                            >查看问卷</span></a>
                            <a href="/question?questionnaireId={{ques.questionnaireId}}&groupId={{groupId}}&action=statistic"><span
                            >查看结果</span></a>
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
    </div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    创建群组
                </h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
            </div>
            <div class="modal-body">
                <%--<div>创建者:<input type="text" class="form-control" name="creatorName" ng-model="creatorName"></div>--%>
                <div>群组名称<input type="text" class="form-control" ng-model="groupName"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
                <a class="btn btn-primary" ng-click="createGroup()" data-dismiss="modal">
                    创建群组
                </a>

            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModala" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabela">
                    发布问卷
                </h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
            </div>
            <div class="modal-body">
                <form action="/questionnaire">
                    请选择发布群组:
                    <div>
                        <select ng-model="groupId" class="form-control">
                            <option value="">请选择</option>
                            <c:forEach var="group" items="${groups}">
                                <option value="${group.groupId}">${group.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
                <a class="btn btn-primary" ng-click="publish()" data-dismiss="modal">
                    发布问卷
                </a>

            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
<script src="dist/js/productor.js"></script>
</html>

