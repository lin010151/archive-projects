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
						<th class="td_center">账号</th>
						<th class="td_center">姓名</th>
						<th class="td_center">证件</th>
						<th class="td_center">性别</th>
						<th class="td_center">电话</th>
						<th class="td_center">邮箱</th>
						<th class="td_center">操作</th>
					</tr>
					<s:iterator value="list">
					<tr class="tr">
						<td class="td_center"><s:property value="managerID" /></td>
						<td class="td_center"><s:property value="managerName" /></td>
						<td class="td_center"><s:property value="managerIDCard" /></td>
						<td class="td_center">
							<s:if test="managerSex==0">
                    			 男
                    		</s:if>
                    		<s:else>
                    			女
                    		</s:else>
						</td>
						<td class="td_center"><s:property value="managerPhone" /></td>
						<td class="td_center"><s:property value="managerEmail" /></td>
						<td class="td_center">
							<a href="manager?managerID=<s:property value="managerID" />&find=1" class="ext_btn">修&nbsp;改</a>&nbsp;
							<a href="manager?managerID=<s:property value="managerID" />&delete=1" class="ext_btn" onClick="return confirm('确定删除吗？');">删&nbsp;除</a>
						</td>
					</tr>
					</s:iterator>
					<tr>
						<td class="td_center" colspan="7">共有<s:property value="list.size()" />个管理员</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
