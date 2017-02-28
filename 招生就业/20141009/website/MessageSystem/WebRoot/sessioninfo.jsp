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
<title>客户端信息</title>
</head>

<body>
	<div class="container">
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th class="td_center">学号</th>
						<th class="td_center">客户端</th>
						<th class="td_center">权限</th>
						<th class="td_center">在线状态</th>
						<th class="td_center">客户端IP</th>
						<th class="td_center">注册时间</th>
					</tr>
					<s:iterator value="voList">
					<tr class="tr">
						<td class="td_center"><s:property value="username"/></td>
						<td class="td_center"><s:property value="resource"/></td>
						<td class="td_center"><s:property value="status"/></td>
						<td class="td_center"><s:if test="presence==Online"><img src="images/user-online.png" /></s:if>
        									  <s:elseif test="presence==Offline"><img src="images/user-offline.png" /></s:elseif>
        									  <s:elseif test="presence==Away"><img src="images/user-away.png" /></s:elseif>
        									  <s:else><img src="images/user-busy.png" /></s:else>
        				</td>
        				<td class="td_center"><s:property value="clientIP"/></td>
						<td class="td_center">
							<s:date name="createdDate" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					</s:iterator>
					<tr>
						<td class="td_center" colspan="6">共有<s:property value="voList.size()" />名毕业生在线</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
