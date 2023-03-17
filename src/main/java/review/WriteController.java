package review;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

import review.ReviewDAO;
import review.ReviewDTO;


@WebServlet("/review/write.do")
public class WriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("write doGet()");
		
		HttpSession session = request.getSession();
		
		//회원인지 아닌지 확인
		if(session.getAttribute("UserId") != null) {
			String user_id = (String)session.getAttribute("UserId");
			
			request.setAttribute("user_id", user_id);
			request.getRequestDispatcher("/Review/Write.jsp").forward(request, response);
		}
		else {
			//회원이 아니면 로그인 화면으로 보냄
			response.sendRedirect("/Member/LoginForm.jsp");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ReviewDTO dto = new ReviewDTO();
		ReviewDAO dao = new ReviewDAO();
		String user_id = request.getParameter("user_id");
		int movie_id = Integer.parseInt(request.getParameter("movie_id"));
		String rating_num = request.getParameter("rating_num");
		String content = request.getParameter("content");
		
		dto.setUser_id(user_id);
		dto.setMovie_id(movie_id);
		dto.setRating_num(rating_num);
		dto.setContent(content);
		
		dao.insertWrite(dto);
		
		response.sendRedirect("/review/list.do");
	}
}
