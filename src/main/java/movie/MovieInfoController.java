package movie;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/movie/info.do")
public class MovieInfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet()");
		MovieDAO dao = new MovieDAO();
		// 합친 후 getMovieInfo("request.getParameter(""));로 바꿔야 함
		String movie_id = request.getParameter("movie_id");		
		MovieDTO dto = dao.getMovieInfo(movie_id);
		
		request.setAttribute("MovieInfo", dto);
		request.getRequestDispatcher("/MovieInfo/MovieInfo.jsp")
		.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
}
