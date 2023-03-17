package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/member/islogin.do")
public class IsLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("isLogin doGet()");
		
		String mode = request.getParameter("mode");
		System.out.println(mode);
		
		HttpSession session = request.getSession();
		
		MemberDAO dao = new MemberDAO();
		
		System.out.println((String)session.getAttribute("UserId"));
		
		if(session.getAttribute("UserId") != null) {
			if(mode.equals("mylist")) {
				//특정 회원이 쓴 리뷰 개수
				int reviewCnt = dao.reviewCount((String)session.getAttribute("UserId"));
				//특정 회원이 저장한 즐겨찾기 개수
				int wishCnt = dao.wishCount((String)session.getAttribute("UserId"));
				//특정 회원의 평균 평점
				double avgRating = dao.avgRating((String)session.getAttribute("UserId"));
				
				request.setAttribute("reviewCnt", reviewCnt); //내가 쓴 리뷰 몇개인지 표시
				request.setAttribute("wishCnt", wishCnt); //내가 저장한 즐겨찾기 개수 표시
				request.setAttribute("avgRating", avgRating); //평균 평점
				request.getRequestDispatcher("/Member/MyPageMain.jsp").forward(request, response);
			}
			
			else if(mode.equals("wishlist")) {
				System.out.println("UserId = " + (String)session.getAttribute("UserId"));
				
				MemberDTO dto = dao.getMemberById((String)session.getAttribute("UserId"));
				
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("/MovieLike/WishSelect.jsp").forward(request, response);
			}
			
			else if(mode.equals("join")) {
				String msg = "<script>alert('이미 가입된 회원입니다')</script>";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("/Member/LoginForm.jsp").forward(request, response);
			}
			
			else if(mode.equals("login")) {
				response.sendRedirect("/Member/LoginForm.jsp");
			}
		}
		else {
			if(mode.equals("join")) {
				response.sendRedirect("/Member/Join.jsp");
			}
			else {
				response.sendRedirect("/Member/LoginForm.jsp");
			}
		}
		dao.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
