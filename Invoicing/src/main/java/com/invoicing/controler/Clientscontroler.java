package com.invoicing.controler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.model.Client;
import com.invoicing.service.ClientService;


@Controller
public class Clientscontroler {
	@SuppressWarnings("resource")
	@RequestMapping(value = "/searchclient", method = RequestMethod.POST)
	public @ResponseBody Client getinfoclient(@RequestParam Map<String,String> requestParams) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		ClientService srvclient = (ClientService) context.getBean("ClientService");
		Client c = new Client(); 	
		if (!requestParams.get("nom").isEmpty()) {		
			try {
			c=srvclient.getclientbyraisonsociale(requestParams.get("nom"));
			} catch (NoResultException  e) {
				return null;
			}
			
		}
		
		if (!requestParams.get("email").isEmpty()) {
			c= srvclient.getclientbyraisonsociale(requestParams.get("email"));
			}
		context.close();
		return c;
	}
	
	@RequestMapping(value = "/Getallclients", method = RequestMethod.GET)
	public @ResponseBody List<Client> Getallclients() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		ClientService srvclient = (ClientService) context.getBean("ClientService");
		List<Client> listc= srvclient.getallclients();
		context.close();
		return listc;
	}
	
	
	
	
	
	
	

	@RequestMapping(value = "/createclient", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String>  addnewclient(@RequestBody Client c) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		ClientService srvclient = (ClientService) context.getBean("ClientService");		
		try{
		srvclient.addclient(c);	
		}catch (Exception e) {
			if (e.getMessage().contains("org.hibernate.exception.ConstraintViolationException: ")) {
			SQLException ex = (SQLException) e.getCause().getCause();
			context.close();
			return ResponseEntity.status(550).body(ex.getMessage().substring(0, ex.getMessage().indexOf("for key")));
			}
			else {
		    context.close();
			return ResponseEntity.status(551).body("Erreur Enregistrement en base de données");	
			}
			}
			context.close();
		    return ResponseEntity.ok("le nouveau client"+c.getRs()+"a été bien créé");
		}
	}

