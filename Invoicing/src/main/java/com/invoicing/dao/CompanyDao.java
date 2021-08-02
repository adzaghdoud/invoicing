package com.invoicing.dao;

import com.invoicing.model.Company;

public interface CompanyDao {
	Company getinfo();
	void updatecompany(String field , String value);
	Company getcompanybyraison(String raison);
	void updatelogocompany(byte[] logo , String raison);

}
