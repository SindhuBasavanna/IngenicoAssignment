package com.ingenico.moneytransfer.model;
/**
 * 
 * @author sindhu
 *
 */
public class Transfer {
	private Integer sourceAccountId;
	private Integer destinationAccountId;
	private Double amount;

	public Integer getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(Integer sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public Integer getDestinationAccountId() {
		return destinationAccountId;
	}

	public void setDestinationAccountId(Integer destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
