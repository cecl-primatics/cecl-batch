package com.primatics.partitioning.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.google.common.base.Stopwatch;
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
		Stopwatch timer = Stopwatch.createStarted();
		Map<Integer, List<String>>  data = new HashMap<Integer, List<String>>();
		Integer numOfLines = 0;
		try {
			data = loanService.splitFile(name);
			numOfLines = data.keySet().iterator().next();
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()/1000)
					.toJobParameters();
			
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		logger.info("CLOCK ----- TimeElapsed ---- OVERALL "+timer.stop());
		List<String> files = data.get(numOfLines);
		for (String s : files) {
		Path fileToDeletePath = Paths.get("/tmp/" + s);
		Files.delete(fileToDeletePath);
		}
		
		return ResponseEntity.ok().body(numOfLines);
	}
}