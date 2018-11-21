package com.billing.test.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.billing.dataisolationservice.services.LegacyService;


@RunWith(MockitoJUnitRunner.class)
public class LegacyServiceTest {
	
	@Mock
	RestTemplate restTemplate;
	
	@InjectMocks
	LegacyService ls;
	
	
	@Before
	public void init()
	{
		restTemplate = new RestTemplate();
	}
	
	@Test
	public void testGetLegacyQuery()
	{
		String expectedQueryForOctber = "select * from task";
		String expectedQueryForNovember = "select * from task";
		Assert.assertEquals(expectedQueryForOctber, ls.getLegacyQuery(""));
		Assert.assertEquals(expectedQueryForNovember, ls.getLegacyQuery(""));
	}

}
