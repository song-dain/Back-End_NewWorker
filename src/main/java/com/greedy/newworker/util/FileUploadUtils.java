package com.greedy.newworker.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {
	
	public static String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		
		// File의 경로를 객체화 한 Path라는 객체로 경로에 대한 가공
		Path uploadPath = Paths.get(uploadDir);
		
		// Path의 경로가 실제로 존재하지 않을 경우, 경로를 만든다.
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
													//본래 파일이 가지고 있던 확장자를 뽑아냄
		String replaceFileName = fileName + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		
		// stream을 이용해서 저장
		try(InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(replaceFileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("파일을 저장하지 못하였습니다. file name : " + fileName);
		}
		
		return replaceFileName;
		
	}

	public static void deleteFile(String uploadDir, String fileName) throws IOException {

		Path uploadPath = Paths.get(uploadDir);
		Path filePath = uploadPath.resolve(fileName);
		try {
			Files.delete(filePath);
		} catch (IOException e) {
			throw new IOException("파일을 삭제하지 못하였습니다. file name : " + fileName);
		}
		
	}

	
	
	
	
	
	
	
}
