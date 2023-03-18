package movie;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import save_movie.SaveMovieDAO;

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
		SaveMovieDAO dao = new SaveMovieDAO();
		String user_id = request.getParameter("user_id");
		String movie_id = request.getParameter("movie_id");
		int check = Integer.parseInt(request.getParameter("check"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("movie_id", movie_id);
		
		if(check == 1) {
			dao.deleteSaveMovie(map);
			check = 0;
		}else {
			dao.insertSaveMovie(map);
			check = 1;
		}
		
		// JSON 형태로 데이터를 반환
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(String.valueOf(check));

	}

}
