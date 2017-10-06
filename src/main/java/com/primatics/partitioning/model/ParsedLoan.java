package com.primatics.partitioning.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "loans")
public class ParsedLoan {
	
	private String loanId;
	private Double balance;
	private Double[] survival;
	private Double[] lossRate;

	public ParsedLoan() {
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double[] getSurvival() {
		return survival;
	}

	public void setSurvival(Double[] survival) {
		this.survival = survival;
	}

	public Double[] getLossRate() {
		return lossRate;
	}

	public void setLossRate(Double[] lossRate) {
		this.lossRate = lossRate;
	}

	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", balance=" + balance + ", survival=" + survival + ", lossRate=" + lossRate
				+ "]";
	}

	public ParsedLoan(String loanId, Double balance, Double[] survival, Double[] lossRate) {
		super();
		this.loanId = loanId;
		this.balance = balance;
		this.survival = survival;
		this.lossRate = lossRate;
	}
}
