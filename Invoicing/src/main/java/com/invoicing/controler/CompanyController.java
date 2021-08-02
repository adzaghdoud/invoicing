package com.invoicing.controler;
import java.util.ArrayList;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.service.CompanyService;
import com.invoicing.tools.Sendmail;

@Controller
public class CompanyController {
	@RequestMapping(value = "/updatecompany", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> getinfoclient(@RequestParam(required = false) String rs ,@RequestParam(required = false) String siret , @RequestParam(required = false) String rib 
			,@RequestParam(required = false) String adresse,@RequestParam(required = false) String ville,@RequestParam(required = false) String cp,@RequestParam(required = false) String bank,
			@RequestParam(required = false) String slug,@RequestParam(required = false) String token)
	    {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		List<String> listfield =new ArrayList<String>();
		        if (!(rs == null)) {
		        	try {
		        		listfield.add("raison sociale");
		        	srvcompany.updatecompany("rs", rs);       
		        	} catch (Exception e) {
		        		context.close();
		        		return ResponseEntity.status(505).body("Erreur enregistrement modification raison sociale base");	
		        	}
		        }
	
		        if (!(siret == null)) {
		        	try {
		        		listfield.add("siret");
		        		srvcompany.updatecompany("siret", siret);
		        	}catch (Exception e) {
		        		context.close();
		        		return ResponseEntity.status(505).body("Erreur enregistrement  modification siret en base");	
		        		
		        	}
		        	
		        }
		    
		        if (!(rib == null)) {
		        	try{
		        		listfield.add("rib");
		        		srvcompany.updatecompany("rib", rib);
		        	}catch (Exception e) {
		        		context.close();
		        		return ResponseEntity.status(505).body("Erreur enregistrement modification rib en base");	
		        		
		        	}
		        
		        }
		        
		        if (!(adresse == null)) {
		       
		        	try {
		        		listfield.add("adresse");
		        		srvcompany.updatecompany("adresse", adresse);	
		        	}catch (Exception e) {
		        		context.close();
		        		return ResponseEntity.status(505).body("Erreur enregistrement  modification adresse en base");	
		        		
		        	}
		           
		        	
		        }
		
		        if (!(ville == null)) {
		        	
		        	try{
		        		listfield.add("ville");
		        		srvcompany.updatecompany("ville", ville);
		        	}catch (Exception e) {
			        		context.close();
			        		return ResponseEntity.status(505).body("Erreur enregistrement modification ville en base");	
			        		
			        	}
		        	
		        }
		
		        if (!(cp == null)) {
		        	try{
		        		listfield.add("code postale");
		        		srvcompany.updatecompany("cp", cp);
		        	}catch (Exception e) {
		        		context.close();
		        	 return ResponseEntity.status(505).body("Erreur enregistrement  modification code postale en base");	
		        		
		        	}
		        	
		        }
		        
		        if (!(bank == null)) {
		        	try{
		        		listfield.add("banque");
		        		srvcompany.updatecompany("bank", bank);
		        	
		        	}catch (Exception e) {
		        		context.close();
		        	 return ResponseEntity.status(505).body("Erreur enregistrement  modification banque en base");	
		        		
		        	}
		        	
		        }
		        
		        if (!(slug == null)) {
		        	try{
		        		listfield.add("sulg");
		        		srvcompany.updatecompany("slug", slug);
		        	}catch (Exception e) {
		        		context.close();
		        	 return ResponseEntity.status(505).body("Erreur enregistrement  modification slug en base");	
		        		
		        	}
	
		        }
		
		        if (!(token == null)) {
		        	try{
		        		listfield.add("token");
		        		srvcompany.updatecompany("token", token);
		        	}catch (Exception e) {
		        		context.close();
		        	 return ResponseEntity.status(505).body("Erreur enregistrement  modification token en base");	
		        		
		        	}
		        }     
		context.close();
		String listoffields="";
		for (int i=0; i<listfield.size(); i++) {
			listoffields=listfield.get(i)+" "+listoffields;
			
		}
		return ResponseEntity.ok("La modification a été bien prise en compte pour "+listoffields);
	}


	@PostMapping(value = "/sendmail")
	public  @ResponseBody ResponseEntity<String> sendmail(@RequestParam(required = true) String mailto,@RequestParam(required = true) String subject,@RequestParam(required = true) String contain,@RequestParam(required = false) MultipartFile attached_file,@RequestParam (required = false)String attached_file_name) {
	Sendmail s= new Sendmail();
	s.setContain(contain);
	s.setSubject(subject);
	s.setMailto(mailto);
	s.setFile(attached_file);
	s.setFilename(attached_file_name);
	s.setSystempath("pdf.stor");
	if (! s.send() ) {
	 return ResponseEntity.status(505).body("une erreur est survenue lors de l'envoi du mail");	
	}
		
	return ResponseEntity.ok("Le mail a été bien envoyé à "+mailto);	
	}
}
