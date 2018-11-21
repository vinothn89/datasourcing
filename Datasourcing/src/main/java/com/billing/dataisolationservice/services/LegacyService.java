package com.billing.dataisolationservice.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LegacyService {
	
	@Autowired
	RestTemplate restTemplate;
	
	public String getLegacyQuery(String report) {
		System.out.println("hai");
		final String LegacyUrl = "http://localhost:8081/lookupservice";
		String query = null;
		HashMap<String, String> map = new HashMap<>(); 
		map.put("reportName",report);
		final HttpEntity<HashMap<String, String>> params = new HttpEntity<HashMap<String, String>>(map);
		new HashMap<String, String>();
		
		System.out.println("url:" + LegacyUrl);
		/*ResponseEntity<String> response = restTemplate.exchange(LegacyUrl, HttpMethod.GET, params, String.class);
		if (response != null) {
			System.out.println(response.getBody());
			query = response.getBody();
		}*/
		
		return "select * from task";
	}

}
