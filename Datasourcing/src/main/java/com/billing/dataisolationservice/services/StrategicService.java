package com.billing.dataisolationservice.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StrategicService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Async("asyncExecutor")
	public String getStrategyQuery(String report) {
		System.out.println("hai");
		final String StrategyUrl = "http:database/strategy/reportname)";
		String query = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("reportname",report);
		System.out.println("url:" + StrategyUrl);
		ResponseEntity<String> response = restTemplate.getForEntity(StrategyUrl, String.class, params);
		if (response != null) {
			System.out.println(response.getBody());
			query = response.getBody();
		}
		
		return query;
	}


}
