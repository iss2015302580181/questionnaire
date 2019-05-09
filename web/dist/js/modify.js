/**
 * Created by wzzz on 2019/4/2.
 */

var questionIndex;
$("#addWarning").hide();
$("#modifyWarning").hide();
function changeInput(index, changedValue) {
    var liId = "li_" + index;
    $("body").find("li").each(function (i, item) {
        var tempIndex = parseInt($(item).attr("id").substring(3));
        if (tempIndex > index) {
            $(item).attr("id", "li_" + (tempIndex + changedValue));
            $(item).find("input").each(function (obi, callback) {
                var tempName = $(callback).attr("name").replace(tempIndex, tempIndex + changedValue);
                $(callback).attr("name", tempName);
            })
            $(item).find("textarea").each(function (obi, callback) {
                var tempName = $(callback).attr("name").replace(tempIndex, tempIndex + changedValue);
                $(callback).attr("name", tempName);
            })
        }
    })
}
$("div").on("click",".add", preAdd);
$("div").on("click", ".modify",preModify);
$("ol").on("click", ".delete",deleteQuestion);

function submitForm() {
    var form = document.getElementById("form1");
    form.submit();
}

function changeQuestionIndex(obj) {
    questionIndex = parseInt($(obj).parent().parent().attr("id").substring(3));
}

function preAdd() {
    $("#addContent").val("")
    $("#options").empty();
    $("#addWarning").hide();
    changeQuestionIndex(this);
}

// $(".add").click(preAdd());

function preModify() {
    changeQuestionIndex(this);
    $("#modifyContent").val($("input[name=question_" + questionIndex + "_content]").val());
    $("#modifyOptions").empty();
    $("#modifyWarning").hide();
    var options = '';
    $("#li_" + questionIndex).find("input").each(function (i, item) {
        if (i > 1) {
            options += '<br><input type="text"  class="form-control" id="modify_' + i + '" value="' + $(item).val() + '">';
        }
    })
    $("#modifyOptions").append(options);
}

// $(".modify").click(preModify());

$("#addOption").click(function () {
    $("#options").append('<br><input type="text"  class="form-control" name="option">');
});

$("#addQuestion").click(function () {
    add()
});
$("#modifyQuestion").click(function () {
    modify()
});

$("#addQuestionType").change(function () {
    var questionType = $("#addQuestionType").get(0).selectedIndex;
    if (questionType == 0 || questionType == 1) {
        $("#addOption").show();
    } else {
        $("#options").empty();
        $("#addOption").hide();
    }
})

function add() {
    var flag=true;
    if( $("#addContent").val().trim()=='')
        flag=false;
    $('input[name="option"]').each(function (i, item) {
        if($(item).val().trim()==''){
            flag=false;
        }
    })
    if(!flag){
        $("#addWarning").show();
        return;
    }
    $("#myModal").modal('hide');
    changeInput(questionIndex, 1);
    var li = '';
    var index = questionIndex;
    var currentIndex = index + 1;
    var questionType = $("#addQuestionType").get(0).selectedIndex;
    var questionContent = $("#addContent").val();

    li = '<li class="list-group-item" id="li_' + currentIndex + '"><span class="quesContent">';
    li += questionContent + '</span><input type="hidden" name="question_' + currentIndex + '_type" value="' + questionType + '">' +
        '<input type="hidden" name="question_' + currentIndex + '_content" value="' + questionContent + '"><br><br>'

    if (0 == questionType || 1 == questionType) {
        $('input[name="option"]').each(function (i, item) {
            li += '<input type="hidden" name="question_' + currentIndex + '_' + i + '" value=' + $(item).val() + '>' +'<span class="option">'+ String.fromCharCode(65 + i) + '、' + $(item).val()+'</span>';
        })
    } else {
        li += '<textarea name="question_' + currentIndex + '"></textarea>';
    }
    li += '<br><div class="back"><a href="javascript:void(0)" class="add" data-toggle="modal" data-target="#myModal">添加</a>&nbsp&nbsp<a href="javascript:void(0)" class="modify" data-toggle="modal" data-target="#myModal_1">修改</a>&nbsp&nbsp<a href="javascript:void(0)" class="delete">删除</a></div><br><br>'
    $('#li_' + questionIndex).after(li);
}

function modify() {
    var flag=true;
    if( $("#modifyContent").val().trim()=='')
        flag=false;
    $('#modifyOptions').find("input").each(function (i, item) {
        if($(item).val().trim()==''){
            flag=false;
        }
    })

    if(!flag){
        $("#modifyWarning").show();
        return;
    }
    $("#myModal_1").modal('hide');
    $("#li_" + questionIndex).find("span").html($("#modifyContent").val());
    $("input[name=question_" + questionIndex + "_content]").val($("#modifyContent").val());
    $("#li_" + questionIndex).find("input").each(function (i, item) {
        if (i > 1) {
            $(item).val($("#modify_" + i).val());
        }
    })
    $("#li_" + questionIndex).find("span").each(function (i, item) {
        if (i > 0) {
            $(item).html(String.fromCharCode(64+i)+'、'+$("#modify_" + (i + 1)).val());
        }
    })
}

function deleteQuestion() {
    var flag = confirm("确认删除问题么");
    if (flag) {
        changeQuestionIndex(this);
        $("#li_" + questionIndex).remove();
        changeInput(questionIndex, -1);
    }
}
