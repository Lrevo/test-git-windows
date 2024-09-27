package reserve;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MeetingRoom;
import bean.ReservationBean;
import bean.RoomBean;


@WebServlet("/ReserveCreate")
public class ReserveCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//直リンできた奴リダイレクト
		response.sendRedirect("login.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//セッション獲得
				HttpSession session = request.getSession(false);	
				if(session==null) {
					response.sendRedirect("login.jsp");
				}
				MeetingRoom meetingroom = (MeetingRoom)session.getAttribute("meetingRoom");
				
				//TODO 受取処理
				String roomId = request.getParameter("roomId");
				String time =request.getParameter("time");
				
				//TODO 受取渡処理
				String nextPage = "reserveConfirm.jsp";//次ページ
				
				ReservationBean reservation = meetingroom.createReservation(roomId,time);
				RoomBean room =  meetingroom.getRoom(roomId);
				
				session.setAttribute("reservation",reservation); //セッション格納
				session.setAttribute("room",room);
									
				//TODO 予約画面移動
				                   
				RequestDispatcher rd = request.getRequestDispatcher(nextPage);
				rd.forward(request,response);
				
				
	}

}
