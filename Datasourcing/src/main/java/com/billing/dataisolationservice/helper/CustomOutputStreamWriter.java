package com.billing.dataisolationservice.helper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CustomOutputStreamWriter{

	public StreamingOutput writeJsonToFile(GenericResponse response) throws WebApplicationException, JsonProcessingException
	{
		
		StreamingOutput stream= new StreamingOutput() {
		public void write(OutputStream output) throws IOException, WebApplicationException {
			
			
			
			JsonFactory jsonFactory = new JsonFactory();
			JsonGenerator jg = jsonFactory.createGenerator(output, JsonEncoding.UTF8);
            
            jg.writeStartObject();
            
            jg.writeFieldName("rows");
            jg.writeStartArray();
            for (Iterator iterator = response.getRows().iterator(); iterator.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				jg.writeStartObject();
				for (Map.Entry<String,Object> entry : map.entrySet())
				{
					jg.writeFieldName(entry.getKey());
					if(null!=entry.getValue())
						jg.writeString(entry.getValue().toString());
					else 
						jg.writeString("");
				}
				
				jg.writeEndObject();
				
			}
            
            jg.writeEndArray();
            
            jg.writeFieldName("mapData");
            jg.writeEmbeddedObject(response.getMapData());
            jg.writeFieldName("statusCode");
            jg.writeNumber(response.getStatusCode());
            jg.writeFieldName("message");
            jg.writeString(response.getMessage());
            jg.writeEndObject();
            
            jg.flush();
            jg.close();
				
			}
		};
		
		return stream;
	}

}


