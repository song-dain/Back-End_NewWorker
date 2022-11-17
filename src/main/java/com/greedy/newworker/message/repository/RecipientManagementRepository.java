package com.greedy.newworker.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.newworker.message.entity.RecipientManagement;

public interface RecipientManagementRepository extends JpaRepository<RecipientManagement, Long> {

}
