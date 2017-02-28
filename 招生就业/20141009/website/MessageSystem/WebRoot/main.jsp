<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <link rel="stylesheet" href="css/common.css">
  <link rel="stylesheet" href="css/style.css">
  <script type="text/javascript" src="scripts/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="scripts/jquery.SuperSlide.js"></script>
  <script type="text/javascript">
  $(function(){
      $(".sideMenu").slide({
         titCell:"h3", 
         targetCell:"ul",
         defaultIndex:0, 
         effect:'slideDown', 
         delayTime:'500' , 
         trigger:'click', 
         triggerTime:'150', 
         defaultPlay:true, 
         returnDefault:false,
         easing:'easeInQuint',
         endFun:function(){
              scrollWW();
         }
       });
      $(window).resize(function() {
          scrollWW();
      });
  });
  function scrollWW(){
    if($(".side").height()<$(".sideMenu").height()){
       $(".scroll").show();
       var pos = $(".sideMenu ul:visible").position().top-38;
       $('.sideMenu').animate({top:-pos});
    }else{
       $(".scroll").hide();
       $('.sideMenu').animate({top:0});
       n=1;
    }
  } 

var n=1;
function menuScroll(num){
  var Scroll = $('.sideMenu');
  var ScrollP = $('.sideMenu').position();
  /*alert(n);
  alert(ScrollP.top);*/
  if(num==1){
     Scroll.animate({top:ScrollP.top-38});
     n = n+1;
  }else{
    if (ScrollP.top > -38 && ScrollP.top != 0) { ScrollP.top = -38; }
    if (ScrollP.top<0) {
      Scroll.animate({top:38+ScrollP.top});
    }else{
      n=1;
    }
    if(n>1){
      n = n-1;
    }
  }
}
  </script>
  <title>广东第二师范学院招生就业处信息推送管理平台</title>
</head>
<body>
    <div class="top">
      <div id="top_t">
        <div id="logo" class="fl"></div>
        <div id="photo_info" class="fr">
          <div id="photo" class="fl">
            <a href="#"><img src="images/a.png" alt="" width="60" height="60"></a>
          </div>
          <div id="base_info" class="fr">
            <div class="help_info">
              <a href="1" id="hp">&nbsp;</a>
              <a href="2" id="gy">&nbsp;</a>
              <a href="manager?logout=1" id="out">&nbsp;</a>
            </div>
            <div class="info_center">
             	 管理员：<s:property value="#session.manager.managerName"/>
              <span id="nt">通知</span><span><a href="#" id="notice">3</a></span>
            </div>
          </div>
        </div>
      </div>
      <div id="side_here">
        <div id="side_here_l" class="fl"></div>
        <div id="here_area" class="fl">当前位置：</div>
      </div>
    </div>
    <div class="side">
        <div class="sideMenu" style="margin:0 auto">
          <h3> 就业指导信息管理</h3>
          <ul>
            <li class="on"><a href="welcome.html" target="right">中心介绍</a></li>
            <li>政策法规</li>
            <li>简历制作</li>
            <li>面试技巧</li>
            <li>求职心得</li>
            <li>职业宝典</li>
            <li>常见问题</li>
          </ul>
          <h3> 管理员信息管理</h3>
          <ul>
            <li><a href="manager?users=1" target="right">管理员信息</a></li>
            <li><a href="insertmanager.jsp" target="right">添加管理员</a></li>
            <li><a href="manager?login=1" target="right">登录信息</a></li>
          </ul>
          <h3> 学生信息管理</h3>
          <ul>
            <li><a href="student" target="right">学生信息</a></li>
            <li><a href="major" target="right">专业信息</a></li>
            <li><a href="address" target="right">生源地信息</a></li>
          </ul>
          <h3> 用人单位信息管理</h3>
          <ul>
            <li><a href="company" target="right">单位信息</a></li>
            <li><a href="post" target="right">招聘信息</a></li>
          </ul>
          <h3> 信息推送管理</h3>
          <ul>
            <li><a href="user" target="right">客户端信息</a></li>
            <li><a href="session" target="right">在线信息</a></li>
            <li><a href="notification.jsp" target="right">信息推送</a></li>
          </ul>
          <h3>信息统计</h3>
          <ul>
            <li>访问情况</li>
            <li>点击数</li>
          </ul>
       </div>
    </div>
    <div class="main">
       <iframe name="right" id="rightMain" src="welcome.html" frameborder="0" scrolling="auto" width="100%" height="auto" allowtransparency="true"></iframe>
    </div>
    <div class="bottom">
      <div id="bottom_bg">&copy;版权所有:广东第二师范学院招生就业处&nbsp;&nbsp;&nbsp;&nbsp;技术支持：广东第二师范学院计算机科学系</div>
    </div>
    <div class="scroll">
          <a href="javascript:;" class="per" title="使用鼠标滚轴滚动侧栏" onClick="menuScroll(1);"></a>
          <a href="javascript:;" class="next" title="使用鼠标滚轴滚动侧栏" onClick="menuScroll(2);"></a>
    </div>
</body>

</html>
   
 