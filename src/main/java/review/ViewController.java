package review;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.ReviewDAO;
import review.ReviewDTO;


@WebServlet("/review/view.do")
public class ViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String review_id = request.getParameter("review_id");
		ReviewDAO dao = new ReviewDAO();
		
		ReviewDTO dto = dao.selectView(review_id);
		dao.updateViewCount(review_id);
		dao.close();
		
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/Review/View.jsp").forward(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
