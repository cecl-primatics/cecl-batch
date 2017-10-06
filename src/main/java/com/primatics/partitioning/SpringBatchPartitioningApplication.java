package com.primatics.partitioning;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@EnableBatchProcessing
@ImportResource({"classpath:batchjob.xml", "classpath:spring.xml"})
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class SpringBatchPartitioningApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchPartitioningApplication.class, args);
	}
}