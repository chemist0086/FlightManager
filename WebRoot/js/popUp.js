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


});
  


$("#dialogEdit").draggable();
$("#dialogEdit").resizable();

$("#dialogPasswd").draggable();
$("#dialogPasswd").resizable();