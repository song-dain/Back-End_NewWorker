//package com.greedy.newworker.rest.service;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.greedy.newworker.rest.dto.RestDto;
//import com.greedy.newworker.rest.entity.Rest;
//import com.greedy.newworker.rest.repository.RestRepository;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Service
//public class RestService {
//
//	private final RestRepository restRepository;
//	private final ModelMapper modelMapper;
//	
//	public RestService(RestRepository restRepository, ModelMapper modelMapper) {
//		this.restRepository = restRepository;
//		this.modelMapper = modelMapper;
//	}
//		
//	/* 등록 */
//	@Transactional
//	public RestDto insertRest(RestDto restDto) {
//		
//		restRepository.save(modelMapper.map(restDto, Rest.class));
//		
//		return restDto;
//		
//	}
//
//	/* 조회 */
//	public Page<RestDto> selectRestListWithPaging(int page, Long restNo) {
//		
//		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("restNo").descending());
//		 
//		Rest foundRest = restRepository.findByRestNo(restNo)
//				 					.orElseThrow(() -> new RuntimeException("휴가가 존재하지 않습니다."));
//		 
//		Page<RestDto> restDtoList = restRepository.findByRest(pageable, foundRest)
//				 							.map(rest -> modelMapper.map(rest, RestDto.class));
//		
//		 
//		return restDtoList;
//	}
//}
