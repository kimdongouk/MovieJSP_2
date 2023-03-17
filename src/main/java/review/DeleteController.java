package review;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.ReviewDAO;
import review.ReviewDTO;


@WebServlet("/review/delete.do")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReviewDAO dao = new ReviewDAO();
		String review_id = request.getParameter("review_id");
		int rs = dao.deletePost(review_id);
		if(rs==1) response.sendRedirect("/review/list.do");
		else System.out.println("삭제실패");
	}
}
