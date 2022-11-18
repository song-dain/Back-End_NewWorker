package com.greedy.newworker.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.newworker.message.entity.SenderManagement;

public interface SenderManagementRepository extends JpaRepository<SenderManagement, Long> {

}
