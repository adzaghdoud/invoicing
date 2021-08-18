package com.invoicing.dao;

import java.util.List;

import com.invoicing.model.Client;

public interface ClientDao {
	Client getclientbyraisonsociale(String rs);
	Client getclientbyemail(String mail);
	Client getclientbyemailandraisonsociale(String rs , String mail);
	void addclient(Client c);
	List<Client> getallclients();
	Long numberclient();
	void updateclient(Client c);
}
