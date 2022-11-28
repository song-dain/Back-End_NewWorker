package com.greedy.newworker.approval.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.newworker.approval.entity.AppLine;

public interface AppLineRepository extends JpaRepository<AppLine, Long> {

}
