package com.billing.dataisolationservice.database;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.billing.dataisolationservice.helper.PropertyReader;

public class ConnectionProvider {
	
	public static DriverManagerDataSource getCaptDataSource() {
		
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName(PropertyReader.getInstance().getDataSourceName());
		datasource.setUrl(PropertyReader.getInstance().getDataSourceUrl());
		datasource.setUsername(PropertyReader.getInstance().getUserName());
		datasource.setPassword(PropertyReader.getInstance().getPassword());
		System.out.println(PropertyReader.getInstance().getDataSourceName());
		System.out.println(datasource.getUsername());
		System.out.println(datasource.getUrl());
		System.out.println(datasource.getPassword());
		return datasource;
	}
		
	public static DriverManagerDataSource getDmDataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(PropertyReader.getInstance().getDataSourceName());
		dataSource.setUrl(PropertyReader.getInstance().getDataSourceUrl());
		dataSource.setUsername(PropertyReader.getInstance().getDmuserName());
		dataSource.setPassword(PropertyReader.getInstance().getDmpassword());
		return dataSource;
		
	}
}
