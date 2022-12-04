package com.greedy.newworker.util;




import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisUtil {
	
	/* 이메일 인증번호를 담아놓기 위한 RedisUtil */
	
	private final StringRedisTemplate redisTemplate;
	
	 public String getData(String key) { // key를 통해 value(데이터)를 얻는다.
	        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
	        log.info(key);
	        return valueOperations.get(key);
	    }

	    public void setDataExpire(String key, String value, long duration) { 
	    //  duration 동안 (key, value)를 저장한다.
	        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
	        Duration expireDuration = Duration.ofMillis(duration);
	        valueOperations.set(key, value, expireDuration);
	    }

	    public void deleteData(String key) {
	    // 데이터 삭제
	        redisTemplate.delete(key);
	    }
	}