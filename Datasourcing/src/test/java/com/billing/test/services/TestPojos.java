package com.billing.test.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.test.BeanTester;
import org.mockito.junit.MockitoJUnitRunner;

import com.billing.dataisolationservice.helper.DBType;
import com.billing.dataisolationservice.helper.DbContextHolder;
import com.billing.dataisolationservice.helper.GenericResponse;
import com.billing.datasourceisolationservice.domain.ConfigInfo;

@RunWith(MockitoJUnitRunner.class)
public class TestPojos {
	
	@Test
	public void testGenericResponse()
	{
		new BeanTester().testBean(GenericResponse.class);
	}
	
	@Test
	public void testDBType() {
	    assertEquals("DEFAULT", DBType.DEFAULT.name());
	}
	
	@Test
	public void testConfigInfo()
	{
		new BeanTester().testBean(ConfigInfo.class);
	}
	
	@Test
	public void testDbContextHolder()
	{
		new BeanTester().testBean(DbContextHolder.class);
	}

	
}
