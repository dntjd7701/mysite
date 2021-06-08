package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.AdminRepository;
import com.douzone.mysite.vo.SiteVo;

@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepository;
	
	public void updateMain(SiteVo vo) {
		adminRepository.update(vo);
	}
	public SiteVo findMain() {
		return adminRepository.find();
	}

}
