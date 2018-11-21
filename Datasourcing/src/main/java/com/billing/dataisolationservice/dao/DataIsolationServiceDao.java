package com.billing.dataisolationservice.dao;

import com.billing.datasourceisolationservice.domain.ConfigInfo;

public interface DataIsolationServiceDao {
	
	public ConfigInfo getLocation(String reportgenerate);

}
