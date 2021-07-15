package com.invoicing.service;

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
	@Override
	public Company getinfo() {
		// TODO Auto-generated method stub
		return dao.getinfo();
	}
	@Override
	public void updatecompany(String field, String value) {
		dao.updatecompany(field, value);
		
	}

}
