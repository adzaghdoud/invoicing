package com.invoicing.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.invoicing.model.Prestations;

public interface PrestationsDao {
	void addprestation(Prestations p);
	Long getlast_id_prestation();
	List<Prestations> getlistprestations(String company);
	List<Prestations> getlistprestationsbyyear(String company);
	List<Prestations> getlistprestations_until_date_cloture(String company,String datecloture);
	Prestations getperstationbynumfacture(String numfacture,String company);
	List<Prestations> getpendingpaiement();
	void validate_paiement(String numfacture);
	long number_paiement_to_validate();
	long number_paiement_validate();


}
