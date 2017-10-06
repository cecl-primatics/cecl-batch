package com.primatics.partitioning.step;

import java.util.ArrayList;
import java.util.List;

import com.primatics.partitioning.dao.LoanService;
import com.primatics.partitioning.model.Loan;

import org.springframework.batch.item.data.MongoItemWriter;

public class Writer extends MongoItemWriter<Loan> {

	private final LoanService loanDao;

	public Writer(LoanService loanDao) {
		this.loanDao = loanDao;
	}

	public void doWrite(List<? extends Loan> loans) {

		loanDao.insert(loans);
	}
}
