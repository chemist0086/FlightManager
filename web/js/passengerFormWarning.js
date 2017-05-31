//编辑、修改表单内容判断
$("input.ipt-id").blur(function () {
    if ($(this).val() == "") {
        if ($("span.editWarningId").length == 0) {
            if ($("span.editWarningIdcard").length == 0) {
                $("#dialogEdit, #dialogAdd").css("width", "40em");
            }
            $(this).after('<span class="editWarning editWarningId" >请输入编号</span>');
        }
    } else {
        $("span.editWarningId").remove();
        if ($("span.editWarning").length == 0) {
            $("#dialogEdit, #dialogAdd").css("width", "35em");
        }
    }
});

$("input.ipt-name").blur(function () {
    if ($(this).val() == "") {
        if ($("span.editWarningName").length == 0) {
            if ($("span.editWarningIdcard").length == 0) {
                $("#dialogEdit, #dialogAdd").css("width", "40em");
            }
            $(this).after('<span class="editWarning editWarningName" >请输入姓名</span>');
        }
    } else {
        $("span.editWarningName").remove();
        if ($("span.editWarning").length == 0) {
            $("#dialogEdit, #dialogAdd").css("width", "35em");
        }
    }
});

$("input.ipt-idcard").blur(function () {
    if ($(this).val() == "" && $("input.ipt-passport").val() == "") {
        if ($("span.editWarningIdcard").length == 0 && $("input.ipt-passport").val() == "") {
            $("#dialogEdit, #dialogAdd").css("width", "45em");
            $(this).after('<span class="editWarning editWarningIdcard" style="width: 10em;">请输入身份证或护照号</span>');
        }
    } else {
        $("span.editWarningIdcard").remove();
        $("span.editWarningPassport").remove();
        if ($("span.editWarning").length == 0) {
            $("#dialogEdit, #dialogAdd").css("width", "35em");
        } else if ($("span.editWarningName").length != 0 && $("span.editWarningPassport").length == 0) {
            $("#dialogEdit, #dialogAdd").css("width", "40em");
        }
    }
});

$("input.ipt-passport").blur(function () {
    if ($(this).val() == "" && $("input.ipt-idcard").val() == "") {
        if ($("span.editWarningPassport").length == 0 && $("input.ipt-idcard").val() == "") {
            $("#dialogEdit, #dialogAdd").css("width", "45em");
            $(this).after('<span class="editWarning editWarningPassport" style="width: 10em;">请输入身份证或护照号</span>');
        }
    } else {
        $("span.editWarningPassport").remove();
        $("span.editWarningIdcard").remove();
        if ($("span.editWarning").length == 0) {
            $("#dialogEdit, #dialogAdd").css("width", "35em");
        } else if ($("span.editWarningName").length != 0 && $("span.editWarningIdcard").length == 0) {
            $("#dialogEdit, #dialogAdd").css("width", "40em");
        }
    }
});
//点击关闭按钮刷新提示
$(function () {
    $("#dialogEdit .claseDialogBtn, #dialogAdd .claseDialogBtn, #dialogPassWd .claseDialogBtn").click(function () {
        while ($("span.editWarning").length != 0) {
            $("span.editWarning").remove();
            $("#dialogEdit, #dialogAdd, #dialogPasswd").css("width", "35em");
        }
    });
})

//修改密码表单内容判断
$(".ipt-new").blur(function () {
    if ($(this).val() == "") {
        if ($("span.editWarningName").length == 0) {
            if ($("span.editWarningBlank").length == 0) {
                $("#dialogPasswd").css("width", "40em");
            }
            $(this).after('<span class="editWarning editWarningBlank" >请输入新密码</span>');
        }
    }
})
$(".ipt-confirm").blur(function () {
    if ($(this).val() != $(".ipt-new").val()) {
        if ($("span.editWarningName").length == 0) {
            if ($("span.editWarningBlank").length == 0) {
                $("#dialogPasswd").css("width", "40em");
            }
            $(this).after('<span class="editWarning editWarningBlank" >输入不一致</span>');
        }
    } else {
        while ($("span.editWarning").length != 0) {
            $("span.editWarning").remove();
            $("#dialogEdit, #dialogAdd, #dialogPasswd").css("width", "35em");
        }
    }
})
