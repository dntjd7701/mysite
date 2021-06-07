package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}	
	
	


	
	

	
	



	public boolean UpdateTitleAndContent(Long no, String modifiedTitle, String modifiedContent) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "update board"
					+ " set title=?, contents=?"
					+ " where no=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, modifiedTitle);
				pstmt.setString(2, modifiedContent);
				pstmt.setLong(3, no);
		
			
			int count = pstmt.executeUpdate();
			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {if(rs != null) {
					rs.close();
				}	
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return result;
	}


	public BoardVo findByIDs(Long contentNo) {
		BoardVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			
			String sql ="select group_no, order_no, depth, user_no"
					+ " from board"
					+ " where no=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, contentNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int groupNo = rs.getInt(1);
				int orderNo = rs.getInt(2);
				int depth = rs.getInt(3);
				Long userNo = rs.getLong(4);
				
				vo = new BoardVo();
				vo.setDepth(depth);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setUserNo(userNo);
				
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}



	public void updateNo(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "update board"
					+ " set order_no=order_no+1"
					+ " where group_no=? and order_no >= ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, vo.getGroupNo());
				pstmt.setInt(2, vo.getOrderNo());
		
			
				pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {if(rs != null) {
					rs.close();
				}	
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
	}



	public void upHits(Long matchNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "update board"
					+ " set hit=hit+1"
					+ " where no=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, matchNo);
			
				pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {if(rs != null) {
					rs.close();
				}	
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	public int totalCount() {
		return sqlSession.selectOne("board.totalCount");
	}

	public int totalPage() {
		return sqlSession.selectOne("board.totalPage");
	}

	
	public List<BoardVo> findThisPage(HashMap<String, Integer> map){
		return sqlSession.selectList("board.findThisPage", map);
	}
	
	public boolean delete(int groupNo) {
		int count = sqlSession.delete("board.delete", groupNo);
		return count==1;		
	}

	public List<BoardVo> findByViewInfo(int no){
		return sqlSession.selectList("board.findByViewInfo", no);
	}
	
	public int maxGroupNo() {
		return sqlSession.selectOne("board.maxGroupNo");
	}
	
	
	public boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.inser");
		return count == 1;
	}



}
