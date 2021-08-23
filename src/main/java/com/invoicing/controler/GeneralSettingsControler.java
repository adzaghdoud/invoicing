package com.invoicing.controler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GeneralSettingsControler {
	final org.apache.logging.log4j.Logger logger =  LogManager.getLogger(this.getClass().getName());
	@RequestMapping(value = "/update_general_settings", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> updatesettings(@RequestParam(required = false) String smtphost,@RequestParam(required = false) String smptport ,@RequestParam(required = false) String smtpusername,@RequestParam(required = false) String smtppassword,@RequestParam(required = false) String companyemail,@RequestParam(required = false) String ldapadmin,@RequestParam(required = false) String ldappassword,@RequestParam(required = false) String ldaphost,@RequestParam(required = false) String ldapport ) {
		try {
			InputStream input = new FileInputStream(System.getProperty("env.file.ext"));
			
			Properties props = new Properties();
			props.load(input);
			if (! props.getProperty("SMTP.HOST").contentEquals(smtphost) ) {
				FileOutputStream out = new FileOutputStream(System.getProperty("env.file.ext"));
				props.setProperty("SMTP.HOST", smtphost);
				props.store(out, null);
				out.close();
			}
			
           if (! props.getProperty("SMTP.PORT").contentEquals(smptport) ) {	
        	   FileOutputStream out = new FileOutputStream(System.getProperty("env.file.ext"));
				props.setProperty("SMTP.PORT", smptport);
				props.store(out, null);
				out.close();
			} 
           
           
           if (! props.getProperty("SMTP.USERNAME").contentEquals(smtpusername) ) {		
        	   FileOutputStream out = new FileOutputStream(System.getProperty("env.file.ext"));
				props.setProperty("SMTP.USERNAME", smtpusername);
				props.store(out, null);
				out.close();
			} 
           
           
           if (! props.getProperty("SMTP.PASSWORD").contentEquals(smtppassword) ) {	
        	   FileOutputStream out = new FileOutputStream(System.getProperty("env.file.ext"));
				props.setProperty("SMTP.PASSWORD", smtppassword);
				props.store(out, null);
				out.close();
			}
			
           if (! props.getProperty("COMPANY.EMAIL").contentEquals(companyemail) ) {	
        	   FileOutputStream out = new FileOutputStream(System.getProperty("env.file.ext"));
				props.setProperty("COMPANY.EMAIL", companyemail);
				props.store(out, null);
				out.close();
			}
           
           
           if (! props.getProperty("LDAP.ADMIN").contentEquals(ldapadmin) ) {
        	       FileOutputStream out = new FileOutputStream(System.getProperty("env.file.ext"));
     				props.setProperty("LDAP.ADMIN", ldapadmin);
     				props.store(out, null);
     				out.close();
     			}
           if (! props.getProperty("SMTP.PASSWORD").contentEquals(ldappassword) ) {	
        	    FileOutputStream out = new FileOutputStream(System.getProperty("env.file.ext"));
				props.setProperty("SMTP.PASSWORD", ldappassword);
				props.store(out, null);
				out.close();
			}
           
           if (! props.getProperty("LDAP.HOST").contentEquals(ldaphost) ) {	
        	    FileOutputStream out = new FileOutputStream(System.getProperty("env.file.ext")); 
				props.setProperty("LDAP.HOST", ldaphost);
				props.store(out, null);
				out.close();
			}
           
           if (! props.getProperty("LDAP.PORT").contentEquals(ldapport) ) {	
        	   FileOutputStream out = new FileOutputStream(System.getProperty("env.file.ext"));
				props.setProperty("LDAP.PORT", ldapport);
				props.store(out, null);
				out.close();
			}
           
           
			
			
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			return ResponseEntity.status(510).body("Une erreur est survenue lors de la Mise à jour");	
			
		}
		return ResponseEntity.ok("La modification a été bien prise en compte");
	}
}
