package com.greedy.newworker.notice.service;

import java.util.UUID;
import java.io.IOException;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.newworker.notice.dto.NoticeDto;
import com.greedy.newworker.notice.entity.Notice;
import com.greedy.newworker.notice.repository.NoticeRepository;
import com.greedy.newworker.survey.dto.SurveyDto;
import com.greedy.newworker.util.FileUploadUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class NoticeService {
	
//	public static final int TEXT_PAGE_SIZE = 15;
//	public static final String SORT_BY = "notNo";
//	public static final String ACTIVE_STATUS = "Y";
	
	@Value("${file.file-dir}" + "/noticeimgs")
	private String FILE_DIR;
	@Value("${file.file-url}" + "noticeimgs/")
	private String FILE_URL;
	
	private final NoticeRepository noticeRepository;
	public final ModelMapper modelMapper;
	
	public NoticeService(NoticeRepository noticeRepository, ModelMapper modelMapper) {
		this.noticeRepository = noticeRepository;
		this.modelMapper = modelMapper;
	}
	
	/* 1. 공지 목록 조회 (페이징) */

	public Page<NoticeDto> selectNoticeListWithPaging(int page) {
		log.info("[NoticeService] getNoticeList Start ==============================");
		 
		Pageable pageable = PageRequest.of(page - 1, 20, Sort.by("notNo").descending());
		
		Page<Notice> noticeList = noticeRepository.findAll(pageable);
		Page<NoticeDto> noticeDtoList = noticeList.map(notice -> modelMapper.map(notice, NoticeDto.class));
		
		 log.info("noticeList : {}", noticeList.getContent());
		 
		 /* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
		 noticeDtoList.forEach(notice -> notice.setNoticeImageUrl(FILE_URL + notice.getNoticeImageUrl()));
		
		return noticeDtoList;
	}

	/* 2. 공지 상세 조회 - notNo로 공지 1개 조회(사원) */

	public NoticeDto selectNotice(Long notNo) {
		
		log.info("[NoticeService] selectNotice Start =====================" );
		log.info("[NoticeService] notNo : {}", notNo);
		
		Notice notice = noticeRepository.findByNotNo(notNo)
				.orElseThrow(() -> new IllegalArgumentException("등록된 공지 사항이 없습니다. notNo=" + notNo));
		NoticeDto noticeDto = modelMapper.map(notice, NoticeDto.class);
		
		
		
        log.info("[NoticeService] noticeDto : " + noticeDto);
		
		log.info("[NoticeService] selectNotice End =====================" );
		
		return noticeDto;
	}
	
	/* 3. 공지 등록 */
	@Transactional
	public NoticeDto insertNotice(NoticeDto noticeDto) {
		
		log.info("[NoticeService] insertNotice Start ===================================");
		log.info("[NoticeService] noticeDto : {}", noticeDto);
		String imageName = UUID.randomUUID().toString().replace("-", "");
		String replaceFileName = null;
		
		try {
			replaceFileName = FileUploadUtils.saveFile(FILE_DIR, imageName, noticeDto.getNoticeImage());
			noticeDto.setNoticeImageUrl(replaceFileName);
			
			log.info("[NoticeService] replaceFileName : {}", replaceFileName);
			
			noticeRepository.save(modelMapper.map(noticeDto, Notice.class));
			
		} catch (IOException e) {
			e.printStackTrace();
			try {
				FileUploadUtils.deleteFile(FILE_DIR, replaceFileName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		log.info("[NoticeService] insertNotice End ===================================");
		
		return noticeDto;
		
	}
	
	/* 4. 공지 수정 */
	@Transactional
	public NoticeDto updateNotice(NoticeDto noticeDto) {

		log.info("[NoticeService] updateNotice Start ===================================");
		log.info("[ProductService] noticeDto : {}", noticeDto);

		

		Notice oriNotice = noticeRepository.findById(noticeDto.getNotNo()).orElseThrow(
				() -> new IllegalArgumentException("등록된 공지사항이 없습니다. notNo=" + noticeDto.getNotNo()));
		
		oriNotice.update(noticeDto.getNotTitle(), 
				noticeDto.getNotContent(),
				noticeDto.getNotDate(),
				noticeDto.getNotUpdate(),
				noticeDto.getNotStatus(),
				noticeDto.getNotNo(),
				noticeDto.getNoticeImageUrl());
				
		
		noticeRepository.save(oriNotice);
		
		log.info("[NoticeService] updateNotice End ===================================");

		return noticeDto;
	}
	
	/* 5. 공지 삭제 */
		public void deleteNotice(Long notNo) {
			
			log.info("[NoticeService] deletenotice Start =========================");
			log.info("[NoticeService] noticeDto : {}", notNo);
			
			Notice foundNotice = noticeRepository.findById(notNo)
					.orElseThrow(() -> new RuntimeException("존재하지 않는 공지사항입니다."));
				 
			noticeRepository.delete(foundNotice);
			
			log.info("[NoticeService] deleteNotice End =========================");
			
		}
	
}
		
		

	

