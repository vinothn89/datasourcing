package com.billing.dataisolationservice.helper;

public enum DBType {

	DEFAULT("DEFAULT"),
	LEGACY("LEGACY"),
	STRATEGY("STRATEGY"),
	DB2_DS1("DB2_DS1"),
	MYSSQL_DS1("MYSSQL_DS1"),
	DB2("DB2"),
	db2("db2"),
	db2_ds("db2_ds");
	
	private String type;
	
	public String getType() {
		return type;
	}

	private DBType(String type)
	{
		this.type = type;
	}

}
