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
<title>单位信息</title>
</head>

<body>
	<div class="container">
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th class="td_center">单位名称</th>
						<th class="td_center">单位简介</th>
						<th class="td_center">联系人</th>
						<th class="td_center">联系电话</th>
						<th class="td_center">单位主页</th>
						<th class="td_center">单位性质</th>
						<th class="td_center">通信地址</th>
						<th class="td_center">电子邮件</th>
						<th class="td_center">邮编</th>
						<th class="td_center">传真</th>
						<th class="td_center">备注</th>
						<th class="td_center">操作</th>
					</tr>
					<s:iterator value="list">
					<tr class="tr">
						<td width="8%" class="td_center"><s:property value="companyName" />
							<input type="hidden" name="companyID" value="<s:property value="companyID"/>"></td>
						<td class="td_center"><s:property value="companyIntro" /></td>
						<td class="td_center"><s:property value="contact" /></td>
						<td class="td_center"><s:property value="telephone" /></td>
						<td class="td_center"><s:property value="weburl" /></td>
						<td class="td_center"><s:property value="type" /></td>
						<td class="td_center"><s:property value="companyAddress" /></td>
						<td class="td_center"><s:property value="companyEmail" /></td>
						<td class="td_center"><s:property value="postalcode" /></td>
						<td class="td_center"><s:property value="fax" /></td>
						<td class="td_center"><s:property value="note" /></td>
						<td class="td_center">
							<a href="company?companyID=<s:property value="companyID" />&find=1" class="ext_btn">修&nbsp;改</a>&nbsp;
							<a href="company?companyID=<s:property value="companyID" />&delete=1" class="ext_btn" onClick="return confirm('确定删除吗？');">删&nbsp;除</a>
						</td>
					</tr>
					</s:iterator>
					<tr>
						<td class="td_center" colspan="12">共有<s:property value="totalsize" />间用人单位</td>
					</tr>
				</table>
				<center>
				<div class="page mt10">
					<div class="pagination">
						<ul>
							<li class="first-child"><a href="insertcompany.jsp">新增</a></li>
							<li class="first-child"><a href="company?pageNo=1">首页</a></li>
							<s:if test="pageNo>1">
       	   						<s:url value="student" id="prepage">
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
       	   						<s:url value="company" id="nextpage">
       	     						<s:param name="pageNo"><s:property value="pageNo+1" /></s:param>
       	   						</s:url>
       	   						<li><s:a href="%{nextpage}">下一页</s:a></li>
       	   					</s:if>
       	   					<s:else><li class="disabled"><span>下一页</span></li>
       	   					</s:else>
       	   					<s:url value="company" id="lastpage">
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
