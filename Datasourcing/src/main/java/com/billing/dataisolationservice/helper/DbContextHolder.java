package com.billing.dataisolationservice.helper;

public class DbContextHolder {
	private static final ThreadLocal<DBType> CONTEXT = new ThreadLocal<DBType>();
	
	public static DBType getDbType() {
		return (DBType) CONTEXT.get();
		
	}

	public static void setDbType(DBType dbType) {
		if (dbType == null) {
			throw new NullPointerException();
		}
		CONTEXT.set(dbType);
	}
	public static void clearDbType() {
		CONTEXT.remove();
		
	}
}
