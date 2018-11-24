package com.billing.test.services;

import java.util.LinkedHashMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DataIsolationServiceControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;

	@Test
	public void retrieveDataSearchAndWrite() throws Exception
	{
		String expected = "{\r\n" + 
				"    \"rows\": [\r\n" + 
				"        {\r\n" + 
				"            \"Id\": \"1\",\r\n" + 
				"            \"Name\": \"Sree\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"Id\": \"2\",\r\n" + 
				"            \"Name\": \"James\"\r\n" + 
				"        }\r\n" + 
				"    ],\r\n" + 
				"    \"statusCode\": 200,\r\n" + 
				"    \"message\": \"File creation successful\"\r\n" + 
				"}";
		
		
		//when
		ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/dis?report=October1", Object.class); 
		
		if(responseEntity.hasBody())
		{
			Gson gson = new Gson();
			String json = gson.toJson(responseEntity.getBody(), LinkedHashMap.class);
			//then
			JSONAssert.assertEquals(expected, json, JSONCompareMode.LENIENT);
		}
		
		 // then
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

	}
}
