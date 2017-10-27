package com.primatics.partitioning.partitioner;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.primatics.partitioning.dao.LoanService;

public class SamplePartitioner implements Partitioner{
	
	@Autowired
	LoanService loanService;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		
		int numSize = loanService.getLoanFiles().size();
		
		Map<String, ExecutionContext> partitionData = new HashMap<String, ExecutionContext>();
		
		for (int i = 0; i < numSize; i++) {
			
			ExecutionContext executionContext = new ExecutionContext();
			
			// give fileName for ExecutionContext
			executionContext.putString("filename", loanService.getLoanFiles().get(i));
			// give a thread name for ExecutionContext
			executionContext.putString("name", "Thread" + i);
			
			partitionData.put("partition: " + i, executionContext);
		}
		
		return partitionData;
	}

}
