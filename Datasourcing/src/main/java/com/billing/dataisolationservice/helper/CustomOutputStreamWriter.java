package com.billing.dataisolationservice.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CustomOutputStreamWriter{

	
	public boolean writeJsonToFile(List<Map<String, Object>> responseOutput) throws WebApplicationException
	{
		StreamingOutput stream= new StreamingOutput() {
			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				
				JsonFactory jsonFactory = new JsonFactory();
				JsonGenerator jg = jsonFactory.createGenerator(output, JsonEncoding.UTF8);
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
			
			String workingDirectory = System.getProperty("user.dir");
			File file = new File(workingDirectory+File.separator+"target"+File.separator+"output.json");
			if(!file.exists())
				file.createNewFile();
			
			System.out.println("File Location:" + file.getAbsolutePath());
			
			stream.write(new FileOutputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}


