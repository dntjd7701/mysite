package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	private static final int LIST_SIZE=3; // 리스트되는 게시물의 수
	private static final int PAGE_SIZE=5; // 페이지 리스트의 페이지 수, 즉, 한 화면에 1~5페이지 다음 페이지엔 6~10페이지로 화면이 나온다. 
	
	@Autowired
	private BoardRepository boardRepository;
	
	
	public Map<String, Object> getList(Integer currentPage, String keyword){
		/**
		 *  페이징 처리,
		 *  화면에 리스트 뽑아내기 
		 *  나 -> 한 단계씩 넘어감. 
		 * 강사님 -> 1~5, 6~10 블록 단위로 넘어감 
		 *  
		 * 보통 default로 전체 리스트를 뽑아내는 이유는,keyword와 관련되어 있다.
		 * 파라미터로 전달받은 keyword의 defaultValue 는 ""이므로 
		 * "" 일때엔 전체의 리스트를, 값이 들어가 있다면 그 조건에 맞는
		 * 페이지와 리스트를 추출하여야 한다. 
		 */
		
	
		/**
		 *  1. 페이징 처리 (ver 1.)
		 */
		
		int totalCount = boardRepository.totalCount(keyword);
		int totalPage = (int)Math.ceil((double)totalCount / LIST_SIZE);
		if(currentPage == null) {
			currentPage = 1;
		}
		
		// view에서 페이지 리스트 렌더링을 위한 데이터 값 계산 
		// 5개 단위로 처음부터. 즉, 0부터.
		int beginPage = (currentPage-1) * PAGE_SIZE;
		int lastPage = currentPage >= 3 ? (currentPage + 2 >= totalPage ? totalPage : currentPage + 2) : (PAGE_SIZE > totalPage ? totalPage : PAGE_SIZE);
		
		
		int firstPage = currentPage >= 3 ? currentPage - 2 : 1;
		if (currentPage + 2 >= totalPage && totalPage >= PAGE_SIZE) {
			firstPage = totalPage - 4;
		}
		
		
		
		// 왼쪽, 오른쪽
		int prevPage = currentPage - 1;
		if (prevPage <= 1) {
			prevPage = 1;
		}
		int nextPage = currentPage + 1;
		if (nextPage >= totalPage) {
			nextPage = totalPage;
		}
		if (totalPage == 0) {
			lastPage = currentPage;
		}
		
		
		/** paging ver2. 
		 * 
		 *  > 강사님 방식(블록 단위, 1~5, 6~10, 
		 *  1. 페이지 처리(필요 데이터 계산)
		 */
		

		//나는 쿼리를 새로 만들었으나, 메소드를 활용하여 해결.
		//전체 페이지의 수, 이를 위해 리스트 해야되는 총 갯수에 한페이지당 리스트를 원하는 list_size
		//를 나누어 천장값을 뽑아준다.
		//예로, 10개의 추출해야하는 리스트가 있다면, 10/3 = 3.XX
		//즉, 3개씩 나타내는 4개의 페이지 값을 가진다. 
	
		//현재 블록의 count수 , 
		//1~5페이지까지는 1이 제일 첫번째가 되며
		//6~10페이지까지는 2
		//7~10페이지까지는 3...
	
		//현재 페이지가 있는 블럭의 위치
		// >(다음페이지 블럭)에 사용될 수 있을 듯 하다. 
		
		//1. 페이징을 위한 기본 데이터 계산
//		int totalCount = boardRepository.totalCount( keyword ); 
//		int pageCount = (int)Math.ceil( (double)totalCount / LIST_SIZE );
//		int blockCount = (int)Math.ceil( (double)pageCount / PAGE_SIZE );
//		int currentBlock = (int)Math.ceil( (double)currentPage / PAGE_SIZE );
//				
//		/**
//		 *  2. 파라미터 page 값 검증. 
//		 */
//		if(currentPage > pageCount) {
//			currentPage = pageCount;
//			currentBlock = (int)Math.ceil( (double)currentPage / PAGE_SIZE );
//		}
//		
//		if(currentPage < 1) {
//			currentPage = 1;
//			currentBlock = 1;
//		}
//		
//		/**
//		 *  3. view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
//		 *  
//		 *  내 코드에서 curretnPage로 값을 짰을 때에는
//		 *  if (currentPage + 2 >= totalPage && totalPage >= 5) {
//			firstPage = totalPage - 4;
//			
//			이런 식의 조건을 걸어줘야 했지만, currentBlock 변수를 따로 만듦으로써
//			해소가 되었다. 
//		 */
//		
//		//3. view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
//		int beginPage = currentBlock == 0 ? 1 : (currentBlock - 1) * PAGE_SIZE + 1;
//		int prevPage = ( currentBlock > 1 ) ? ( currentBlock - 1 ) * PAGE_SIZE : 0;
//		int nextPage = ( currentBlock < blockCount ) ? currentBlock * PAGE_SIZE + 1 : 0;
//		int endPage = ( nextPage > 0 ) ? ( beginPage - 1 ) + LIST_SIZE : pageCount;
//		
		
		
		
		
		/**
		 *  2. 리스트 가져오기 
		 */
		List<BoardVo> list = boardRepository.findAllByKeywordAndPage(currentPage, keyword, LIST_SIZE);
		
		/**
		 *  3. map 에 저장 
		 */
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("currentPage", currentPage);
		map.put("totalCount", totalCount);
		map.put("totalPage", totalPage);
		
		map.put("firstPage", firstPage);
		map.put("lastPage", lastPage);
		map.put("beginPage", beginPage);
		
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("listSize", LIST_SIZE);
		map.put("pageSize", PAGE_SIZE);
	
		map.put("keyword", keyword);
		
		return map;
	}


	public void deleteList(Long no) {
		boardRepository.delete(no);
	}

	public  List<BoardVo> viewList(Long no) {
		List<BoardVo> viewInfos = boardRepository.findByViewInfo(no);
		 for(BoardVo vo : viewInfos) {
			 String content = vo.getContents();
			 String newlineAdapt = content.replaceAll("\r\n", "<br/>");
			 vo.setContents(newlineAdapt);
		 }
		 
		boardRepository.upHits(no);
		return viewInfos;
	}


	public void writeList(BoardVo vo) {
		boardRepository.insert(vo); 
	}


	public int findMaxGroupNo() {
		return boardRepository.maxGroupNo();
	}

	public BoardVo findByID(Long no) {
		return boardRepository.findByIDs(no);
	}
	public void doReply(BoardVo vo) {
		boardRepository.insert(vo);
	}


	public void upNo(BoardVo replyVo) {
		boardRepository.updateNo(replyVo);
	}


	public void updateView(Long no, String title, String contents) {
		boardRepository.UpdateTitleAndContent(no, title, contents);
	}
}
