<%@page import="javax.swing.text.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>登录</title>
<!-- Bootstrap -->
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container" style="width: 300px;margin-top: 100px;">
	<h1 class="text-center">登录</h1>
	<form action="login.action" id="loginForm">
		<input type="hidden" id="location" name="location">
		<div class="form-group">
			<label for="exampleInputUserName">邮箱/用户名/手机号：</label> <input type="text" class="form-control" id="userName" placeholder="UserName" name="username">
		</div>
		<div class="form-group">
			<label for="exampleInputPassword">密码：</label> <input type="password" class="form-control" id="password" placeholder="Password" name="password">
		</div>
		<div class="form-group">
			<label for="exampleInputVerificationCode">选出图片中的“${tip}”：</label>
			<div id="insert">
				<img src="<%=request.getContextPath()%>/targetImage/${file}" height="150" width="300" onclick="addImg()">
			</div>
		</div>
		<div class="form-group">
			<button type="button" class="btn btn-default" style="float: right;" onclick="login()">登录</button>
		</div>
		<div class="form-group">
			<label for="loginByThree">第三方登录：</label>
			<a href="qqlogin.jsp"><img src="images/qqlogin.ico" width="20px" height="20px"/></a>
		</div>
	</form>
	<c:if test="${msg!=null}">
		<div class="alert alert-success" role="alert">
			<button class="close" data-dismiss="alert" type="button">&times;</button>
			<p>${msg}</p>
		</div>
	</c:if>
	
	
	<script src="<%=request.getContextPath()%>/js/jquery-1.10.2.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		var index = 1;
		function addImg(e){
			var parentDiv =document.getElementById("insert");
			var topValue = 0,leftValue = 0;
			var obj = parentDiv;
			while(obj){
				topValue += obj.offsetTop;
				leftValue += obj.offsetLeft;
				obj = obj.offsetParent;
			}
			
			e = e || window.event;
			var left = e.clientX + document.body.scrollLeft - document.body.clientLeft - 10;//
			var top = e.clientY + document.body.scrollTop - document.body.clientTop - 10;
			var imgDivId = "img_" + index++;
			var newDiv = document.createElement("div");
			parentDiv.appendChild(newDiv);
			
			newDiv.id = imgDivId;
			newDiv.style.position = "relative";
			newDiv.style.zIndex = index;
			newDiv.style.width = "20px";
			newDiv.style.height = "20px";
			newDiv.style.top = (top - topValue - 150 ) + "px";
			newDiv.style.left = (left - leftValue) + "px";
			newDiv.style.display = "inline";
			newDiv.setAttribute("onclick","removeSelf('"+ imgDivId +"')");
			console.log((top - topValue + 10) +"   " + (left - leftValue + 10));
			var img = document.createElement("img");
			newDiv.appendChild(img);
			
			img.src = "images/logo.png";
			img.style.width = "20px";
			img.style.height = "20px";
			img.style.top = "0px";
			img.style.left = "0px";
			img.style.position = "absolute";
			img.style.zIndex = index;
		}
		
		function removeSelf(id){
			document.getElementById("insert").removeChild(document.getElementById(id));
		}
		
		function login(){
			/* if(""==$("#userName").val()){
				alert("请输入用户名！");
				return;
			}
			if(""==$("#password").val()){
				alert("请输入密码！");
				return;
			} */
			var parentDiv = document.getElementById("insert");
			var nodes = parentDiv.childNodes;
			var result = "";
			for(var i=0;i < nodes.length;i++){
				var id = nodes[i].id;
				if(id && id.startsWith('img_')){
					var top = document.getElementById(id).style.top;
					var left = document.getElementById(id).style.left;
					result = result + top.replace('px','')+','+left.replace('px','')+';';
				}
			}
			$("#location").val(result.substr(0,result.length - 1));
			$("#loginForm").submit();
		}
		
	</script>
</body>
</html>