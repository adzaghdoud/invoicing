package com.invoicing.dao;

import java.util.List;

import com.invoicing.model.Client;

public interface ClientDao {
	Client getclientbyraisonsociale(String rs);
	void addclient(Client c);
	List<Client> getallclients();
	Long numberclient();
}
