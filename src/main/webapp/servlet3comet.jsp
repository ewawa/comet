<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Comet Test</title>
<script type='text/javascript'>
    function append(str) {
    	var textField = document.getElementById("textField");
    	textField.innerHTML = textField.innerHTML + str + "<br/>";
    };  
</script>
</head>
<body>
	<!-- textField——用来显示服务器推送的内容 -->
	<p id="textField"></p>
	<!-- cometFrame——隐藏的iframe，用来访问AsyncServlet,建立长连接-->
	<iframe id="cometFrame" style="display: none;" src="/CometTest/Async"></iframe>
</body>
</html>
