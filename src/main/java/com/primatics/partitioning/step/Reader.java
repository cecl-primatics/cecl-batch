package com.primatics.partitioning.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import com.google.common.base.Stopwatch;
import com.primatics.partitioning.model.Loan;
import com.primatics.partitioning.model.LoanFieldSetMapper;

public class Reader {
	
	static Logger logger = LoggerFactory.getLogger(Reader.class);

	public static FlatFileItemReader<Loan> reader(String path) {
		
		Stopwatch watch1 = Stopwatch.createStarted();
		FlatFileItemReader<Loan> reader = new FlatFileItemReader<Loan>();
		reader.setResource(new FileSystemResource("/tmp/"+path));
		
		DefaultLineMapper<Loan> lineMapper = new DefaultLineMapper<Loan>();
		lineMapper.setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setDelimiter("|");
						setNames(new String[] { "loanId", "balance", "survival", "lossRate" });
					}
				});
		lineMapper.setFieldSetMapper(new LoanFieldSetMapper());
		reader.setLineMapper(lineMapper);
		reader.open(new ExecutionContext());
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("STEP 4 - FlatFile Reader - "+heapSize+" - Time: "+watch1.stop());
		
		return reader;
	}
}