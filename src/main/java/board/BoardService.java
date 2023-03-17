package board;

import java.util.List;

public interface BoardService {
	// 글 작성(insert)
	public int writeBoard(String id);
	
	// 전체 글 보기
	public List<BoardDTO> selectBoard();
}
