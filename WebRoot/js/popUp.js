var w,h,className;
function getSrceenWH(){
  w = $(window).width();
  h = $(window).height();
  $('#dialogBgEdit').width(w).height(h);
  $('#dialogBgPasswd').width(w).height(h);
}

window.onresize = function(){  
  getSrceenWH();
}  
$(window).resize();  

$(function(){
  getSrceenWH();
  
  /*=============================编辑弹窗===========================================*/
  //打开弹窗
  $('table').delegate('.bounceInDownEdit', 'click', function() {
    className = $(this).attr('class');
    $('#dialogBgEdit').fadeIn(300);
    $('#dialogEdit').removeAttr('class').addClass('animated '+className+'').fadeIn();
  });  
  //关闭弹窗
  $('.claseDialogBtn').click(function(){
    $('#dialogBgEdit').fadeOut('fast=op-',function(){
      $('#dialogEdit').addClass('bounceOutUp').fadeOut('fast');
    });
  });
  
  /*=============================密码弹窗===========================================*/
  $('a.bounceInDownPasswd').click(function(){
    className = $(this).attr('class');
    $('#dialogBgPasswd').fadeIn(300);
    $('#dialogPasswd').removeAttr('class').addClass('animated '+className+'').fadeIn();
  });

   $('.claseDialogBtn').click(function(){
     $('#dialogBgPasswd').fadeOut(300,function(){
       $('#dialogPasswd').addClass('bounceOutUp').fadeOut();
     });
   });
   
   /*=============================添加弹窗===========================================*/
   $('button.bounceInDownAdd').click(function(){
	    className = $(this).attr('class');
	    $('#dialogBgAdd').fadeIn(300);
	    $('#dialogAdd').removeAttr('class').addClass('animated '+className+'').fadeIn();
   });   
   //关闭弹窗
   $('.claseDialogBtn').click(function(){
     $('#dialogBgAdd').fadeOut('fast=op-',function(){
       $('#dialogAdd').addClass('bounceOutUp').fadeOut('fast');
     });
   });
});
  
/*=============================添加弹窗(乘机人界面)===========================================*/
$('table').delegate('.bounceInDownAddOrder', 'click', function() {
    className = $(this).attr('class');
    $('#dialogBgAddOrder').fadeIn(300);
    $('#dialogAddOrder').removeAttr('class').addClass('animated '+className+'').fadeIn();
  });    
//关闭弹窗
$('.claseDialogBtn').click(function(){
  $('#dialogBgAddOrder').fadeOut('fast=op-',function(){
    $('#dialogAddOrder').addClass('bounceOutUp').fadeOut('fast');
  });
});

$("#dialogEdit").draggable();
$("#dialogEdit").resizable();

$("#dialogPasswd").draggable();
$("#dialogPasswd").resizable();

$("#dialogAdd").draggable();
$("#dialogAdd").resizable();

$("#dialogAddOrder").draggable();
$("#dialogAddOrder").resizable();