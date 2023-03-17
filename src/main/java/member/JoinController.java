package member;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/join.do")
public class JoinController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Join doGet()");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Join doPost()");
		
		 request.setCharacterEncoding("utf-8");
		 
		 MemberDAO dao = new MemberDAO();
		 
	 	//회원가입 insert 처리
		String id = request.getParameter("user_id");
		String pass = request.getParameter("user_pass");
		String nick = request.getParameter("nick_id");
		String name = request.getParameter("name");
		//select - option도 select name 값으로 받으면 됨
		String birth = request.getParameter("birth");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		
		String ask = request.getParameter("ask");
		String answer = request.getParameter("answer");
		
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String domain = request.getParameter("domain");
		
		//checkbox 중복 선택 시
		String[] fav_movie = request.getParameterValues("fav_movie");
		
		//int uClass = Integer.parseInt(request.getParameter("user_class"));
			
		MemberDTO dto = new MemberDTO();
		dto.setUser_id(id);
		dto.setUser_pass(pass);
		dto.setName(name);
		dto.setNick(nick);
		dto.setTel(tel);
		dto.setAsk(ask);
		dto.setAnswer(answer);
		dto.setBirth(birth + "-" + month + "-" + day);
		if(domain.equals("직접입력하기")) {
			dto.setEmail(email);
		}
		else {
			dto.setEmail(email + "@" + domain);
		}
		dto.setFav_movie(Arrays.toString(fav_movie));
		
		int res = dao.insertMember(dto);
		if(res == 1) {
			response.sendRedirect("/Member/Welcome.jsp");
		}
		else {
			System.out.println("회원 추가 에러 발생");
		}
	}
}
