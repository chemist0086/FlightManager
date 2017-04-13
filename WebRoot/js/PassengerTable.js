var table = document.querySelector('table');
    table.GM({
    	supportDrag: false
      ,supportRemind: false
      ,gridManagerName: 'test'
  //      ,disableCache:true
      ,isCombSorting: true
      ,height: 'auto'
      ,supportAjaxPage:true
      ,supportSorting: true
      /*,ajax_url: 'http://www.lovejavascript.com/learnLinkManager/getLearnLinkList'*/
      ,ajax_url: 'http://127.0.0.1:8080/myWork-05/generatePassengerData.action'
  //    ,ajax_url: 'http://127.0.0.1:1314/learnLinkManager/getLearnLinkList'
  //      ,ajax_headers: {'header-test': 'baukh'}
      ,ajax_type: 'POST'
      ,ajax_headers: {"Content-Type":"application/x-www-form-urlencoded" }
      ,query: {pluginId: 1}
      ,pageSize: 20
      ,sizeData: [5, 10, 15, 20]
      ,columnData: [{
          key: 'pass_id',
          width: '80px',
          text: '客户编号',
          sorting: ''
        },{
          key: 'pass_name',
          width: '80px',
          text: '客户姓名'
        },{
          key: 'pass_age',
          width: "60px",
          text: '年龄'
        },{
          key: 'pass_sex',
          width: "60px",
          text: '性别',
        },{
          key: 'pass_idcard',
          width: "150px",
          text: '身份证号',
        },{
          key: 'pass_passport',
          text: '护照编号',
        }, {
          key: 'pass_phone',
          text: '联系方式',
        }, {
          key: 'pass_email',
          text: 'E-mail'
        }, {
          key: 'pass_count',
          width: "60px",
          text: '购票数量'
        }, {
          key: 'pass_amount',
          width: "80px",
          text: '购票金额'
        }, {
          key: 'action',
          width: "180px",
          text: '操作',
          template: function(action, rowObject){
              return '<span class="plugin-action edit-action" learnLink-id="'+rowObject.id+'" style="margin-right: 1em;"><a href="javascript:;" class="bounceInDownEdit editTable">编辑</a></span>' + 
              '<span class="plugin-action add-action" learnLink-id="'+rowObject.id+'"><a href="javascript:;" class="bounceInDownAddOrder addOrder">添加订单</a></span>';
          }
        }
      ]
      // 分页前事件
      ,pagingBefore: function(query){
        console.log('pagingBefore', query);
      }
      // 分页后事件
      ,pagingAfter: function(data){
        console.log('pagingAfter', data);
      }
      // 排序前事件
      ,sortingBefore: function (data) {
    	  	var _query = {
    	  			pass_id: document.querySelector('[name="pass_id"]').value,
    	  			pass_name: document.querySelector('[name="pass_name"]').value,
    	  			pass_age: document.querySelector('[name="pass_age"]').value,
    	  			pass_sex: document.querySelector('[name="pass_sex"]').value,
    	  			pass_idcard: document.querySelector('[name="pass_idcard"]').value,
    	  			pass_passport: document.querySelector('[name="pass_passport"]').value,
    	  			pass_phone: document.querySelector('[name="pass_phone"]').value,
    	  			pass_email: document.querySelector('[name="pass_email"]').value,
    	  			cPageEmpty: 1
        	      };
    	  	table.GM('setQuery', _query).GM('refreshGrid', function () {
    	  		console.log('搜索成功...');
        });
        console.log('sortBefore', data);
      }
      // 排序后事件
      ,sortingAfter: function (data) {
        console.log('sortAfter', data);
      }
      // 宽度调整前事件
      ,adjustBefore: function (event) {
        console.log('adjustBefore', event);
      }
      // 宽度调整后事件
      ,adjustAfter: function (event) {
        console.log('adjustAfter', event);
      }
      // 拖拽前事件
      ,dragBefore: function (event) {
        console.log('dragBefore', event);
      }
      // 拖拽后事件
      ,dragAfter: function (event) {
        console.log('dragAfter', event);
      }
    });

    // 日期格式化,不是插件的代码,只用于处理时间格式化
    Date.prototype.format = function(fmt){
      var o = {
        "M+": this.getMonth() + 1, //月份
        "D+": this.getDate(), //日
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
      };
      if (/([Y,y]+)/.test(fmt)){
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
      }
      for (var k in o){
        if(new RegExp("(" + k + ")").test(fmt)){
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
      }
      return fmt;
    };

    // 绑定搜索事件
    document.querySelector('.search-action').addEventListener('click', function () {
    	if ($("table tbody tr td div").text() == "数据为空"){
    		window.location.reload();
    	} else {    	
    	    var _query = {
    	    		pass_id: document.querySelector('[name="pass_id"]').value,
    	    	    pass_name: document.querySelector('[name="pass_name"]').value,
    	    	    pass_age: document.querySelector('[name="pass_age"]').value,
    	    	    pass_sex: document.querySelector('[name="pass_sex"]').value,
    	    	    pass_idcard: document.querySelector('[name="pass_idcard"]').value,
    	    	    pass_passport: document.querySelector('[name="pass_passport"]').value,
    	    	    pass_phone: document.querySelector('[name="pass_phone"]').value,
    	    	    pass_email: document.querySelector('[name="pass_email"]').value,
    	    	    cPageEmpty: 1
    	    };
	      table.GM('setQuery', _query).GM('refreshGrid', function () {
	    	  console.log('搜索成功...');
	      });
    	}
    });

    // 绑定重置
    document.querySelector('.reset-action').addEventListener('click', function () {
    	document.querySelector('[name="pass_id"]').value = '';
    	document.querySelector('[name="pass_name"]').value = '';
    	document.querySelector('[name="pass_age"]').value = '';
    	document.querySelector('[name="pass_sex"]').value = '';
    	document.querySelector('[name="pass_idcard"]').value = '';
    	document.querySelector('[name="pass_passport"]').value = '';
    	document.querySelector('[name="pass_phone"]').value = '';
    	document.querySelector('[name="pass_email"]').value = '';
    });
            
    //删除/添加/编辑乘机人
    $(function() {
    	/*========================================删除==========================================*/
        $(".search-area .sa-ele .delete-action").click(function() {
        	var checkedData = $("tr[checked=true]");
        	var count = 0;
        	var dataArray = new Array();
        	while (count < checkedData.length){
        		dataArray[count] =checkedData.eq(count).children().eq(2).text();
        		count++;
        	}
        	var params = dataArray.join();
            $.ajax({
                url: 'deletePassenger.action',
                type: 'post',
                async: false,
                dataType: 'json',
                data: {
                	data: params
                },
                success: function(data, status) {
                    var res = new Array();
                	if(data.status=="0"){
                        alert("删除失败！");
                    } else {
                    for (var i = 0; i < count; i++){
                    	res[i] = "乘客编号：" + dataArray[i] + (data[dataArray[i]] == 0 ? "存在订单，无法删除\n": "删除成功\n");
                    }
                    var output = res.join("");
                    alert(output);
                    window.location.reload();
                  }
                },
                error: function(){
                  alert("网络故障");
                }
              });    
        }) ;
        
        /*========================================添加==========================================*/
        //清空表单
        $(".search-area .sa-ele .add-action").click(function() {        	
        	var selectCount = 0;
    		var inputCount = ":eq("+selectCount+")";
    		var COLUM = 10;
        	while (selectCount < COLUM){
        		inputCount = ":eq("+selectCount+")";
        		$("#dialogAdd .editInfos").children(inputCount).find("input").val();
        		selectCount++;
        	}       	       	
        });
      //提交添加
        $("#dialogAdd .editInfos .btn .submitBtn").click(function(){
        	var pass_sex = $("#dialogAdd .editInfos").children(":eq(3)").find("select").val();
        	if (pass_sex == "/"){
        		pass_sex = "";
        	}       	   	
        	if ($("span.editWarning").length != 0){
        		alert("请填写必须要填写的选项！");
        	} else {
                $.ajax({
                    url: 'addPassenger.action',
                    type: 'post',
                    async: false,
                    dataType: 'json',
                    data: {
                      pass_id: $("#dialogAdd .editInfos").children(":eq(0)").find("input").val(),
                      pass_name: $("#dialogAdd .editInfos").children(":eq(1)").find("input").val(),
                      pass_age: $("#dialogAdd .editInfos").children(":eq(2)").find("input").val(),
                      pass_sex: pass_sex,
                      pass_idcard: $("#dialogAdd .editInfos").children(":eq(4)").find("input").val(),
                      pass_passport: $("#dialogAdd .editInfos").children(":eq(5)").find("input").val(),
                      pass_phone: $("#dialogAdd .editInfos ").children(":eq(6)").find("input").val(),
                      pass_email: $("#dialogAdd .editInfos").children(":eq(7)").find("input").val()         
                    },
                    success: function(data, status) {
                      if(data.status=="1"){
                        alert("添加成功！");
                        //清空表格
                        $(".search-area .sa-ele .add-action").click(function() {        	
                        	var selectCount = 0;
                    		var inputCount = ":eq("+selectCount+")";
                    		var COLUM = 10;
                        	while (selectCount < COLUM){
                        		inputCount = ":eq("+selectCount+")";
                        		$("#dialogAdd .editInfos").children(inputCount).find("input").val();
                        		selectCount++;
                        	}       	       	
                        });
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
        
        /*========================================编辑==========================================*/
        //预填充表格
        $("table").delegate('.editTable', 'click', function(){        	
        	var selectCount = 0;
    		var inputCount = ":eq("+selectCount+")";
    		var valueCount = ":eq("+(selectCount + 2)+")";
    		var COLUM = 10;
        	while (selectCount < COLUM){
        		inputCount = ":eq("+selectCount+")";
        		valueCount = ":eq("+(selectCount + 2)+")";
        		$("#dialogEdit .editInfos").children(inputCount).find("input").val($(this).parents("tr").children(valueCount).text());
        		selectCount++;
        	}
        	var sex = $(this).parents("tr").children(":eq(5)").text();
        	if (sex == "男" || sex == "女") {
        		$("#dialogEdit .editInfos").children(":eq(3)").find("select").val(sex);
        	} else {
        		$("#dialogEdit .editInfos").children(":eq(3)").find("select").val("/");
        	}       		        	
        }); 
        
        //提交修改
        $("#dialogEdit .editInfos .btn .submitBtn").click(function(){
        	var pass_sex = $("#dialogEdit .editInfos").children(":eq(3)").find("select").val();
        	if (pass_sex == "/"){
        		pass_sex = "";
        	}       	   	
        	if ($("span.editWarning").length != 0){
        		alert("请填写必须要填写的选项！");
        	} else {
                $.ajax({
                    url: 'editPassenger.action',
                    type: 'post',
                    async: false,
                    dataType: 'json',
                    data: {
                      pass_id: $("#dialogEdit .editInfos").children(":eq(0)").find("input").val(),
                      pass_name: $("#dialogEdit .editInfos").children(":eq(1)").find("input").val(),
                      pass_age: $("#dialogEdit .editInfos").children(":eq(2)").find("input").val(),
                      pass_sex: pass_sex,
                      pass_idcard: $("#dialogEdit .editInfos").children(":eq(4)").find("input").val(),
                      pass_passport: $("#dialogEdit .editInfos").children(":eq(5)").find("input").val(),
                      pass_phone: $("#dialogEdit .editInfos ").children(":eq(6)").find("input").val(),
                      pass_email: $("#dialogEdit .editInfos").children(":eq(7)").find("input").val()         
                    },
                    success: function(data, status) {
                      if(data.status=="1"){
                        alert("修改成功！");
                        window.location.reload();
                      }
                      if(data.status=="0")
                      alert("修改失败！");
                    },
                    error: function(){
                      alert("网络故障");
                    }
                  });
        	}   	
        });
        /*=====================================添加订单===================================================*/
        //清空表单
        $('table').delegate('.addOrder', 'click', function() {
        	var selectCount = 0;
    		var inputCount = ":eq("+selectCount+")";
    		var COLUM = 13;
        	while (selectCount < COLUM){
        		inputCount = ":eq("+selectCount+")";
        		$("#dialogAdd .editInfos").children(inputCount).find("input").val();
        		selectCount++;
        	}  
          });
        //预填充表格
        $("table").delegate('.addOrder', 'click', function(){        	
        	var selectCount = 0;
    		var inputCount = ":eq("+selectCount+")";
    		var valueCount = ":eq("+(selectCount + 2)+")";
    		var COLUM = 10;
    		$("#dialogAddOrder .editInfos").children(":eq(1)").find("input").val($(this).parents("tr").children(":eq(2)").text());
    		$("#dialogAddOrder .editInfos").children(":eq(2)").find("input").val($(this).parents("tr").children(":eq(3)").text());
    		$("#dialogAddOrder .editInfos").children(":eq(3)").find("input").val($(this).parents("tr").children(":eq(6)").text());
    		$("#dialogAddOrder .editInfos").children(":eq(4)").find("input").val($(this).parents("tr").children(":eq(7)").text());
        }); 
        //提交添加
        $("#dialogAddOrder .editInfos .btn .submitBtn").click(function(){   	   	
        	if ($("span.editWarning").length != 0){
        		alert("请填写必须要填写的选项！");
        	} else {
                $.ajax({
                    url: 'addOrder.action',
                    type: 'post',
                    async: false,
                    dataType: 'json',
                    data: {
                        order_id: $("#dialogAddOrder .editInfos").children(":eq(0)").find("input").val(),
                        pass_id: $("#dialogAddOrder .editInfos").children(":eq(1)").find("input").val(),
                        pass_name: $("#dialogAddOrder .editInfos").children(":eq(2)").find("input").val(),
                        pass_idcard: $("#dialogAddOrder .editInfos").children(":eq(3)").find("input").val(),
                        pass_passport: $("#dialogAddOrder .editInfos").children(":eq(4)").find("input").val(),
                        flight_id: $("#dialogAddOrder .editInfos").children(":eq(5)").find("input").val(),
                        dep_city: $("#dialogAddOrder .editInfos ").children(":eq(6)").find("input").val(),
                        arr_city: $("#dialogAddOrder .editInfos").children(":eq(7)").find("input").val(),
                        dep_time: $("#dialogAddOrder .editInfos").children(":eq(8)").find("input").val(),
                        arr_time: $("#dialogAddOrder .editInfos").children(":eq(9)").find("input").val(),
                        deli_id: $("#dialogAddOrder .editInfos").children(":eq(10)").find("input").val(),
                        deli_name: $("#dialogAddOrder .editInfos").children(":eq(11)").find("input").val(),
                        price_purc: $("#dialogAddOrder .editInfos").children(":eq(12)").find("input").val(),       
                    },
                    success: function(data, status) {
                      if(data.status=="1"){
                        alert("添加成功！");
                        //清空表单     	
                        	var selectCount = 0;
                    		var inputCount = ":eq("+selectCount+")";
                    		var COLUM = 13
                        	while (selectCount < 13){
                        		inputCount = ":eq("+selectCount+")";
                        		$("#dialogAdd .editInfos").children(inputCount).find("input").val();
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
    });