package com.ingenico.moneytransfer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingenico.moneytransfer.model.Account;
import com.ingenico.moneytransfer.repository.AccountRepository;
import com.ingenico.moneytransfer.util.LockUtil;
/**
 * 
 * @author sindhu
 *
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AccountServiceTest {
	AccountService accountService;
	String jsonString;
	private Account account1;
	private List<Account> accounts;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private LockUtil lockMock;

	@Before
	public void init() {
		accountRepository = Mockito.mock(AccountRepository.class);
		lockMock = Mockito.mock(LockUtil.class);
	}

	@Before
	public void setup() throws Exception {
		accounts = new ArrayList<Account>();
		account1 = new Account();
		account1.setAccountId(1001);
		account1.setUserName("De Coster");
		account1.setBalance(500);
		accounts.add(account1);

		ObjectMapper mapper = new ObjectMapper();
		jsonString = mapper.writeValueAsString(accounts);

		accountService = new AccountService();
		accountService.setAccountRepository(accountRepository);
		accountService.setLockUtil(lockMock);
	}

	@Test
	public void test_createAccount() {
		when(accountRepository.save(account1)).thenReturn(account1);
		assertEquals("Account details added successfully: 1001",
				accountService.createAccount(account1));
	}

	@Test
	public void test_getAccount() {
		when(accountRepository.findAll()).thenReturn(accounts);
		assertNotNull(accountService.getAccount());
	}

	@Test
	public void test_getAccountById() {
		when(accountRepository.findByAccountId(1001)).thenReturn(account1);
		assertNotNull(accountService.getAccountById(1001));
	}

}