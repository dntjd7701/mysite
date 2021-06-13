package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public int totalCount(String keyword) {
		return sqlSession.selectOne("board.totalCount", keyword);
	}
	

	public List<BoardVo> findAllByKeywordAndPage(Integer currentPage, String keyword, Integer size) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("startIndex", (currentPage-1)*size);
		map.put("size", size);
		return 	sqlSession.selectList("board.findAllByKeywordAndPage",map);
	}
	
	
	public boolean UpdateTitleAndContent(Long no, String modifiedTitle, String modifiedContent) {
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setTitle(modifiedTitle);
		vo.setContents(modifiedContent);
		int count = sqlSession.update("board.UpdateTitleAndContent", vo);
		return count == 1;
	}
	public boolean delete(Long no) {
		int count = sqlSession.delete("board.delete", no);
		return count==1;		
	}

	public List<BoardVo> findByViewInfo(Long no){
		return sqlSession.selectList("board.findByViewInfo", no);
	}
	
	public int maxGroupNo() {
		return sqlSession.selectOne("board.maxGroupNo");
	}
	
	
	public boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return count == 1;
	}

	public void upHits(Long matchNo) {
		sqlSession.update("board.upHits", matchNo);
	}

	public BoardVo findByIDs(Long contentNo) {
		return  sqlSession.selectOne("board.findByIDs", contentNo);
	}
	
	public void updateNo(BoardVo replyVo) {
		sqlSession.update("board.updateNo", replyVo);
	}







}



//package com.douzone.mysite.repository;
//
//import java.util.List;
//
//import org.apache.ibatis.session.SqlSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import com.douzone.mysite.vo.BoardVo;
//
//@Repository
//public class BoardRepository {
//	@Autowired
//	private SqlSession sqlSession;
//	
//	
//	public boolean UpdateTitleAndContent(Long no, String modifiedTitle, String modifiedContent) {
//		BoardVo vo = new BoardVo();
//		vo.setNo(no);
//		vo.setTitle(modifiedTitle);
//		vo.setContents(modifiedContent);
//		int count = sqlSession.update("board.UpdateTitleAndContent", vo);
//		return count == 1;
//	}
//
//	
//	public int totalCount() {
//		return sqlSession.selectOne("board.totalCount");
//	}
//
//	public int totalPage() {
//		return sqlSession.selectOne("board.totalPage");
//	}
//
//	
//	public List<BoardVo> findThisPage(HashMap<String, Integer> map){
//		return sqlSession.selectList("board.findThisPage", map);
//	}
//	
//	public boolean delete(Long no) {
//		int count = sqlSession.delete("board.delete", no);
//		return count==1;		
//	}
//
//	public List<BoardVo> findByViewInfo(Long no){
//		return sqlSession.selectList("board.findByViewInfo", no);
//	}
//	
//	public int maxGroupNo() {
//		return sqlSession.selectOne("board.maxGroupNo");
//	}
//	
//	
//	public boolean insert(BoardVo vo) {
//		int count = sqlSession.insert("board.insert", vo);
//		return count == 1;
//	}
//
//	public void upHits(Long matchNo) {
//		sqlSession.update("board.upHits", matchNo);
//	}
//
//	public BoardVo findByIDs(Long contentNo) {
//		return  sqlSession.selectOne("board.findByIDs", contentNo);
//	}
//	
//	public void updateNo(BoardVo replyVo) {
//		sqlSession.update("board.updateNo", replyVo);
//	}
//	
////	private Connection getConnection() throws SQLException {
////		Connection conn = null;
////		try {
////			Class.forName("org.mariadb.jdbc.Driver");
////			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=utf8";
////			conn = DriverManager.getConnection(url, "webdb", "webdb");
////		} catch (ClassNotFoundException e) {
////			System.out.println("드라이버 로딩 실패:" + e);
////		} 
////		
////		return conn;
////	}	
//
//}
