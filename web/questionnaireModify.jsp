<%--
  Created by IntelliJ IDEA.
  User: wzzz
  Date: 2019/3/22
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <title>制作问卷</title>
    <link rel="stylesheet" href="dist/css/bootstrap.css">
    <link rel="stylesheet" href="dist/css/questionnaire.css">
    <script src="dist/js/jquery-3.3.1.js"></script>
    <script src="dist/js/bootstrap.js"></script>
</head>
<body>
<jsp:include page="top.jsp"></jsp:include>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <h1>
                《${questionnaire.queTitle}》
            </h1>
            <br>
            <p>
                截止时间：<fmt:formatDate value="${questionnaire.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </p>
            <p>
                ${questionnaire.queDis}
            </p>
            <br>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <form action="/createQuestionnaire" id="form1" method="post">
                <input type="hidden" name="questionnaireId" value="${questionnaire.questionnaireId}">
                <input type="hidden" name="queTitle" value="${questionnaire.queTitle}">
                <input type="hidden" name="queDis" value="${questionnaire.queDis}">
                <input type="hidden" name="queDeadline" value="${questionnaire.timestamp}">
                <ol class="list-group">
                    <c:forEach var="question" items="${questions}" varStatus="status">
                        <li class="list-group-item" id="li_${status.index}"><span
                                class="quesContent">${question.content}</span>
                            <input type="hidden" name="question_${status.index}_type" value="${question.type}">
                            <input type="hidden" name="question_${status.index}_content"
                                   value="${question.content}">
                            <br><br>
                            <c:choose>
                                <c:when test="${question.type == 0 || question.type == 1}">
                                    <c:forEach var="option" items="${question.options}" varStatus="status1">
                                        <input type="hidden"
                                               name="question_${status.index}_${status1.index}"
                                               value="${fn:substring(option.questionOption, 2, -1)}">
                                        <span class="option"> ${option.questionOption}</span>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <textarea name="question_${status.index}"></textarea>
                                </c:otherwise>
                            </c:choose>
                            <br>
                            <div class="back">
                                <a href="javascript:void(0)" class="add" data-toggle="modal"
                                   data-target="#myModal">添加</a>&nbsp&nbsp<a href="javascript:void(0)"
                                                                             class="modify" data-toggle="modal"
                                                                             data-target="#myModal_1">修改</a>&nbsp&nbsp<a
                                    href="javascript:void(0)" class="delete">删除</a>
                            </div>

                            <br><br>
                        </li>
                    </c:forEach>
                </ol>
                <br>
                <div class="back">
                    <a href="javascript:void(0)" onclick="submitForm()"><span>提交问卷</span></a>&nbsp&nbsp
                    <a href="uploadXsl.jsp"><span>返回</span> </a>
                </div>
                <br><br><br><br><br><br>
            </form>
        </div>
    </div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    添加问题
                </h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
            </div>
            <div class="modal-body">
                <label>题干</label>
                <input type="text" class="form-control" id="addContent">
                <label>题目类型</label>
                <select class="form-control" id="addQuestionType">
                    <option>单选</option>
                    <option>多选</option>
                    <option>问答</option>
                </select>
                <div id="options">

                </div>
                <div class="addEle" id="addOption">
                    <span class="glyphicon glyphicon-plus"></span>
                    <a href="javascript:void(0)" >添加选项</a>
                </div>
                <div class="myWarning" id="addWarning">
                    <span>不能包含未填写字段</span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="addQuestion">
                    提交
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal_1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel_1">
                    修改问题
                </h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
            </div>
            <div class="modal-body">
                <label>题干</label>
                <input type="text" class="form-control" id="modifyContent">
                <div id="modifyOptions">

                </div>
                <div class="myWarning" id="modifyWarning">
                    <span>不能包含未填写字段</span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="modifyQuestion">
                    提交
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>
<script src="dist/js/modify.js"></script>