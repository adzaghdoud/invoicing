package com.invoicing.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import com.invoicing.model.Transaction;

public interface TransactionsDao {

void addtransaction(Transaction t);
boolean checkexistancetransaction(String transactionID);
long countnbtransaction();
List<Transaction> getlist();
void updatetvatransaction(String setted_at,double amountttc,Timestamp t);
List<Transaction> searchtransacbetweentwodates(String datedeb , String datefin) ;
}
