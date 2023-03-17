package review;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.ReviewDAO;
import review.ReviewDTO;


@WebServlet("/review/edit.do")
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int review_id = Integer.parseInt(request.getParameter("review_id"));
		
		request.setAttribute("review_id", review_id);
		request.getRequestDispatcher("/Review/Edit.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				ReviewDAO dao = new ReviewDAO();
				ReviewDTO dto = new ReviewDTO();
				int review_id = Integer.parseInt(request.getParameter("review_id"));
				String content = request.getParameter("content");
				String rating_num = request.getParameter("rating_num");
				dto.setReview_id(review_id);
				dto.setContent(content);
				dto.setRating_num(rating_num);
				int tot = dao.updateEdit(dto);
				
				response.sendRedirect("/review/list.do");
	}
}
