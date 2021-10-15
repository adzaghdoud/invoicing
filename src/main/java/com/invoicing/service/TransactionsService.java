package com.invoicing.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import com.invoicing.model.Transaction;

public interface TransactionsService {
	void addtransaction(Transaction t);
	List<Transaction> getlist(String company);
	boolean checkexistancetransaction(String transactionID);
	long countnbtransaction();
	void updatetvatransaction(String setted_at,double amountttc,Timestamp t,String updated_at);
	List<Transaction> searchtransacbetweentwodates(String datedeb , String datefin,String company) ;
	List<Transaction> searchtransacbetweentwodates_with_tva(String datedeb , String datefin,String company) ;
}
