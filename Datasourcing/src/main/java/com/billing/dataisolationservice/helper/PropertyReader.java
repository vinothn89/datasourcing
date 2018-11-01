package com.billing.dataisolationservice.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
@PropertySource("classpath:application.properties")

public class PropertyReader {
	
	public static PropertyReader instance;
	
	public static PropertyReader getInstance() {
		if(instance  == null) {
		   instance = new PropertyReader();
		}
		return instance;
	}

	public static void setInstance(PropertyReader instance) {
		PropertyReader.instance = instance;
	}

	@Value("$(spring.datasource.driver-class-name}")
	private String dataSourceName;
	
	@Value("$(spring.datasource.url}")
	private String dataSourceUrl;
	
	@Value("$(spring.datasource.username}")
	private String userName;
	
	@Value("$(spring.datasource.password}")
	private String password;

	@Value("$(spring.dmddatasource.driver-class-name}")
	private String dmdataSourceName;
	
	@Value("$(spring.dmdatasource.url}")
	private String dmdataSourceUrl;
	
	@Value("$(spring.dmdatasource.username}")
	private String dmuserName;
	
	@Value("$(spring.dmdatasource.password}")
	private String dmpassword;
	
	public String getDmdataSourceName() {
		return dmdataSourceName;
		
	}

	public void setDmdataSourceName(String dmdataSourceName) {
		this.dmdataSourceName = dmdataSourceName;
	}
	
	public String getDmdataSourceUrl() {
		return dmdataSourceName;
		
	}

	public void setDmdataSourceUrl(String dmdataSourceUrl) {
		this.dmdataSourceUrl = dmdataSourceUrl;
	}
	
	public String getDmpassword() {
		return dmpassword;
	}

	public void setDmpassword(String dmpassword) {
		this.dmpassword = dmpassword;
	}
	
   public String getDataSourceName() {
	   return dataSourceName;		   
	   }

	public String getDataSourceUrl() {
		return dataSourceUrl;
	}

	public void setDataSourceUrl(String dataSourceUrl) {
		this.dataSourceUrl = dataSourceUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDmuserName() {
		return dmuserName;
	}

	public void setDmuserName(String dmuserName) {
		this.dmuserName = dmuserName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	   
}

