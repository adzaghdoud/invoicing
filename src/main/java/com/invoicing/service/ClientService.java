package com.invoicing.service;

import java.util.List;

import com.invoicing.model.Client;

public interface ClientService {
	Client getclientbyraisonsociale(String rs);
	void addclient(Client c);
	List<Client> getallclients();
}
