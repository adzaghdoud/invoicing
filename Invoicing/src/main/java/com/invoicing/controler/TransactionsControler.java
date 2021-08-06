package com.invoicing.controler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.service.CompanyService;
import com.invoicing.service.LoginsService;
import com.invoicing.service.PrestationsService;
import com.invoicing.service.TransactionsService;

@Controller
public class TransactionsControler {
	final org.apache.logging.log4j.Logger log =  LogManager.getLogger(TransactionsControler.class);
	@GetMapping(value = "/refresh_transactions")
	public @ResponseBody int refresh_transactions (@CookieValue("invoicing_username") String cookielogin){
		long nbafter=0;
		long nbbefore=0;
		try {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		 TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		 LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");	
		 ProcessBuilder processBuilder = new ProcessBuilder(System.getProperty("path.script.import."+srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getBankname().toLowerCase()),System.getProperty("path.json.input"), System.getProperty("path.backend.jar"),srvcompany.getinfo().getRib(),srvcompany.getinfo().getSlug(),srvcompany.getinfo().getToken());
		 nbbefore = srvt.countnbtransaction();
		 processBuilder.redirectErrorStream(true);
		 Process p = processBuilder.start();
		 log.info(new String(IOUtils.toByteArray(p.getInputStream()))); 
		 if( p.getErrorStream().available() >0) {
		 log.error(new String(IOUtils.toByteArray(p.getErrorStream()))); 
	     }
		 p.waitFor();
		 nbafter= srvt.countnbtransaction();
		 context.close();
		 
	} catch (Exception e) {
	log.error(ExceptionUtils.getStackTrace(e));
	}
	   if ( nbafter == 0) {
		return 0;   
	   }
		return (int) (nbafter-nbbefore);
	}
   

	@PostMapping(value = "/GestionTVA")
	public @ResponseBody void gestiontva (@RequestParam(required = true) String  typeoperation,@RequestParam(required = true) String  amountttc,@RequestParam(required = true) String  settled_at){
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); 
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		System.out.println("*************************typeoperation"+typeoperation);
		if (typeoperation.contentEquals("add")) {
		System.out.println("*******************"+Double.parseDouble(amountttc));
		System.out.println("*******************"+Double.parseDouble(amountttc)/1.2);
	    //srvt.updatetvatransaction(settled_at, Double.parseDouble(amountttc)/1.2);
		}
		if (typeoperation.contentEquals("reduce")) {
			srvt.updatetvatransaction(settled_at, 0);
			}
		
	context.close();
	}





}
