﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  		<link href="<%=path %>/css/layout.css" rel="stylesheet" type="text/css" />
        <script src="<%=basePath %>js/jquery-1.2.6.js" type="text/javascript" ></script>
		<script src="<%=basePath %>js/materialMessage.js" type="text/javascript" ></script>
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
   	<div id="user_info"><span>登录姓名：${users.userNo } 登录时间：${loginTime }</span></div>
	<div id="lm_info"><span>当前位置：罪犯信息 </span></div>
       <form action="materialMessage.do?method=search" method="post">
		     	<table   style="border-style:solid; border-color:#76a5af; border-width:1px 0 0 1px; width:30%; margin:0 auto; margin-top:10px;">
		     		<tr><td><font color="red">导入成功</font></td></tr>
		     		<tr><td><a href="materialMessage.do?method=search"><img src="images/fanhui.gif"></img></a></td></tr>
		     	</table>
        </form> 
  </body>
  		
 
</html>
