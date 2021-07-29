package com.invoicing.service;

import java.util.List;

import com.invoicing.model.Transaction;

public interface TransactionsService {
	void addtransaction(Transaction t);
	List<Transaction> getlist();
	boolean checkexistancetransaction(String transactionID);
}
