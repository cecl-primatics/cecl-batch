package com.primatics.partitioning.step;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.primatics.partitioning.model.Loan;

@Component("itemProcessor")
@Scope(value = "step")
public class Processor implements ItemProcessor<Loan, Loan> {

	private String threadName;
	
	@Value("#{jobParameters['scenarioName']}")
	private String scenarioName;

	@Override
	public Loan process(Loan loan) throws Exception {
		
		final String scenario = scenarioName;
		final String loanID = loan.getLoanId();
		final Double balance = loan.getBalance();
		final String survival = loan.getSurvival();
		final String lossRate = loan.getLossRate();

		final Loan fixedLoan = new Loan(scenario, loanID, balance, survival, lossRate);

		return fixedLoan;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
}