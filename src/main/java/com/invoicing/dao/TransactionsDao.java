package com.invoicing.dao;

import java.text.ParseException;
import java.util.List;

import com.invoicing.model.Transaction;

public interface TransactionsDao {

void addtransaction(Transaction t);
List<Transaction> getlist();
boolean checkexistancetransaction(String transactionID);
long countnbtransaction();
void updatetvatransaction(String setted_at,double amountttc);
List<Transaction> searchtransacbetweentwodates(String datedeb , String datefin) ;
}
