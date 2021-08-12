package com.invoicing.service;

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

	
	public List<Prestations> getlistprestations() {
		// TODO Auto-generated method stub
		return dao.getlistprestations();
	}

	
	public Prestations getperstationbynomfacture(String nomfacture) {
		// TODO Auto-generated method stub
		return dao.getperstationbynomfacture(nomfacture);
	}

	
	public List<Prestations> getpendingpaiement() {
		// TODO Auto-generated method stub
		return dao.getpendingpaiement();
	}


	public void validate_paiement(String numfacture) {
		dao.validate_paiement(numfacture);
		
	}


	public double chiffre_affaire() {
		// TODO Auto-generated method stub
		return dao.chiffre_affaire();
	}


	public long number_paiement_to_validate() {
		// TODO Auto-generated method stub
		return dao.number_paiement_to_validate();
	}


	public long number_paiement_validate() {
		// TODO Auto-generated method stub
		return dao.number_paiement_validate();
	}

}
