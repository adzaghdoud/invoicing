package com.invoicing.service;

import com.invoicing.model.Company;

public interface CompanyService {
	Company getinfo();
	void updatecompany(String field , String value);

}
