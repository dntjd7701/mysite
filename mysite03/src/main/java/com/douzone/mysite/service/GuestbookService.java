package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	GuestbookRepository guestbookRepository;

	public List<GuestbookVo> getMessageList() {
		// TODO Auto-generated method stub
		return guestbookRepository.findAll();
	}

	public void deleteMessage(GuestbookVo vo) {
		guestbookRepository.delete(vo);
	}

	
	public void addMessage(GuestbookVo vo) {
		guestbookRepository.insert(vo);
	}
}
