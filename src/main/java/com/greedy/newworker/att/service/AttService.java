package com.greedy.newworker.att.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
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
	
	public AttService (AttRepository attRepository,
			ModelMapper modelMapper) {
		
		this.attRepository = attRepository;
		this.modelMapper = modelMapper;
		
	}
	@Transactional
	public AttDto insertAttStart(AttDto attDto) throws ParseException {
		
	    SimpleDateFormat sdf = new SimpleDateFormat("HH24:MI:SS");
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
	   
		attRepository.save(modelMapper.map(attDto, Att.class));
		
		return attDto;
	}
}