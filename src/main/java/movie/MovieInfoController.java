package movie;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import save_movie.SaveMovieDAO;
import save_movie.SaveMovieDTO;

@WebServlet("/movie/info.do")
public class MovieInfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet()");
		MovieDAO dao = new MovieDAO();
		HttpSession session = request.getSession();
		// 합친 후 getMovieInfo("request.getParameter(""));로 바꿔야 함
		String movie_id = request.getParameter("movie_id");		
		String user_id = (String)session.getAttribute("UserId");
		MovieDTO dto = dao.getMovieInfo(movie_id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("movie_id", movie_id);
		SaveMovieDAO sdao = new SaveMovieDAO();
		int check = sdao.checkLike(map);
		System.out.println(check);
		
		session.setAttribute("user_id", user_id);
		request.setAttribute("MovieInfo", dto);
		request.setAttribute("check", check);
		request.getRequestDispatcher("/MovieInfo/MovieInfo.jsp")
		.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
}
