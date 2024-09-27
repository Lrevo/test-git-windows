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


@WebServlet("/ChangeDate")
public class ChangeDateServlet extends HttpServlet {
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
		
		MeetingRoom meetingRoom = (MeetingRoom)session.getAttribute("meetingRoom");

		//ポスト送信されてきたデータ
		String date = request.getParameter("date"); //変更する日付
		String page = request.getParameter("page");//どのページから来たか
		
		//日付適応
		meetingRoom.setDate(date);
		//セット
		session.setAttribute("meetingRoom", meetingRoom);
		
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request,response);

	}

}
