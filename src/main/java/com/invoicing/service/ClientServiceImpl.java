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
	@Override
	public Client getclientbyraisonsociale(String rs)  {
		// TODO Auto-generated method stub
		return dao.getclientbyraisonsociale(rs);
	}
	@Override
	public void addclient(Client c) {
		dao.addclient(c);
		
	}
	@Override
	public List<Client> getallclients() {
		// TODO Auto-generated method stub
		return dao.getallclients();
	}

}
