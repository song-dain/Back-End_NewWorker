package com.greedy.newworker.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.greedy.newworker.calendar.entity.CalendarCategory;

import io.lettuce.core.dynamic.annotation.Param;

public interface CalendarCategoryRepository extends JpaRepository<CalendarCategory, Long> {

	CalendarCategory findByCalendarCategoryName(String calendarCategoryName);

}
