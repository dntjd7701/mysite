package com.douzone.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileUploadService {
	private static final String SAVE_PATH="/Users/kang-woosung/uploads-mysite/";
	private static final String URL_BASE="/images";
	/**
	 * 
	 * @param file1
	 * @return
	 * 
	 *  Service에서 해야할 것. 
	 *  1. (save) upload directory 지정 혹은 생성하기 
	 *  	- orgin file name -> changed name
	 *  	- 이름의 중복이 없도록 하기 위해. 이름의 재지정이 필요함. 
	 *  	- upload란 테이블을 생성하여 정보를 저장 및 중복값이 발생하지 않도록 설정.
	 *
	 *	2. (mapping:img의 경우)저장 장소는 변경될 수 있기 때문에, 프로젝트 밑에 저장하면 안된다. 
	 *		- 가상 url 설정할 것. 
	 *		- 물리 url과 가상 url의 mapping 설정할 것. 
	 *
	 * 
	 * 
	 */
	public String restore(MultipartFile file) {
		String url = null;
	try {
		if(file.isEmpty()) {
			return url;
		}
		
		/**
		 *  1. 원본 파일명 받기. 
		 *  2. 파일의 사이즈 받기.
		 *  3. 파일의 확장자 얻기.
		 */
		String orginFileName = file.getOriginalFilename();
//		long fileSize = file.getSize();
		
		// '.'을 포함하지 않고 끝까지. 
		String extName = orginFileName.substring(orginFileName.lastIndexOf('.')+1);
		String saveFileName = generateSaveFileName(extName);
		
		// check
//		System.out.println("########### " + orginFileName);
//		System.out.println("########### " + fileSize);
//		System.out.println("########### " + saveFileName);
		
		
			byte[] data = file.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(data);
			os.close();
			
			
			url = URL_BASE + "/" + saveFileName;
			
		} catch (IOException e) {
			throw new RuntimeException("file upload error" + e);
		}
	
		
		return url;
	}

	private String generateSaveFileName(String extName) {
		String filename = "";
		
		// Calendar를 이용, getInstance 메소드로 현재 시각 받기. 
		Calendar calendar = Calendar.getInstance();
		
		// 현재 시간으로 파일명 만듦.
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		
		return filename;
	}

}
