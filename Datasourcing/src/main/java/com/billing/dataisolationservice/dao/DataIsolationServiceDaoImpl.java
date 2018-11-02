package com.billing.dataisolationservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.billing.dataisolationservice.helper.DBType;
import com.billing.dataisolationservice.helper.DataSourceRouter;
import com.billing.dataisolationservice.helper.DbContextHolder;
import com.billing.datasourceisolationservice.domain.ConfigInfo;

@Component
public class DataIsolationServiceDaoImpl  implements DataIsolationServiceDao{
	
	@Autowired
	DataSourceRouter dsr;
	
	ConfigInfo cf;
	
	@Override
	public ConfigInfo getLocation(String reportgenerate) {
		String sql = "SELECT REPORT_NAME,LOCATION,DATABASE FROM CONFIG WHERE REPORT_NAME=?";
		try {
			System.out.println("rep"+reportgenerate);
			
			DbContextHolder.setDbType(DBType.DEFAULT);
			
			Connection con = null;
			con = dsr.getConnection();
			
			
			System.out.println("sql" + sql);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, reportgenerate);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if(null == cf)
					cf = new ConfigInfo();
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				cf.setReport_name(rs.getString(1));
				cf.setLocation(rs.getString(2));
				cf.setDatabase(rs.getString(3));
			}
			
		} catch (SQLException e) {
			System.out.println("SQLException occured in getService method.");
			e.printStackTrace();
		}
		return cf;
	}

}
