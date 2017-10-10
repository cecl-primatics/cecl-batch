package com.primatics.partitioning.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "loans")
public class ParsedLoan {
	
	@Id
	private Integer key;
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
	
	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
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
		return "Loan [key=" + key + ", loanId=" + loanId + ", balance=" + balance + ", survival=" + survival + ", lossRate=" + lossRate
				+ "]";
	}

	public ParsedLoan(Integer key, String loanId, Double balance, Double[] survival, Double[] lossRate) {
		super();
		this.key = key;
		this.loanId = loanId;
		this.balance = balance;
		this.survival = survival;
		this.lossRate = lossRate;
	}
}
