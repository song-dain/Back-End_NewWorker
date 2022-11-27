package com.greedy.newworker.survey.service;

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

import com.greedy.newworker.survey.dto.SurveyDto;
import com.greedy.newworker.survey.entity.Survey;
import com.greedy.newworker.survey.repository.SurveyRepository;
import com.greedy.newworker.util.FileUploadUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class SurveyService {
	
//	public static final int TEXT_PAGE_SIZE = 15;
//	public static final String SORT_BY = "notNo";
//	public static final String ACTIVE_STATUS = "Y";
	
	@Value("${file.file-dir}" + "/noticeimgs")
	private String FILE_DIR;
	@Value("${file.file-url}" + "/noticeimgs/")
	private String FILE_URL;
	
	private final SurveyRepository surveyRepository;
	public final ModelMapper modelMapper;
	
	public SurveyService(SurveyRepository surveyRepository, ModelMapper modelMapper) {
		this.surveyRepository = surveyRepository;
		this.modelMapper = modelMapper;
	}
	
	/* 1.설문 페이지 목록 조회 (페이징) */

	public Page<SurveyDto> selectSurveyListWithPaging(int page) {
		log.info("[SurveyService] getSurveyList Start ==============================");
		 
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("notNo").descending());
		
		Page<Survey> surveyList = surveyRepository.findAll(pageable);
		Page<SurveyDto> surveyDtoList = surveyList.map(survey -> modelMapper.map(survey, SurveyDto.class));
		
		 log.info("noticeList : {}", surveyList.getContent());
		
		return surveyDtoList;
	}

	/* 2. 설문 상세 조회 - surNo로 공지 1개 조회(사원) */

	public SurveyDto selectSurvey(Long surNo) {
		
		log.info("[SurveyService] selectSurvey Start =====================" );
		log.info("[SurveyService] surNo : {}", surNo);
		
		Survey survey = surveyRepository.findBySurNo(surNo)
				.orElseThrow(() -> new IllegalArgumentException("등록된 설문이 없습니다. surNo=" + surNo));
		SurveyDto surveyDto = modelMapper.map(survey, SurveyDto.class);
		
		
        log.info("[SurveyService] surveyDto : " + surveyDto);
		
		log.info("[SurveyService] selectSurvey End =====================" );
		
		return surveyDto;
	}
	
	/* 3. 설문 등록 */
	@Transactional
	public SurveyDto insertSurvey(SurveyDto surveyDto) {
		
		log.info("[SurveyService] insertSurvey Start ===================================");
		log.info("[SurveyService] surveyDto : {}", surveyDto);
		String imageName = UUID.randomUUID().toString().replace("-", "");
		String replaceFileName = null;
		
		try {
			replaceFileName = FileUploadUtils.saveFile(FILE_DIR, imageName, surveyDto.getSurveyImage());
			surveyDto.setSurveyImageUrl(replaceFileName);
			
			log.info("[SurveyService] replaceFileName : {}", replaceFileName);
			
			surveyRepository.save(modelMapper.map(surveyDto, Survey.class));
			
		} catch (IOException e) {
			e.printStackTrace();
			try {
				FileUploadUtils.deleteFile(FILE_DIR, replaceFileName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		log.info("[SurveyService] insertSurvey End ===================================");
		
		return surveyDto;
		
	}
	
	/* 4. 설문 수정 */
	@Transactional
	public SurveyDto updateSurvey(SurveyDto surveyDto) {

		log.info("[SurveyService] updateSurvey Start ===================================");
		log.info("[SurveyService] surveyDto : {}", surveyDto);

		

		Survey oriSurvey = surveyRepository.findById(surveyDto.getSurNo()).orElseThrow(
				() -> new IllegalArgumentException("등록된 설문이 없습니다. surNo=" + surveyDto.getSurNo()));
		
		oriSurvey.update(surveyDto.getSurTitle(), 
				surveyDto.getSurContent(),
				surveyDto.getSurDate(),
				surveyDto.getSurUpDate(),
				surveyDto.getSurStatus(),
				surveyDto.getSurNo(),
				surveyDto.getSurEndDate(),
				surveyDto.getSurStartDate(),
				surveyDto.getSurveyImageUrl());
				
		
		surveyRepository.save(oriSurvey);
		
		log.info("[SurveyService] updateSurvey End ===================================");

		return surveyDto;
	}
	
}
		
		

	

