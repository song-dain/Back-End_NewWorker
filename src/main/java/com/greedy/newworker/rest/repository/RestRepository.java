package com.greedy.newworker.rest.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.newworker.rest.entity.Rest;

public interface RestRepository extends JpaRepository<Rest, Long>{


	/* 조회*/
	@EntityGraph(attributePaths= {"restNo"})
	Page<Rest> findAll(Pageable pageable);

	/* 휴가 인가 페이지 */
	@EntityGraph(attributePaths= {"restNo"})
	Page<Rest> findByRestOk(Pageable pageable, String restOk);
	
	
}
