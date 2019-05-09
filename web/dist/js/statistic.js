/**
 * Created by wzzz on 2019/4/5.
 */
var ocanvas;
var mycanvas;
function getStatistic(groupId, questionnaireId, type, questionId, item) {
    $("#statistic").remove();
    $.ajax({
        type: "post",
        url: "/statistic",
        data: JSON.stringify({
            groupId: groupId,
            questionnaireId: questionnaireId,
            type: type,
            questionId: questionId
        }),
        contentType: "json/application",
        dataType: "json",
        success: function (data) {
            // console.log(data[0]);
            if (data[0]['total'] == 0) {
                $(item).parent().after('<div class="canvasDiv" style="height: 600px" id="statistic"><canvas id="canvas" width="1000" height="600"></canvas></div>');
                ocanvas = document.getElementById("canvas");
                mycanvas = ocanvas.getContext("2d");
                mycanvas.font = "40px Arial";
                write("此问题暂无统计数据", 300, 300);
            } else if (type == 0 || type == 1) {
                $(item).parent().after('<div class="list-group-item" style="height: 600px" id="statistic"><canvas id="canvas" width="1000" height="600"></canvas></div>');
                ocanvas = document.getElementById("canvas");
                mycanvas = ocanvas.getContext("2d");
                mycanvas.font = "18px Arial";
                var total=data[0]['total'];
                var numberOfOption=$(item).parent().find('input').length;
                write("共有" + total + "人作答", 300, 40);

                line(300, 80, 300, 480);
                line(900, 80, 900, 480);
                var ceil = Math.ceil(total / 5) * 5;

                for (var i = 0; i < 6; i++) {
                    line(300, 80 + i * 80, 900, 80 + i * 80);
                    write(ceil - i / 5 * ceil, 280, 80 + i * 80)
                }

                var gap = 600 / numberOfOption / 5;
                var width = 3 * gap;

                for (var i = 1; i <= numberOfOption; i++) {
                    var height;
                    if(null!=data[0][String.fromCharCode(64+i)])
                        height = data[0][String.fromCharCode(64+i)] / ceil * 400;
                    else
                        height=0;
                    var X = 300 + gap + (i - 1) * (width + 2 * gap);
                    var Y = 480 - height;
                    rect(X, Y, width, height);
                    write(String.fromCharCode(64 + i), 300 + 2.5 * gap + (i - 1) * (width + 2 * gap), 500)
                }
            } else if (type == 2) {
                var str = '<div class="canvasDiv" style="height: 600px" id="statistic">共有' + data.length + '人作答</div>';
                for (var i = 0; i < data.length; i++) {
                    str += data[i] + '<br>'
                }
                str += '</li>';
                $(item).parent().after(str);
            }
        }
    });
}
//第一先定义一个画线的函数方法    画两条线
function line(aX, aY, bX, bY) {//开始和结束的横坐标  开始和结束的纵坐标
    mycanvas.beginPath();
    mycanvas.moveTo(aX, aY);
    mycanvas.lineTo(bX, bY);
    mycanvas.stroke();
}

//第三定义一个矩形的函数方法
function rect(X, Y, width, height) {
    mycanvas.beginPath();
    mycanvas.fillStyle = "#abcdef";
    mycanvas.rect(X, Y, width, height);
    mycanvas.fill();
    mycanvas.closePath()
}

//添加字
function write(start, ox, oy) {
    mycanvas.beginPath();
    mycanvas.fillStyle = "black";
    mycanvas.fillText(start, ox, oy);
    mycanvas.closePath();
}