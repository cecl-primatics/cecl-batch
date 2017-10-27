package com.primatics.partitioning.model;

public class Loan {
	
	private String scenario;
	private String loanId;
	private Double balance;
	private String survival;
	private String lossRate;

	public Loan() {
	}
	
	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
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

	public String getSurvival() {
		return survival;
	}

	public void setSurvival(String survival) {
		this.survival = survival;
	}

	public String getLossRate() {
		return lossRate;
	}

	public void setLossRate(String lossRate) {
		this.lossRate = lossRate;
	}

	@Override
	public String toString() {
		return "Loan [scenario=\" + scenario + \", loanId=" + loanId + ", balance=" + balance + ", survival=" + survival + ", lossRate=" + lossRate
				+ "]";
	}

	public Loan(String scenario, String loanId, Double balance, String survival, String lossRate) {
		super();
		this.scenario = scenario;
		this.loanId = loanId;
		this.balance = balance;
		this.survival = survival;
		this.lossRate = lossRate;
	}
}
