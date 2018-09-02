<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>main</title>
		<link href="<%=path %>/css/layout.css" rel="stylesheet" type="text/css" />
      	<script src="<%=basePath %>js/jquery-1.2.6.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/userTeam.js" type="text/javascript" ></script>
  		<script   language="javascript"> 
			 document.onkeydown   =   function()   
				{       
				if(event.keyCode   ==   8)   
				{   
				if(event.srcElement.tagName.toLowerCase()   !=   "input"   
				&&   event.srcElement.tagName.toLowerCase()   !=   "textarea")   
				event.returnValue   =   false;   
				}   
				}   
		</script>   
  </head>
  <body>
   	<div id="user_info"><span>登录姓名：${users.userName } 登录时间：${loginTime }</span></div>
	<div id="lm_info"><span>当前位置：用户组管理 </span></div>
     <form action="userTeam.do?method=search" method="post">
	   	<table  style="border-style:solid; border-color:#76a5af; border-width:1px 0 0 1px; width:60%; margin:0 auto; margin-top:10px;">
      		<tr>
      			<td width="30%">用户组编号：</td>
      			<td width="30%"><input type="text" id="groupNo" name="groupNo" value="${sug.groupNo}" disabled="disabled" /></td>
      		</tr>
      		<tr>
      			<td>用户组名称：</td>
      			<td><input type="text" id="groupName" name="groupName" value="${sug.groupName}" /></td>
      		</tr>
      		<tr>
      	 		<td>部门：</td>
      	 		<td>
     				<select id="deptName" style="width:130px">
     					<option value="null">全部</option>
     					<logic:iterate id="jl" name="deptList">
     						<c:if test="${sug.userDepart==jl}"><option value="${jl}" selected="selected">${jl}</option></c:if>
     						<c:if test="${sug.userDepart!=jl}"><option value="${jl}">${jl}</option></c:if>
     					</logic:iterate>
     				</select>
     			</td>
      	 	</tr>
      	 		<tr>
      	 		<td>是否为审批部门：</td>
      	 		<td>
     				<select id="isSpDept" style="width:130px" onchange="selectSpbm();">
     					<c:if test="${sug.isSpDept==0 }">
     						<option value="0">不是</option>
     						<option value="1">是</option>
     					</c:if>
     					<c:if test="${sug.isSpDept==1 }">
     						<option value="1">是</option>
     						<option value="0">不是</option>
     					</c:if>
     					
     				</select>
     			</td>
      	 	</tr>
      	 	<tr>
      	 		<td>审批部门级别：</td>
      	 		<td>
      	 			<c:if test="${sug.isSpDept==0 }">
      	 			<select id="spQxJb" style="width:130px" disabled="disabled">
      	 			</c:if>
     				<c:if test="${sug.isSpDept==1 }">
     				<select id="spQxJb" style="width:130px" >
     				</c:if>
     				    <option value="${sug.spQxJb }">${sug.spQxJb }</option>
     					<option value="0">0</option>
     					<option value="1">1</option>
     					<option value="2">2</option>
     					<option value="3">3</option>
     					<option value="4">4</option>
     					<option value="5">5</option>
     					<option value="6">6</option>
     					
     				</select>
     			</td>
      	 	</tr>
      		<tr>
      			<td><a href="javascript:void(0);" onclick="saveUpdateUserGroup();return false;"><img src="images/baocun.gif"></img></a><input type="hidden" id="groupId" name="groupId" value="${sug.webId}" /></td>
      			<td><a href="userTeam.do?method=search"><img src="images/fanhui.gif"></img></a></td>
      		</tr>
         </table>
      </form>
  </body>
</html>
