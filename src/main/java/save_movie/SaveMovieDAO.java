package save_movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.JDBConnect;
import movie.MovieDTO;

public class SaveMovieDAO extends JDBConnect{
	
    public List<MovieDTO> getMovieList(String user_id) {
        
        String query = " SELECT * FROM save_movie "; 
        query += " INNER JOIN movie ON save_movie.movie_id = movie.movie_id ";  // 쿼리문 템플릿
        query += " where user_id = ? ";
        List<MovieDTO> listDto = new ArrayList<MovieDTO>();
        
        try {
            // 쿼리 실행
            psmt = con.prepareStatement(query); // 동적 쿼리문 준비
            psmt.setString(1, user_id);
            rs = psmt.executeQuery();  // 쿼리문 실행
            
            // 결과 처리
            while (rs.next()) {
            	MovieDTO dto = new MovieDTO();  // 회원 정보 DTO 객체 생성
                // 쿼리 결과로 얻은 회원 정보를 DTO 객체에 저장
            	int movie_id = rs.getInt("movie_id");
            	String title = rs.getString("title");
            	String cate = rs.getString("cate");
            	String open_date = rs.getString("open_date");
            	String actor = rs.getString("actor");
            	int m_time = rs.getInt("m_time");
            	String grade = rs.getString("grade");
            	
            	dto.setMovie_id(movie_id);
            	dto.setTitle(title);
            	dto.setCate(cate);
            	dto.setOpen_date(open_date);
            	dto.setActor(actor);
            	dto.setM_time(m_time);
            	dto.setGrade(grade);
            	
            	listDto.add(dto);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return listDto;  // DTO 객체 반환
    }

	public List<MovieDTO> selectListPage(Map<String, Object> map) { // 게시물 목록
		List<MovieDTO> bbs = new ArrayList<MovieDTO>();	
		
//		String sql = " SELECT * FROM ( SELECT * FROM save_movie as sm "
//				+ " inner join movie as m on sm.movie_id = m.movie_id where sm.user_id = ? ";
//				if(map.get("searchWord") != null) {
//					sql += " and m.title like ? ";
//				}
//				sql += " order by m.movie_id desc) as mm limit ?,? ";
				
		String sql = " SELECT sm.user_id, m.movie_id, m.title, m.cate, m.open_date, m.actor, m.m_time, m.grade, m.director ";
				sql += " FROM save_movie AS sm INNER JOIN movie AS m ON sm.movie_id = m.movie_id ";
				sql += " WHERE sm.user_id = ? ";
				if(map.get("searchWord") != null) {
					sql += " AND m.title LIKE '%"+map.get("searchWord")+"%' "; 
				}
				sql += " ORDER BY m.movie_id DESC ";
				sql += " LIMIT ?,? ";
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, (String)map.get("user_id")); // id값 받아와야 함
//			psmt.setString(2, "%"+ map.get("searchWord") +"%"); //title은 map에서 가져올 값의 가칭
			psmt.setInt(2, (int)map.get("start"));
			psmt.setInt(3, (int)map.get("end"));
			rs = psmt.executeQuery();
			while(rs.next()) {				
				MovieDTO dto = new MovieDTO();
				dto.setMovie_id(rs.getInt("m.movie_id"));
				dto.setTitle(rs.getString("m.title"));
				dto.setCate(rs.getString("m.cate"));
				dto.setOpen_date(rs.getString("m.open_date"));
				dto.setActor(rs.getString("m.actor"));
				dto.setM_time(rs.getInt("m.m_time"));
				dto.setGrade(rs.getString("m.grade"));
				dto.setDirector(rs.getString("m.director"));
				bbs.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bbs;		
	}

	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;	
//		String sql = "SELECT count(*) as cnt FROM MOVIE ";
//		if(map.get("searchWord") != null) {
//			sql += " WHERE " + map.get("searchField") + " like ";
//			sql += "'%" + map.get("searchWord") + "%'";
//		}
		String sql = " SELECT count(*) as cnt FROM save_movie as sm inner join ";
				sql += " movie as m on sm.movie_id = m.movie_id ";
				sql += " where sm.user_id = ? ";
		if(map.get("searchWord") != null) {
			sql += " and m.title like '%" + map.get("searchWord") + "%' ";
		}
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, (String)map.get("user_id"));
//			psmt.setString(2, (String)map.get("title"));
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

    public int insertSaveMovie(Map<String, Object> map) {
    	int i = 0;
		String sql = " insert into save_movie values(?,?) ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, (String)map.get("user_id"));
			psmt.setInt(2, (int)map.get("movie_id"));
			i = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return i;
    }
}
