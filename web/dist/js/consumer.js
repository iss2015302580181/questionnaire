/**
 * Created by wzzz on 2019/4/5.
 */
var app = angular.module('myApp', []);
app.controller('siteCtrl', function ($scope, $http, $compile) {
    $scope.joinGroup = function () {
        $http({
            method: 'POST',
            url: '/group',
            data: {creatorName: $scope.creatorName, "groupName": $scope.groupName, action: "join"},
        }).then(function successCallback(response) {
            if (response.data.toString().substring(0, 7) == "success") {
                var resData = response.data.toString();
                var html = '<div class="group"><span class="glyphicon glyphicon-link"></span><span ng-click="myDis(' + resData.substring(resData.indexOf("_") + 1) + ')">' + resData.substring(7, resData.indexOf("_")) + '</span></div> '
                var $html = $compile(html)($scope);
                $(".groups").append($html);
                alert("加入群组成功")
            } else
                alert("创建者名或群组名错误，请核对后重试");
        }, function errorCallback(response) {
            alert("请求失败");
        });
    };

    $scope.myDis = function (groupId) {
        $http({
            method: 'POST',
            url: '/questionnaire',
            data: {groupId: groupId, action: "consumer"},
        }).then(function successCallback(response) {
            var questionnaires = response.data;
            $scope.questionnaire = questionnaires;
            $scope.groupId=groupId;
        }, function errorCallback(response) {
            // 请求失败执行代码
            alert("请求失败");
        });
    };
});