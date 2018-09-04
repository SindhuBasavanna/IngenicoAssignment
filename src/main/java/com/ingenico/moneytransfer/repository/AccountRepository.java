package com.ingenico.moneytransfer.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ingenico.moneytransfer.model.Account;

/**
 * 
 * @author sindhu
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	public Account findByAccountId(@Param(value = "accountId") Integer accountId);

	@Query("update com.ingenico.moneytransfer.model.Account acc set acc.balance = :balance where acc.accountId= :accountId")
	@Modifying
	@Transactional
	public void updateById(@Param("accountId") Integer accountId,
			@Param("balance") double balance);

	@Query("Select balance from com.ingenico.moneytransfer.model.Account acc where acc.accountId= :accountId")
	public Double getBalanceById(@Param("accountId") Integer accountId);

}
