package com.primatics.partitioning.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.primatics.partitioning.model.Loan;
import com.primatics.partitioning.model.ParsedLoan;

public interface ILoanService extends MongoRepository<ParsedLoan, String>{
	
	public void insert(List<? extends Loan> loans);

}
