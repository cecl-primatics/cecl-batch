package com.primatics.partitioning.step;

import java.util.List;

import org.springframework.batch.item.data.MongoItemWriter;

import com.primatics.partitioning.dao.LoanService;
import com.primatics.partitioning.model.Loan;

public class Writer extends MongoItemWriter<Loan> {

	private final LoanService loanDao;

	public Writer(LoanService loanDao) {
		this.loanDao = loanDao;
	}

	public void doWrite(List<? extends Loan> loans) {

		loanDao.insert(loans);
	}
}
