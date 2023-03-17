package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import movie.MovieDAO;
import movie.MovieDTO;

@WebServlet("/GetDataServlet")
public class GetDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MovieDTO dto = new MovieDTO();
		MovieDAO dao = new MovieDAO();
		dao.getMovieInfo(getServletInfo());


		// 서버로부터 가져올 데이터
		Map<String, Object> data = new HashMap<>();
		data.put("name", "John");
		data.put("age", 30);
		data.put("city", "New York");

		// JSON 형태로 데이터를 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}
}
