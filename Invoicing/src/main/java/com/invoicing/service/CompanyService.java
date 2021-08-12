package com.invoicing.service;

import java.sql.Timestamp;

import com.invoicing.model.Company;

public interface CompanyService {
	Company getinfo();
	void updatecompany(String field , String value);
	Company getcompanybyraison(String raison);
	void updatelogocompany(byte[] logo , String raison);
	void updatetimestamprefresh(Timestamp t, String rs);

}
