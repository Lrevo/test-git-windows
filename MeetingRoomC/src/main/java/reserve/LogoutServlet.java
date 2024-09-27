package reserve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//直リンできた奴リダイレクト
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン名をリクエストから取得する
		HttpSession session = request.getSession(false);	
		if(session==null) {
			response.sendRedirect("login.jsp");
		}
	   // MeetingRoom meetingroom = (MeetingRoom)session.getAttribute("meetingroom");
	    
	    // 既にセッションが存在する場合は一度破棄する
	    if (session != null) {
	      session.invalidate();
	    }
	    // セッション破棄後にログイン画面にリダイレクトする
	    response.sendRedirect("login.jsp");
	    
	}

}
