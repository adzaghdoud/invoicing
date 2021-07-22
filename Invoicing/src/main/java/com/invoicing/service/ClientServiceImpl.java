package com.invoicing.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoicing.dao.ClientDao;
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

	@Override
	public Long numberclient() {
		// TODO Auto-generated method stub
		return dao.numberclient();
	}

}
