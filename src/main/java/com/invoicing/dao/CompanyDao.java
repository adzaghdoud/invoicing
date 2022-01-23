package com.invoicing.dao;

import java.sql.Timestamp;

import com.invoicing.model.Company;

public interface CompanyDao {
	Company getinfo(String rs);
	void updatecompany(String field , String value);
	Company getcompanybyraison(String raison);
	void updatetimestamprefresh(Timestamp t, String rs);
	void updatlogo(String rs, byte[] logo);
	void updatedatecloturecomptable(String rs,String newdate);

}
