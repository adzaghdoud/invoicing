package com.invoicing.dao;

import com.invoicing.model.Company;

public interface CompanyDao {
	Company getinfo();
	void updatecompany(String field , String value);

}
