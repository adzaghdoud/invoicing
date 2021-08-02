package com.invoicing.controler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.service.CompanyService;

@Controller
public class TransactionsControler {
	final org.apache.logging.log4j.Logger log =  LogManager.getLogger(TransactionsControler.class);
	@GetMapping(value = "/refresh_transactions")
	public @ResponseBody void refresh_transactions (){
	try {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		 ProcessBuilder processBuilder = new ProcessBuilder(System.getProperty("path.script.import"),System.getProperty("path.json.input"), System.getProperty("path.backend.jar"),srvcompany.getinfo().getRib(),srvcompany.getinfo().getSlug(),srvcompany.getinfo().getToken());
		 context.close();
		 processBuilder.redirectErrorStream(true);
		 Process p = processBuilder.start();
		 log.info(new String(IOUtils.toByteArray(p.getInputStream()))); 
		 if( p.getErrorStream().available() >0) {
		 log.error(new String(IOUtils.toByteArray(p.getErrorStream()))); 
	     }
		 p.waitFor();
			
	} catch (Exception e) {
	log.error(ExceptionUtils.getStackTrace(e));
	}

	}
}
