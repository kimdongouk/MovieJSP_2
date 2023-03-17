package member;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.ReviewDAO;
import review.ReviewDTO;

@WebServlet("/member/mypage.do")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("mypage doGet()");
		
		String mode = request.getParameter("mode");
		
		//Profile에서 로그인 한 회원 정보 필요
		String id = request.getParameter("id");
		
		ReviewDAO rdao = new ReviewDAO();
		
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMemberById(id);
		
		dao.close();
		
		if(mode.equals("profile")) {
			System.out.println(mode);
			request.setAttribute("dto", dto);
			request.getRequestDispatcher("/Member/MemberView.jsp").forward(request, response);
		}
		else if(mode.equals("wishlist")) {
			//MovieLikeController에서 처리
			System.out.println(mode);
			request.setAttribute("dto", dto);
			request.getRequestDispatcher("/MovieLike/WishSelect.jsp").forward(request, response);
		}
		else if(mode.equals("mymovie")){
			System.out.println(mode);
			request.setAttribute("dto", dto);
			request.getRequestDispatcher("/Member/MyMovieList.jsp").forward(request, response);
		}
		else if(mode.equals("myreview")){
			System.out.println(mode);
			
			List<ReviewDTO> reviews = rdao.selectListById(id);
			
			request.setAttribute("reviews", reviews);
			request.getRequestDispatcher("/Member/MyReviewList.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
