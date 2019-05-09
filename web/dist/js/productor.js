/**
 * Created by wzzz on 2019/4/4.
 */
var app = angular.module('myApp', []);
app.controller('siteCtrl', function ($scope, $http, $compile,$window) {
    $scope.questionnaireId = '';
    $scope.groupId = '';
    $scope.createGroup = function () {
        $http({
            method: 'POST',
            url: '/group',
            data: {groupName: $scope.groupName, action: "create"},
        }).then(function successCallback(response) {
            if (response.data.toString().substring(0, 7) == "success") {
                var resData = response.data.toString();
                var html = '<div class="group"><span class="glyphicon glyphicon-link"></span><span ng-click="myDis(' + resData.substring(resData.indexOf("_") + 1) + ')">' + resData.substring(7, resData.indexOf("_")) + '</span></div> '
                var $html = $compile(html)($scope);
                $(".groups").append($html);
                alert("创建群组成功")
            } else
                alert("群组名重复，请更换群组名！");
        }, function errorCallback(response) {
            // 请求失败执行代码
        });
    };
    $scope.publishQues = function (questionnaireId) {
        $scope.questionnaireId = questionnaireId;
    };
    $scope.myDis = function (groupId) {
        $http({
            method: 'POST',
            url: '/questionnaire',
            data: {groupId: groupId, action: "productor"},

        }).then(function successCallback(response) {
            var questionnaires = response.data;
            $scope.questionnaire = questionnaires;
            $scope.groupId=groupId;
        }, function errorCallback(response) {
            // 请求失败执行代码
            alert("请求失败");
        });
    };

    $scope.publish = function () {
        $http({
            method: 'POST',
            url: '/questionnaire',
            data: {groupId: $scope.groupId, questionnaireId: $scope.questionnaireId, action: "publish"}
        }).then(function successCallback(response) {
            if (response.data.toString().substring(0, 7) == "success") {
                alert("发布成功");
                $window.location.reload();
            } else
                alert("此问卷已经发布到该群组,请勿重复发布");
        }, function errorCallback(response) {
            alert("发生错误请重试！");
        });
    };
    $scope.deleteQues = function (questionnaireId) {
        var flag = confirm("确认删除么?此操作将会删除所有已填写记录，请慎重考虑！");
        if (flag) {
            $http({
                method: 'POST',
                url: '/questionnaire',
                data: {groupId: 0, questionnaireId: questionnaireId, action: "delete"}
            }).then(function successCallback(response) {
                if (response.data.toString().substring(0, 7) == "success") {
                    alert("删除成功");
                    $("#questionnaire_" + questionnaireId).remove();
                }
            }, function errorCallback(response) {
                alert("error");
            });
        }
    }
});
