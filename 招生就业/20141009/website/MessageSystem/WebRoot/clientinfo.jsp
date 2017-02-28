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
						<th class="td_center">在线</th>
						<th class="td_center">学号</th>
						<th class="td_center">名字</th>
						<th class="td_center">电子邮箱</th>
						<th class="td_center">注册时间</th>
					</tr>
					<s:iterator value="list">
					<tr class="tr">
						<td class="td_center"><s:if test="online"><img src="images/user-online.png" /></s:if>
        									  <s:else><img src="images/user-offline.png" /></s:else>
        				</td>
						<td class="td_center"><s:property value="username"/></td>
						<td class="td_center"><s:property value="name"/></td>
						<td class="td_center"><s:property value="email"/></td>
						<td class="td_center">
							<s:date name="createdDate" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					</s:iterator>
					<tr>
						<td class="td_center" colspan="9">共有<s:property value="totalsize" />名毕业生</td>
					</tr>
				</table>
				<center>
				<div class="page mt10">
					<div class="pagination">
						<ul>
							<li class="first-child"><a href="user?pageNo=1" target="right">首页</a></li>
							<s:if test="pageNo>1">
       	   						<s:url value="user" id="prepage">
       	     						<s:param name="pageNo"><s:property value="pageNo-1" /></s:param>
       	   						</s:url>
       	   						<li><s:a href="%{prepage}">上一页</s:a></li>
       	 					</s:if>
       	 					<s:else>
							<li class="disabled"><span>上一页</span></li>
							</s:else>
							<li class="active"><span><s:property value="pageNo" /></span></li>
							<li><a href="#">2</a></li>
							<s:if test="pageNo<(totalsize/20)+1">
       	   						<s:url value="user" id="nextpage">
       	     						<s:param name="pageNo"><s:property value="pageNo+1" /></s:param>
       	   						</s:url>
       	   						<li><s:a href="%{nextpage}">下一页</s:a></li>
       	   					</s:if>
       	   					<s:else><li class="disabled"><span>下一页</span></li>
       	   					</s:else>
       	   					<s:url value="user" id="lastpage">
       	     						<s:param name="pageNo"><s:property value="(totalsize/20)+1" /></s:param>
       	   					</s:url>
							<li class="last-child"><s:a href="%{lastpage}">末页</s:a></li>
						</ul>
					</div>
				</div>
				</center>
			</div>
		</div>
	</div>
</body>
</html>
