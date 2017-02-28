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
            <div class="box_top"><b class="pl15">添加用人单位</b></div>
            <div class="box_center">
              <form action="company" class="jqtransform">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                 <tr>
                  <td class="td_right">单位名称：<input type="hidden" name="company.companyID"  value="<s:property value="company.companyID" />"></td>
                  <td class=""> 
                    <input type="text" name="company.companyName" class="input-text lh30" size="50" value="<s:property value="company.companyName" />">
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">单位简介：</td>
                  <td class="">
					<textarea name="company.companyIntro" cols="30" rows="10" class="textarea"><s:property value="company.companyIntro" /></textarea>
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">联系人：</td>
                  <td class=""> 
                    <input type="text" name="company.contact" class="input-text lh30" size="50" value="<s:property value="company.contact" />">
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">联系电话：</td>
                  <td class="">
                  	<input type="text" name="company.telephone" class="input-text lh30" size="50" maxlength="15" value="<s:property value="company.telephone" />">
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">单位主页：</td>
                  <td class=""> 
                    <input type="text" name="company.weburl" class="input-text lh30" size="50" value="<s:property value="company.weburl" />">
                  </td>
                </tr>
                <tr>
                  <td class="td_right">单位性质：</td>
                  <td class=""> 
                    <input type="text" name="company.type" class="input-text lh30" size="50" value="<s:property value="company.type" />">
                  </td>
                </tr>
                <tr>
                  <td class="td_right">通信地址：</td>
                  <td class="">
                    <input type="text" name="company.companyAddress" class="input-text lh30" size="50" value="<s:property value="company.companyAddress" />">
                  </td>
                 </tr>
                <tr >
                  <td class="td_right">电子邮件：</td>
                  <td class="">
                     <input type="text" name="company.companyEmail" class="input-text lh30" size="50" value="<s:property value="company.companyEmail" />">
                  </td>
                 </tr>
                <tr >
                  <td class="td_right">邮编：</td>
                  <td class="">
                     <input type="text" name="company.postalcode" class="input-text lh30" size="50" value="<s:property value="company.postalcode" />">
                  </td>
                 </tr>
                 <tr >
                  <td class="td_right">传真：</td>
                  <td class="">
                     <input type="text" name="company.fax" class="input-text lh30" size="50" value="<s:property value="company.fax" />">
                  </td>
                 </tr>
                 <tr >
                  <td class="td_right">备注：</td>
                  <td class="">
                     <input type="text" name="company.note" class="input-text lh30" size="50" value="<s:property value="company.note" />">
                  </td>
                 </tr>
                 <tr>
                   <td class="td_right">&nbsp;<input type="hidden" name="update" value="1"></td>
                   <td class="">
                     <input type="submit" name="submit" class="btn btn82 btn_add" value="修改"> 
                    <input type="button" name="reset" class="btn btn82 btn_res" value="返回" onClick="location.href='company'"> 
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
