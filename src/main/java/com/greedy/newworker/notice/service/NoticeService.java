package com.greedy.newworker.notice.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.newworker.notice.dto.NoticeDto;
import com.greedy.newworker.notice.entity.Notice;
import com.greedy.newworker.notice.repository.NoticeRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
public class NoticeService {
	
	public static final int TEXT_PAGE_SIZE = 15;
	public static final String SORT_BY = "notNo";
	public static final String ACTIVE_STATUS = "Y";
	
	private final NoticeRepository noticeRepository;
	public final ModelMapper modelMapper;
	
	public NoticeService(NoticeRepository noticeRepository, ModelMapper modelMapper) {
		this.noticeRepository = noticeRepository;
		this.modelMapper = modelMapper;
	}

	public Page<NoticeDto> selectNoticeList(int page) {

		Pageable pageable = PageRequest.of(page - 1, TEXT_PAGE_SIZE, Sort.by(SORT_BY).descending());
		Page<Notice> noticeList = noticeRepository.findByNotStatus(ACTIVE_STATUS, pageable);
		
		log.info("noticeList : {}", noticeList.getContent());
		
		return noticeList.map(notice -> modelMapper.map(notice, NoticeDto.class));
	}

	public void noticeRegist(NoticeDto notice) {

		noticeRepository.save(modelMapper.map(notice, Notice.class));
		
	}

	public NoticeDto findNoticeByNo(Long notNo) {

		Notice notice = noticeRepository.findById(notNo).get();
		
		
		return modelMapper.map(notice, NoticeDto.class);
	}
	
	public NoticeDto seleNoticeDetail(Long notNo) {
		
		Notice notice = noticeRepository.findByNotNo(notNo);
		
		return modelMapper.map(notice, NoticeDto.class);
	}

	public void modifyEmployee(NoticeDto noticeModify) {
		
		Notice savedNotice = noticeRepository.findByNotNo(noticeModify.getNotNo());
		savedNotice.setNotTitle(noticeModify.getNotTitle());
		savedNotice.setNotContent(noticeModify.getNotContent());
	}


	public void deleteNot(Long notNo) {
		
		Notice notice = noticeRepository.findByNotNo(notNo);
		
		notice.setNotStatus("N");
		
	}
	
}
