package board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.JDBConnect;

public class BoardDAO extends JDBConnect{

	//공지사항 전체 보기
	public List<BoardDTO> selectBoard(){
		List<BoardDTO> boards = new ArrayList<BoardDTO>();

		String query = "select * from board order by postdate desc";
		try {
			psmt = con.prepareStatement(query);
			rs = psmt.executeQuery();

			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setB_id(rs.getInt("b_id"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setTitle(rs.getString("title"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setVisitcount(rs.getInt("visitcount"));
				dto.setRe_level(rs.getInt("re_level"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_sequence(rs.getInt("re_sequence"));
				boards.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boards;
	}

	//공지사항의 특정 게시글 상세 보기
	public BoardDTO selectBoard(int b_id) {
		String query = "select * from board where b_id = ?";
		BoardDTO dto = new BoardDTO();

		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, b_id);
			rs = psmt.executeQuery();
			if(rs.next()) {
				dto.setB_id(rs.getInt("b_id"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setTitle(rs.getString("title"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setVisitcount(rs.getInt("visitcount"));
				dto.setRe_level(rs.getInt("re_level"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_sequence(rs.getInt("re_sequence"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	// 특정 아이디가 쓴 게시글 확인 + 검색 조건
	public List<BoardDTO> selectBoard(Map<String, Object> map, String user_id) {
		String query = "select * from board where user_id = ?";

		if(map.get("searchWord") != null) {
			query += " and " + map.get("searchField") + " like '%"
					+ map.get("searchWord") + "%'";
		}

		query += "order by postdate desc";

		List<BoardDTO> boards = new ArrayList<BoardDTO>();

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, user_id);
			rs = psmt.executeQuery();

			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setB_id(rs.getInt("b_id"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setTitle(rs.getString("title"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setVisitcount(rs.getInt("visitcount"));
				dto.setRe_level(rs.getInt("re_level"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_sequence(rs.getInt("re_sequence"));

				boards.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boards;
	}

	//조회수 증가
	public void visitCount(int b_id) {
		int res = 0;
		String query = "update board set visitcount = visitcount + 1 where b_id = ?";

		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, b_id);
			res = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//게시글 삭제
	public int deleteBoard(int b_id) {
		int res = 0;
		String query = "delete from board where b_id = ?";

		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, b_id);
			res = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	//특정 아이디 제외하고 조회하기
	public List<BoardDTO> selectBoardNotId(String admin){
		List<BoardDTO> boards = new ArrayList<BoardDTO>();

		String query = "select * from board where user_id != ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, admin);
			rs = psmt.executeQuery();

			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setB_id(rs.getInt("b_id"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setTitle(rs.getString("title"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setVisitcount(rs.getInt("visitcount"));
				dto.setRe_level(rs.getInt("re_level"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_sequence(rs.getInt("re_sequence"));

				boards.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boards;
	}

	//특정 아이디 제외하고 조회하기 + 검색 조건
	public List<BoardDTO> selectBoardNotId(Map<String,Object> map, String admin){
		List<BoardDTO> boards = new ArrayList<BoardDTO>();

		String query = "select * from board where user_id != ?";

		if(map.get("searchWord") != null) {
			query += " and " + map.get("searchField") + " like '%"
					+ map.get("searchWord") + "%'";
		}

		query += " order by postdate desc";

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, admin);
			rs = psmt.executeQuery();

			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setB_id(rs.getInt("b_id"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setTitle(rs.getString("title"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setVisitcount(rs.getInt("visitcount"));
				dto.setRe_level(rs.getInt("re_level"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_sequence(rs.getInt("re_sequence"));

				boards.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boards;
	}

	// 특정 아이디가 쓴 게시글 개수
	public int countBoard(String user_id) {
		String query = "select count(user_id) as cnt from board where user_id = ?";
		int cnt = 0;

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, user_id);
			rs = psmt.executeQuery();

			if(rs.next()) {
				cnt = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}

	//검색 조건에 맞는 게시글 조회(페이징 X)
	public List<BoardDTO> selectBoard(Map<String, Object> map){
		List<BoardDTO> boards = new ArrayList<BoardDTO>();

		String query = "select * from ("
				+ " select @rownum:=@rownum+1 as rownum, b.* "
				+ " from board b, (select @rownum:=0) tmp";

		if(map.get("searchWord") != null) {
			query += " where " + map.get("searchField")
			+ " like '%" + map.get("searchWord") + "%'";
		}

		query += " order by postdate desc) tb";

		try {
			psmt = con.prepareStatement(query);
			rs = psmt.executeQuery();

			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setB_id(rs.getInt("b_id"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setTitle(rs.getString("title"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setVisitcount(rs.getInt("visitcount"));
				dto.setRe_level(rs.getInt("re_level"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_sequence(rs.getInt("re_sequence"));

				boards.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boards;
	}

	//검색 조건에 맞는 게시글 조회(페이징 O)
	public List<BoardDTO> selectBoardPaging(Map<String, Object> map){
		List<BoardDTO> boards = new ArrayList<BoardDTO>();

		String query = "select * from ("
				+ " select @rownum:=@rownum+1 as rownum, b.* "
				+ " from board b, (select @rownum:=0) tmp";

		if(map.get("searchWord") != null) {
			query += " where " + map.get("searchField")
			+ " like '%" + map.get("searchWord") + "%'";
		}

		query += " order by re_ref desc, re_sequence asc) tb"
				+ " where tb.rownum between ? and ?";

		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, (Integer)map.get("start"));
			psmt.setInt(2, (Integer)map.get("end"));
			rs = psmt.executeQuery();

			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setB_id(rs.getInt("b_id"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setTitle(rs.getString("title"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setVisitcount(rs.getInt("visitcount"));
				dto.setRe_level(rs.getInt("re_level"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_sequence(rs.getInt("re_sequence"));

				boards.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boards;
	}

	//검색 조건에 맞는 게시글 조회(페이징 O, 특정 아이디만)
	public List<BoardDTO> selectBoardPaging(Map<String, Object> map, String user_id){
		List<BoardDTO> boards = new ArrayList<BoardDTO>();

		String query = "select * from "
				+ " (select @rownum:=@rownum+1 as rownum, b.* "
				+ " from board b, (select @rownum:=0) tmp";

		if(map.get("searchWord") != null) {
			query += " where " + map.get("searchField")
			+ " like '%" + map.get("searchWord") + "%' and user_id = ? order by postdate desc) tb"; 
		}
		else {
			query += " where user_id = ? order by postdate desc) tb";
		}

		query += " where tb.rownum between ? and ?";

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, user_id);
			psmt.setInt(2, (Integer)map.get("start"));
			psmt.setInt(3, (Integer)map.get("end"));
			rs = psmt.executeQuery();

			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setB_id(rs.getInt("b_id"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setTitle(rs.getString("title"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setVisitcount(rs.getInt("visitcount"));
				dto.setRe_level(rs.getInt("re_level"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_sequence(rs.getInt("re_sequence"));

				boards.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boards;
	}
	
	//검색 조건에 맞는 게시글 조회(페이징 O, 특정 아이디 제외)
		public List<BoardDTO> selectBoardPaging2(Map<String, Object> map, String user_id){
			List<BoardDTO> boards = new ArrayList<BoardDTO>();

			String query = "select * from "
					+ " (select @rownum:=@rownum+1 as rownum, b.* "
					+ " from board b, (select @rownum:=0) tmp";

			if(map.get("searchWord") != null) {
				query += " where " + map.get("searchField")
				+ " like '%" + map.get("searchWord") + "%' and user_id != ? order by postdate desc) tb"; 
			}
			else {
				query += " where user_id != ? order by postdate desc) tb";
			}

			query += " where tb.rownum between ? and ?";

			try {
				psmt = con.prepareStatement(query);
				psmt.setString(1, user_id);
				psmt.setInt(2, (Integer)map.get("start"));
				psmt.setInt(3, (Integer)map.get("end"));
				rs = psmt.executeQuery();

				while(rs.next()) {
					BoardDTO dto = new BoardDTO();
					dto.setB_id(rs.getInt("b_id"));
					dto.setContent(rs.getString("content"));
					dto.setPostdate(rs.getDate("postdate"));
					dto.setTitle(rs.getString("title"));
					dto.setUser_id(rs.getString("user_id"));
					dto.setVisitcount(rs.getInt("visitcount"));
					dto.setRe_level(rs.getInt("re_level"));
					dto.setRe_ref(rs.getInt("re_ref"));
					dto.setRe_sequence(rs.getInt("re_sequence"));
					boards.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return boards;
		}

	//검색 조건에 맞는 게시글 전체 개수 1) 특정 아이디 O
	public int countBoard(Map<String, Object> map, String admin){
		int cnt = 0;

		String query = "select count(b_id) as cnt from board ";
		if(map.get("searchWord") != null) {
			query += "where " + map.get("searchField")
			+ " like '%" + map.get("searchWord") + "%'"
			+ "and user_id = ?";
		}
		else {
			query += " where user_id = ?";
		}

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, admin);
			rs = psmt.executeQuery();

			if(rs.next()) {
				cnt = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}

	//검색 조건에 맞는 게시글 전체 개수 2) 특정 아이디 제외
	public int countBoard2(Map<String, Object> map, String admin){
		int cnt = 0;

		String query = "select count(b_id) as cnt from board ";
		if(map.get("searchWord") != null) {
			query += "where " + map.get("searchField")
			+ " like '%" + map.get("searchWord") + "%'"
			+ "and user_id != ? order by postdate desc";
		}
		else {
			query += " where user_id != ? order by postdate desc";
		}

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, admin);
			rs = psmt.executeQuery();

			if(rs.next()) {
				cnt = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	//검색 조건에 맞는 게시글 전체 개수
	public int countBoard(Map<String, Object> map){
		int cnt = 0;

		String query = "select count(b_id) as cnt from board ";
		if(map.get("searchWord") != null) {
			query += "where " + map.get("searchField")
			+ " like '%" + map.get("searchWord") + "%'";
		}
		query += " order by postdate desc";

		try {
			psmt = con.prepareStatement(query);
			rs = psmt.executeQuery();

			if(rs.next()) {
				cnt = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}

	//=========INSERT============
	//게시글 작성
	public void insertBoard(BoardDTO dto) {
		String query = "insert into board (user_id, title, content)"
				+ " values (?, ?, ?)";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getUser_id());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());

			psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//답글 작성
	public int reInsertBoard(BoardDTO dto) {
		//답글의 부모글에 대한 값들을 답글이 초기화 값으로 가짐
		int re_ref = dto.getRe_ref();
		int re_level = dto.getRe_level();
		int re_seq = dto.getRe_sequence();
		
		int num = 0; //새롭게 작성될 답글의 게시물 번호를 새롭게 추가하기 위함
		String query = "";
		int res = 0;
		
		//1. 답글 번호 설정 
		//답글도 게시글이므로 현재 게시글의 가장 큰 번호에 +1을 해야 한다!
		try {
			query = "select max(b_id) as num from board";
			psmt = con.prepareStatement(query);
			rs = psmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt("num") + 1;
			}
			else {
				num = 1;
			}
			
			query = "";
			
		//2. 답글 순서 설정
		//같은 그룹 기준(re_ref)에서 
			query += "update board set re_sequence = re_sequence+1"
					+ " where re_ref = ? and re_sequence > ?";
			
			psmt = con.prepareStatement(query);
			psmt.setInt(1, dto.getRe_ref());
			psmt.setInt(2, dto.getRe_sequence());
			psmt.executeUpdate();
			
			System.out.println("level = " +re_level);
			System.out.println("seq = " + re_seq);
			
			query = "";
			
			query += "insert into board (b_id, user_id, title, content, "
					+ "re_ref, re_level, re_sequence)"
					+ " values(?, ?, ?, ?, ?, ?, ?)";
			
			psmt = con.prepareStatement(query);
			psmt.setInt(1, num);
			psmt.setString(2, dto.getUser_id());
			psmt.setString(3, dto.getTitle());
			psmt.setString(4, dto.getContent());
			//부모의 re_ref 값을 넣어줘야 함!!
			psmt.setInt(5, dto.getRe_ref());
			psmt.setInt(6, dto.getRe_level());
			psmt.setInt(7, dto.getRe_sequence());
			
			res = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}















