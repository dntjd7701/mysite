package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class ProproBoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public int insert( BoardVo boardVo ) {
		
		return sqlSession.insert( "proboard.insert", boardVo );
		
	}
	
	public List<BoardVo> findAllByPageAndKeword( String keyword, Integer page, Integer size ) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "keyword", keyword );
		map.put( "startIndex", (page-1)*size );
		map.put( "size", size );
		
		return sqlSession.selectList( "proboard.findAllByPageAndKeword", map );
	}

	public int update( BoardVo boardVo ) {
		
		return sqlSession.update( "proboard.update", boardVo );
		
	}
	
	public int delete( Long no, Long userNo ) {
		
		Map<String, Long> map = new HashMap<String, Long>();
		map.put( "no", no );
		map.put( "userNo", userNo );
		
		return sqlSession.delete( "proboard.delete", map );
	}

	public BoardVo findByNo( Long no ) {
		return sqlSession.selectOne( "proboard.findByNo", no );
	}
	
	public BoardVo findByNoAndUserNo( Long no, Long userNo ) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put( "no", no );
		map.put( "userNo", userNo );
		
		return sqlSession.selectOne( "proboard.findByNoAndUserNo", map );
	}

	public int updateHit( Long no ) {
		return sqlSession.update( "proboard.updateHit", no );
	}
	
	public int updateOrderNo( Integer groupNo, Integer orderNo ) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put( "groupNo", groupNo );
		map.put( "orderNo", orderNo );
		
		return sqlSession.update( "proboard.updateOrederNo", map );
	}
	
	public int getTotalCount( String keyword ) {
		return sqlSession.selectOne( "proboard.totalCount", keyword );
	}	
}
