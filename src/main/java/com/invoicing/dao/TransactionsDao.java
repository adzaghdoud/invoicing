package com.invoicing.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import com.invoicing.model.Transaction;

public interface TransactionsDao {

void addtransaction(Transaction t);
boolean checkexistancetransaction(String transactionID);
long countnbtransaction();
List<Transaction> getlist(String company);
void updatetvatransaction(String setted_at,double amountttc,Timestamp t,String updated_at);
List<Transaction> searchtransacbetweentwodates_with_tva(String datedeb , String datefin,String company) ;
List<Transaction> searchtransacbetweentwodates(String datedeb , String datefin,String company) ;
}
