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
<script type="text/javascript" src="scripts/jquery-1.11.1.min.js"></script>
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
function importIPlan(theform){
    var pathName = $("#excelFile").val();
    if (pathName=="")
    {
    	alert("请点击浏览按钮，选择您要上传的文件。");
    	$("#excelFile").focus;
    	return (false);
    }
    var suffix = pathName.substring(pathName.lastIndexOf('.')+1, pathName.length);
    if((suffix != "xls") && (suffix != "xlsx")){
        alert("请选择excel文件！");
        return (false);
    }
}
</script>
<title>学生信息</title>
</head>

<body>
	<div class="container">
		<div id="table" class="mt10">
			<div class="box span10 oh">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="list_table">
					<tr>
						<th class="td_center">学号</th>
						<th class="td_center">姓名</th>
						<th class="td_center">身份证</th>
						<th class="td_center">性别</th>
						<th class="td_center">专业</th>
						<th class="td_center">生源地</th>
						<th class="td_center">政治面貌</th>
						<th class="td_center">电子邮件</th>
						<th class="td_center">操作</th>
					</tr>
					<s:iterator value="list">
					<tr class="tr">
						<td width="8%" class="td_center"><s:property value="stuID" /></td>
						<td width="5%" class="td_center"><s:property value="stuName"/></td>
						<td width="22%" class="td_center"><s:property value="stuIDCard"/></td>
						<td width="2%" class="td_center"><s:if test="stuSex==0">男</s:if>
        										<s:else>女</s:else></td>
						<td width="10%" class="td_center"><s:property value="major.majorName"/></td>
						<td width="15%" class="td_center"><s:property value="address.addressName" /></td>
						<td width="4%" class="td_center"><s:if test="stuPolitical==0">中国共产党党员</s:if>
        										<s:elseif test="stuPolitical==1">中共预备党员</s:elseif>
        										<s:elseif test="stuPolitical==2">共青团员</s:elseif>
        										<s:else>群众</s:else></td>
						<td width="16%" class="td_center"><s:property value="stuEmail"/></td>
						<td width="18%" class="td_center">
							<a href="student?stuID=<s:property value="stuID" />&find=1" class="ext_btn">修&nbsp;改</a>&nbsp;
							<a href="student?stuID=<s:property value="stuID" />&delete=1" class="ext_btn" onClick="return confirm('确定删除吗？');">删&nbsp;除</a>
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
							<li class="first-child"><a href="student?pageNo=1" target="right">首页</a></li>
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
       	   						<s:url value="student" id="nextpage">
       	     						<s:param name="pageNo"><s:property value="pageNo+1" /></s:param>
       	   						</s:url>
       	   						<li><s:a href="%{nextpage}">下一页</s:a></li>
       	   					</s:if>
       	   					<s:else><li class="disabled"><span>下一页</span></li>
       	   					</s:else>
       	   					<s:url value="student" id="lastpage">
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
	
	<div id="forms" class="mt10">
        <div class="box">
          <div class="box_border">
            <div class="box_top"><b class="pl15">批量上传学生信息</b></div>
            <div class="box_center">
              <form action="student" class="jqtransform" method="post" enctype="multipart/form-data" onsubmit="return importIPlan(this)">
               <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
               <tr>
                  <td class="td_right">文件：</td>
                  <td class=""><input type="file" name="excelFile" id="excelFile" class="input-text lh30" size="10"></td>
                 </tr>
                 <tr>
                   <td class="td_right">&nbsp;<input type="hidden" name="insert" value="1"></td>
                   <td class="">
                     <input type="submit" name="button" class="btn btn82 btn_save2" value="保存"> 
                   </td>
                 </tr>
               </table>
               </form>
            </div>
          </div>
        </div>
     </div>
   </div> 
</body>
</html>
