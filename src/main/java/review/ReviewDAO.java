package review;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.JDBConnect;

public class ReviewDAO extends JDBConnect {
	//동욱님 DAO
	public List<ReviewDTO> myReviewList(String uid){
		List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
		
		String query = "SELECT rm.user_id, m2.title, rm.content, rm.rating_num, rm.postdate FROM"
				+ " (SELECT m.user_id AS user_id, movie_id, content, rating_num, postdate FROM"
				+ " MEMBER m, review r"
				+ " WHERE m.user_id = r.user_id AND m.user_id = '?') rm, movie m2"
				+ " WHERE rm.movie_id = m2.movie_id";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, uid);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setUser_id(rs.getString("user_id"));
				dto.setContent(rs.getString("content"));
				dto.setRating_num(rs.getString("rating_num"));
				dto.setPostdate(rs.getDate("postdate"));
				
				reviews.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reviews;
	}
	
	
	
	//지유님 DAO
	//리뷰 작성한 아이디가 몇개 있는지 카운트
	public int selectCount() {
		int totalCount = 0;
		String sql = "SELECT COUNT(review_id) as cnt FROM review";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				totalCount = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalCount;
	}
	
	/*
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;	
		String sql = "SELECT count(review_id) as cnt FROM review ";
		if(map.get("searchWord") != null) {
			sql += " WHERE " + map.get("searchField") + " like ";
			sql += "'%" + map.get("searchWord") + "%'";
		}
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {				
				totalCount = rs.getInt(1);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalCount;		
	}
	*/
	//리뷰 목록
	public List<ReviewDTO> selectList(){
		List<ReviewDTO> bbs = new ArrayList<ReviewDTO>();
		String sql = "SELECT * FROM review order by review_id desc";
		try {
			psmt = con.prepareStatement(sql);
			rs= psmt.executeQuery();
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setReview_id(rs.getInt("review_id"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setRating_num(rs.getString("rating_num"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setView(rs.getInt("view"));
				bbs.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bbs;
	}
	
	//특정 영화에 대한 리뷰 전체 목록 보기
	public List<ReviewDTO> selectList(String movie_id){
		List<ReviewDTO> bbs = new ArrayList<ReviewDTO>();
		String sql = "SELECT * FROM review where movie_id = ? order by review_id desc";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, movie_id);
			rs= psmt.executeQuery();
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setReview_id(rs.getInt("review_id"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setRating_num(rs.getString("rating_num"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setView(rs.getInt("view"));
				bbs.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bbs;
	}
	
	//특정 회원 리뷰 전체 보기
	public List<ReviewDTO> selectListById(String user_id){
		List<ReviewDTO> bbs = new ArrayList<ReviewDTO>();
		String sql = "SELECT m.title as t1, r.* FROM review r, movie m"
				+ " where r.movie_id = m.movie_id and user_id = ?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, user_id);
			rs= psmt.executeQuery();
			
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setReview_id(rs.getInt("review_id"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setRating_num(rs.getString("rating_num"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setView(rs.getInt("view"));
				dto.setTitle(rs.getString("t1"));
				bbs.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bbs;
	}
	/*
	//검색
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;	
		String sql = "SELECT count(*) as cnt FROM review ";
		if(map.get("searchWord") != null) {
			sql += " WHERE " + map.get("searchField") + " " + "LIKE '%" + map.get("searchWord") + "%'";
		}
		sql += "ORDER BY review_id DESC";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {				
				totalCount = rs.getInt(1);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalCount;		
	}
*/
	// 리뷰 목록(검색 포함)
	public List<ReviewDTO> selectList(Map<String, Object> map) { 
		List<ReviewDTO> bbs = new ArrayList<ReviewDTO>();	
		String sql = "SELECT * FROM review ";
		if(map.get("searchWord") != null) {
			sql += " WHERE " + map.get("searchField") + " like ";
			sql += "'%" + map.get("searchWord") + "%'";			
		}
		sql += " ORDER BY review_id desc ";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {				
				ReviewDTO dto = new ReviewDTO();
				dto.setUser_id(rs.getString("review_id"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setRating_num(rs.getString("rating_num"));
				dto.setContent(rs.getString("content"));
				dto.setView(rs.getInt("view"));
				dto.setPostdate(rs.getDate("postdate"));
				bbs.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bbs;		
	}

	//리뷰 작성
	public int insertWrite(ReviewDTO dto) {
		int totalCount = 0;	
		String sql = "insert into review (user_id, movie_id, rating_num, content) ";
		sql+= " values (?,?,?,?)";
		System.out.println(sql);
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, dto.getUser_id());
			psmt.setInt(2, dto.getMovie_id());
			psmt.setString(3, dto.getRating_num());
			psmt.setString(4, dto.getContent());
		
			totalCount = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalCount;		
	}

	//리뷰 수정
	public int updateEdit(ReviewDTO dto) {
		int totalCount = 0;	
		String sql = "update review set rating_num=?, content=? where review_id=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, dto.getRating_num());
			psmt.setString(2, dto.getContent());
			psmt.setInt(3, dto.getReview_id());
			totalCount = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalCount;		
	}
	
	//리뷰 카운트 증가
	public void updateViewCount(String review_id) {
		String query = "UPDATE review SET VIEW = VIEW +1 WHERE review_id = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, Integer.parseInt(review_id));	
			psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	//리뷰 상세보기(movie & review 조인)
	public ReviewDTO selectView(String review_id) {
		ReviewDTO dto  = new ReviewDTO();	
		String sql = "SELECT m.title, r.* FROM review r, movie m"
				+ " where r.movie_id = m.movie_id and review_id = ?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(review_id));
			rs = psmt.executeQuery();
			if(rs.next()) {
				dto.setReview_id(rs.getInt("review_id"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setRating_num(rs.getString("rating_num"));
				dto.setContent(rs.getString("content"));
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setView(rs.getInt("view"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setTitle(rs.getString("m.title"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;		
	}
	
	//삭제
	public int deletePost(String review_id) {
		int result = 0;
		String sql = "DELETE FROM review WHERE review_id = ?";

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(review_id));			
			result = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	
	// 게시물 목록(reviewPage)-수정
	public List<ReviewDTO> selectListPage(Map<String, Object> map) { 
		List<ReviewDTO> bbs = new ArrayList<ReviewDTO>();	
		String sql = "SELECT * FROM "
				+ " (SELECT tb.* , rownum rNum FROM " 
				+ " (SELECT * FROM review b ORDER BY num DESC) tb ) "
				+ " WHERE rNum BETWEEN ? AND ? ";
		try {
			psmt = con.prepareStatement(sql); 
			psmt.setInt(1, (int)map.get("start"));
			psmt.setInt(2, (int)map.get("end"));
			rs = psmt.executeQuery();
			while(rs.next()) {				
				ReviewDTO dto = new ReviewDTO();
				dto.setUser_id(rs.getString("user_id"));
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setRating_num(rs.getString("rating_num"));
				dto.setContent(rs.getString("content"));
				dto.setView(rs.getInt("view"));
				dto.setPostdate(rs.getDate("postdate"));
				bbs.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bbs;		
	}
	
	
}
