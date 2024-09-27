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


@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//XSS対策
		public String escapeHtml(String input) {
			return input.replaceAll("&", "&amp;")
					.replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;")
					.replaceAll("\"","&quot;")
					.replaceAll("'", "&#x27;")
					.replaceAll("/", "&#x2F;");
			}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//直リンリダイレクト
		response.sendRedirect("login.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//  セッション獲得
		HttpSession session = request.getSession();
		MeetingRoom meetingRoom = new MeetingRoom();

		//  受取り処理やる
		String id = request.getParameter("userId");
		String password = request.getParameter("userPw");

		//次ページ
		String nextPage;
		
		//ログイン判定
		if(meetingRoom.login(id, password)) {
			//ログイン成功
			nextPage = "menu.jsp";
			session.setAttribute("meetingRoom", meetingRoom);

		}else {
			//ログイン失敗
			nextPage = "login.jsp";
			session.setAttribute("errr", "ログインできませんでした");
		}

		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request,response);
	}

}
