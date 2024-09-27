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

/**
 * Servlet implementation class Reserveervlet
 */
@WebServlet("/Cancel")
public class CancelServlet extends HttpServlet {
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
		//予約登録画面に強制送還
		//TODO 何も処理記述してないです
		String nextPage ="";

		ReservationBean rb = (ReservationBean) session.getAttribute("reservation");								
		MeetingRoom meet = (MeetingRoom)session.getAttribute("meetingRoom");

		//受け取り処理
		//String roomId = request.getParameter("roomId");
		//String time =request.getParameter("time");

		try {
			meet.cancel(rb);
			nextPage = "canceled.jsp";
		}catch(Exception e) {
			nextPage = "cancelError.jsp";
			request.setAttribute("error",e);
		}finally {

			RequestDispatcher rd = request.getRequestDispatcher(nextPage);
			rd.forward(request,response);		
		}
	}	
}

