package com.primatics.partitioning.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.base.Stopwatch;
import com.mongodb.gridfs.GridFSDBFile;
import com.primatics.partitioning.model.Loan;
import com.primatics.partitioning.model.ParsedLoan;

@Repository
public class LoanService implements ILoanService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	GridFsTemplate gridFsTemplate;

	public List<String> loanFiles;

	public void insert(List<? extends Loan> loans) {
		
		List<ParsedLoan> parsedLoans = new ArrayList<ParsedLoan>();
		
		for (Loan l : loans) {
			String s = l.getSurvival();
			String lo = l.getLossRate();
			String li = l.getLoanId();
			Double b = l.getBalance();
			
			ParsedLoan parsedLoan  = new ParsedLoan(li, b, fromString(s),fromString(lo));
			parsedLoans.add(parsedLoan);
			
		}
		mongoTemplate.insertAll(parsedLoans);
	}
	
	private static Double[] fromString(String string) {
		//Double[] newList;
	    String[] strings = string.replace("\"", "").replace("[", "").replace("]", "").replace("\"", "").split(", ");
	    Double result[] = new Double[strings.length];
	    for (int i = 0; i < result.length; i++) {
	      result[i] = Double.parseDouble(strings[i]);
	      //newList = result;
	    }
	    return result;
	  }

	public GridFSDBFile getFile(String name) {
		Stopwatch timer = Stopwatch.createStarted();
		Query query = new Query();
		query.addCriteria(Criteria.where("metadata.run_name").is(name));
		GridFSDBFile gfs = gridFsTemplate.findOne(query);
		logger.info("CLOCK ----- TimeElapsed ---- getFile " + timer.stop());
		return gfs;
	}

	public String getNewLine(String oldLine) {

		String newLine = new String();
		List<String> lineList = Arrays.asList(oldLine.split("\\|"));
		List<Double> survivals = new ArrayList<Double>();
		List<Double> lossrates = new ArrayList<Double>();
		for (int i = 0; i < lineList.size(); i++) {

			if (i == 0) {
				newLine = newLine + lineList.get(i) + "|";
			}
			if (i == 1) {
				
				newLine = newLine + new BigDecimal(lineList.get(i));
				newLine = newLine + "|";
			}
			if (i >= 2 && i <= 17) {
				survivals.add(Double.parseDouble(lineList.get(i)));
			}
			if (i >= 18 && i <= 33) {
				lossrates.add(Double.parseDouble(lineList.get(i)));
			}
		}

		newLine = newLine + survivals;
		newLine = newLine + "|";
		newLine = newLine + lossrates;

		return newLine;
	}

	public Map<Integer, List<String>> splitFile(String runName) throws IOException {
		Stopwatch timer = Stopwatch.createStarted();
		List<String> fileNames = new ArrayList<String>();
		GridFSDBFile gfs = getFile(runName);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gfs.getInputStream(), "UTF-8"));
		// String loanLines =
		// "LoanID|Balance|Survival1|Survival2|Survival3|Survival4|Survival5|Survival6|Survival7|Survival8|Survival9|Survival10|Survival11|Survival12|Survival13|Survival14|Survival15|Survival16|LossRate1|LossRate2|LossRate3|LossRate4|LossRate5|LossRate6|LossRate7|LossRate8|LossRate9|LossRate10|LossRate11|LossRate12|LossRate13|LossRate14|LossRate15|LossRate16"+"\n";
		bufferedReader.readLine();
		String line = "";
		int numOfLines = 0;
		int fileNum = 1;
		int fileSize = 0;
		BufferedWriter fos = new BufferedWriter(new FileWriter("/tmp/" + runName + "_" + fileNum + ".csv", true));
		fileNames.add(runName + "_" + fileNum + ".csv");
		// if (fileNum == 1) {
		// fos.write(loanLines+"\n");
		// }
		while ((line = bufferedReader.readLine()) != null) {
			numOfLines++;
			String newLine = getNewLine(line);
			if (fileSize + line.getBytes().length > 2.5 * 1024 * 1024) {
				fileNum++;
				fos.flush();
				fos.close();
				fos = new BufferedWriter(new FileWriter("/tmp/" + runName + "_" + fileNum + ".csv", true));
				fileNames.add(runName + "_" + fileNum + ".csv");
				// fos.append(loanLines);
				fos.write(newLine + "\n");
				fileSize = newLine.getBytes().length;

			} else {
				fos.append(newLine + "\n");
				fileSize += newLine.getBytes().length;
			}
		}
		fos.flush();
		fos.close();
		bufferedReader.close();
		loanFiles = fileNames;
		Map<Integer, List<String>>  data = new HashMap<Integer, List<String>>();
		data.put(numOfLines, fileNames);
		logger.info("CLOCK ----- TimeElapsed ---- splitFile " + timer.stop());
		return data;
	}

	public List<String> getFiles() {
		return loanFiles;
	}

	@Override
	public <S extends ParsedLoan> List<S> save(Iterable<S> entites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParsedLoan> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParsedLoan> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ParsedLoan> S insert(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ParsedLoan> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ParsedLoan> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ParsedLoan> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ParsedLoan> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ParsedLoan> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParsedLoan findOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<ParsedLoan> findAll(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ParsedLoan entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends ParsedLoan> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends ParsedLoan> S findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ParsedLoan> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ParsedLoan> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends ParsedLoan> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}
}