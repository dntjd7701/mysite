package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;

public class BoardRepository {
	
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
	
	

	public int maxGroupNo() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxGroupNo = 0;
		try {
			conn = getConnection();
			
			String sql = "select max(group_no) from board";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				maxGroupNo = rs.getInt(1);
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
		return maxGroupNo;
	}
	
	
	
	
	public boolean insert(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "insert" 
						+ " into board" 
						+ " values (null, ?, ?, now(), 0,"
						+ " ? , ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setInt(3, vo.getGroupNo());
				pstmt.setInt(4, vo.getOrderNo());
				pstmt.setInt(5, vo.getDepth());
				pstmt.setLong(6, vo.getUserNo());
		
		
			
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


	public List<BoardVo> findAll() {
		List<BoardVo> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			
			String sql ="select b.no, b.title, b.depth, b.hit, u.name,"
					+ " b.user_no, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as reg_date,"
					+ " b.group_no, b.order_no, b.depth "
					+ " from user u, board b"
					+ " where b.user_no=u.no"
					+ " order by group_no DESC, order_no asc";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				int depth = rs.getInt(3);
				int hit = rs.getInt(4);
				String name = rs.getString(5);
				Long user_no = rs.getLong(6);
				String reg_date = rs.getString(7);
				
				int groupNo = rs.getInt(8);
				int orderNo = rs.getInt(9);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setDepth(depth);
				vo.setHit(hit);
				vo.setUserName(name);
				vo.setUserNo(user_no);
				vo.setRegDate(reg_date);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				
				list.add(vo);
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
		
		return list;
	}
	
	public boolean delete(Long no) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql =
					" delete" +
					"  from board" +
					"  where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			int count = pstmt.executeUpdate();
			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
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



	public List<BoardVo> findByViewInfo(Long matchNo){
	List<BoardVo> list = new ArrayList<>();
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
			
	try {
		conn = getConnection();
		
		String sql = "select no, title, contents, hit, user_no"
				+ " from board"
				+ " where no=?";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, matchNo);
		
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			Long no = rs.getLong(1);
			String title = rs.getString(2);
			String contents = rs.getString(3);
			int hit = rs.getInt(4);
			Long user_no = rs.getLong(5);
			
			BoardVo vo = new BoardVo();
			vo.setNo(no);
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setHit(hit);
			vo.setUserNo(user_no);
			
			list.add(vo);
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
	
	return list;
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
		boolean result = false;

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
	
}
