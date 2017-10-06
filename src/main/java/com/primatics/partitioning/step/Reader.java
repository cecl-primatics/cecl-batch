package com.primatics.partitioning.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import com.google.common.base.Stopwatch;
import com.primatics.partitioning.model.Loan;
import com.primatics.partitioning.model.LoanFieldSetMapper;

public class Reader {
	
	static Logger logger = LoggerFactory.getLogger(Reader.class);

	public static FlatFileItemReader<Loan> reader(String path) {
		
		Stopwatch timer = Stopwatch.createStarted();
		FlatFileItemReader<Loan> reader = new FlatFileItemReader<Loan>();
		reader.setResource(new FileSystemResource("C:/tmp/"+path));
		
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
		logger.info("CLOCK ----- TimeElapsed ---- reader "+timer.stop());
		return reader;
	}
}