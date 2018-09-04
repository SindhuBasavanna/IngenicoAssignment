package com.ingenico.moneytransfer.controller;

import static org.mockito.Matchers.anyObject;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import com.ingenico.moneytransfer.model.Transfer;
import com.ingenico.moneytransfer.repository.AccountRepository;
import com.ingenico.moneytransfer.service.AccountService;
import com.ingenico.moneytransfer.service.TransactionService;
/**
 * 
 * @author sindhu
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = TransactionController.class, secure = false)
@ActiveProfiles("test")
public class TransactionControllerTest {
	@Autowired
	private MockMvc mocMvc;

	@MockBean
	TransactionService transactionServiceMock;
	
	@MockBean
	private AccountService accountServiceMock;
	
	@MockBean
	private AccountRepository accountRepositoryMock;

	
	String jsonString;
	
	Transfer transfer;
	List<Transfer> transfers;
	
	@Before
	public void setup() throws Exception {
		transfers = new ArrayList<Transfer>();
		transfer = new Transfer();
	 transfer.setSourceAccountId(1);
	 transfer.setDestinationAccountId(2);
	 transfer.setAmount(50.00);
	 transfers.add(transfer);
	 ObjectMapper mapper = new ObjectMapper();
		jsonString = mapper.writeValueAsString(transfers);
	}
	
	@Test
	public void testAndTransferDetails() throws Exception {
		when(transactionServiceMock.makeTransfer(anyObject())).thenReturn("Success");
		mocMvc.perform(post("/transfers").
				content(jsonString).
				contentType(MediaType.APPLICATION_JSON));
				
		}
	

}
