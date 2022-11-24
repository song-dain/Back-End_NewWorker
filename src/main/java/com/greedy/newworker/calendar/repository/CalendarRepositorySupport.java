package com.greedy.newworker.calendar.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import static com.greedy.newworker.calendar.entity.QCalendar.calendar;
import com.greedy.newworker.calendar.dto.Criteria;
import com.greedy.newworker.calendar.entity.Calendar;
import com.greedy.newworker.employee.entity.Employee;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CalendarRepositorySupport implements CalendarRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public CalendarRepositorySupport(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<Calendar> scheduleFilter(Criteria criteria, Employee employee) {

		BooleanBuilder builder = new BooleanBuilder();

		 /* 내 일정 */ 
		if(criteria.getMySchedule() != null && criteria.getMySchedule().equals("mySchedule")) {
			builder.and(calendar.calendarCategory.calendarCategoryName.eq(criteria.getMySchedule()));
			builder.and(calendar.employee.eq(employee));
		}
		
		 /*연차*/ 
		 if(criteria.getDayOff() != null && criteria.getDayOff().equals("dayOff")) {
			 builder.and(calendar.calendarCategory.calendarCategoryName.eq(criteria.getDayOff()));
			 builder.and(calendar.employee.eq(employee));
		 }
		 
		 /* 부서 일정 */
		 if(criteria.getDeptSchedule() != null && criteria.getDeptSchedule().equals("deptSchedule")) {
			 builder.and(calendar.calendarCategory.calendarCategoryName.eq(criteria.getDeptSchedule()));
			 builder.and(calendar.dep.eq(employee.getDep()));
		 }
		 
		 /* 전사 일정 */
		 if(criteria.getComSchedule() != null && criteria.getComSchedule().equals("comSchedule")) {
			 builder.and(calendar.calendarCategory.calendarCategoryName.eq(criteria.getDeptSchedule()));
		 }
		 
		 List<Calendar> fetch = queryFactory
				 .selectFrom(calendar)
				 .where(builder)
				 .fetch();

		return fetch;
	}

}
