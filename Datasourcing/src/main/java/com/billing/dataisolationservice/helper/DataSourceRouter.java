package com.billing.dataisolationservice.helper;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceRouter  extends AbstractRoutingDataSource {
	
	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		return DbContextHolder.getDbType();
	}
	

}
