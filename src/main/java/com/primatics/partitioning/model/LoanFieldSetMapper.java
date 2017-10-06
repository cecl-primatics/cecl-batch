package com.primatics.partitioning.model;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class LoanFieldSetMapper implements FieldSetMapper<Loan> {
	public Loan mapFieldSet(FieldSet fieldSet) {
		Loan loan = new Loan();

		loan.setLoanId(fieldSet.readString(0));
		loan.setBalance(fieldSet.readDouble(1));
		loan.setSurvival(fieldSet.readString(2));
		loan.setLossRate(fieldSet.readString(3));

		return loan;
	}
}
