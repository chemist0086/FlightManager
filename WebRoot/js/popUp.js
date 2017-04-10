var w,h,className;
function getSrceenWH(){
  w = $(window).width();
  h = $(window).height();
  $('#dialogBg').width(w).height(h);
}

window.onresize = function(){  
  getSrceenWH();
}  
$(window).resize();  

$(function(){
  getSrceenWH();
  

  $('table').delegate('.bounceInDown', 'click', function() {
    className = $(this).attr('class');
    $('#dialogBg').fadeIn(300);
    $('#dialog').removeAttr('class').addClass('animated '+className+'').fadeIn();
  });
  
  //关闭弹窗

  $('.claseDialogBtn').click(function(){
    $('#dialogBg').fadeOut('fast=op-',function(){
      $('#dialog').addClass('bounceOutUp').fadeOut('fast');
    });
  });
});

$("#dialog").draggable();
$("#dialog").resizable();