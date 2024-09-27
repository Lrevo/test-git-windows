<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="bean.*" %>
 <% 
   
  MeetingRoom meetingRoom = (MeetingRoom)session.getAttribute("meetingRoom");
   
  if (session == null || session.getAttribute("meetingRoom") == null){
		response.sendRedirect("login.jsp");
		return;
	}
	
	 ReservationBean[][] reservation = meetingRoom.getReservation();//予約状況一覧 
	  RoomBean[] rooms = meetingRoom.getRooms();//部屋一覧
	  String[] period =meetingRoom.getPeriod();//予約時間一覧
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
<h2>利用日:${meetingRoom.date}</h2>

	<h4>ログインユーザー：${meetingRoom.user.name }様</h4>
<form action = "ChangeDate" method = post>   
      <input type = "date" name = "date" value = "${meetingRoom.date}">
      <input type = "hidden" name="page" value = "cancelInput.jsp">
      <input type = "submit" value = "日付変更" >
</form>
<h2>キャンセル可能時間帯</h2>

<%


out.print("<table>");
out.print("<tr><th class='width'>会議室＼時間帯</th><th>9:00</th><th>10:00</th><th>11:00</th><th>12:00</th><th>13:00</th><th>14:00</th><th>15:00</th><th>16:00</th></tr>");

for(int i = 0;i<rooms.length;i++){//部屋一覧
	out.print("<tr><td>"+rooms[i].getName()+"</td>");
	for(int j=0;j<period.length;j++){//時間一覧
		out.print("<form action='CancelCreate' method='post'>");
		if(reservation[i][j] == null){
			out.print("<td><span>"+period[j]+"</span></td>");
			
		}else if(!reservation[i][j].getUserId().equals(meetingRoom.getUser().getId())){
			out.print("<td><span>"+period[j]+"</span></td>");
			
		}else{
			out.print(
					"<td> <input type='hidden' name='roomId' value='"+rooms[i].getId()+"' >"+
				"<input type = 'submit' name='time' class='button' value= '"+period[j]+"'></td>"				
					);
		}
		out.print("</form>");
	}out.println("</tr>");
}

out.print("</table>");
%>

<form action = "menu.jsp" method = "post">
<input type = "submit" value = "戻る">
<div class="border"></div>

</form>
</body>
</html>