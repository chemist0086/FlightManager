$("#dialogPasswd #editInfos .btn input").click(function() {
	if ($("span.editWarning").length != 0){
		alert("两次密码输入不一致！");
	} else {
        $.ajax({
            url: 'changePassword.action',
            type: 'post',
            async: false,
            dataType: 'json',
            data: {
            	newPassword: $("#dialogAdd .editInfos").children(":eq(0)").find("input").val(),      
            },
            success: function(data, status) {
              if(data.status=="1"){
                alert("密码修改成功！");
                //清空表格     	
                	var selectCount = 0;
            		var inputCount = ":eq("+selectCount+")";
            		var COLUM = 2;
                	while (selectCount < COLUM){
                		inputCount = ":eq("+selectCount+")";
                		$("#dialogPasswd .editInfos").children(inputCount).find("input").val();
                		selectCount++;
                	}       	       	
                window.location.reload();
              }
              if(data.status=="0")
              alert("添加失败！");
            },
            error: function(){
              alert("网络故障");
            }
          });
	}   	
});