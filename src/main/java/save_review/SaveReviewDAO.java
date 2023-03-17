package save_review;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.JDBConnect;
import review.ReviewDTO;

public class SaveReviewDAO extends JDBConnect{
	
	public List<ReviewDTO> selectListPage(Map<String, Object> map) { // 게시물 목록
		List<ReviewDTO> bbs = new ArrayList<ReviewDTO>();
//		String sql = " SELECT * FROM (SELECT * FROM REVIEW WHERE content LIKE ? ";
//		if(map.get("searchWord") != null) {
//			sql += " WHERE content like ? ";
//		}
//		sql += " ORDER BY review_id DESC) as r LIMIT ?, ? ";
		
		//특정 user가 저장한 리뷰 목록 조회
		String sql = " SELECT r.review_id, r.user_id, r.movie_id, r.rating_num, r.content, r.view, r.postdate ";
		sql += " FROM save_review AS sr INNER JOIN review AS r ON sr.review_id = r.review_id ";
		sql += " WHERE sr.user_id = ? ";
		
		if(map.get("searchWord") != null) {
			sql += " AND r.content LIKE '%"+map.get("searchWord")+"%' "; 
		}
		sql += " ORDER BY r.review_id DESC ";
		sql += " LIMIT ?,? ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, (String)map.get("user_id")); //title은 map에서 가져올 값의 가칭
			psmt.setInt(2, (int)map.get("start"));
			psmt.setInt(3, (int)map.get("end"));
			rs = psmt.executeQuery();
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setReview_id(rs.getInt("review_id"));
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

	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;	
//		String sql = "SELECT count(*) as cnt FROM REVIEW ";
//		if(map.get("searchWord") != null) {
//			sql += " WHERE content like ";
//			sql += "'%" + map.get("searchWord") + "%'";
//		}
		String sql = " SELECT count(*) as cnt FROM save_review as sr inner join ";
		sql += " review as r on sr.review_id = r.review_id ";
		sql += " where sr.user_id = ? ";
		if(map.get("searchWord") != null) {
			sql += " and r.content like '%" + map.get("searchWord") + "%' ";
		}
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, (String)map.get("user_id"));
			rs = psmt.executeQuery();
			if(rs.next()) {
				totalCount = rs.getInt(1);
				//totalCount = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return totalCount;		
	}

	
}
