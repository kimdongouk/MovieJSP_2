package movie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MovieLikePage;
import common.ReviewLikePage;
import review.ReviewDTO;
import save_movie.SaveMovieDAO;
import save_review.SaveReviewDAO;

@WebServlet("/movie/MovieLike.one")
public class MovieLikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MovieLikeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MovieLike doGet()");

		HttpSession session = request.getSession();
		String sid = (String)session.getAttribute("UserId");
		System.out.println(sid);

		String movie_id = request.getParameter("movie_id");
		MovieDAO dao = new MovieDAO();

		//로그인 안된 상태에서 접근 시 로그인 화면으로 이동
		//		if (sid == null) {
		//			PrintWriter writer = response.getWriter();
		//			writer.println("<script>alert('login'); location.href='/Member/LoginForm.jsp';</script>");
		//			writer.close();
		//			response.sendRedirect("/Member/LoginForm.jsp");
		//		}

		//		System.out.println(view);
		//		request.setAttribute("view", view);
		//		request.getRequestDispatcher("/test/MovieLike.jsp").forward(request, response);

		//MovieLike.jsp 파일에서 a링크로 view 값 받아올 수 있음
		//request.setAttribute("view", "movie");

		String view = request.getParameter("view");
		System.out.println(view);

		//		if (view == null) {
		//			view = "movie";
		//		}
		if(sid != null) {
			if (view != null && view.equals("review")) {
				System.out.println(view);
				ReviewInfo(request);
				request.setAttribute("view", view);
				request.getRequestDispatcher("/MovieLike/MovieLike.jsp").forward(request, response);
			}
			if (view != null && view.equals("movie")) {
				MovieInfo(request);
				request.setAttribute("view", view);
				request.getRequestDispatcher("/MovieLike/MovieLike.jsp").forward(request, response);
			} 
			if(view != null && view.equals("insert")) {
				System.out.println(sid);
				int res = dao.insertSaveMovie(sid, movie_id);
				MovieDTO dto = dao.getMovieInfo(movie_id);
				
				if(res == 1) {
					//System.out.println("추가 성공");
					String msg = "<script>alert('즐겨찾기 목록에 추가되었습니다.')</script>";
					request.setAttribute("MovieInfo", dto);
					request.setAttribute("msg", msg);
					request.getRequestDispatcher("/MovieInfo/MovieInfo.jsp").forward(request, response);
				}
				else {
					//System.out.println("추가 실패");	
					String msg = "<script>alert('즐겨찾기 목록에 있는 영화입니다.')</script>";
					request.setAttribute("MovieInfo", dto);
					request.setAttribute("msg", msg);
					request.getRequestDispatcher("/MovieInfo/MovieInfo.jsp").forward(request, response);
				}
				dao.close();
			}
		}
		else {
			response.sendRedirect("/Member/LoginForm.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	public void MovieInfo(HttpServletRequest request) {
		//request.setAttribute("view", "movie");
		// Movie 리스트 출력 시작
		Map<String, Object> param = new HashMap<String, Object>();
		//					String searchField = request.getParameter("searchField");
		String searchWord = request.getParameter("searchWord");
		if(searchWord != null){
			//						param.put("searchField", searchField);
			param.put("searchWord", searchWord);
		}
		String pageTemp = request.getParameter("pageNum");
		int pageNum = 1;
		if(pageTemp != null && !(pageTemp.equals(""))){
			pageNum = Integer.parseInt(pageTemp);
		}
		SaveMovieDAO dao = new SaveMovieDAO();
		int pageSize = 10;
		int blockPage = 10;

		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("UserId");
		param.put("user_id", user_id);
		int totalCount = dao.selectCount(param);  // 게시물 수 확인

		int totalPage = (int)Math.ceil((double)totalCount/pageSize) ; // 101 ~ 109 / 10 10.1 => 11 올림
		// System.out.println(">>>>>>>>totalPage" + totalPage);
		// DAO를 생성해 DB에 연결
		int start = (pageNum - 1) * pageSize;
		int end = pageSize;
		param.put("start",start);
		param.put("end",end);
		//		param.put("title", "영"); // 나중에 검색값으로 바꿔줄것!!!!!!!!!

		List<MovieDTO> movieLists = dao.selectListPage(param);  // 게시물 목록 받기
		dao.close();  // DB 연결 닫기

		//		MovieLikePage movieLikePage = new MovieLikePage();

		request.setAttribute("movieLists", movieLists);

		//		String pageStr = movieLikePage.pagingStr(totalCount, pageSize, blockPage, pageNum, request.getRequestURI());

		String str = MovieLikePage.pagingStr(totalCount, pageSize, blockPage,
				pageNum, request.getRequestURI());

		request.setAttribute("str", str);
	}

	public void ReviewInfo(HttpServletRequest request) {
		//request.setAttribute("view", "review");
		// Movie 리스트 출력 시작

		Map<String, Object> param = new HashMap<String, Object>();
		//					String searchField = request.getParameter("searchField");
		String searchWord = request.getParameter("searchWord");
		if(searchWord != null){
			//						param.put("searchField", searchField);
			param.put("searchWord", searchWord);
		}
		String pageTemp = request.getParameter("pageNum");
		int pageNum = 1;
		if(pageTemp != null && !(pageTemp.equals(""))){
			pageNum = Integer.parseInt(pageTemp);
		}
		SaveReviewDAO dao = new SaveReviewDAO();
		int pageSize = 10;
		int blockPage = 10;

		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("UserId");
		param.put("user_id", user_id);
		int totalCount = dao.selectCount(param);  // 게시물 수 확인

		int totalPage = (int)Math.ceil((double)totalCount/pageSize) ; // 101 ~ 109 / 10 10.1 => 11 올림
		// System.out.println(">>>>>>>>totalPage" + totalPage);
		// DAO를 생성해 DB에 연결
		int start = (pageNum - 1) * pageSize;
		int end = pageSize;
		param.put("start",start);
		param.put("end",end);

		//		param.put("searchWord", searchWord); // 나중에 검색값으로 바꿔줄것!!!!!!!!!

		List<ReviewDTO> reviewLists = dao.selectListPage(param);  // 게시물 목록 받기
		dao.close();  // DB 연결 닫기

		ReviewLikePage reviewLikePage = new ReviewLikePage();

		request.setAttribute("reviewLists", reviewLists);

		String str = ReviewLikePage.pagingStr(totalCount, pageSize, blockPage,
				pageNum, request.getRequestURI());

		//		String str = ReviewLikePage.pagingStr(totalCount, 10, 10,
		//	            1, request.getRequestURI());

		request.setAttribute("str", str);

	}

}
