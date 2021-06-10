package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	@Autowired
	private GalleryRepository galleryRepository;
	

	public void uploadImage(GalleryVo vo) {
		galleryRepository.insert(vo);
	}


	public List<GalleryVo> findAllUrl() {
		return galleryRepository.findAll();
	}


	public void deleteImage(int no) {
		galleryRepository.deleteImg(no);
	}
}
