package board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/view.do")
public class ViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("view board doGet()");
		
		String mode = request.getParameter("mode");
		String board_id = request.getParameter("b_id");
		int b_id = Integer.parseInt(board_id);
		
		BoardDAO bdao = new BoardDAO();
		
		//1대1 문의 게시글 상세 보기
		if(mode.equals("viewqna")) {
			System.out.println(mode);
			
			BoardDTO dto = bdao.selectBoard(b_id);
			bdao.visitCount(dto.getB_id());
			
			bdao.close();
			
			request.setAttribute("dto", dto);
			request.getRequestDispatcher("/Board/QnAView.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
