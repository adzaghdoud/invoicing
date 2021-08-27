package com.invoicing.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import com.invoicing.model.Transaction;

public interface TransactionsService {
	void addtransaction(Transaction t);
	List<Transaction> getlist();
	boolean checkexistancetransaction(String transactionID);
	long countnbtransaction();
	void updatetvatransaction(String setted_at,double amountttc,Timestamp t);
	List<Transaction> searchtransacbetweentwodates(String datedeb , String datefin) ;
}
