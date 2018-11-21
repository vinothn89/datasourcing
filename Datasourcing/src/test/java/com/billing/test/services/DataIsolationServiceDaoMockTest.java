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
		Class.forName("com.mysql.jdbc.Driver");
        con = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/taskservice?useSSL=false", "root", "test");
        
        expectedConfigInfo = new ConfigInfo();
        expectedConfigInfo.setDatabase("db2");
        expectedConfigInfo.setLocation("legacy");
        expectedConfigInfo.setReport_name("November");
	}
	
	@Test
	public void testGetLocationForReportOctober1() throws ClassNotFoundException, SQLException
	{
		when(dsr.getConnection()).thenReturn(con);
		ConfigInfo actualConfigInfo = disDAO.getLocation("November");
		Assert.assertEquals(actualConfigInfo.getLocation(), expectedConfigInfo.getLocation());
		Assert.assertEquals(actualConfigInfo.getDatabase(), expectedConfigInfo.getDatabase());
		Assert.assertEquals(actualConfigInfo.getReport_name(), expectedConfigInfo.getReport_name());
		
	}
	
	@Test
	public void testGetLocationForReportForMSSQL() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.con = (Connection) DriverManager.getConnection("jdbc:sqlserver://HP246\\\\SQLEXPRESS:1433;databaseName=springbootdb", "springbootdb", "spring123");
		when(dsr.getConnection()).thenReturn(con);
		ConfigInfo actualConfigInfo = disDAO.getLocation("November");
		Assert.assertEquals(actualConfigInfo.getLocation(), expectedConfigInfo.getLocation());
		Assert.assertEquals(actualConfigInfo.getDatabase(), expectedConfigInfo.getDatabase());
		Assert.assertEquals(actualConfigInfo.getReport_name(), expectedConfigInfo.getReport_name());
		
	}
	
	
	
}
