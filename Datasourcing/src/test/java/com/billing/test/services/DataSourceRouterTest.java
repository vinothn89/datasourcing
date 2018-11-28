package com.billing.test.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.billing.dataisolationservice.helper.DBType;
import com.billing.dataisolationservice.helper.DataSourceRouter;
import com.billing.dataisolationservice.helper.DbContextHolder;


@RunWith(MockitoJUnitRunner.class)
public class DataSourceRouterTest {
	
	@Mock
	DbContextHolder dbch;
	
	@InjectMocks
	DataSourceRouter dsr;
	
	@Before
	public void init()
	{
		DbContextHolder.getInstance().setDbType(DBType.DEFAULT);
	}
	
	@Test
	public void testDSRLookUpKey()
	{
		dsr = new DataSourceRouter() {
            public DataSourceRouter callProtectedMethod() {
            	Assert.assertEquals(DBType.DEFAULT.name(),((DBType)determineCurrentLookupKey()).name());
                return this;
            }
        }.callProtectedMethod();
       
	}
}
