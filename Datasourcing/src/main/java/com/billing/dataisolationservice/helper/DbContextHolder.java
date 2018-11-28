package com.billing.dataisolationservice.helper;

public class DbContextHolder {
	
	private static DbContextHolder myObj;
    
    static{
        myObj = new DbContextHolder();
    }
     
    private DbContextHolder(){
     
    }
    
    public static DbContextHolder getInstance(){
        return myObj;
    }
	
	private final ThreadLocal<DBType> CONTEXT = new ThreadLocal<DBType>();
	
	public DBType getDbType() {
		return (DBType) CONTEXT.get();
		
	}

	public void setDbType(DBType dbType) {
		if (dbType == null) {
			throw new NullPointerException();
		}
		CONTEXT.set(dbType);
	}
	public void clearDbType() {
		CONTEXT.remove();
		
	}
}
