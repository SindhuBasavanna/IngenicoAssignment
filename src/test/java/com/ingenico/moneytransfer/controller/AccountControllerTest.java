package com.ingenico.moneytransfer.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingenico.moneytransfer.model.Account;
import com.ingenico.moneytransfer.repository.AccountRepository;
import com.ingenico.moneytransfer.service.AccountService;
/**
 * 
 * @author sindhu
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountController.class, secure = false)
@ActiveProfiles("test")
public class AccountControllerTest {
	@Autowired
	private MockMvc mocMvc;

	private Account account1;
	private List<Account> accounts;

	String jsonString;

	@MockBean
	private AccountService accountServiceMock;

	@MockBean
	private AccountRepository accountRepositoryMock;

	@Before
	public void setup() throws Exception {
		accounts = new ArrayList<Account>();
		account1 = new Account();
		account1.setAccountId(1);
		account1.setUserName("De Coster");
		account1.setBalance(500);
		accounts.add(account1);

		ObjectMapper mapper = new ObjectMapper();
		jsonString = mapper.writeValueAsString(accounts);
	}

	@Test
	public void testAndRetrieveAccountDetails() throws Exception {
		when(accountServiceMock.getAccount()).thenReturn(accounts);
		mocMvc.perform(get("/accounts")).andExpect(status().is(200));
	}

	@Test
	public void testAndRetrieveAccountDetail() throws Exception {
		when(accountServiceMock.getAccountById(1)).thenReturn(account1);
		mocMvc.perform(get("/accounts").param("accountId", "1").accept(
				MediaType.APPLICATION_JSON));
	}

	@Test
	public void testAndPostAccountDetails() throws Exception {
		when(accountServiceMock.createAccount(anyObject())).thenReturn(
				"Success");
		mocMvc.perform(post("/accounts").content(jsonString).contentType(
				MediaType.APPLICATION_JSON));
	}

}
