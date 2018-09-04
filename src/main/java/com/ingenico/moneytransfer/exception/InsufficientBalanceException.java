package com.ingenico.moneytransfer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * 
 * @author sindhu
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InsufficientBalanceException extends Exception {
	private static final long serialVersionUID = 1350491980758966320L;

	public InsufficientBalanceException(String message) {
		super(message);
	}

}
