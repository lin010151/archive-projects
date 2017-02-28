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
<title>专业信息</title>
</head>

<body>
	<div class="container">
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th class="td_center">专业</th>
					</tr>
					<s:iterator value="list">
					<tr class="tr">
						<td class="td_center"><s:property value="majorName" /></td>
					</tr>
					</s:iterator>
					<tr>
						<td class="td_center" colspan="9">共有<s:property value="totalsize" />个专业</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
