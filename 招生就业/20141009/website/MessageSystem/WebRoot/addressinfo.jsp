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
<title>生源地信息</title>
</head>

<body>
	<div class="container">
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th class="td_center">生源地</th>
					</tr>
					<s:iterator value="list">
					<tr class="tr">
						<td class="td_center"><s:property value="addressName" /></td>
					</tr>
					</s:iterator>
					<tr>
						<td class="td_center" colspan="9">共有<s:property value="totalsize" />个生源地</td>
					</tr>
				</table>
				<center>
				<div class="page mt10">
					<div class="pagination">
						<ul>
							<li class="first-child"><a href="address?pageNo=1" target="right">首页</a></li>
							<s:if test="pageNo>1">
       	   						<s:url value="address" id="prepage">
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
       	   						<s:url value="address" id="nextpage">
       	     						<s:param name="pageNo"><s:property value="pageNo+1" /></s:param>
       	   						</s:url>
       	   						<li><s:a href="%{nextpage}">下一页</s:a></li>
       	   					</s:if>
       	   					<s:else><li class="disabled"><span>下一页</span></li>
       	   					</s:else>
       	   					<s:url value="address" id="lastpage">
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
