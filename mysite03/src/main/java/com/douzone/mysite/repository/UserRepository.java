package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	@Autowired
	private SqlSession sqlSession;
//	public boolean authUserUpdate(UserVo userVo) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		boolean result = false;
//
//		try {
////			conn = getConnection();
//			if ("".equals(userVo.getPassword())) {
//				String sql = "update user" 
//						+ " set name=?, gender=?" 
//						+ " where no=?";
//
//				pstmt = conn.prepareStatement(sql);
//				pstmt.setString(1, userVo.getName());
//				pstmt.setString(2, userVo.getGender());
//				pstmt.setLong(3, userVo.getNo());
//				int count = pstmt.executeUpdate();
//
//				result = count == 1;
//
//			} else {
//				String sql = "update user" 
//						+ " set name=?, gender=? password=?" 
//						+ " where no=?";
//
//				pstmt = conn.prepareStatement(sql);
//				pstmt.setString(1, userVo.getName());
//				pstmt.setString(2, userVo.getGender());
//				pstmt.setString(3, userVo.getPassword());
//				pstmt.setLong(4, userVo.getNo());
//
//				int count = pstmt.executeUpdate();
//
//				result = count == 1;
//			}
//
//		} catch (
//
//		SQLException e) {
//			e.printStackTrace();
//		}
//
//		return result;
//
//	}

	public boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		return count==1;
	}

	
	public UserVo findByEmailAndPassword (String email, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("e", email);
		map.put("p", password);
		
		return sqlSession.selectOne("user.findByEmailAndPassword", map);
	}

	public UserVo findByNo(Long no){
		return sqlSession.selectOne("user.findByNo", no);
		
	}


	public UserVo update(UserVo userVo) {
		return sqlSession.selectOne("user.update", userVo);
	}


	public UserVo findByEmail(String email) {
		return sqlSession.selectOne("user.findByEmail",email);
	}
}
