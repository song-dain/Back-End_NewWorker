package com.greedy.newworker.notice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.newworker.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>{

	/* 1. 공지 사항 리스트 조회 - 페이징*/
	Page<Notice> findByNotStatus(String notStatus, Pageable pageable);

	/* 2. 공지 상세 조회 - notNo로 공지 1개 조회(사원) */
	
	@Query("SELECT n " +
	         "FROM Notice n " +
			"WHERE n.notNo = :notNo ")
	Optional<Notice> findByNotNo(@Param("notNo") Long notNo);
	
	/* 3. 공지 등록 4. 공지 수정 => save 메소드가 이미 구현 되어 있음 */
	
	/* 5. 공지 삭제 */
//	Notice findByNotNo(Long notNo);
}
