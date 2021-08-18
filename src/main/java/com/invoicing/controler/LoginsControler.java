package com.invoicing.controler;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.model.Logins;
import com.invoicing.model.Prestations;
import com.invoicing.service.LoginsService;
import com.invoicing.service.PrestationsService;

@Controller
public class LoginsControler {
	final org.apache.logging.log4j.Logger log =  LogManager.getLogger(LoginsControler.class);
	@RequestMapping(value = "/getuserinfo", method = RequestMethod.POST)
	public @ResponseBody Logins getinforuser(HttpServletRequest request,@CookieValue("invoicing_username") String cookielogin) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");	
		Logins p = srvlogins.getinfo(cookielogin);
	    context.close();
	    return p;
	
	}
	
	@RequestMapping(value = "/updateavatar", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> updateavatar(HttpServletRequest request,@CookieValue("invoicing_username") String cookielogin ,@RequestParam(required = true) MultipartFile avatar) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");	
		byte[] newavatar;
		try {
			  newavatar = avatar.getBytes();	 
		      srvlogins.updateavatar(cookielogin, newavatar);	      
			
		} catch (Exception e) {
			context.close();
			 log.error(ExceptionUtils.getStackTrace(e));
		}
			
		context.close();
	    return  ResponseEntity.ok("L'avatar a �t� bien mis � jour");
	
	}
	
	@RequestMapping(value = "/updatepassword/{login}/{password}", method = RequestMethod.POST)
	public @ResponseBody  ResponseEntity<String> updatepassword(@PathVariable("login") String login,@PathVariable("password") String password) {
		try {
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://vmi537338.contaboserver.net:389");
		env.put(Context.SECURITY_PRINCIPAL, "cn=Directory Manager");
		env.put(Context.SECURITY_CREDENTIALS, "09142267");
		DirContext ctx = new InitialDirContext(env); 		
		ModificationItem[] mods = new ModificationItem[1];
		mods[0]= new ModificationItem(DirContext.REPLACE_ATTRIBUTE , new BasicAttribute("userPassword",password));
		ctx.modifyAttributes("uid="+login+",ou=people,dc=vmi537338,dc=contaboserver,dc=net", mods);
		ctx.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(ExceptionUtils.getStackTrace(e));
			return ResponseEntity.status(510).body("Erreur mise � jour mot de passe dans LDAP");
			
		}
	  
	  return ResponseEntity.ok("Le mot de passe  a �t� bien mis � jour dans LDAP");
	}
	}
	
	
	
	
	
	

