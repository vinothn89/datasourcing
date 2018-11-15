package com.billing.test.services;

import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.billing.dataisolationservice.dao.DataIsolationServiceDaoImpl;
import com.billing.dataisolationservice.helper.DataSourceRouter;
import com.billing.datasourceisolationservice.domain.ConfigInfo;

@RunWith(MockitoJUnitRunner.class)
public class DataIsolationServiceDaoMockTest {
	
	@Mock
	DataSourceRouter dsr;
	
	ConfigInfo expectedConfigInfo;
	
	@InjectMocks
	DataIsolationServiceDaoImpl disDAO;
	
	Connection con;
	
	@Before
	public void init() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = (Connection) DriverManager.getConnection("jdbc:sqlserver://HP246\\\\SQLEXPRESS:1433;databaseName=springbootdb", "springbootdb", "spring123");
        expectedConfigInfo = new ConfigInfo();
        expectedConfigInfo.setDatabase("MYSSQL_DS1");
        expectedConfigInfo.setLocation("Legacy");
        expectedConfigInfo.setReport_name("October1");
	}
	
	@Test
	public void testGetLocationForReportOctober1() throws ClassNotFoundException, SQLException
	{
		when(dsr.getConnection()).thenReturn(con);
		ConfigInfo actualConfigInfo = disDAO.getLocation("October1");
		Assert.assertEquals(actualConfigInfo.getLocation(), expectedConfigInfo.getLocation());
		Assert.assertEquals(actualConfigInfo.getDatabase(), expectedConfigInfo.getDatabase());
		Assert.assertEquals(actualConfigInfo.getReport_name(), expectedConfigInfo.getReport_name());
		
	}
	
}
