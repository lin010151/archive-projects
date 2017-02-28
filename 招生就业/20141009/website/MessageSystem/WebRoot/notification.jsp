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
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript" src="scripts/colResizable-1.3.min.js"></script>
<script type="text/javascript" src="scripts/common.js"></script>
<script type="text/javascript" src="scripts/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	getCompanyInfo();
	getMajorInfo();
	getAddressInfo();
	// 单击按钮响应事件
	$('input[name=broadcast]').click(function() {
		if ($('input[name=broadcast]')[0].checked) {
			$('#trUser').hide();
		} else {
			$('#trUser').show();
		}
	});
	
	if ($('input[name=broadcast]')[0].checked) {
		$('#trUser').hide();
	} else {
		$('#trUser').show();
	}
	$(".list_table").colResizable({
		liveDrag : true,
		gripInnerHtml : "<div class='grip'></div>",
		draggingClass : "dragging",
		minWidth : 30
	});
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
			$("#post").empty();						// 清空select控件
		},
		error:function(){
			alert("请求失败!");
			$("#post").empty();						// 清空select控件
		}
	});
}
function getMajorInfo(){
	$.ajax({
		url:"majorjson", 			// 设置请求地址
		type:"POST",				// 设置请求方式
		dataType:"json",			// 设置返回数据的类型
		// 设置请求成功时执行的回调函数
		success:function(json){
			// 设置select控件
			for (var i=0; i<json.length; i++){
				var jsonObj=json[i];
				var optionstring="<option value=\""+jsonObj.majorID+"\">"+jsonObj.majorName+"</option>";
				$("#major").append(optionstring);	// 为select追加一个option
			}
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
			// 设置select控件
			for (var i=0; i<json.length; i++){
				var jsonObj=json[i];
				var optionstring="<option value=\""+jsonObj.addressID+"\">"+jsonObj.addressName+"</option>";
				$("#address").append(optionstring);	// 为select追加一个option
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
}
function changecompany() {
	$.ajax({
		url:"postjson", 			// 设置请求地址
		type:"POST",				// 设置请求方式
		dataType:"json",			// 设置返回数据的类型
		data:{
			companyid:$("#company option:selected").val(),
			what:"bycompany"
		},
		// 设置请求成功时执行的回调函数
		success:function(json){
			// 设置select控件
			$("#post").empty();						// 清空select控件
			for (var i=0; i<json.length; i++){
				var jsonObj=json[i];
				var optionstring="<option value=\""+jsonObj.postID+"\">"+jsonObj.postinfo+"</option>";
				$("#post").append(optionstring);	// 为select追加一个option
			}
		},
		error:function(){
			alert("该用人单位尚未发布招聘岗位信息。");
			$("#post").empty();						// 清空select控件
		}
	});
}
</script>
<title>信息推送</title>
</head>
<body>
	<div id="forms" class="mt10">
		<div class="box">
			<div class="box_border">
				<div class="box_top">
					<center>
						<b class="pl15">消息推送</b>
					</center>
				</div>
				<div class="box_center">
					<form action="notification" class="jqtransform" target="right">
						<table class="form_table pt15 pb15" width="100%" border="0"
							cellpadding="0" cellspacing="0">
							<tr>
								<td class="td_right">推送对象：</td>
								<td class=""><input type="radio" name="broadcast" value="Y"
									checked="checked"> 所有毕业生 <input type="radio"
									name="broadcast" value="N"> 选定毕业生</td>
							</tr>
							<tr id="trUser" style="display:none;">
								<td class="td_right">选定对象：</td>
								<td class="">专业：
								<select name="majorID"  class="select" style="width:auto;" id="major">
									<option value="0" selected="selected">请选择要求的专业</option>
        						</select>&nbsp;&nbsp;
        						生源地：
								<select name="addressID"  class="select" style="width:auto;" id="address">
									<option value="0" selected="selected">请选择要求的生源地</option>
        						</select>&nbsp;&nbsp;
        						性别:
        						<input type="radio" name="sex" value="2"
									checked="checked"> 不限&nbsp;<input type="radio"
									name="sex" value="0"> 男&nbsp;<input type="radio"
									name="sex" value="1"> 女
								</td>
							</tr>
							<tr>
								<td class="td_right">推送主题：</td>
								<td class=""><input type=text id="title" name="title"
									class="input-text lh30" size="100"></td>
							</tr>
							<tr>
								<td class="td_right">推送消息：</td>
								<td class=""><textarea id="message" name="message"
										cols="100" rows="50" class="textarea"></textarea></td>
							</tr>
							<tr>
								<td class="td_right">推送岗位：</td>
								<td class=""><span class="fl">
										<div class="select_border">
											<div class="select_containers">
												<select name="company" class="select" style="width:auto;"
													id="company" onchange="changecompany()">
													<option>请选择用人单位</option>
												</select>&nbsp;&nbsp;&nbsp;&nbsp; <select name="postID"
													class="select" style="width:auto;" id="post">
													<option>请选择岗位</option>
												</select>(必选项目)
											</div>
										</div>
								</span></td>
							</tr>
							<tr>
								<td class="td_right">&nbsp;</td>
								<td class=""><input type="submit" name="submit"
									class="btn btn82 btn_save2" value="推送"> <input
									type="button" name="reset" class="btn btn82 btn_res" value="重置">
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
