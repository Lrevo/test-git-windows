<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
HttpSession meetingRoom = request.getSession(false);
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
<title>予約確定画面</title>
</head>
<header>
	<h1 class="border">会議室予約</h1>
</header>


<body>
	<h2>予約完了</h2>
	<div class="center">
		<p class="left">
		　予約日　　: ${sessionScope.meetingRoom.date}<br>
		　会議室　　: ${sessionScope.room.name}<br>
		　予約時刻　: ${sessionScope.reservation.start}～${sessionScope.reservation.end}<br>
		　予約者　　: ${sessionScope.meetingRoom.user.name}<br>
	</div>
	
		<form action="menu.jsp" method="post">
			<input type="submit" value="完了">
		</form>
	<div class="border"></div>
</body>
</html>