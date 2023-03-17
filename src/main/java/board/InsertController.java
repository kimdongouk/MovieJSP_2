package board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board/insert.do")
public class InsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("insert doGet()");
		
		String mode = request.getParameter("mode");
		HttpSession session = request.getSession();
		
		//========"/Board/Board.jsp", "/Board/QnA.jsp" 에서 글 쓰기 눌렀을 때
		if(mode.equals("boardwrite")) {
			System.out.println(mode);
			request.setAttribute("mode", mode);
			request.getRequestDispatcher("/Board/Insert.jsp").forward(request, response);
		}
		
		if(mode.equals("answrite")) {
			System.out.println(mode);
			String board_id = request.getParameter("b_id");
			int b_id = Integer.parseInt(board_id);
			String user_id = (String)session.getAttribute("UserId");
			
			BoardDAO bdao = new BoardDAO();
			BoardDTO dto = bdao.selectBoard(b_id);
			
			request.setAttribute("user_id", user_id);
			request.setAttribute("dto", dto);
			request.setAttribute("mode", mode);
			request.getRequestDispatcher("/Board/Insert.jsp").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("insert doPost()");
		
		String mode = request.getParameter("mode");
		BoardDAO bdao = new BoardDAO();
		
		//====== "/Board/Insert.jsp" 에서 작성한 공지사항 혹은 QnA 답글 작성 완료 클릭했을 때
		if(mode.equals("boardwrite")) {
			System.out.println(mode);
			
		}
		
		if(mode.equals("answrite")) {
			System.out.println(mode);
			
			int b_id = Integer.parseInt((String)request.getParameter("b_id"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int re_sequence = Integer.parseInt((String)request.getParameter("re_sequence"));
			int re_ref = Integer.parseInt((String)request.getParameter("re_ref"));
			int re_level = Integer.parseInt((String)request.getParameter("re_level"));
			String user_id = request.getParameter("user_id");
			
			BoardDTO dto = new BoardDTO();
			dto.setTitle(title);
			dto.setContent(content);
			dto.setRe_ref(re_ref);
			dto.setRe_level(re_level+1);
			dto.setRe_sequence(re_sequence+1);
			dto.setUser_id(user_id);
			dto.setB_id(b_id);
			
			System.out.println(dto.toString());
			
			int res = bdao.reInsertBoard(dto);
			
			if(res == 1) {
				System.out.println("댓글 입력 완료");
				response.sendRedirect("/member/adminpage.do?mode=qna");
			}
			else {
				System.out.println("댓글 입력 실패");
			}
		}
	}
}
