package com.invoicing.service;

import java.util.List;

import com.invoicing.model.Article;
import com.invoicing.model.Client;

public interface ClientService {
	Client getclientbyraisonsociale(String rs);
	void addclient(Client c);
	List<Client> getallclients();
	Long numberclient();
	Client getclientbyemail(String mail);
	Client getclientbyemailandraisonsociale(String rs , String mail);
	void updateclient(Client c);

}
