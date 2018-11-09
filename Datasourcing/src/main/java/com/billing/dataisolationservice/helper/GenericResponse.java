package com.billing.dataisolationservice.helper;

import java.util.Collection;
import java.util.Map;

/**
 * A simple POJO to send JSON response to ajax requests. This POJO enables  us to
 * send messages and error codes with the actual objects in the application.
 * 
 * 
 */
@SuppressWarnings("rawtypes")
public class GenericResponse {

	/**
	 * An array that contains the actual objects
	 */
	private Collection rows;
	
	/**
	 * An Map that contains the actual objects
	 */
	private Map mapData;
	
	/**
	 * A String containing error code. Set to 1 if there is an error
	 */
	private int statusCode = 0;
	
	/**
	 * A String containing error message.
	 */
	private String message;
	
	/**
	 * An array that contains the actual objects
	 * 
	 * @return the rows
	 */
	public Collection getRows() {
	    return rows;
	}
	
	/**
	 * An array that contains the actual objects
	 * 
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(Collection rows) {
	    this.rows = rows;
	}
	
	/**
	 * An Map that contains the actual objects
	 * 
	 * @return the mapData
	 */
	public Map getMapData() {
	    return mapData;
	}
	
	/**
	 * An Map that contains the actual objects
	 * 
	 * @param mapData
	 *            the mapData to set
	 */
	public void setMapData(Map mapData) {
	    this.mapData = mapData;
	}
	
	/**
	 * A String containing error code.
	 * 
	 * @return the errorCode
	 */
	public int getStatusCode() {
	    return statusCode;
	}
	
	/**
	 * A String containing error code.
	 * 
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setStatusCode(int errorCode) {
	    this.statusCode = errorCode;
	}
	
	/**
	 * A String containing error message.
	 * 
	 * @return the errorMessage
	 */
	public String getMessage() {
	    return message;
	}
	
	/**
	 * A String containing error message.
	 * 
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setMessage(String errorMessage) {
	    this.message = errorMessage;
	}
}

