//package com.greedy.newworker.rest.repository;
//
//import java.util.Optional;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.greedy.newworker.rest.entity.Rest;
//
//public interface RestRepository extends JpaRepository<Rest, Long>{
//
//	Page<Rest> findByRest(Pageable pageable, Rest rest);
//	
//	Optional<Rest> findByRestNo(Long restNo);
//
//}
