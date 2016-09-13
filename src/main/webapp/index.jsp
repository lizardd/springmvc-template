<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
</head>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
<body>
	username:
	<c:out value="${username}"></c:out>
	<br /> msg:
	<c:out value="${msg}"></c:out>
	<br />
	<div id="qrcode"></div>
	<a href="logout.action">注销</a>
</body>
<script type="text/javascript">
	function utf16to8(str) {
		var out, i, len, c;
		out = "";
		len = str.length;
		for (i = 0; i < len; i++) {
			c = str.charCodeAt(i);
			if ((c >= 0x0001) && (c <= 0x007F)) {
				out += str.charAt(i);
			} else if (c > 0x07FF) {
				out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
				out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
				out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
			} else {
				out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
				out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
			}
		}
		return out;
	}
	//在刷新或关闭时调用的事件  
	/* $(window).bind('beforeunload', function() {
		$.ajax({
			url : "logout.action",
			type : "post",
			success : function() {
				alert("你关闭了或者刷新浏览器");
			}
		});
	}); */
	var content = "BEGIN:VCARD" + "\n" + "VERSION:2.1" + "\n" + "N:姓;名" + "\n"
			+ "FN:姓名" + "\n" + "NICKNAME:nickName" + "\n" + "ORG:公司;部门" + "\n"
			+ "TITLE:职位" + "\n" + "TEL;WORK;VOICE:电话1" + "\n"
			+ "TEL;WORK;VOICE:电话2" + "\n" + "TEL;HOME;VOICE:电话1" + "\n"
			+ "TEL;HOME;VOICE:电话2" + "\n" + "TEL;CELL;VOICE:1521533374" + "\n"
			+ "TEL;PAGER;VOICE:0755" + "\n" + "TEL;WORK;FAX:传真" + "\n"
			+ "TEL;HOME;FAX:传真" + "\n" + "ADR;WORK:;;单位地址;深圳;广东;433000;国家"
			+ "\n" + "ADR;HOME;POSTAL;PARCEL:;;街道地址;深圳;广东;433330;中国" + "\n"
			+ "URL:网址" + "\n" + "URL:单位主页" + "\n" + "EMAIL;PREF;INTERENT:邮箱地址"
			+ "\n" + "X-QQ:372633334" + "\n" + "END:VCARD";
	jQuery('#qrcode').qrcode(utf16to8(content));
</script>
</html>