package com.invoicing.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoicing.dao.ClientDao;
import com.invoicing.model.Article;
import com.invoicing.model.Client;
@Service("ClientService")
@Transactional
public class ClientServiceImpl implements ClientService {
	@Autowired
    private ClientDao dao;
	
	public Client getclientbyraisonsociale(String rs)  {
		// TODO Auto-generated method stub
		return dao.getclientbyraisonsociale(rs);
	}
	
	public void addclient(Client c) {
		dao.addclient(c);
		
	}
	
	public List<Client> getallclients() {
		// TODO Auto-generated method stub
		return dao.getallclients();
	}


	public Long numberclient() {
		// TODO Auto-generated method stub
		return dao.numberclient();
	}

	public Client getclientbyemail(String mail) {
		// TODO Auto-generated method stub
		return dao.getclientbyemail(mail);
	}

	public Client getclientbyemailandraisonsociale(String rs, String mail) {
		// TODO Auto-generated method stub
		return dao.getclientbyemailandraisonsociale(rs, mail);
	}

	public void updateclient(Client c) {
		// TODO Auto-generated method stub
		dao.updateclient(c);
	}



}
