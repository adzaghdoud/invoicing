package com.invoicing.service;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.invoicing.dao.CompanyDao;
import com.invoicing.model.Company;
@Service("CompanyService")
@Transactional
public class CompanyServiceImpl implements CompanyService {
	@Autowired
    private CompanyDao dao;
	
	public Company getinfo(String rs) {
		// TODO Auto-generated method stub
		return dao.getinfo(rs);
	}

	public void updatecompany(String field, String value) {
		dao.updatecompany(field, value);
		
	}

	public Company getcompanybyraison(String raison) {
		// TODO Auto-generated method stub
		return dao.getcompanybyraison(raison);
	}



	public void updatetimestamprefresh(Timestamp t, String rs) {
		// TODO Auto-generated method stub
		dao.updatetimestamprefresh(t, rs);
	}

	public void updatlogo(String rs, byte[] logo) {
		dao.updatlogo(rs, logo);
	}

	@Override
	public void updatedatecloturecomptable(String rs, String newdate) {
		dao.updatedatecloturecomptable(rs, newdate);
	}



}
