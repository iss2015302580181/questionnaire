<%--
  Created by IntelliJ IDEA.
  User: wzzz
  Date: 2019/3/21
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>问卷统计</title>
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
            <div class="row clearfix">
                <h1>
                    《${questionnaire.queTitle}》
                </h1>
                <p>
                    截止时间：<fmt:formatDate value="${questionnaire.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </p>
                <p align="center">
                    ${questionnaire.queDis}
                </p>
            </div>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <ol class="list-group">
                        <c:forEach var="question" items="${questions}">
                            <li class="list-group-item"><span class="quesContent">${question.content}</span>
                                <br><br>
                                <c:choose>
                                    <c:when test="${question.type == 0}">
                                        <c:forEach var="option" items="${question.options}" varStatus="status1">
                                            <input type="radio"
                                                   name="question_${question.questionId}"
                                                   value="${fn:substring(option.questionOption, 2, -1)}">
                                            ${option.questionOption}
                                        </c:forEach>
                                    </c:when>
                                    <c:when test="${question.type == 1}">
                                        <c:forEach var="option" items="${question.options}" varStatus="status1">
                                            <input type="checkbox"
                                                   name="question_${question.questionId}"
                                                   value="${fn:substring(option.questionOption, 2, -1)}">
                                            ${option.questionOption}
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <textarea name="question_${question.questionId}"></textarea>
                                    </c:otherwise>
                                </c:choose>

                                <a href="javascipt:void(0);" class="pull-right"
                                   onclick="getStatistic(${groupId},${questionnaire.questionnaireId},${question.type},${question.questionId},this)">查看统计结果</a>

                            </li>
                        </c:forEach>
                    </ol>
                    <div class="back">
                        <a href="/consumer?action=productor"><span style="font-size: 20px">返回</span> </a>
                    </div>
                    <br><br><br><br><br><br>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script src="dist/js/statistic.js"></script>



