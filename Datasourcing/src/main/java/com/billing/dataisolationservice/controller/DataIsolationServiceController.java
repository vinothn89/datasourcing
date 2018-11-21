package com.billing.dataisolationservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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
import com.billing.dataisolationservice.helper.CustomOutputStreamWriter;
import com.billing.dataisolationservice.helper.DBType;
import com.billing.dataisolationservice.helper.DbContextHolder;
import com.billing.dataisolationservice.helper.GenericResponse;
import com.billing.dataisolationservice.services.LegacyService;
import com.billing.dataisolationservice.services.StrategicService;
import com.billing.datasourceisolationservice.domain.ConfigInfo;

@Controller
@EnableAsync
public class DataIsolationServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(DataIsolationServiceController.class);
	
	@Autowired
	DataIsolationServiceDao dao;
	
	@Autowired
	DataIsolationServiceAbstractionDao daoabs;
	
	@Autowired
	LegacyService legacyservice;
	
	@Autowired
	StrategicService strategyservice;
	
	@RequestMapping(value = ""+"",method = RequestMethod.GET)
	@Async("asyncExecutor")
	public CompletableFuture<ResponseEntity<ArrayList<Map<String, Object>>>> getDataSearchWithAbstraction(
			@RequestParam(value = "report") String report, RedirectAttributes redirectAttributes, ModelMap model)
	        throws IOException, InterruptedException, ExecutionException {
		
		List<Map<String,Object>> result = null;
		
		ConfigInfo configInfo = dao.getLocation(report);
		String sql = null;
		
			if (configInfo.getLocation().equalsIgnoreCase("Legacy")) 
			{
				sql = legacyservice.getLegacyQuery(report);
			}
			else {
				sql = strategyservice.getStrategyQuery(report);
			}
			
			if(null!=configInfo.getDatabase())
			{
				DbContextHolder.setDbType(DBType.valueOf(configInfo.getDatabase()));
			}
			
			result = daoabs.executeQuery(sql);
		
	
		return CompletableFuture.completedFuture(new ResponseEntity<ArrayList<Map<String, Object>>>(
				(ArrayList<Map<String, Object>>) result, HttpStatus.OK));
	
	}
	
	@RequestMapping(value = "/dis",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Async("asyncExecutor")
	public CompletableFuture<ResponseEntity<StreamingResponseBody>> getDataSearchAndWrite(
			@RequestParam(value = "report") String report, RedirectAttributes redirectAttributes, ModelMap model)
	        throws IOException, InterruptedException, ExecutionException {
		
		logger.info("Enter method - getDataSearchAndWrite");
		
		if("".equalsIgnoreCase(report))
		{
			report = "December";
		}
		
		List<Map<String,Object>> result = null;
		
		ConfigInfo configInfo = dao.getLocation(report);
		String sql = null;
		
		if (configInfo.getLocation().equalsIgnoreCase("Legacy")) {
			sql = legacyservice.getLegacyQuery(report);
		}
		else {
			sql = strategyservice.getStrategyQuery(report);
		}
		
		if(null!=configInfo.getDatabase())
		{
			DbContextHolder.setDbType(DBType.valueOf(configInfo.getDatabase()));
		}
		
		result = daoabs.executeQuery(sql);
	    
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setRows(result);
		genericResponse.setStatusCode(HttpStatus.OK.value());
		genericResponse.setMessage("File creation successful");
		
		StreamingResponseBody stream = out ->
		{
			new CustomOutputStreamWriter().writeJsonToFile(genericResponse).write(out);
			out.flush();
		};
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=\"output.json\"");
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		 
		
		return CompletableFuture.completedFuture(new ResponseEntity<StreamingResponseBody>(stream, headers, HttpStatus.OK));
		
	}
	
}

