package com.ingenico.moneytransfer.service;

import java.util.Random;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.ingenico.moneytransfer.controller.AccountController;
import com.ingenico.moneytransfer.exception.AccountNotFoundException;
import com.ingenico.moneytransfer.exception.InsufficientBalanceException;
import com.ingenico.moneytransfer.model.Account;
import com.ingenico.moneytransfer.model.Transfer;
import com.ingenico.moneytransfer.repository.AccountRepository;
import com.ingenico.moneytransfer.util.LockUtil;
/**
 * 
 * @author sindhu
 *
 */
@Service
public class TransactionService {
	private static final Logger LOG = LoggerFactory
			.getLogger(TransactionService.class);
	@Autowired
	private LockUtil lockUtil;

	@Autowired
	AccountRepository accountRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public String makeTransfer(Transfer transfer)
			throws InsufficientBalanceException, AccountNotFoundException {

		int srcAccountId = transfer.getSourceAccountId();
		int desAccountId = transfer.getDestinationAccountId();
		double amount = transfer.getAmount();

		Account sourceAccount = accountRepository.findByAccountId(srcAccountId);
		if (sourceAccount == null) {
			throw new AccountNotFoundException("Account number doesn't exist");
		}
		if (sourceAccount.getAccountId() == transfer.getDestinationAccountId()) {
			throw new AccountNotFoundException(
					"source and destination accounts must be different");
		}
		Account destinationAccount = accountRepository
				.findByAccountId(desAccountId);
		if (destinationAccount == null) {
			throw new AccountNotFoundException("Account number doesn't exist");
		}
		transfer(sourceAccount, destinationAccount, amount);
		return "Transaction is successful";
	}

	private void transfer(Account sourceAccount, Account destinationAccount,
			double amount) throws InsufficientBalanceException {
		Lock lock1, lock2;
		if (amount <= 0) {
			throw new IllegalArgumentException("amount: " + amount);
		}

		lock1 = sourceAccount.getAccountId() < destinationAccount
				.getAccountId() ? lockUtil.getLockEntry(sourceAccount
				.getAccountId()) : lockUtil.getLockEntry(destinationAccount
				.getAccountId());
		lock2 = sourceAccount.getAccountId() < destinationAccount
				.getAccountId() ? lockUtil.getLockEntry(destinationAccount
				.getAccountId()) : lockUtil.getLockEntry(sourceAccount
				.getAccountId());

		synchronized (lock1) {
			Double currbalance = accountRepository.getBalanceById(sourceAccount
					.getAccountId());
			LOG.info("Withdrawal account id : " + sourceAccount.getAccountId()
					+ "has amount : " + amount + " currBalance is:"
					+ currbalance);
			if (currbalance < amount) {
				throw new InsufficientBalanceException("Insufficient Balance");
			}
			currbalance -= amount;
			LOG.info("After deduction:withdrawal account id : "
					+ sourceAccount.getAccountId() + "has amount : " + amount
					+ " currBalance after withdraw :" + currbalance);
			accountRepository.updateById(sourceAccount.getAccountId(),
					currbalance);
			synchronized (lock2) {
				Double destCurrbalance = accountRepository
						.getBalanceById(destinationAccount.getAccountId());
				LOG.info("Deposit account id : "
						+ destinationAccount.getAccountId() + "has amount : "
						+ amount + " currBalance is:" + destCurrbalance);
				destCurrbalance += amount;
				LOG.info("After deposit: Deposit account id : "
						+ destinationAccount.getAccountId() + "has amount : "
						+ amount + " currBalance after deposit :"
						+ destCurrbalance);
				accountRepository.updateById(destinationAccount.getAccountId(),
						destCurrbalance);
			}
		}
	}

	//Method to simulate real multithreading environment, where more than one request can arrive from same resource.
	//Two threads are created each sending 25 request to transfer money simultaneously. 
	/*public String testMultithreading() {

		Thread t1 = new Thread() {
			public void run() {
				Random r = new Random();
				for (int i = 0; i < 25; i++) {
					Transfer t = new Transfer();
					t.setSourceAccountId(1001);
					t.setDestinationAccountId(1002);
					t.setAmount((double) r.nextInt(1000) + 1);
					LOG.info(" From acc1 to acc2" + t.getAmount());
					try {
						makeTransfer(t);
					} catch (AccountNotFoundException e) {
						e.printStackTrace();
					} catch (InsufficientBalanceException e) {
						e.printStackTrace();
					}
				}
			}

		};

		Thread t2 = new Thread() {
			public void run() {
				Random r = new Random();
				for (int i = 0; i < 25; i++) {
					Transfer t = new Transfer();
					t.setSourceAccountId(1002);
					t.setDestinationAccountId(1001);
					t.setAmount((double) r.nextInt(1000) + 1);
					LOG.info(" From acc2 to acc1" + t.getAmount());
					try {
						makeTransfer(t);
					} catch (AccountNotFoundException e) {
						e.printStackTrace();
					} catch (InsufficientBalanceException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Total balance :"
				+ (accountRepository.getBalanceById(1001) + accountRepository
						.getBalanceById(1002));

	}*/
}
