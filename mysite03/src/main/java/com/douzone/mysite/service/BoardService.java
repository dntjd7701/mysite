package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	BoardRepository boardRepository;
	
	
	public List<BoardVo> getList(HashMap<String, Integer> map){
		return boardRepository.findThisPage(map);
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


	public HashMap<String, Integer> paging(Integer p) {
		HashMap<String, Integer> map = new HashMap<>();
		int currentPage = 1;
		if(p != null) {
			currentPage = p;
		}
		int totalCount = boardRepository.totalCount();
		int totalPage = boardRepository.totalPage();

		
		int onePageCount = 5;
		int startPage = (currentPage - 1) * onePageCount;
		int count = totalCount - startPage;
		int lastPage = currentPage >= 3 ? (currentPage + 2 >= totalPage ? totalPage : currentPage + 2) : (5 > totalPage ? totalPage : 5);
		
		int firstPage = currentPage >= 3 ? currentPage - 2 : 1;
		
		if (currentPage + 2 >= totalPage && totalPage >= 5) {
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
		map.put("currentPage", currentPage);
		
		map.put("firstPage", firstPage);
		map.put("lastPage", lastPage);
		
		map.put("startPage", startPage);
		map.put("totalPage", totalPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("count", count);
		map.put("onePageCount", onePageCount);

		return map;
	}


	
}
