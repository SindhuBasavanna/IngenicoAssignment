package com.ingenico.moneytransfer.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.ingenico.moneytransfer.exception.InsufficientBalanceException;
/**
 * 
 * @author sindhu
 *
 */

@Entity
public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7011358916689586121L;
	@Id
	private Integer accountId;
	private String userName;
	private volatile double balance;

	public Integer getAccountId() {
		return accountId;
	}

	public String getUserName() {
		return userName;
	}

	public double getBalance() {
		return balance;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Account(int accountId, String userName, double balance) {
		super();
		this.accountId = accountId;
		this.userName = userName;
		this.balance = balance;
	}

	public Account() {
		super();
	}

	public synchronized void setBalance(double balance) throws InsufficientBalanceException {
		if (balance <= 0) {
			throw new InsufficientBalanceException("Added balance cannot be zero or negative ");
		}
		this.balance = balance;
	}

}
