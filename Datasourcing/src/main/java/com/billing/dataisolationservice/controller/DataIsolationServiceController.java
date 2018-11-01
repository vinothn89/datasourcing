package com.billing.dataisolationservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.billing.dataisolationservice.dao.DataIsolationServiceAbstractionDao;
import com.billing.dataisolationservice.dao.DataIsolationServiceDao;
import com.billing.dataisolationservice.helper.DBType;
import com.billing.dataisolationservice.helper.DbContextHolder;
import com.billing.dataisolationservice.services.LegacyService;
import com.billing.dataisolationservice.services.StrategicService;

@Controller
@EnableAsync
public class DataIsolationServiceController {
	
	private static final Logger logger = Logger.getLogger(DataIsolationServiceController.class);
	
	@Autowired
	DataIsolationServiceDao dao;
	
	@Autowired
	DataIsolationServiceAbstractionDao daoabs;
	
	@Autowired
	LegacyService legacyservice;
	
	@Autowired
	StrategicService strategyservice;
	
	@RequestMapping(value = ""
			+"",method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Map<String,Object>>> getDataSearchWithAbstraction(
			@RequestParam(value = "report") String report, RedirectAttributes redirectAttributes, ModelMap model)
	        throws IOException {
		
		System.out.println("hai"+report);
		
		List<Map<String,Object>> result = null;
		
		System.out.println("hai"+report);
		String location = dao.getLocation(report);
		String sql = null;
		if (location.equals("Legacy")) {
			sql = legacyservice.getLegacyQuery(report);
			DbContextHolder.setDbType(DBType.LEGACY);
	}else {
		sql = strategyservice.getStrategyQuery(report);
		DbContextHolder.setDbType(DBType.STRATEGY);				
	}
	
	result = daoabs.executeQuery(sql);
	
	return new ResponseEntity<ArrayList<Map<String, Object>>>((ArrayList<Map<String, Object>>) result,
			HttpStatus.OK);
	
}

}

