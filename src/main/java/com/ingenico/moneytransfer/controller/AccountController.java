package com.ingenico.moneytransfer.controller;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ingenico.moneytransfer.exception.AccountNotFoundException;
import com.ingenico.moneytransfer.model.Account;
import com.ingenico.moneytransfer.service.AccountService;
/**
 * 
 * @author sindhu
 *
 */
@RestController
public class AccountController {
	private static final Logger LOG = LoggerFactory
			.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.POST, value = "/accounts")
	public String createAccount(@RequestBody Account account) {
		LOG.info("createAccount: [{}]", account);
		return accountService.createAccount(account);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/accounts")
	public Collection<Account> getAccount() {
		LOG.info("getAccount: [{}]");
		if (accountService.getAccount() == null
				|| accountService.getAccount().size() == 0) {
			throw new AccountNotFoundException(
					"Currently there are no account details in database to display");
		}
		return accountService.getAccount();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/accounts/{accountId}")
	public Account getAccountById(@PathVariable Integer accountId) {
		LOG.info("getAccountById: [{}]", accountId);
		Account account = accountService.getAccountById(accountId);
		if (account == null) {
			throw new AccountNotFoundException(
					"Account Id is not valid. Please try with valid account id");
		}
		return account;
	}

}
