<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/main.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/colResizable-1.3.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>

<script type="text/javascript">
$(function() {
	$(".list_table").colResizable({
		liveDrag : true,
		gripInnerHtml : "<div class='grip'></div>",
		draggingClass : "dragging",
		minWidth : 30
	});
});
</script>
<title>登录信息</title>
</head>

<body>
	<div id="forms" class="mt10">
        <div class="box">
          <div class="box_border">
            <div class="box_top"><b class="pl15">管理员登陆信息</b></div>
            <div class="box_center">
              <form action="manager" class="jqtransform">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                 <tr>
                  <td class="td_right">账号：</td>
                  <td class=""> 
                    <input type="text" name="managerlogin.managerID" class="input-text lh30" size="50" value="<s:property value="managerlogin.managerID" />"/>
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">密码：</td>
                  <td class="">
					<input type="text" name="managerlogin.managerPSW" class="input-text lh30" size="50" value="<s:property value="managerlogin.managerPSW" />"/>
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">提示问题：</td>
                  <td class=""> 
                    <input type="text" name="managerlogin.question" class="input-text lh30" size="50" value="<s:property value="managerlogin.question" />"/>
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">提示答案：</td>
                  <td class="">
                  	<input type="text" name="managerlogin.answer" class="input-text lh30" size="50" value="<s:property value="managerlogin.answer" />">
                  </td>
                 </tr>
                 <tr>
                   <td class="td_right">&nbsp;<input type="hidden" name="updatelogin" value="1"></td>
                   <td class="">
                     <input type="submit" name="submit" class="btn btn82 btn_add" value="修改">
                   </td>
                 </tr>
               </table>
               </form>
            </div>
          </div>
        </div>
     </div>
</body>
</html>
