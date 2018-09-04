package com.ingenico.moneytransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * 
 * @author sindhu
 *
 */
@SpringBootApplication
@ComponentScan({"com.ingenico.moneytransfer.service",
"com.ingenico.moneytransfer.model","com.ingenico.moneytransfer.controller","com.ingenico.moneytransfer.daoimpl",
"com.ingenico.moneytransfer.repository","com.ingenico.moneytransfer.exception","com.ingenico.moneytransfer.util"})
public class MoneyTransferApplication {
	public static void main(String[] args) {
		SpringApplication.run(MoneyTransferApplication.class, args);
	}
}
