package com.billing.dataisolationservice.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.billing.dataisolationservice.dao.DataIsolationServiceAbstractionDao;
import com.billing.dataisolationservice.dao.DataIsolationServiceDao;
import com.billing.dataisolationservice.helper.DBType;
import com.billing.dataisolationservice.helper.DbContextHolder;
import com.billing.dataisolationservice.services.LegacyService;
import com.billing.dataisolationservice.services.StrategicService;
import com.billing.datasourceisolationservice.domain.ConfigInfo;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@EnableAsync
public class DataIsolationServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(DataIsolationServiceController.class);
	
	private ObjectMapper objectMapper;
	
	@Autowired
	DataIsolationServiceDao dao;
	
	@Autowired
	DataIsolationServiceAbstractionDao daoabs;
	
	@Autowired
	LegacyService legacyservice;
	
	@Autowired
	StrategicService strategyservice;
	
	@RequestMapping(value = ""+"",method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Map<String,Object>>> getDataSearchWithAbstraction(
			@RequestParam(value = "report") String report, RedirectAttributes redirectAttributes, ModelMap model)
	        throws IOException {
		
		
		List<Map<String,Object>> result = null;
		
		ConfigInfo info = dao.getLocation(report);
		String sql = null;
		if (info.getLocation().equalsIgnoreCase("Legacy")) 
		{
			sql = legacyservice.getLegacyQuery(report);
		}
		else {
			sql = strategyservice.getStrategyQuery(report);
		}
		
		if(null!=info.getDatabase())
		{
			DbContextHolder.setDbType(DBType.valueOf(info.getDatabase()));
		}
		
	result = daoabs.executeQuery(sql);
	
	return new ResponseEntity<ArrayList<Map<String, Object>>>((ArrayList<Map<String, Object>>) result,
			HttpStatus.OK);
	
	}
	
	@RequestMapping(value = "/dis",method = RequestMethod.GET)
	public ResponseEntity<StreamingResponseBody> getDataSearchAndWrite(
			@RequestParam(value = "report") String report, RedirectAttributes redirectAttributes, ModelMap model)
	        throws IOException, InterruptedException, ExecutionException {
		
		logger.info("Enter method - getDataSearchAndWrite");
		
		List<Map<String,Object>> result = null;
		
		ConfigInfo info = dao.getLocation(report);
		String sql = null;
		
		if (info.getLocation().equalsIgnoreCase("Legacy")) {
			sql = legacyservice.getLegacyQuery(report);
		}
		else {
			sql = strategyservice.getStrategyQuery(report);
		}
		
		if(null!=info.getDatabase())
		{
			DbContextHolder.setDbType(DBType.valueOf(info.getDatabase()));
		}
		
		result = daoabs.executeQuery(sql);
	    
		final List<Map<String,Object>> ouputJson = result;
		
		/*objectMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		String outputData = objectMapper.writeValueAsString(result);*/
		
		StreamingResponseBody stream = out ->{
			out = new ObjectOutputStream(new FileOutputStream("target/output.json"));
			JsonGenerator jg = objectMapper.getJsonFactory().createJsonGenerator(out, JsonEncoding.UTF8 );
            jg.writeStartArray();
      
            for (Map<String, Object> map : ouputJson)
            {
				jg.writeStartObject();
				for (Map.Entry<String,Object> entry : map.entrySet())
				{
					jg.writeFieldName(entry.getKey());
					jg.writeString(entry.getValue().toString());
				}
				
				jg.writeEndObject();
				
			}
            jg.writeEndArray();
            jg.flush();
            jg.close();
			
			out.flush();
			out.close();
		};
		
		return new ResponseEntity<StreamingResponseBody>(stream, HttpStatus.OK);
	
	}

}

