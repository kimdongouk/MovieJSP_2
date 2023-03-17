package movie;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBConnect;

public class MovieDAO extends JDBConnect{

	public MovieDTO getMovieInfo(String movie_id) {
		String query = "SELECT * FROM movie where movie_id = ?";  // 쿼리문 템플릿
		MovieDTO dto = new MovieDTO();  // 회원 정보 DTO 객체 생성
		try {
			// 쿼리 실행
			psmt = con.prepareStatement(query); // 동적 쿼리문 준비
			psmt.setInt(1, Integer.parseInt(movie_id));
			rs = psmt.executeQuery();  // 쿼리문 실행

			// 결과 처리
			if (rs.next()) {
				// 쿼리 결과로 얻은 회원 정보를 DTO 객체에 저장
				int movie_id_int = rs.getInt("movie_id");
				String title = rs.getString("title");
				String cate = rs.getString("cate");
				String open_date = rs.getString("open_date");
				String actor = rs.getString("actor");
				int m_time = rs.getInt("m_time");
				String grade = rs.getString("grade");
				String director = rs.getString("director");
				String image = rs.getString("image");
				String trailer = rs.getString("trailer");
				String summary = rs.getString("summary");
				
				dto.setMovie_id(movie_id_int);
				dto.setTitle(title);
				dto.setCate(cate);
				dto.setOpen_date(open_date);
				dto.setActor(actor);
				dto.setM_time(m_time);
				dto.setGrade(grade);
				dto.setDirector(director);
				dto.setImage(image);
				dto.setTrailer(trailer);
				dto.setSummary(summary);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return dto;  // DTO 객체 반환
	}
	
	public List<MovieDTO> movieListLimit() {
		List<MovieDTO> list = new ArrayList<MovieDTO>();
		try {
			String sql = "select * from movie limit 5";
			psmt = con.prepareStatement(sql); 
			rs = psmt.executeQuery(); 
			
			while (rs.next()) {                   
				MovieDTO dto = new MovieDTO();
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setTitle(rs.getString("title")); 
				dto.setCate(rs.getString("cate"));
				dto.setOpen_date(rs.getString("open_date"));
				dto.setActor(rs.getString("actor")); 
				dto.setM_time(rs.getInt("m_time"));
				dto.setGrade(rs.getString("grade"));
				dto.setDirector(rs.getString("director"));
				dto.setImage(rs.getString("image"));

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
	public List<MovieDTO> movieList() {
		List<MovieDTO> list = new ArrayList<MovieDTO>();
		try {
			String sql = "select * from movie";
			psmt = con.prepareStatement(sql); 
			rs = psmt.executeQuery(); 
			
			while (rs.next()) {                   
				MovieDTO dto = new MovieDTO();
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setTitle(rs.getString("title")); 
				dto.setCate(rs.getString("cate"));
				dto.setOpen_date(rs.getString("open_date"));
				dto.setActor(rs.getString("actor")); 
				dto.setM_time(rs.getInt("m_time"));
				dto.setGrade(rs.getString("grade"));
				dto.setDirector(rs.getString("director"));
				dto.setImage(rs.getString("image"));

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	//영화 상세 페이지에서 즐겨찾기에 영화 추가할 때
	public int insertSaveMovie(String user_id, String movie_id) {
		int res = 0;
		String query = "insert into save_movie (user_id, movie_id) "
				+ " values (?, ?)";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, user_id);
			psmt.setInt(2, Integer.parseInt(movie_id));
			res = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}


	/*
	 * public MovieDTO getMovieList(String movie_id) {
	 * 
	 * String query = "SELECT * FROM movie where movie_id = ?"; // 쿼리문 템플릿 MovieDTO
	 * dto = new MovieDTO(); // 회원 정보 DTO 객체 생성
	 * 
	 * try { // 쿼리 실행 psmt = con.prepareStatement(query); // 동적 쿼리문 준비
	 * psmt.setInt(1, Integer.parseInt(movie_id)); rs = psmt.executeQuery(); // 쿼리문
	 * 실행
	 * 
	 * // 결과 처리 if (rs.next()) { // 쿼리 결과로 얻은 회원 정보를 DTO 객체에 저장 movie_id =
	 * rs.getString("movie_id"); String title = rs.getString("title"); String cate =
	 * rs.getString("cate"); String open_date = rs.getString("open_date"); String
	 * actor = rs.getString("actor"); String m_time = rs.getString("m_time"); String
	 * grade = rs.getString("grade");
	 * 
	 * dto.setMovie_id(movie_id); dto.setTitle(title); dto.setCate(cate);
	 * dto.setOpen_date(open_date); dto.setActor(actor); dto.setM_time(m_time);
	 * dto.setGrade(grade);
	 * 
	 * } } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return dto; // DTO 객체 반환 }
	 */
	/*
	 * public List<MovieDTO> selectListPage(Map<String, Object> map) { // 게시물 목록
	 * List<MovieDTO> bbs = new ArrayList<MovieDTO>(); String sql =
	 * " SELECT * FROM (SELECT * FROM MOVIE WHERE title LIKE ? ORDER BY movie_id DESC) as m "
	 * ; sql += " LIMIT ?, ? "; try { psmt = con.prepareStatement(sql);
	 * psmt.setString(1, "%"+ map.get("title") +"%"); //title은 map에서 가져올 값의 가칭
	 * psmt.setInt(2, (int)map.get("start")); psmt.setInt(3, (int)map.get("end"));
	 * rs = psmt.executeQuery(); while(rs.next()) { MovieDTO dto = new MovieDTO();
	 * dto.setMovie_id(String.valueOf(rs.getInt("movie_id")));
	 * dto.setTitle(rs.getString("title")); dto.setCate(rs.getString("cate"));
	 * dto.setOpen_date(rs.getString("open_date"));
	 * dto.setActor(rs.getString("actor")); dto.setM_time(rs.getString("m_time"));
	 * dto.setGrade(rs.getString("grade"));
	 * dto.setDirector(rs.getString("director")); bbs.add(dto); } } catch (Exception
	 * e) { e.printStackTrace(); }
	 * 
	 * return bbs; }
	 */	
	/*
	 * public int selectCount(Map<String, Object> map) { int totalCount = 0; String
	 * sql = "SELECT count(*) as cnt FROM MOVIE "; if(map.get("searchWord") != null)
	 * { sql += " WHERE " + map.get("searchField") + " like "; sql += "'%" +
	 * map.get("searchWord") + "%'"; } try { stmt = con.createStatement(); rs =
	 * stmt.executeQuery(sql); if(rs.next()) { totalCount = rs.getInt(1);
	 * //totalCount = rs.getInt("cnt"); } } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return totalCount; }
	 */


}
