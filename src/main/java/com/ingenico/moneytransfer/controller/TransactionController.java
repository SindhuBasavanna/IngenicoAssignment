package com.ingenico.moneytransfer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ingenico.moneytransfer.exception.AccountNotFoundException;
import com.ingenico.moneytransfer.exception.InsufficientBalanceException;
import com.ingenico.moneytransfer.model.Transfer;
import com.ingenico.moneytransfer.service.TransactionService;
/**
 * 
 * @author sindhu
 *
 */
@RestController
public class TransactionController {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	TransactionService transactionService;

	@RequestMapping(method = RequestMethod.POST, value = "/transfers")
	public String makeTransfer(@RequestBody Transfer transfer)
			throws InsufficientBalanceException, AccountNotFoundException {
        LOG.info("makeTransfer: [{}]", transfer);
		return transactionService.makeTransfer(transfer);
	}

	//This method is used to simulate real multithreaded environment testing.
		/*@RequestMapping(method = RequestMethod.GET, value = "/tested")
	public String testMultithreading()
			throws AccountNotFoundException {
		return transactionService.testMultithreading();
	}*/
}
