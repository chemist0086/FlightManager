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
      ,pageSize:5
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
          width: "80px",
          text: '操作',
          template: function(action, rowObject){
              return '<span class="plugin-action edit-action" learnLink-id="'+rowObject.id+'"><a href="javascript:;" class="bounceInDownEdit editTable">编辑</a></span>';
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
    
    $(function() {
        $("table").delegate('.editTable', 'click', function(){
        	//预填充表格
        	var selectCount = 0;
    		var inputCount = ":eq("+selectCount+")";
    		var valueCount = ":eq("+(selectCount + 2)+")";
        	while (selectCount < 10){
        		inputCount = ":eq("+selectCount+")";
        		valueCount = ":eq("+(selectCount + 2)+")";
        		$(".editInfos").children(inputCount).find("input").val($(this).parents("tr").children(valueCount).text());
        		selectCount++;
        	}
        	var sex = $(this).parents("tr").children(":eq(5)").text();
        	if (sex == "男" || sex == "女") {
        		$(".editInfos").children(":eq(3)").find("select").val(sex);
        	} else {
        		$(".editInfos").children(":eq(3)").find("select").val("/");
        	}       		        	
        });
    });
    
    function submitEdit() {
    	var pass_sex = $(".editInfos").children(":eq(3)").find("select").val();
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
                  pass_id: $(".editInfos").children(":eq(0)").find("input").val(),
                  pass_name: $(".editInfos").children(":eq(1)").find("input").val(),
                  pass_age: $(".editInfos").children(":eq(2)").find("input").val(),
                  pass_sex: pass_sex,
                  pass_idcard: $(".editInfos").children(":eq(4)").find("input").val(),
                  pass_passport: $(".editInfos").children(":eq(5)").find("input").val(),
                  pass_phone: $(".editInfos").children(":eq(6)").find("input").val(),
                  pass_email: $(".editInfos").children(":eq(7)").find("input").val()         
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
    } 
    
    $(function() {
        $(".delete-action").click(function() {
        	var checkedData = $("tr[checked=true]");
        	var count = 0;
        	var dataArray = new Array();
        	while (count < checkedData.length){
        		dataArray[count] = checkedData.eq(count).children().eq(2).text();
        		count++;
        	}
            $.ajax({
                url: 'editPassenger.action',
                type: 'post',
                async: false,
                dataType: 'json',
                data: {
                	data: dataArray
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
        }) 
    });
