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

<script language="javascript" src="scripts/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	getCompanyInfo();
});
function getCompanyInfo(){
	$.ajax({
		url:"companyjson", 			// 设置请求地址
		type:"POST",				// 设置请求方式
		dataType:"json",			// 设置返回数据的类型
		// 设置请求成功时执行的回调函数
		success:function(json){
			// 设置select控件
			for (var i=0; i<json.length; i++){
				var jsonObj=json[i];
				var optionstring="<option value=\""+jsonObj.companyID+"\">"+jsonObj.companyName+"</option>";
				$("#company").append(optionstring);	// 为select追加一个option
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
}
function changecompany() {
	var id=$("#company option:selected").text();	// 保存原来的选项
	// 设置id为选中
	$("#companyname").val(id);
}
</script>
<title>添加用人单位</title>
</head>
<body>
	<div id="forms" class="mt10">
        <div class="box">
          <div class="box_border">
            <div class="box_top"><b class="pl15">添加招聘岗位信息</b></div>
            <div class="box_center">
              <form action="post" class="jqtransform">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                 <tr>
                  <td class="td_right">单位名称：</td>
                  <td class=""> 
                    <span class="fl">
                      <div class="select_border">
                        <div class="select_containers">
                        	<select name="post.company.companyID" class="select" style="width:auto;" id="company" onchange="changecompany()">
                        		<option>请选择单位</option>
                        		<option value="<s:property value="companyID" />"><s:property value="companyName" /></option>
                        	</select> 
                        </div> 
                      </div> 
                    </span>
                    <input type="hidden" name="post.company.companyName" id="companyname">
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">岗位名称：</td>
                  <td class="">
					<input type="text" name="post.postName" class="input-text lh30" size="50"/>
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">学历：</td>
                  <td class=""> 
                    <input type="text" name="post.education" class="input-text lh30" size="50"/>
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">招聘人数：</td>
                  <td class="">
                  	<input type="text" name="post.recruitNum" class="input-text lh30" size="50" maxlength="15">
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">工作类型：</td>
                  <td class=""> 
                    <input type="text" name="post.jobsCategory" class="input-text lh30" size="50">
                  </td>
                </tr>
                <tr>
                  <td class="td_right">职位要求：</td>
                  <td class=""> 
                    <input type="text" name="post.postRequirements" class="input-text lh30" size="50">
                  </td>
                </tr>
                <tr>
                  <td class="td_right">薪水待遇：</td>
                  <td class="">
                    <input type="text" name="post.salary" class="input-text lh30" size="50">
                  </td>
                 </tr>
                <tr >
                  <td class="td_right">专业要求：</td>
                  <td class="">
                     <input type="text" name="post.major" class="input-text lh30" size="50"/>
                  </td>
                 </tr>
                <tr >
                  <td class="td_right">相关文件：</td>
                  <td class="">
                     <input type="text" name="post.relatefile" class="input-text lh30" size="50"/>
                  </td>
                 </tr>
                 <tr >
                  <td class="td_right">备注：</td>
                  <td class="">
                     <input type="text" name="post.note" class="input-text lh30" size="50">
                  </td>
                 </tr>
                 <tr>
                   <td class="td_right">&nbsp;<input type="hidden" name="insert" value="1"></td>
                   <td class="">
                     <input type="submit" name="submit" class="btn btn82 btn_add" value="添加"> 
                    <input type="button" name="reset" class="btn btn82 btn_res" value="返回" onClick="location.href='post'"> 
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
