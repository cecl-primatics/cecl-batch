package com.primatics.partitioning.step;

import org.springframework.batch.item.ItemProcessor;

import com.primatics.partitioning.model.Loan;

public class Processor implements ItemProcessor<Loan, Loan> {

	private String threadName;

	@Override
	public Loan process(Loan loan) throws Exception {
		
		final String loanID = loan.getLoanId();
		final Double balance = loan.getBalance();
		final String survival = loan.getSurvival();
		final String lossRate = loan.getLossRate();

		final Loan fixedLoan = new Loan(loanID, balance, survival, lossRate);

		//log.info(threadName + " processing : " + "Converting (" + loan + ") into (" + fixedLoan + ")");
		return fixedLoan;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
}