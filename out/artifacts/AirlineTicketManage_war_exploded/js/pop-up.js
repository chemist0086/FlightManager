var w,h,className;
function getSrceenWH(){
  w = $(window).width();
  h = $(window).height();
  $('#dialog-bg-edit').width(w).height(h);
  $('#dialog-bg-passwd').width(w).height(h);
}

window.onresize = function(){  
  getSrceenWH();
}  
$(window).resize();  

$(function(){
  getSrceenWH();
  
  /*=============================编辑弹窗===========================================*/
  //打开弹窗
  $('table').delegate('.bounce-in-down-edit', 'click', function() {
    className = $(this).attr('class');
    $('#dialog-bg-edit').fadeIn(300);
    $('#dialog-edit').removeAttr('class').addClass('animated '+className+'').fadeIn();
  });  
  //关闭弹窗
  $('.close-dialog-btn').click(function(){
    $('#dialog-bg-edit').fadeOut('fast=op-',function(){
      $('#dialog-edit').addClass('bounce-out-up').fadeOut('fast');
    });
  });
  
  /*=============================密码弹窗===========================================*/
  $('a.bounce-in-down-passwd').click(function(){
    className = $(this).attr('class');
    $('#dialog-bg-passwd').fadeIn(300);
    $('#dialog-passwd').removeAttr('class').addClass('animated '+className+'').fadeIn();
  });
    //关闭弹窗
   $('.close-dialog-btn').click(function(){
     $('#dialog-bg-passwd').fadeOut(300,function(){
       $('#dialog-passwd').addClass('bounce-out-up').fadeOut();
     });
   });
   
   /*=============================添加弹窗===========================================*/
   $('button.bounce-in-down-add').click(function(){
	    className = $(this).attr('class');
	    $('#dialog-bg-add').fadeIn(300);
	    $('#dialog-add').removeAttr('class').addClass('animated '+className+'').fadeIn();
   });   
   //关闭弹窗
   $('.close-dialog-btn').click(function(){
     $('#dialog-bg-add').fadeOut('fast=op-',function(){
       $('#dialog-add').addClass('bounce-out-up').fadeOut('fast');
     });
   });
});
  
/*=============================添加订单弹窗(乘机人界面)===========================================*/
$('table').delegate('.bounce-in-down-add-order', 'click', function() {
    className = $(this).attr('class');
    $('#dialog-bg-add-order').fadeIn(300);
    $('#dialog-add-order').removeAttr('class').addClass('animated '+className+'').fadeIn();
  });    
//关闭弹窗
$('.close-dialog-btn').click(function(){
  $('#dialog-bg-add-order').fadeOut('fast=op-',function(){
    $('#dialog-add-order').addClass('bounce-out-up').fadeOut('fast');
  });
});

$("#dialog-edit").draggable();
$("#dialog-edit").resizable();

$("#dialog-passwd").draggable();
$("#dialog-passwd").resizable();

$("#dialog-add").draggable();
$("#dialog-add").resizable();

$("#dialog-add-order").draggable();
$("#dialog-add-order").resizable();