package com.greedy.newworker.calendar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.greedy.newworker.calendar.entity.Calendar;

import io.lettuce.core.dynamic.annotation.Param;

public interface CalendarRepository extends JpaRepository<Calendar, Long>, JpaSpecificationExecutor<Calendar> {

	@EntityGraph(attributePaths= {"employee"})
	@Query("select c from Calendar c where c.calendarNo =:calendarNo and c.employee.employeeNo =:employeeNo ")
	Optional<Calendar> findByCalendarNoAndEmployee(@Param("calendarNo")Long calendarNo, @Param("employeeNo")Long employeeNo);

	Optional<Calendar> findByScheduleTitle(String string);

	@Query(value="SELECT * FROM TBL_CALENDAR "
			+ "WHERE EMPLOYEE_NO =:employeeNo "
			+ "AND TO_CHAR(SYSDATE, 'yy/MM/dd') >= TO_CHAR(START_DATE, 'yy/MM/dd') "
			+ "AND TO_CHAR(SYSDATE, 'yy/MM/dd') <= TO_CHAR (END_DATE, 'yy/MM/dd')", nativeQuery=true)
	List<Calendar> findByEmployeeNoAndStartDate(@Param("employeeNo")Long employeeNo);

}
