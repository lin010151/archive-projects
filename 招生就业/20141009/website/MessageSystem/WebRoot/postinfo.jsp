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
<title>招聘岗位信息</title>
</head>

<body>
	<div class="container">
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th class="td_center">公司名称</th>
						<th class="td_center">岗位名称</th>
						<th class="td_center">学历</th>
						<th class="td_center">招聘人数</th>
						<th class="td_center">发布时间</th>
						<th class="td_center">工作类型</th>
						<th class="td_center">职位要求</th>
						<th class="td_center">薪水待遇</th>
						<th class="td_center">专业要求</th>
						<th class="td_center">相关文件</th>
						<th class="td_center">备注</th>
						<th class="td_center">操作</th>
					</tr>
					<s:iterator value="list">
					<tr class="tr">
						<td class="td_center"><s:property value="company.companyName" /></td>
						<td class="td_center"><s:property value="postName"/></td>
						<td class="td_center"><s:property value="education"/></td>
						<td class="td_center"><s:property value="recruitNum"/></td>
						<td class="td_center"><s:property value="releaseTime"/></td>
						<td class="td_center"><s:property value="jobsCategory"/></td>
						<td class="td_center"><s:property value="postRequirements"/></td>
						<td class="td_center"><s:property value="salary"/></td>
						<td class="td_center"><s:property value="major"/></td>
						<td class="td_center"><s:property value="relatefile"/></td>
						<td class="td_center"><s:property value="note"/></td>
						<td class="td_center">
							<a href="post?postID=<s:property value="postID" />&find=1" class="ext_btn">修&nbsp;改</a>&nbsp;
							<a href="post?postID=<s:property value="postID" />&delete=1" class="ext_btn" onClick="return confirm('确定删除吗？');">删&nbsp;除</a>
						</td>
					</tr>
					</s:iterator>
					<tr>
						<td class="td_center" colspan="12">共有<s:property value="totalsize" />个招聘岗位</td>
					</tr>
				</table>
				<center>
				<div class="page mt10">
					<div class="pagination">
						<ul>
							<li class="first-child"><a href="insertpost.jsp">新增</a></li>
							<li class="first-child"><a href="post?pageNo=1" target="right">首页</a></li>
							<s:if test="pageNo>1">
       	   						<s:url value="post" id="prepage">
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
       	   						<s:url value="post" id="nextpage">
       	     						<s:param name="pageNo"><s:property value="pageNo+1" /></s:param>
       	   						</s:url>
       	   						<li><s:a href="%{nextpage}">下一页</s:a></li>
       	   					</s:if>
       	   					<s:else><li class="disabled"><span>下一页</span></li>
       	   					</s:else>
       	   					<s:url value="post" id="lastpage">
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
