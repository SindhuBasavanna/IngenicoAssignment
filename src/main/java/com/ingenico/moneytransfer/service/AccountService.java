package com.ingenico.moneytransfer.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingenico.moneytransfer.model.Account;
import com.ingenico.moneytransfer.repository.AccountRepository;
import com.ingenico.moneytransfer.util.LockUtil;
/**
 * 
 * @author sindhu
 *
 */
@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private LockUtil lockUtil;

	public AtomicInteger nextAccountId = new AtomicInteger(1001);

	public String createAccount(Account account) {
		account.setAccountId(nextAccountId.getAndIncrement());
		accountRepository.save(account);
		lockUtil.putLockEntry(account.getAccountId());
		return "Account details added successfully: " + account.getAccountId();
	}

	public List<Account> getAccount() {
		return accountRepository.findAll();
	}

	public Account getAccountById(Integer accountId) {
		return accountRepository.findByAccountId(accountId);
	}

	public AccountRepository getAccountRepository() {
		return accountRepository;
	}

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public LockUtil getLockUtil() {
		return lockUtil;
	}

	public void setLockUtil(LockUtil lockUtil) {
		this.lockUtil = lockUtil;
	}

}
