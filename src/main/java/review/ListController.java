package review;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.ReviewAndMovieDTO;
import review.ReviewDAO;
import review.ReviewDTO;


@WebServlet("/review/list.do")
public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("review doGet()");
		
	    ReviewDAO dao = new ReviewDAO();
	    
	    String movie_id = request.getParameter("movie_id");
	    
	    Map<String, Object> param = new HashMap<String, Object>();
	    String searchField = request.getParameter("searchField");
	    String searchWord = request.getParameter("searchWord");
	    if(searchWord != null){
	    	param.put("searchField", searchField);
	    	param.put("searchWord", searchWord);
	    }
	    
	    List<ReviewDTO> reviewLists = dao.selectList(movie_id);   
	    dao.close();
	    
	    int totalCount = reviewLists.size();
	    request.setAttribute("reviewLists", reviewLists);
	    request.setAttribute("totalCount", totalCount);
	    request.setAttribute("param", param);
	    
	    request.getRequestDispatcher("/Review/List.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}
