<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

if (session == null || session.getAttribute("meetingRoom") == null){
	response.sendRedirect("login.jsp");
	return;
}
session.setMaxInactiveInterval(15*60);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style/style.css">
<title>メニュー画面</title>
</head>
<header>
	<h1 class="border">会議室予約</h1>
</header>
<body>
	<h2>メニュー</h2>
		<h4>ログインユーザー：${meetingRoom.user.name }様</h4>
	<form action="reserveInput.jsp" method="post">
		<input type="submit" value="会議室予約" class="menub">
	</form>
	
	<form action="cancelInput.jsp" method="post">
		<input type="submit" value="予約キャンセル" class="menub">
	</form>
	
	<form action="Logout" method="post">
		<input type="submit" value="ログアウト" class="menub">
	</form>
	<div class="border"></div>
</body>
</html>