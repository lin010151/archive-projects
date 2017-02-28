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

<title>添加用人单位</title>
</head>
<body>
	<div id="forms" class="mt10">
        <div class="box">
          <div class="box_border">
            <div class="box_top"><b class="pl15">修改管理员信息</b></div>
            <div class="box_center">
              <form action="manager" class="jqtransform">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                 <tr>
                  <td class="td_right">账号：</td>
                  <td class=""> 
                    <input type="text" name="manager.managerID" class="input-text lh30" size="50" value="<s:property value="manager.managerID" />">
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">姓名：</td>
                  <td class="">
                    <input type="text" name="manager.managerName" class="input-text lh30" size="50" value="<s:property value="manager.managerName" />">
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">证件：</td>
                  <td class=""> 
                    <input type="text" name="manager.managerIDCard" class="input-text lh30" size="50" value="<s:property value="manager.managerIDCard" />">
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">性别：</td>
                  <td class="">
                    <s:if test="manager.managerSex==0">
                    	<input type="radio" name="manager.managerSex" value="0" checked="checked"> 男
                    	<input type="radio" name="manager.managerSex" value="1" > 女
                    </s:if>
                    <s:else>
                    	<input type="radio" name="manager.managerSex" value="0"> 男
                    	<input type="radio" name="manager.managerSex" value="1" checked="checked"> 女
                    </s:else>
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">电话：</td>
                  <td class=""> 
                    <input type="text" name="manager.managerPhone" class="input-text lh30" size="50" maxlength="15" value="<s:property value="manager.managerPhone" />">
                  </td>
                </tr>
                <tr>
                  <td class="td_right">邮箱：</td>
                  <td class=""> 
                    <input type="text" name="manager.managerEmail" class="input-text lh30" size="50" value="<s:property value="manager.managerEmail" />">
                  </td>
                </tr>
                 <tr>
                   <td class="td_right">&nbsp;<input type="hidden" name="update" value="1"></td>
                   <td class="">
                     <input type="submit" name="submit" class="btn btn82 btn_add" value="修改"> 
                    <input type="button" name="reset" class="btn btn82 btn_res" value="返回" onClick="location.href='manager?users=1'"> 
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
