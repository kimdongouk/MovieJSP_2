package member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/delete.do")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("delete doGet()");
		
		String id = request.getParameter("id");
		
		MemberDAO dao = new MemberDAO();
		int res = dao.deleteMember(id);
		
		if(res == 1) {
			System.out.println("삭제 완료");
			response.sendRedirect("/Member/MemberList.jsp");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("delete doPost()");
	}
}


