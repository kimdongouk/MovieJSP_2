package mainpage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import movie.MovieDAO;
import movie.MovieDTO;

@WebServlet("/mainpage/main.do")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//start 페이지에서 Main.jsp 가기 위한 컨트롤러
		
		String mode = request.getParameter("mode");
		MovieDAO dao = new MovieDAO();
		
		if(mode.equals("main")) {
			List<MovieDTO> movies = dao.movieListLimit();
			
			request.setAttribute("movies", movies);
			request.getRequestDispatcher("/MainPage/Main.jsp").forward(request, response);
		}
		
		if(mode.equals("more")) {
			List<MovieDTO> movies = dao.movieList();
			
			request.setAttribute("movies", movies);
			request.getRequestDispatcher("/MainPage/More.jsp").forward(request, response);
		}
	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
