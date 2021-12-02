package com.invoicing.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import org.json.simple.JSONObject;

import com.invoicing.model.Transaction;

public interface TransactionsService {
	void addtransaction(Transaction t);
	List<Transaction> getlist(String company);
	boolean checkexistancetransaction(String transactionID);
	long countnbtransaction();
	long count_debit_trancactions(String company);
	long count_credit_trancactions(String company);
	void updatetvatransaction(String setted_at,double amountttc,Timestamp t,String updated_at);
	List<Transaction> searchtransacbetweentwodates(String datedeb , String datefin,String company) ;
	List<Transaction> searchtransacbetweentwodates_with_tva(String datedeb , String datefin,String company) ;
	void updateproof(String settled_at, String updated_at, String proof_file_name);
	JSONObject checkeexistproof(String settled_at, String updated_at,String company);
}
