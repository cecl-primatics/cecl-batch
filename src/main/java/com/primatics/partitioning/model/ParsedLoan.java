package com.primatics.partitioning.model;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "loans")
public class ParsedLoan {
	
	@Id
	private String key;
	private String scenario;
	private String loanId;
	private Double balance;
	private Double[] survival;
	private Double[] lossRate;

	public ParsedLoan() {
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
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
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
		return "Loan [scenario=\" + scenario + \", key=" + key + ", loanId=" + loanId + ", balance=" + balance + ", survival=" + survival + ", lossRate=" + lossRate
				+ "]";
	}

	public ParsedLoan(String loanId, String scenario, Double balance, Double[] survival, Double[] lossRate) {
		super();
		this.key = generateUniqueId();
		this.scenario = scenario;
		this.loanId = loanId;
		this.balance = balance;
		this.survival = survival;
		this.lossRate = lossRate;
	}
	
 	public static String generateUniqueId() {      
 		RandomStringGenerator randomStringGenerator =
 		        new RandomStringGenerator.Builder()
 		                .withinRange('0', 'z')
 		                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
 		                .build();
 		return randomStringGenerator.generate(12);
    }
}
