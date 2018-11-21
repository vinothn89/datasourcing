/**
 * 
 */
package com.billing.dataisolationservice.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.scheduling.annotation.Async;

/**
 * @author Vinoth Natesan
 *
 */
public class DataIsolationServiceAbstractionDao extends JdbcDaoSupport {
	
	public List<Map<String, Object>> executeQuery(String query) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result1 = null;
		result1 = this.getJdbcTemplate().queryForList(query);
		return result1;
	}

}
