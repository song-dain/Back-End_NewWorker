package com.greedy.newworker.att.service;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.newworker.att.dto.AttDto;
import com.greedy.newworker.att.dto.AttTypeDto;
import com.greedy.newworker.att.entity.Att;
import com.greedy.newworker.att.repository.AttRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class AttService {
	
	private final AttRepository attRepository;
	private final ModelMapper modelMapper;
	
	
	private Date date;
    private SimpleDateFormat simpl;
    
    
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public SimpleDateFormat getSimpl() {
        return simpl;
    }
    public void setSimpl(SimpleDateFormat simpl) {
        this.simpl = simpl;
    }
	
	public AttService (AttRepository attRepository,
			ModelMapper modelMapper) {
		
		this.attRepository = attRepository;
		this.modelMapper = modelMapper;
		
	}
	
	/* 출근 등록
	 * 지각 체크까지 완료 */
	@Transactional
	public AttDto insertAttStart(AttDto attDto) throws ParseException {
		
		AttTypeDto attCheck = new AttTypeDto();
		Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("HH시mm분ss초");
        
        LocalDateTime now = LocalDateTime.now();
		attDto.setAttStart(now);
		
        String str = sim.format(attDto.getAttStart());
        
        int hour = Integer.parseInt(str.substring(0, 2));
        int minute = Integer.parseInt(str.substring(3, 5));
        
        if (hour >= 9 && minute > 01) {
        	log.info("[AttService] 현재 시각 : {}", str);
        	attCheck.setAttTypeNo((long)2);
        	attDto.setAttType(attCheck);
            log.info("[AttService] 지각");
            
        }  else if (str == null || str.isEmpty()){
            log.info("[AttService] 현재 시각 : {}", str);
            attCheck.setAttTypeNo((long) 4);
            attDto.setAttType(attCheck);
            log.info("[AttService] 결근");
        
		} else {
            log.info("[AttService] 현재 시각 : {}", str);
            attCheck.setAttTypeNo((long)1);
            attDto.setAttType(attCheck);
            log.info("[AttService] 정시출근");
            
        }
        
        Att att = attRepository.save(modelMapper.map(attDto, Att.class));
        log.info("[AttService] 출근 등록 시 생성되는 att.getAttNo() : {}", att.getAttNo());
        
        attDto = modelMapper.map(att, AttDto.class);
        
        log.info("[AttService] 출근 등록 시 생성되는 attDto.getAttNo() : {}", attDto.getAttNo());
		return attDto;
	}
	
	/* 퇴근 등록 */
	@Transactional
	public AttDto insertAttEnd(AttDto attDto) {
		
		AttTypeDto attCheck = new AttTypeDto();
		Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("HH시mm분ss초");
        
        LocalDateTime now = LocalDateTime.now();
		attDto.setAttEnd(now);
		attDto.setAttDate(now);
        String str = sim.format(attDto.getAttEnd());
        
        int hour = Integer.parseInt(str.substring(0, 2));
        int minute = Integer.parseInt(str.substring(3, 5));
        
        if (hour < 18) {
        	log.info("[AttService] 현재 시각 : {}", str);
        	attCheck.setAttTypeNo((long) 3);
        	attDto.setAttType(attCheck);
            log.info("[AttService] 조퇴");
            
        } else if (str == null || str.isEmpty()){
            log.info("[AttService] 현재 시각 : {}", str);
            attCheck.setAttTypeNo((long) 4);
            attDto.setAttType(attCheck);
            log.info("[AttService] 결근");
            
        } else if (hour >=18 ) {
        	log.info("[AttService] 현재 시각 : {}", str);
        	attCheck.setAttTypeNo((long) 1);
        	attDto.setAttType(attCheck);
            log.info("[AttService] 정상출근");
        }
        
        SimpleDateFormat work = new SimpleDateFormat("HH시mm분ss초");
        String start = work.format(attDto.getAttStart());
        
        SimpleDateFormat mon = new SimpleDateFormat("yyyy-MM");
        String attMon = mon.format(attDto.getAttStart());
        attDto.setAttMonth(attMon);
        
        Date startDate = null;
		try {
			startDate = new SimpleDateFormat("HH시mm분ss초").parse(start);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Date endDate = null;
		try {
			endDate = new SimpleDateFormat("HH시mm분ss초").parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        Integer diffSec = (int) ((endDate.getTime() - startDate.getTime()) / 1000); //초 차이
        log.info("[AttService] 근무 시간 : {}", diffSec);
        
		/* 근무시간 초단위 Integer diffsec -> xx분xx초 로 변환 */
        DecimalFormat df = new DecimalFormat("00");
        
        int remain = diffSec.intValue();
        attDto.setAttWorkTime(remain);
        
        String dayStr = "일 ";
        String hourStr = "시간 ";
        String minStr = "분 ";
        String secStr = "초";
        
        int day = remain / 86400;
        remain %= 86400;
 
        StringBuilder sb = new StringBuilder();
        if (day > 0)
        {
            sb.append(df.format(day));
            sb.append(dayStr);
        }
 
        int whour = remain / 3600;
        remain %= 3600;
        if (whour > 0)
        {
            sb.append(df.format(whour));
            sb.append(hourStr);
        }
 
        int wminute = remain / 60;
        remain %= 60;
        if (wminute > 0)
        {
            sb.append(df.format(wminute));
            sb.append(minStr);
        }
 
        int wsecond = remain;
        if (wsecond > 0)
        {
            sb.append(df.format(wsecond));
            sb.append(secStr);
        }
        
        log.info("[AttService] 근무 시간 변환 : {}", sb.toString());
		
        Att foundAtt = attRepository.findById(attDto.getAttNo())
				.orElseThrow(() -> new RuntimeException("존재하지 않는 근태번호입니다."));
		foundAtt.updateEnd(now);
		attRepository.save(foundAtt);
		attRepository.save(modelMapper.map(attDto, Att.class));
        
		return attDto;
	}
	
	/* 근태번호로 직원 근태 검색 -> 접속한 직원 본인의 근태 외 조회 불가능 */
	public AttDto selectAttDay(Long attNo, Long employeeNo ) {
		
		Att att = attRepository.findByAttNoAndEmployeeNo(attNo, employeeNo)
				.orElseThrow(() -> new IllegalArgumentException("근태 번호 또는 회원 번호가 틀립니다"));
		AttDto attDto = modelMapper.map(att, AttDto.class);
		
		log.info("[AttService] 접속자 번호 : {}", attDto.getEmployee().getEmployeeNo());
		log.info("[AttService] 근태 번호 : {}", attNo);
		return attDto;
	}
	
	/* 근태번호로 직원 검색 -> 관리자용이며 모든 직원 근태 조회 가능 */
	public AttDto selectAttDayAdmin(Long attNo) {
		
		Att att = attRepository.findById(attNo)
				.orElseThrow(() -> new IllegalArgumentException("근태 번호 또는 회원 번호가 틀립니다"));
		AttDto attDto = modelMapper.map(att, AttDto.class);
		
		log.info("[AttService] 접속자 번호 admin : {}", attDto.getEmployee().getEmployeeNo());
		log.info("[AttService] 근태 번호 admin : {}", attNo);
		return attDto;
	}
	
	/* 날짜(월), page처리 */
	public Page<AttDto> selectAttListByMonthAndEmployeeNo(int page, String attMonth, Long employeeNo) {
		
		log.info("[AttService] month : {}", attMonth);
		log.info("[AttService] employeeNo : {}", employeeNo);
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("attNo").descending());
		Page<Att> attList = attRepository.findByAttMonthAndEmployeeNo(pageable, attMonth, employeeNo);
		Page<AttDto> attDtoList = attList.map(att -> modelMapper.map(att, AttDto.class));
		
		return attDtoList;
	
	}

}