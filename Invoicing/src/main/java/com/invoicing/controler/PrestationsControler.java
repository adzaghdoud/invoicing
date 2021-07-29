package com.invoicing.controler;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.model.Client;
import com.invoicing.model.Company;
import com.invoicing.model.Prestations;
import com.invoicing.service.ClientService;
import com.invoicing.service.CompanyService;
import com.invoicing.service.PrestationsService;
import com.invoicing.tools.Generatepdf;
import com.invoicing.tools.Sendmail;

@Controller
public class PrestationsControler {
	@PostMapping(value = "/generateinvoice")
	public  @ResponseBody ResponseEntity<String> generateinvoice(@RequestParam(required = true) int  quantite,@RequestParam(required = true) double  pvht,
			@RequestParam(required = true) double  pvttc , @RequestParam(required = true) double  totalttc , @RequestParam(required = true) String namearticle, @RequestParam(required = true) String nomclient,HttpServletResponse response,
			@RequestParam(required = true) String taxe,@RequestParam(required = true) double valtaxe){
		  //save prestation in BDD
		 
		  AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		  PrestationsService srvprestation = (PrestationsService) context.getBean("PrestationsService");
		  CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		  ClientService srvclient = (ClientService) context.getBean("ClientService");
         
		  Date date = new Date();
          Timestamp ts=new Timestamp(date.getTime());
          SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
          String nomfacture;
          String numfacture;
          Prestations p = new Prestations();
		  if (srvprestation.getlast_id_prestation() <10) {
	      numfacture=formatter.format(date)+"-"+"0"+srvprestation.getlast_id_prestation();
		  nomfacture="Facture"+formatter.format(date)+"-"+"0"+srvprestation.getlast_id_prestation()+"-"+nomclient;
		  p.setNomfacture(nomfacture);
		  p.setNumfacture(numfacture);
	      }
		  else {
		  numfacture=formatter.format(date)+"-"+srvprestation.getlast_id_prestation();
	      nomfacture= "Facture"+formatter.format(date)+"-"+srvprestation.getlast_id_prestation()+"-"+nomclient;
		  p.setNumfacture(numfacture);
		  p.setNomfacture(nomfacture);
		  }
		  p.setDate(ts);
		  p.setQuantite(quantite);
		  p.setMontantHT(pvht);
		  p.setMontantTTC(pvttc);
		  p.setArticle(namearticle); 
		  p.setTotalttc(totalttc);
		  p.setValtaxe(valtaxe);
		  p.setTaxe(taxe);
		  p.setStatut_paiement("en attente");
		  p.setClient(nomclient);
		  try {
		  srvprestation.addprestation(p);
		  }
		  catch (Exception e) {  
		  System.out.println(ExceptionUtils.getStackTrace(e));	 
		  context.close();
		  return ResponseEntity.status(505).body("Erreur saisie nouvelle prestation en BDD");
		  }
		  
		  Generatepdf pdf = new Generatepdf();
		  pdf.setCompany(srvcompany.getinfo());
		  pdf.setFilename(nomfacture);
		  pdf.setFiletype("invoice");
		  pdf.setClient(srvclient.getclientbyraisonsociale(nomclient));
		  pdf.setPrestation(srvprestation.getperstationbynomfacture(nomfacture));
		  if (! pdf.generate(response)) {
		      context.close();
			  return ResponseEntity.status(506).body("Erreur génération facture : Facture"+formatter.format(date)+"-"+srvprestation.getlast_id_prestation()+"-"+nomclient); 
			  
		  }
		  context.close();
		  return ResponseEntity.ok("La facture "+nomfacture+"a été bien générée");
		
	}

	@PostMapping(value = "/validatepaiement/{numfacture}")
	public  @ResponseBody  boolean validatepaiement (@PathVariable("numfacture") String numfacture){
		  AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		  PrestationsService srvprestation = (PrestationsService) context.getBean("PrestationsService");
		  try {
			  srvprestation.validate_paiement(numfacture);
			  context.close();
			  return true;
		  }catch(Exception e) {
			  System.out.println(ExceptionUtils.getStackTrace(e));
			  context.close();
			  return false;
		  }
		
	}

	
	@PostMapping(value = "/relance_paiement/{nomfacture}/{nomclient}")
	public  @ResponseBody  boolean validatepaiement (@PathVariable("nomfacture") String nomfacture , @PathVariable("nomclient") String nomclient,HttpServletResponse response){
		  AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		  PrestationsService srvprestation = (PrestationsService) context.getBean("PrestationsService");
		  CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		  ClientService srvclient = (ClientService) context.getBean("ClientService");
		  Client c = srvclient.getclientbyraisonsociale(nomclient);
		  Company company = srvcompany.getinfo();
		  Prestations p = srvprestation.getperstationbynomfacture(nomfacture);
		  Generatepdf g = new Generatepdf();
		  g.setClient(c);
		  g.setCompany(company);
		  g.setFilename(nomfacture);
		  g.setPrestation(p);
	
		  if ( ! g.generate(response) ) {
			  context.close();
			  return false;
		  }
	
		  
		  
		  Sendmail s = new Sendmail();
		  s.setFilename(nomfacture);
		  s.setSystempath("pdf.stor");
		  s.setMailto(c.getMail());
		  s.setSubject("Relance Facture "+nomfacture);
		  s.setContain("Merci de penser à régler la facture ci-jointe , Cordialement ");
		  s.setFiletype("pdf");
		  
		  if (! s.send() ) {
			  context.close();
			  return false;
		    
		  }
		  
		  		  
		context.close();
		return true;
		
	}
	
	@RequestMapping(value = "/liste_prestations", method = RequestMethod.GET)
	public @ResponseBody List<Prestations> getprestations() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		PrestationsService srvprestations = (PrestationsService) context.getBean("PrestationsService");	
	    List<Prestations> p = srvprestations.getlistprestations();
	    context.close();
		return p;
	
	}
	
	


}
