package com.greedy.newworker.att.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.greedy.newworker.att.dto.AttDto;
import com.greedy.newworker.att.dto.AttTypeDto;
import com.greedy.newworker.att.entity.Att;
import com.greedy.newworker.att.entity.AttType;
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
		
		/*
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    Date empStart = attDto.getAttStart();
	    Date deadLine = sdf.parse("09:00:00");
	   
	    AttTypeDto attCheck = new AttTypeDto();
	   
	    if (empStart.compareTo(deadLine) > 0) {
	        log.info("[ AttService ] 지각, 출근시간 : {}", empStart);
	        attCheck.setAttTypeNo((long) 1);
	        attDto.setAttType(attCheck);
	       
	    } else if(empStart.compareTo(deadLine) < 0) {
	    	log.info("[ AttService ] 정상, 출근시간 : {}", empStart);
	    	attCheck.setAttTypeNo((long) 2);
	        attDto.setAttType(attCheck);
	    	
	    }
	    */
		
		AttTypeDto attCheck = new AttTypeDto();
		Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("HH시mm분ss초");
        
        LocalDateTime now = LocalDateTime.now();
		attDto.setAttStart(now);
        
        String str = sim.format(attDto.getAttStart());
        log.info("[AttService] 현재 시각 : {}", str);
        
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
        
		attRepository.save(modelMapper.map(attDto, Att.class));
		
		return attDto;
	}
	
	/* 퇴근 등록 
	 * 퇴근 등록까지는 되는데 행이 새로 생김 - 출근+퇴근 행이 불가능 */
	@Transactional
	public Object insertAttEnd(AttDto attDto) {
		
		Att foundAtt = attRepository.findById(attDto.getAttNo())
				.orElseThrow(() -> new RuntimeException("존재하지 않는 근태번호입니다."));
		
		AttTypeDto attCheck = new AttTypeDto();
		Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("HH시mm분ss초");
        
        LocalDateTime now = LocalDateTime.now();
		attDto.setAttEnd(now);
        
        String str = sim.format(attDto.getAttEnd());
        log.info("[AttService] 현재 시각 : {}", str);
        
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
        
		foundAtt.updateEnd(now);
		attRepository.save(foundAtt);
		
		return attDto;
	}
}