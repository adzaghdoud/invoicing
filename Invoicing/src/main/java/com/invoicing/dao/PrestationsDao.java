package com.invoicing.dao;

import java.util.HashMap;
import java.util.List;

import com.invoicing.model.Prestations;

public interface PrestationsDao {
	void addprestation(Prestations p);
	Long getlast_id_prestation();
	List<Prestations> getlistprestations();
	Prestations getperstationbynomfacture(String nomfacture);
	List<Prestations> getpendingpaiement();
	void validate_paiement(String numfacture);
	double chiffre_affaire();
	long number_paiement_to_validate();
	long number_paiement_validate();


}
