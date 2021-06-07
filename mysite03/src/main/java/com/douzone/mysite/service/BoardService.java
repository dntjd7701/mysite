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


	public int getTotalCount() {
		return boardRepository.totalCount();
	}


	public int getTotalPage() {
		return boardRepository.totalPage();
	}


	public void deleteList(int groupNo) {
		boardRepository.delete(groupNo);
	}


	public  List<BoardVo> viewList(int no) {
		return boardRepository.findByViewInfo(no);
	}


	public void writeList(BoardVo vo) {
		boardRepository.insert(vo); 
	}


	public int findMaxGroupNo() {
		return boardRepository.maxGroupNo();
	}
}
