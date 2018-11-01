package com.example.DataSourcing;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import com.billing.dataisolationservice.helper.PropertyReader;

@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableAsync
public class DataSourcingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataSourcingApplication.class, args);
	}
	
	@Bean
	public PropertyReader getPropertyReaderBean() {
		return PropertyReader.getInstance();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean(name="asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("DataDAOLookup-");
        executor.initialize();
        return executor;
    }
	
	@Configuration
	@ImportResource({"classpath:applicationContext.xml"})
	public class XmlConfiguration {
				
}
	}
