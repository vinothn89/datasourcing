package com.billing.dataisolationservice.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CustomOutputStreamWriter{

	/**
	 * 
	 */
	private ObjectMapper objectMapper;

    public CustomOutputStreamWriter() {
        objectMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
	
	public boolean writeJsonToFile(List<Map<String, Object>> responseOutput) throws WebApplicationException
	{
		StreamingOutput stream= new StreamingOutput() {
			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				
				JsonGenerator jg = objectMapper.getJsonFactory().createJsonGenerator(output, JsonEncoding.UTF8 );
                jg.writeStartArray();
                
                for (Map<String, Object> map : responseOutput)
                {
					jg.writeStartObject();
					for (Map.Entry<String,Object> entry : map.entrySet())
					{
						jg.writeFieldName(entry.getKey());
						jg.writeString(entry.getValue().toString());
					}
					
					jg.writeEndObject();
					
				}
                jg.writeEndArray();
                jg.flush();
                jg.close();
				
			}
		};
		
		try {
			stream.write(new ObjectOutputStream(new FileOutputStream("target/output.json")));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}


