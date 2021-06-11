package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.ProproBoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class ProBoardService {
	private static final int LIST_SIZE = 5; //리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; //페이지 리스트의 페이지 수
	
	@Autowired
	private ProproBoardRepository proBoardRepository;
	
	public boolean addContents( BoardVo boardVo ) {
		if( boardVo.getGroupNo() != null ) {
			increaseGroupOrderNo( boardVo );
		}
		return proBoardRepository.insert( boardVo ) == 1;
	}
	
	public BoardVo getContents( Long no ) {
		BoardVo boardVo = proBoardRepository.findByNo( no );
		
		if( boardVo != null ) {
			proBoardRepository.updateHit( no );
		}
		
		return boardVo;
	}

	public BoardVo getContents( Long no, Long userNo ) {
		BoardVo boardVo = proBoardRepository.findByNoAndUserNo( no, userNo );
		return boardVo;
	}
	
	public boolean modifyContents( BoardVo boardVo ) {
		int count = proBoardRepository.update( boardVo );
		return count == 1;
	}
	
	public boolean deleteContents( Long boardNo, Long userNo ) {
		int count = proBoardRepository.delete( boardNo, userNo );
		return count == 1;
	}
	
	public Map<String, Object> getContentsList( int currentPage, String keyword ){
		
		//1. 페이징을 위한 기본 데이터 계산
		int totalCount = proBoardRepository.getTotalCount( keyword ); 
		int pageCount = (int)Math.ceil( (double)totalCount / LIST_SIZE );
		int blockCount = (int)Math.ceil( (double)pageCount / PAGE_SIZE );
		int currentBlock = (int)Math.ceil( (double)currentPage / PAGE_SIZE );
		
		//2. 파라미터 page 값  검증
		if( currentPage > pageCount ) {
			currentPage = pageCount;
			currentBlock = (int)Math.ceil( (double)currentPage / PAGE_SIZE );
		}		
		
		if( currentPage < 1 ) {
			currentPage = 1;
			currentBlock = 1;
		}
		
		//3. view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
		int beginPage = currentBlock == 0 ? 1 : (currentBlock - 1) * PAGE_SIZE + 1;
		int prevPage = ( currentBlock > 1 ) ? ( currentBlock - 1 ) * PAGE_SIZE : 0;
		int nextPage = ( currentBlock < blockCount ) ? currentBlock * PAGE_SIZE + 1 : 0;
		int endPage = ( nextPage > 0 ) ? ( beginPage - 1 ) + LIST_SIZE : pageCount;
		
		//4. 리스트 가져오기
		List<BoardVo> list = proBoardRepository.findAllByPageAndKeword( keyword, currentPage, LIST_SIZE );
		
		//5. 리스트 정보를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put( "list", list );
		map.put( "totalCount", totalCount );
		map.put( "listSize", LIST_SIZE );
		map.put( "currentPage", currentPage );
		map.put( "beginPage", beginPage );
		map.put( "endPage", endPage );
		map.put( "prevPage", prevPage );
		map.put( "nextPage", nextPage );
		map.put( "keyword", keyword );

		return map;
	}
	
	public boolean increaseGroupOrderNo( BoardVo boardVo ) {
		return proBoardRepository.updateOrderNo( boardVo.getGroupNo(), boardVo.getOrderNo() ) > 0;
	}

}
