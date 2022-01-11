package com.invoicing.controler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.model.Article;
import com.invoicing.model.Client;
import com.invoicing.model.Company;
import com.invoicing.model.Prestations;
import com.invoicing.service.ClientService;
import com.invoicing.service.CompanyService;
import com.invoicing.service.LoginsService;
import com.invoicing.service.PrestationsService;
import com.invoicing.tools.Generatepdf;
import com.invoicing.tools.Sendmail;

@Controller
public class PrestationsControler {
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/generateinvoice")
	public  @ResponseBody JSONObject generateinvoice(@RequestBody Prestations p ,HttpServletResponse response ,@CookieValue("invoicing_username") String cookielogin){	
		  JSONObject jsonresult = new JSONObject();
		  AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		  PrestationsService srvprestation = (PrestationsService) context.getBean("PrestationsService");
		  CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		  LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		  ClientService srvclient = (ClientService) context.getBean("ClientService");
          p.setCompany(srvlogins.getinfo(cookielogin).getCompany());
		  Date date = new Date();
          Timestamp ts=new Timestamp(date.getTime());
          SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
          String nomfacture;
          String numfacture;
		  if (srvprestation.getlast_id_prestation() <10) {
	      numfacture=formatter.format(date)+"-"+"0"+srvprestation.getlast_id_prestation();
		  nomfacture="Facture"+formatter.format(date)+"-"+"0"+srvprestation.getlast_id_prestation()+"-"+p.getClient();
		  p.setNomfacture(nomfacture);
		  p.setNumfacture(numfacture);
	      }
		  else {
		  long numseq=srvprestation.getlast_id_prestation()+1;
		  numfacture=formatter.format(date)+"-"+numseq;
	      nomfacture= "Facture"+formatter.format(date)+"-"+numseq+"-"+p.getClient();
		  p.setNumfacture(numfacture);
		  p.setNomfacture(nomfacture);
		  }
		  p.setDate(ts);
		  p.setStatut_paiement("en attente");
		  try {
		  srvprestation.addprestation(p);
		  }
		  catch (Exception e) {  
		  System.out.println(ExceptionUtils.getStackTrace(e));	 
		  context.close();
		  jsonresult.put("msg", "Erreur saisie nouvelle prestation en BDD");
		  return jsonresult;
		  }
		  
		  Generatepdf pdf = new Generatepdf();
		  pdf.setCompany(srvcompany.getinfo(srvlogins.getinfo(cookielogin).getCompany()));
		  pdf.setFilename(nomfacture);
		  pdf.setFiletype("invoice");
		  pdf.setClient(srvclient.getclientbyraisonsociale(p.getClient()));
		  pdf.setPrestation(srvprestation.getperstationbynumfacture(numfacture,srvlogins.getinfo(cookielogin).getCompany()));
		  JSONObject json = pdf.generate(response);
		  
		  if (json.get("msg").toString().contains("Error")) {
		      context.close();
		      jsonresult.put("msg", json.get("msg").toString());
			  return jsonresult;
			  
		  }
		  jsonresult.put("Invoice_path",srvlogins.getinfo(cookielogin).getCompany()+"/INVOICES/"+nomfacture+".pdf");
		  jsonresult.put("Invoice_name",nomfacture+".pdf");
		  jsonresult.put("msg","La facture "+nomfacture+".pdf a été bien générée");
		  context.close();
		  return jsonresult;
		
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
	public  @ResponseBody  boolean validatepaiement (@PathVariable("nomfacture") String nomfacture , @PathVariable("nomclient") String nomclient,HttpServletResponse response,@CookieValue("invoicing_username") String cookielogin){
		  AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		  PrestationsService srvprestation = (PrestationsService) context.getBean("PrestationsService");
		  CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		  ClientService srvclient = (ClientService) context.getBean("ClientService");
		  LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		  Client c = srvclient.getclientbyraisonsociale(nomclient);
		  Company company = srvcompany.getinfo(srvlogins.getinfo(cookielogin).getCompany());
		  Prestations p = srvprestation.getperstationbynumfacture(nomfacture,srvlogins.getinfo(cookielogin).getCompany());
		  Generatepdf g = new Generatepdf();
		  g.setClient(c);
		  g.setCompany(company);
		  g.setFilename(nomfacture);
		  g.setPrestation(p);
	
		  /*if ( ! g.generate(response) ) {
			  context.close();
			  return false;
		  } */ 
		  Sendmail s = new Sendmail();
		  s.setFilename(nomfacture+".pdf");
		  s.setMailto(c.getMail());
		  s.setSubject("Relance Facture "+nomfacture);
		  s.setContain("Merci de penser de régler la facture ci-jointe , Cordialement ");
		  
		  if (! s.send() ) {
			  context.close();
			  return false;
		    
		  }
		  
		  		  
		context.close();
		return true;
		
	}
	
	@RequestMapping(value = "/liste_prestations_by_year", method = RequestMethod.GET)
	public @ResponseBody List<Prestations> getprestations(@CookieValue("invoicing_username") String cookielogin) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		PrestationsService srvprestations = (PrestationsService) context.getBean("PrestationsService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
	    List<Prestations> p = srvprestations.getlistprestationsbyyear(srvlogins.getinfo(cookielogin).getCompany());
	    context.close();
		return p;
	
	}
	
	@GetMapping(value = "/Get_Prestation_by_numfacture/{numfacture}")
	public  @ResponseBody  Prestations Getprestation (@PathVariable("numfacture") String numfacture,@CookieValue("invoicing_username") String cookielogin){
		
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		PrestationsService srvprestations = (PrestationsService) context.getBean("PrestationsService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		Prestations p = srvprestations.getperstationbynumfacture(numfacture, srvlogins.getinfo(cookielogin).getCompany());
		context.close();
		return p;
	
	
	}

	@PostMapping(value = "/Generate_Post_Invoice/{nomfacture}",produces = "application/pdf")
	public  ResponseEntity<byte[]> GeneratePostinvoice(@PathVariable("nomfacture") String nomfacture,@CookieValue("invoicing_username") String cookielogin){	
		  AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		  PrestationsService srvprestation = (PrestationsService) context.getBean("PrestationsService");
		  CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		  LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		  ClientService srvclient = (ClientService) context.getBean("ClientService");
		  Prestations P = srvprestation.getperstationbynomfacture(nomfacture, srvlogins.getinfo(cookielogin).getCompany());
		  Client c = srvclient.getclientbyraisonsociale(P.getClient());
		  Generatepdf pdf = new Generatepdf();
		  pdf.setCompany(srvcompany.getinfo(srvlogins.getinfo(cookielogin).getCompany()));
		  pdf.setFilename(nomfacture);
		  pdf.setFiletype("invoice");
		  pdf.setClient(c);
		  pdf.setPrestation(P);
	      HttpHeaders headers = new HttpHeaders();
	      headers.add("content-disposition", "attachment; filename=" + nomfacture+".pdf");
	      ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
	      pdf.GenerateInvoiceAsBytes(), headers, HttpStatus.OK);
		  context.close();
		  return response;
		
	}

	

	
	@PostMapping(value = "/DetelePrestation/{invoicename}")
	public  @ResponseBody ResponseEntity<String> DeletePrestation(@PathVariable("invoicename") String invoicename){
		  AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		  PrestationsService srvprestation = (PrestationsService) context.getBean("PrestationsService");
		  try {
			  srvprestation.DeletePrestation(invoicename);
			  context.close();
		
		  }catch(Exception e) {
			  System.out.println(ExceptionUtils.getStackTrace(e));
			  context.close();
			  return ResponseEntity.status(505).body("Une erreur est servenue , merci de consulter la log");
		  }
		  return ResponseEntity.ok("Suppresion Prestation OK");
	}

	
	@PostMapping(value = "/ModifyPrestation")
	public  @ResponseBody ResponseEntity<String> UpdatePrestation(@RequestBody Prestations  p ,@CookieValue("invoicing_username") String cookielogin){
		  AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		  PrestationsService srvprestation = (PrestationsService) context.getBean("PrestationsService");
		  LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		  p.setCompany(srvlogins.getinfo(cookielogin).getCompany());
		  try {
	     	  srvprestation.UpdatePrestation(p);
			  context.close();
		
		  }catch(Exception e) {
			  System.out.println(ExceptionUtils.getStackTrace(e));
			  context.close();
			  return ResponseEntity.status(505).body("Une erreur est servenue , merci de consulter la log");
		  }
		  return ResponseEntity.ok("Update Prestation OK");
	}
	
	
	
   



}
	

