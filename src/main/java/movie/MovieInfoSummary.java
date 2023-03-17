package movie;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class MovieInfoSummary
 */
@WebServlet("/movie/summary.do")
public class MovieInfoSummary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MovieInfoSummary() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet()");
		MovieDAO dao = new MovieDAO();
		// 합친 후 getMovieInfo("request.getParameter(""));로 바꿔야 함
		String movie_id = request.getParameter("movie_id");		
		MovieDTO dto = dao.getMovieInfo(movie_id);
		
		// JSON 형태로 데이터를 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(dto));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result;
		MovieDAO dao = new MovieDAO();
		String user_id = request.getParameter("user_id");
		String movie_id = request.getParameter("movie_id");
		result = dao.insertSaveMovie(user_id, movie_id);
		
		// JSON 형태로 데이터를 반환
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result);

	}

}
