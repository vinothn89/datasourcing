package com.billing.dataisolationservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.billing.dataisolationservice.database.ConnectionProvider;
import com.billing.datasourceisolationservice.domain.ConfigInfo;

@Component
public class DataIsolationServiceDaoImpl  implements DataIsolationServiceDao{
	
	@Override
	public String getLocation(String reportgenerate) {
		String loc = null;
		String sql = "SELECT REPORT_NAME,LOCATION FROM CONFIG WHERE REPORT_NAME=?";
		try {
			System.out.println("rep"+reportgenerate);
			Connection con = null;
			con = ConnectionProvider.getCaptDataSource().getConnection();
			ConfigInfo cf = new ConfigInfo();
			System.out.println("sql" + sql);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, reportgenerate);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				cf.setReport_name(rs.getString(3));
				cf.setLocation(rs.getString(4));
			}
			
			loc = cf.getLocation();
		} catch (SQLException e) {
			System.out.println("SQLException occured in getService method.");
			e.printStackTrace();
		}
		return loc;
	}

}
