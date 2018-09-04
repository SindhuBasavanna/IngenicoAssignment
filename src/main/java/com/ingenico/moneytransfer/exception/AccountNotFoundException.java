package com.ingenico.moneytransfer.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
/**
 * 
 * @author sindhu
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6438833137905897673L;

	public AccountNotFoundException(String message) {
		super(message);
	}
}
