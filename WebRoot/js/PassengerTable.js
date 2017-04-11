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
      ,pageSize:20
      ,columnData: [{
          key: 'pass_id',
          width: '100px',
          text: '客户编号',
          sorting: ''
        },{
          key: 'pass_name',
          text: '客户姓名'
        },{
          key: 'pass_age',
          text: '年龄'
        },{
          key: 'pass_sex',
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
          text: '购票数量'
        }, {
          key: 'pass_amount',
          text: '购票金额'
        }, {
          key: 'action',
          remind: 'the action',
          text: '操作',
          template: function(action, rowObject){
              return '<span class="plugin-action edit-action" learnLink-id="'+rowObject.id+'"><a href="javascript:;" class="bounceInDownEdit editTable">编辑</a></span>'
                        +'<span class="plugin-action del-action" learnLink-id="'+rowObject.id+'">删除</span>';
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
        pass_count: document.querySelector('[name="pass_count"]').value,
        pass_amount: document.querySelector('[name="pass_amount"]').value
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
      document.querySelector('[name="pass_count"]').value = '';
      document.querySelector('[name="pass_amount"]').value = '';
    });
    
    $("table").delegate('.editTable', 'click', function(){
    	var selectCount = 0;
		var inputCount = ":eq("+selectCount+")";
		var valueCount = ":eq("+(selectCount + 2)+")";
    	while (selectCount < 10){
    		inputCount = ":eq("+selectCount+")";
    		valueCount = ":eq("+(selectCount + 2)+")";
    		$(".editInfos").children(inputCount).find("input").attr("value", $(this).parents("tr").children(valueCount).text());
    		selectCount++;
    	}
    });