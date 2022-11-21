package com.greedy.newworker.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



import com.greedy.newworker.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>{


	Page<Notice> findByNotStatus(String notStatus, Pageable pageable);

	Notice findByNotNoAndNotStatus(Long notNo, String activeStatus);

	Notice findByNotNo(Long notNo);

	
}
