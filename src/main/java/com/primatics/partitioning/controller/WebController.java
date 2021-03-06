package com.primatics.partitioning.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.primatics.partitioning.dao.LoanService;

@RestController
public class WebController {
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	LoanService loanService;

	@Autowired
	Job job;
	
	@GetMapping("/runjob/split/{name}")
	public ResponseEntity<Integer> split(@PathVariable("name") String name) throws Exception {
		
		Logger logger = LoggerFactory.getLogger(this.getClass());
		Map<Integer, List<String>>  data = new HashMap<Integer, List<String>>();
		Integer numOfLines = 0;
		System.out.println("*******In Split************"+name);
		try {
			System.out.println("*******In Try************");
			data = loanService.splitFile(name);
			numOfLines = data.keySet().iterator().next();
			
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()/1000).addString("scenarioName", name)
					.toJobParameters();
			jobLauncher.run(job, jobParameters);
			
		} catch (Exception e) {
			System.out.println("*******In Exception************");
			logger.info(e.getMessage());
		}
		List<String> files = data.get(numOfLines);
		for (String s : files) {
		Path fileToDeletePath = Paths.get("/tmp/" + s);
		Files.delete(fileToDeletePath);
		}
		
		return ResponseEntity.ok().body(numOfLines);
	}
}