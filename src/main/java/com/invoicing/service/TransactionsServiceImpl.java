package com.invoicing.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.invoicing.dao.TransactionsDao;
import com.invoicing.model.Transaction;
@Service("TransactionsService")
@Transactional
public class TransactionsServiceImpl implements TransactionsService {
	@Autowired
    private TransactionsDao dao;

	public void addtransaction(Transaction t) {
		// TODO Auto-generated method stub
		dao.addtransaction(t);
	}

	public List<Transaction> getlist(String company) {
		// TODO Auto-generated method stub
		return dao.getlist(company);
	}

	public boolean checkexistancetransaction(String transactionID) {
		// TODO Auto-generated method stub
		return dao.checkexistancetransaction(transactionID);
	}

	public long countnbtransaction() {
		// TODO Auto-generated method stub
		return dao.countnbtransaction();
	}

	public void updatetvatransaction(String setted_at, double amountttc,Timestamp t,String updated_at) {
		// TODO Auto-generated method stub
		dao.updatetvatransaction(setted_at, amountttc,t,updated_at);
	}

	public List<Transaction> searchtransacbetweentwodates(String datedeb, String datefin,String company)  {
		// TODO Auto-generated method stub
		return dao.searchtransacbetweentwodates(datedeb, datefin,company);
	}

	public List<Transaction> searchtransacbetweentwodates_with_tva(String datedeb, String datefin, String company) {
		return dao.searchtransacbetweentwodates_with_tva(datedeb, datefin, company);
	}

	@Override
	public long count_debit_trancactions(String company) {
		return dao.count_debit_trancactions(company);
	}

	@Override
	public long count_credit_trancactions(String company) {
		return dao.count_credit_trancactions(company);
	}

	@Override
	public void updateproof(String settled_at, String updated_at, String proof_file_name) {
		dao.updateproof(settled_at, updated_at, proof_file_name);
	}

	@Override
	public JSONObject checkeexistproof(String settled_at, String updated_at,String company) {
		return dao.checkeexistproof(settled_at, updated_at,company);
	}

}
