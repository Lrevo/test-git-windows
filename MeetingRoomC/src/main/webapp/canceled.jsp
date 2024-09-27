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
<link rel ="stylesheet" href ="style/style.css">
<title>キャンセル確定画面</title>
</head>

<header>
	<h1 class="border">会議室予約キャンセル</h1>
</header>
<body>
	<h2>キャンセル完了</h2>
	
	<div class="center">
	<p class="left">
	　予約日　　: ${sessionScope.meetingRoom.date}<br>
		　会議室　　: ${sessionScope.room.name}<br>
		　予約時刻　: ${sessionScope.reservation.start}～${sessionScope.reservation.end}<br>
		　予約者　　: ${sessionScope.meetingRoom.user.name}<br>
	</p>
    </div>
    
    <div class="yoko">
	<form action="menu.jsp" method="get">
		<input type="submit" value="完了">
	</form>
	</div>
	<div class="border"></div>
</body>
</html>