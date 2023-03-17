package member;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardDTO;
import utils.BoardPage;

@WebServlet("/member/adminpage.do")
public class AdminPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("adminpage doGet()");
		
		String mode = request.getParameter("mode");
		String id = request.getParameter("user_id");
		
		MemberDAO dao = new MemberDAO();
		int totNum = dao.memberCount();
		int initNum = 0;
		
		BoardDAO bdao = new BoardDAO();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//관리자만 볼 수 있는 회원 목록(list)
		if(mode.equals("memberlist")) {
			List<MemberDTO> members = dao.selectAll();
			
			request.setAttribute("members", members);
			request.setAttribute("totNum", totNum);
			request.setAttribute("initNum", (int)initNum);
			request.getRequestDispatcher("/Member/MemberList.jsp").forward(request, response);
			
			dao.close();
		}
		
		//=======공지사항 보기(=관리자 글만 보기)=======
		if(mode.equals("board")) {
			System.out.println(mode);
			String admin = "admin";
			
			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");
			
			if(searchWord != null) {
				map.put("searchField", searchField);
				map.put("searchWord", searchWord);
			}
			
			//페이지 처리
			ServletContext application = getServletContext();
			// 1.페이지 별로 몇개의 게시글이 있는지(현재 10)
	        int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
	        // 2. 하단에 페이지 번호 몇개씩 할 지(현재 5)
	        int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
			
	        // 3.현재 페이지 번호
	        int pageNum = 1;
	        // pageNum을 어디서 받아오나??? => utils.BoardPage에서 페이지 번호 클릭하면 pageNum 값 같이 전달
	        String pageTemp = request.getParameter("pageNum");
	        System.out.println(pageTemp);
	        if (pageTemp != null && !pageTemp.equals(""))
	            pageNum = Integer.parseInt(pageTemp);
	        
	        // 4.목록에 출력할 게시물 범위 계산
	        int start = (pageNum - 1) * pageSize + 1;  // 첫 게시물 번호
	        int end = pageNum * pageSize; // 마지막 게시물 번호
	        map.put("start", start);
	        map.put("end", end);
	        
	        // 5.(특정 아이디 + 검색 결과에 따른) 게시글  총 개수
			int totalCount = bdao.countBoard(map, admin);
	        
	        // 6.전체 페이지 개수
	        int totalPage = (int)Math.ceil((double)totalCount / pageSize);
	        
	        //특정 아이디 + 검색 결과에 따른 게시글 조회
			List<BoardDTO> boards = bdao.selectBoardPaging(map, admin);
			
			bdao.close();
			
			String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "/member/adminpage.do?mode=board");
			 map.put("pagingImg", pagingImg);
	        map.put("totalCount", totalCount);
	        map.put("totalPage", totalPage);
	        map.put("pageSize", pageSize);
	        map.put("pageNum", pageNum);
	        
			request.setAttribute("boards", boards);
			request.setAttribute("map", map);
			
			request.getRequestDispatcher("/Board/Board.jsp").forward(request, response);
		}
		
		//========관리자 포함 전체 게시물 보기========
		if(mode.equals("qna")) {
			System.out.println(mode);
			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");
			
			if(searchWord != null) {
				map.put("searchField", searchField);
				map.put("searchWord", searchWord);
			}
			
			//페이지 처리
			ServletContext application = getServletContext();
			// 1.페이지 별로 몇개의 게시글이 있는지(현재 10)
	        int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
	        // 2. 하단에 페이지 번호 몇개씩 할 지(현재 5)
	        int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
			
	        // 3.현재 페이지 번호
	        int pageNum = 1;
	        // pageNum을 어디서 받아오나??? => utils.BoardPage에서 페이지 번호 클릭하면 pageNum 값 같이 전달
	        String pageTemp = request.getParameter("pageNum");
	        System.out.println("pageTemp= " + pageTemp);
	        if (pageTemp != null && !pageTemp.equals(""))
	            pageNum = Integer.parseInt(pageTemp);
	        
	        // 4.목록에 출력할 게시물 범위 계산
	        int start = (pageNum - 1) * pageSize + 1;  // 첫 게시물 번호
	        int end = pageNum * pageSize; // 마지막 게시물 번호
	        map.put("start", start);
	        map.put("end", end);
	        
	        //회원 전체 게시물 총 개수
			int totalCount = bdao.countBoard(map);
	        
	        int totalPage = (int)Math.ceil((double)totalCount / pageSize);
			
			//전체 게시물 조회
			List<BoardDTO> boards = bdao.selectBoardPaging(map);
			
			bdao.close();
			
			String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "/member/adminpage.do?mode=qna");
			 map.put("pagingImg", pagingImg);
	        map.put("totalCount", totalCount);
	        map.put("totalPage", totalPage);
	        map.put("pageSize", pageSize);
	        map.put("pageNum", pageNum);
			
			request.setAttribute("map", map);
			request.setAttribute("boards", boards);
			request.getRequestDispatcher("/Board/QnA.jsp").forward(request, response);
		}
		
		//=======특정 게시물 보기=======
		if(mode.equals("view")) {
			System.out.println(mode);
			int b_id = Integer.parseInt((String)request.getParameter("b_id"));
			
			BoardDTO dto = bdao.selectBoard(b_id);
			bdao.visitCount(dto.getB_id());
			bdao.close();
			
			request.setAttribute("dto", dto);
			request.getRequestDispatcher("/Board/BoardView.jsp").forward(request, response);
		}
		
		//=======관리자 페이지의 회원 목록에서 회원 상세 보기=======
		if(mode.equals("adminview")) {
			MemberDTO dto = dao.getMemberById(id);
			dao.close();
			
			request.setAttribute("dto", dto);
			request.getRequestDispatcher("/Member/MemberListView.jsp").forward(request, response);
		}
		
		//=======게시물 삭제=======
		if(mode.equals("delete")) {
			System.out.println(mode);
			String board_id = request.getParameter("b_id");
			int b_id = Integer.parseInt(board_id);
			//System.out.println(b_id);
			
			int res = bdao.deleteBoard(b_id);
			if(res == 1) {
				// 바로 Board.jsp로 이동하면 DTO에 대한 아무 정보가 없으니까
				// 컨트롤러를 통해 Board.jsp로 접근해야 할 거 같음
				response.sendRedirect("/member/adminpage.do?mode=board");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("adminpage doPost()");
		
		String board_id = request.getParameter("b_id");
		
		String mode = request.getParameter("mode");
		BoardDAO bdao = new BoardDAO();
		
		if(mode.equals("delete")) {
			int b_id = Integer.parseInt(board_id);
			int res = bdao.deleteBoard(b_id);
			if(res == 1) {
				response.sendRedirect("/Board/Board.jsp");
			}
			bdao.close();
		}
		
	}

}
