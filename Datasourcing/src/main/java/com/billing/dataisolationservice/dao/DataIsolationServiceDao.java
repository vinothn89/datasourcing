package com.billing.dataisolationservice.dao;

import java.util.List;

import com.billing.datasourceisolationservice.domain.ConfigInfo;

public interface DataIsolationServiceDao {
	
	public List<ConfigInfo> getLocation(String reportgenerate);

}
