<html>
<head>
<title>QQ登录跳转</title>
<script src="<%=request.getContextPath()%>/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	//切割字符串转换参数表
	function toParamMap(str) {
		var map = {};
		var segs = str.split("&");
		for ( var i in segs) {
			var seg = segs[i];
			var idx = seg.indexOf('=');
			if (idx < 0) {
				continue;
			}
			var name = seg.substring(0, idx);
			var value = seg.substring(idx + 1);
			map[name] = value;
		}
		return map;
	}

	//隐式获取url响应内容(JSONP)
	function openImplict(url) {
		var script = document.createElement('script');
		script.src = url;
		document.body.appendChild(script);
	}

	//获得openid的回调
	function callback(obj) {
		var openid = obj.openid;
		$("#openid").text(openid);

		//跳转服务端登录url
		var resulturl = "@{openapi.QQs.login_result()}";
		var accessToken = $("#accessToken").text();

		//向服务端传输access_token及openid参数
		document.location.href = resulturl + "?access_token=" + accessToken
				+ "&openid=" + openid;
	}
</script>
</head>

<body>
	<p>
		AccessToken:<span id="accessToken"></span>--ExpireIn<span id="expire"></span>
	</p>
	<p>
		OpenID:<span id="openid"></span>
	</p>

	<!-- 执行脚本 -->
	<script type="text/javascript">
		//应用的APPID
		var appID = "101207268";

		//登录授权后的回调地址，设置为当前url
		var redirectURI = "@@{openapi.QQs.login()}";

		//初始构造请求
		if (window.location.hash.length == 0) {
			var path = 'https://graph.qq.com/oauth2.0/authorize?';
			var queryParams = [
					'client_id=' + appID,
					'redirect_uri=' + redirectURI,
					'scope='
							+ 'get_user_info,list_album,upload_pic,add_feeds,do_like',
					'response_type=token' ];

			var query = queryParams.join('&');
			var url = path + query;
			window.location.href = url;
		}
		//在成功授权后回调时location.hash将带有access_token信息，开始获取openid
		else {
			//获取access token
			var accessToken = window.location.hash.substring(1);
			var map = toParamMap(accessToken);

			//记录accessToken
			$("#accessToken").text(map.access_token);
			$("#expire").text(map.expires_in);

			//使用Access Token来获取用户的OpenID
			var path = "https://graph.qq.com/oauth2.0/me?";
			var queryParams = [ 'access_token=' + map.access_token,
					'callback=callback' ];
			var query = queryParams.join('&');
			var url = path + query;
			openImplict(url);
		}
	</script>
</body>
</html>