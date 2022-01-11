package com.invoicing.service;
import java.util.List;

import com.invoicing.model.Prestations;

public interface PrestationsService {
	void addprestation(Prestations p);
	Long getlast_id_prestation();
	List<Prestations> getlistprestations(String company);
	Prestations getperstationbynumfacture(String numfacture,String company);
	Prestations getperstationbynomfacture(String nomfacture,String company);
	List<Prestations> getlistprestationsbyyear(String company);
	List<Prestations> getlistprestations_until_date_cloture(String company,String datecloture);
	List<Prestations> getpendingpaiement();
	void validate_paiement(String numfacture);
	long number_paiement_to_validate();
	long number_paiement_validate();
	void DeletePrestation (String invoicename);
	void UpdatePrestation(Prestations p);

}
