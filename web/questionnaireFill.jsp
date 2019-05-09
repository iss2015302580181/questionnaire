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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
    <title>问卷</title>
    <link rel="stylesheet" href="dist/css/bootstrap.css">
    <link rel="stylesheet" href="dist/css/questionnaire.css">
    <script src="dist/js/jquery-3.3.1.js"></script>
    <script src="dist/js/bootstrap.js"></script>
    <script src="https://cdn.staticfile.org/angular.js/1.4.6/angular.min.js"></script>
</head>
<body>
<jsp:include page="top.jsp"></jsp:include>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <h1>
                《${questionnaire.queTitle}》
            </h1>
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
            <form action="/answer" id="form1" method="post">
                <input type="hidden" name="groupId" value="${groupId}">
                <input type="hidden" name="questionnaireId" value="${questionnaire.questionnaireId}">
                <ol class="list-group">
                    <c:forEach var="question" items="${questions}">
                        <li class="list-group-item"><span class="quesContent">${question.content}</span>
                            <br><br>
                            <c:choose>
                                <c:when test="${question.type == 0}">
                                    <c:forEach var="option" items="${question.options}" varStatus="status1">
                                        <input type="radio"
                                               name="question_${question.questionId}"
                                        value="${fn:substring(option.questionOption, 0, 1)}">
                                        ${option.questionOption}
                                    </c:forEach>
                                </c:when>
                                <c:when test="${question.type == 1}">
                                    <c:forEach var="option" items="${question.options}" varStatus="status1">
                                        <input type="checkbox"
                                               name="question_${question.questionId}"
                                               value="${fn:substring(option.questionOption, 0, 1)}">
                                        ${option.questionOption}
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <textarea name="question_${question.questionId}"></textarea>
                                </c:otherwise>
                            </c:choose>
                            <br><br>
                        </li>
                    </c:forEach>
                </ol>
                <br>
                <div class="back">
                    <c:if test="${user.type==2}">
                        <a onclick="mySubmit()"><span>提交</span> </a>
                    </c:if>
                    <c:if test="${user.type==1}">
                        <a href="/consumer?action=productor"><span>返回</span> </a>
                    </c:if>
                </div>
                <br><br><br><br><br><br>
            </form>
        </div>
    </div>
</div>
</body>
</html>
<script>
    function mySubmit() {

        var flag=true;
        $("input:radio").each(function (i,item) {
                if($("input:radio[name="+$(item).attr('name')+"]:checked").length==0){
                    console.log(i);
                    flag=false;
                }
            })
        $("input:checkbox").each(function (i,item) {
            if($("input:checkbox[name="+$(item).attr('name')+"]:checked").length==0){
                console.log(i);
                flag=false;
            }
        })
        $("textarea").each(function (i,item) {
            if($(item).val().trim()==''){
                flag=false;
                console.log(i);
            }

        })
        if(!flag){
            alert("请填写所有问题");
            return;
        }
        var form = document.getElementById("form1");
        var flag = confirm("提交后无法更改，确认提交么？");
        if (flag == true)
            form.submit();
    }
</script>

