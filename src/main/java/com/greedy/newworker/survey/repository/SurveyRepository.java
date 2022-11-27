package com.greedy.newworker.survey.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.newworker.survey.entity.Survey;



public interface SurveyRepository extends JpaRepository<Survey, Long>{

	/* 1. 공지 사항 리스트 조회 - 페이징*/
	Page<Survey> findBySurStatus(String surStatus, Pageable pageable);

	/* 2. 공지 상세 조회 - notNo로 공지 1개 조회(사원) */
	
	@Query("SELECT S " +
	         "FROM Survey s " +
			"WHERE s.surNo = :surNo ")
	Optional<Survey> findBySurNo(@Param("surNo") Long surNo);
	
	/* 3. 공지 등록 4. 공지 수정 => save 메소드가 이미 구현 되어 있음 */
	
	/* 5. 공지 삭제 */
//	Notice findByNotNo(Long notNo);
}
