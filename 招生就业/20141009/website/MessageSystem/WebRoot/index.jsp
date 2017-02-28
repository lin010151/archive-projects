<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//Dtd HTML 4.01 transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>广东第二师范学院招生就业处信息推送系统管理平台</title>
<meta http-equiv="Content-Language" content="zh-cn">
<meta content="MSHTML 6.00.2800.1611" name="GENERATOR">
<link href="images/login/css1.css" type="text/css" rel="stylesheet">
<link href="images/login/newhead.css" type="text/css" rel="stylesheet">
<script language="javascript" src="scripts/jquery.js"></script>
<script type="text/javascript">
function resetlogin() {
	$("#username").val("");
	$("#password").val("");
}
</script>
</head>

<body bgcolor="#eef8e0" marginleft="0" margintop="0" marginright="0"
	marginbottom="0">
	<form name="adminlogin" action="<%=request.getContextPath() %>/manager" method="post">
		<table cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<td colspan="6"><img alt="" src="images/login/crm_1.gif"></td>
					<td colspan="4"><img alt="" src="images/login/crm_2.gif"></td>
					<td><img alt="" src="images/login/crm_3.gif"></td>
				</tr>
				<tr>
					<td colspan="6"><img alt="" src="images/login/crm_4.gif"></td>
					<td colspan="4"><img alt="" src="images/login/crm_5.gif"></td>
					<td><img alt="" src="images/login/crm_6.gif"></td>
				</tr>
				<tr>
					<td rowspan="5"><img alt="" src="images/login/crm_7.gif"></td>
					<td colspan="5"><img alt="" src="images/login/crm_8.gif"></td>
					<td colspan="4"><img alt="" src="images/login/crm_9.gif"></td>
					<td><img alt="" src="images/login/crm_10.gif"></td>
				</tr>
				<tr>
					<td><img alt="" src="images/login/crm_11.gif"></td>
					<td background=images/login/crm_12.gif colspan="6">
						<table id="table1" cellspacing="0" cellpadding="0" width="98%"
							border="0">
							<tbody>
								<tr>
									<td>
										<table id="table2" cellspacing="1" cellpadding="0"
											width="100%" border="0">
											<tbody>
												<tr>
													<td align="center" width="81"><font color="#ffffff">用户名：</font></td>
													<td><input class="regtxt" title="请填写用户名"
														maxLength="16" size="16" id="username"
														name="username"></td>
												</tr>
													<td colspan="2"><br></td>
												<tr>
												</tr>
												<tr>
													<td align="center" width="81"><font color="#ffffff">密&nbsp;&nbsp;
															码：</font></td>
													<td><input class="regtxt" title="请填写密码 "
														type="password" maxLength="16" size="16"
														id="password" name="password"></td>
												</tr>
												<tr>
											</tbody>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
					<td colspan="2" rowspan="2"><img alt=""
						src="images/login/crm_13.gif"></td>
					<td rowspan="2"><img alt="" src="images/login/crm_14.gif"></td>
				</tr>
				<tr>
					<td rowspan="3"><img alt="" src="images/login/crm_15.gif"></td>
					<td rowspan="3"><img alt="" src="images/login/crm_16.gif"></td>
					<td><input title="登录后台" type="image" alt=""
						src="images/login/crm_17.gif" name="image"></td>
					<td><img alt="" src="images/login/crm_18.gif"></td>
					<td colspan="2"><img title="返回首页 " style="CURSOR: hand" alt=""
						src="images/login/crm_19.gif" border="0" onclick="resetlogin()"/></td>
					<td><img alt="" src="images/login/crm_20.gif"></td>
				</tr>
				<tr>
					<td colspan="5" rowspan="2"><img alt=""
						src="images/login/crm_21.gif"></td>
					<td rowspan="2"><img alt="" src="images/login/crm_22.gif"></td>
					<td colspan="2"><img alt="" src="images/login/crm_23.gif"></td>
				</tr>
				<tr>
					<td colspan="2"><img alt="" src="images/login/crm_24.gif"></td>
				</tr>
				<tr>
					<td><img height="1" alt="" src="images/login/spacer.gif"
						width="59"></td>
					<td><img height="1" alt="" src="images/login/spacer.gif"
						width="127"></td>
					<td><img height="1" alt="" src="images/login/spacer.gif"
						width="24"></td>
					<td><img height="1" alt="" src="images/login/spacer.gif"
						width="86"></td>
					<td><img height="1" alt="" src="images/login/spacer.gif"
						width="21"></td>
					<td><img height="1" alt="" src="images/login/spacer.gif"
						width="28"></td>
					<td><img height="1" alt="" src="images/login/spacer.gif"
						width="56"></td>
					<td><img height=1 alt="" src="images/login/spacer.gif"
						width="101"></td>
					<td><img height="1" alt="" src="images/login/spacer.gif"
						width="170"></td>
					<td><img height="1" alt="" src="images/login/spacer.gif"
						width="125"></td>
					<td><img height="1" alt="" src="images/login/spacer.gif"
						width="207"></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>
