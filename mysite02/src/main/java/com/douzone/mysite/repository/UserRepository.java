package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douzone.mysite.vo.UserVo;

public class UserRepository {

	public boolean authUserUpdate(UserVo userVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();
			if ("".equals(userVo.getPassword())) {
				String sql = "update user" 
						+ " set name=?, gender=?" 
						+ " where no=?";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userVo.getName());
				pstmt.setString(2, userVo.getGender());
				pstmt.setLong(3, userVo.getNo());
				int count = pstmt.executeUpdate();

				result = count == 1;

			} else {
				String sql = "update user" 
						+ " set name=?, gender=? password=?" 
						+ " where no=?";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userVo.getName());
				pstmt.setString(2, userVo.getGender());
				pstmt.setString(3, userVo.getPassword());
				pstmt.setLong(4, userVo.getNo());

				int count = pstmt.executeUpdate();

				result = count == 1;
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	public boolean insert(UserVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = " insert" + "   into user" + " values(null, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

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

	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = " select no, name, gender" + " from user" + " where email=? and password=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, email);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String gender = rs.getString(3);

				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
