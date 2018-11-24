package com.billing.test.services;

import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.HttpStatus;

import com.billing.dataisolationservice.helper.CustomOutputStreamWriter;
import com.billing.dataisolationservice.helper.GenericResponse;

@RunWith(MockitoJUnitRunner.class)
public class CustomeOutputStreamWriterTest {

	@Mock
	GenericResponse response;
	
	@InjectMocks
	CustomOutputStreamWriter cosw;
	
	List<Map<String,Object>> result = null;
	
	String expectedResult;
	
	@Before
	public void setUp()
	{
		expectedResult = "{\r\n" + 
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
							"    \"mapData\": null,\r\n" + 
							"    \"statusCode\": 200,\r\n" + 
							"    \"message\": \"File creation successful\"\r\n" + 
							"}";
		Map<String, Object> map = new LinkedHashMap<String,Object>();
		map.put("Id", "1");
		map.put("Name", "Sree");
		
		Map<String, Object> map1 = new LinkedHashMap<String,Object>();
		map1.put("Id", "2");
		map1.put("Name", "James");
		
		result = new ArrayList<Map<String,Object>>();
		result.add(map);
		result.add(map1);
	}
	
	@Test
	public void testCustomOutputStreamWriter() throws WebApplicationException, IOException, JSONException
	{
		when(response.getRows()).thenReturn(result);
		when(response.getStatusCode()).thenReturn(HttpStatus.OK.value());
		when(response.getMessage()).thenReturn("File creation successful");
		when(response.getMapData()).thenReturn(null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		StreamingOutput output = cosw.writeJsonToFile(response);
		output.write(baos);
		byte[] data = baos.toByteArray();
		String coswJsonOutput = new String(data, StandardCharsets.UTF_8);
		
		JSONAssert.assertEquals(expectedResult, coswJsonOutput, JSONCompareMode.LENIENT);
		
	}
}
