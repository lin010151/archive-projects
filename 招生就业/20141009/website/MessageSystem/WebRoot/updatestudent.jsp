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
<script type="text/javascript" src="scripts/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	getMajorInfo();
	getAddressInfo();
	changepolitical();
});
function getMajorInfo(){
	$.ajax({
		url:"majorjson", 			// 设置请求地址
		type:"POST",				// 设置请求方式
		dataType:"json",			// 设置返回数据的类型
		// 设置请求成功时执行的回调函数
		success:function(json){
			var id=$("#major option:selected").val();	// 保存原来的选项
			$("#major").empty();						// 清空select控件
			// 设置select控件
			for (var i=0; i<json.length; i++){
				var jsonObj=json[i];
				var optionstring="<option value=\""+jsonObj.majorID+"\">"+jsonObj.majorName+"</option>";
				$("#major").append(optionstring);	// 为select追加一个option
			}
			// 设置id为选中
			$("#major").val(id);
		},
		error:function(){
			alert("请求失败!");
		}
	});
}
function getAddressInfo(){
	$.ajax({
		url:"addressjson", 			// 设置请求地址
		type:"POST",				// 设置请求方式
		dataType:"json",			// 设置返回数据的类型
		// 设置请求成功时执行的回调函数
		success:function(json){
			var id=$("#address option:selected").val();	// 保存原来的选项
			$("#address").empty();						// 清空select控件
			// 设置select控件
			for (var i=0; i<json.length; i++){
				var jsonObj=json[i];
				var optionstring="<option value=\""+jsonObj.addressID+"\">"+jsonObj.addressName+"</option>";
				$("#address").append(optionstring);	// 为select追加一个option
			}
			// 设置id为选中
			$("#address").val(id);
		},
		error:function(){
			alert("请求失败!");
		}
	});
}
function changepolitical() {
	var id=$("#political").val();	// 保存原来的选项
	// 设置id为选中
	$("#politicalsel").val(id);
}
function changemajor(){
	var id=$("#major option:selected").text();	// 保存原来的选项
	// 设置
	$("#majorname").val(id);
}
function changeaddress() {
	var id=$("#address option:selected").text();	// 保存原来的选项
	// 设置
	$("#addressname").val(id);
}
</script>
<title>信息推送</title>
</head>
<body>
	<div id="forms" class="mt10">
        <div class="box">
          <div class="box_border">
            <div class="box_top"><b class="pl15">修改学生信息</b></div>
            <div class="box_center">
              <form action="student" class="jqtransform">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                 <tr>
                  <td class="td_right">学号：</td>
                  <td class=""> 
                    <input type="text" id="title" name="student.stuID" class="input-text lh30" size="50" value="<s:property value="student.stuID" />">
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">姓名：</td>
                  <td class=""> 
                    <input type="text" id="title" name="student.stuName" class="input-text lh30" size="50" value="<s:property value="student.stuName" />">
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">身份证号：</td>
                  <td class=""> 
                    <input type="text" id="title" name="student.stuIDCard" class="input-text lh30" size="50" value="<s:property value="student.stuIDCard" />">
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">性别：</td>
                  <td class="">
                  	<s:if test="student.stuSex==0">
                    	<input type="radio" name="student.stuSex" value="0" checked="checked"> 男
                    	<input type="radio" name="student.stuSex" value="1" > 女
                    </s:if>
                    <s:else>
                    	<input type="radio" name="student.stuSex" value="0"> 男
                    	<input type="radio" name="student.stuSex" value="1" checked="checked"> 女
                    </s:else>
                  </td>
                 </tr>
                 <tr>
                  <td class="td_right">专业：</td>
                  <td class=""> 
                    <span class="fl">
                      <div class="select_border">
                        <div class="select_containers">
                        	<select name="student.major.majorID" class="select" style="width:auto;" id="major" onchange="changemajor()">
                        		<option value="<s:property value="student.major.majorID"/>"><s:property value="student.major.majorName"/></option>
                        	</select> 
                        </div> 
                      </div> 
                    </span>
                    <input type="hidden" name="student.major.majorName" id="majorname" value="<s:property value="student.major.majorName"/>">
                  </td>
                </tr>
                <tr>
                  <td class="td_right">生源地：</td>
                  <td class=""> 
                    <span class="fl">
                      <div class="select_border">
                        <div class="select_containers">
                        	<select name="student.address.addressID" class="select" style="width:auto;" id="address"  onchange="changeaddress()">
                        		<option value="<s:property value="student.address.addressID"/>"><s:property value="student.address.addressName"/></option>
                        	</select> 
                        </div> 
                      </div> 
                    </span>
                    <input type="hidden" name="student.address.addressName" id="addressname" value="<s:property value="student.address.addressName"/>">
                  </td>
                </tr>
                <tr>
                  <td class="td_right">政治面貌：</td>
                  <td class="">
                  	<select name="student.stuPolitical" class="select" style="width:auto;" id="politicalsel">
                        <option value="0">中国共产党党员</option>
                        <option value="1">中共预备党员</option>
                        <option value="2">共青团员</option>
                        <option value="3">群众</option>
                    </select>
                    <input type="hidden" name="stuPolitical" id="political" value="<s:property value="student.stuPolitical"/>">
                  </td>
                 </tr>
                <tr >
                  <td class="td_right">电子邮件：</td>
                  <td class="">
                     <input type="text" name="student.stuEmail" class="input-text lh30" size="50" value="<s:property value="student.stuEmail" />">
                  </td>
                 </tr>
                 <tr>
                   <td class="td_right">&nbsp;<input type="hidden" name="update" value="1"></td>
                   <td class="">
                     <input type="submit" name="submit" class="btn btn82 btn_save2" value="修改"> 
                    <input type="button" name="reset" class="btn btn82 btn_res" value="返回" onClick="location.href='student'"> 
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
