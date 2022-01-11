package com.invoicing.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoicing.dao.PrestationsDao;
import com.invoicing.model.Prestations;
@Service("PrestationsService")
@Transactional
public class PrestationsServiceImpl implements PrestationsService{
	@Autowired
    private PrestationsDao dao;

	public void addprestation(Prestations p) {
		// TODO Auto-generated method stub
		dao.addprestation(p);
	}


	public Long getlast_id_prestation() {
		// TODO Auto-generated method stub
		return dao.getlast_id_prestation();
	}

	
	public List<Prestations> getlistprestations(String company) {
		// TODO Auto-generated method stub
		return dao.getlistprestations(company);
	}

	
	public Prestations getperstationbynumfacture(String numfacture,String company) {
		// TODO Auto-generated method stub
		return dao.getperstationbynumfacture(numfacture, company);
	}

	
	public List<Prestations> getpendingpaiement() {
		// TODO Auto-generated method stub
		return dao.getpendingpaiement();
	}


	public void validate_paiement(String numfacture) {
		dao.validate_paiement(numfacture);
		
	}

	public long number_paiement_to_validate() {
		// TODO Auto-generated method stub
		return dao.number_paiement_to_validate();
	}


	public long number_paiement_validate() {
		// TODO Auto-generated method stub
		return dao.number_paiement_validate();
	}


	public List<Prestations> getlistprestationsbyyear(String company) {
		return dao.getlistprestationsbyyear(company);
	}


	public List<Prestations> getlistprestations_until_date_cloture(String company, String datecloture) {
		return dao.getlistprestations_until_date_cloture(company, datecloture);
	}


	public Prestations getperstationbynomfacture(String nomfacture, String company) {
		return dao.getperstationbynomfacture(nomfacture, company);
	}

	@Override
	public void DeletePrestation(String invoicename) {
		dao.DeletePrestation(invoicename);
	}


	@Override
	public void UpdatePrestation(Prestations p) {
		dao.UpdatePrestation(p);
	}
}
