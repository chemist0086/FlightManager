//表单内容判断
$("input.ipt-name").blur(function() {
    	if ($(this).val() == ""){
    		if ($("span.editWarningName").length == 0){
    			$("#dialogEdit").css("width", "40em");
    			$(this).after('<span class="editWarning editWarningName" >请输入姓名</span>');
    		}
    	} else {
    		$("span.editWarningName").remove();
    		if ($("span.editWarning").length == 0){
    			$("#dialogEdit").css("width", "35em");
    		}    		
    	}
});

$("input.ipt-idcard").blur(function() {
    	if ($(this).val() == ""){
    		if ($("span.editWarningIdcard").length == 0 && $("input.ipt-passport").val() == 0){
    			$("#dialogEdit").css("width", "45em");
    			$(this).after('<span class="editWarning editWarningIdcard" style="width: 10em;">请输入身份证或护照号</span>');
    		}
    	} else {
    		$("span.editWarningIdcard").remove();
    		$("span.editWarningPassport").remove();	
    	    if ($("span.editWarning").length == 0){
    			$("#dialogEdit").css("width", "35em");
    		} else if ($("span.editWarningName").length != 0 && $("span.editWarningPassport").length == 0){
    			$("#dialogEdit").css("width", "40em");
    		}
    	}
});

$("input.ipt-passport").blur(function() {
    	if ($(this).val() == ""){
    		if ($("span.editWarningPassport").length == 0 && $("input.ipt-idcard").val() == ""){
    			$("#dialogEdit").css("width", "45em");
    			$(this).after('<span class="editWarning editWarningPassport" style="width: 10em;">请输入身份证或护照号</span>');
    		}
    	} else {
    		$("span.editWarningPassport").remove();
    		$("span.editWarningIdcard").remove();
    	   	if ($("span.editWarning").length == 0){
    			$("#dialogEdit").css("width", "35em");
    		} else if ($("span.editWarningName").length != 0 && $("span.editWarningIdcard").length == 0){
    			$("#dialogEdit").css("width", "40em");
    		}
    	}
});

//点击关闭按钮刷新提示
function refreshWarning(){
	while ($("span.editWarning").length != 0){
		$("span.editWarning").remove();
		$("#dialogEdit").css("width: 35em");
    }
}