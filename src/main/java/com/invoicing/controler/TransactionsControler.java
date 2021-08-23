package com.invoicing.controler;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.model.Transaction;
import com.invoicing.service.CompanyService;
import com.invoicing.service.LoginsService;
import com.invoicing.service.PrestationsService;
import com.invoicing.service.TransactionsService;

@Controller
public class TransactionsControler {
	final org.apache.logging.log4j.Logger log =  LogManager.getLogger(this.getClass().getName());
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
		 Date date = new Date();
         Timestamp ts=new Timestamp(date.getTime());
         srvcompany.updatetimestamprefresh(ts, srvlogins.getinfo(cookielogin).getCompany());
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
		if (typeoperation.contentEquals("add")) {
		BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(amountttc)/1.2);
		bd = bd.setScale(2, RoundingMode.DOWN);
	    srvt.updatetvatransaction(settled_at, bd.doubleValue());
		}
		if (typeoperation.contentEquals("reduce")) {
			srvt.updatetvatransaction(settled_at, 0);
			}
		
	context.close();
	}

	@RequestMapping(value = "/totaltva/{date1}/{date2}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Double> tva_collectee(@PathVariable("date1") String datedeb,@PathVariable("date2") String datefin ) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); 
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		 Map<String, Double> tva  = new HashMap<String, Double>();
		double totaltva=0;
		double totaltvarecoltes=0;
		double total_tva_deductible=0;
		
		for (int i=0 ; i<srvt.searchtransacbetweentwodates(datedeb, datefin).size() ; i++) {
			 if (srvt.searchtransacbetweentwodates(datedeb, datefin).get(i).getOperation_type().toString().contentEquals("income")) {
				    BigDecimal bd = BigDecimal.valueOf(srvt.searchtransacbetweentwodates(datedeb, datefin).get(i).getAmount());
					BigDecimal bd2 = BigDecimal.valueOf(srvt.searchtransacbetweentwodates(datedeb, datefin).get(i).getAmount_HT());
					BigDecimal result=bd.subtract(bd2);
					result = result.setScale(2, RoundingMode.DOWN);
					totaltvarecoltes=totaltvarecoltes+(result.doubleValue());
			 }
		}
		
		
		
		for (int i=0 ; i<srvt.searchtransacbetweentwodates(datedeb, datefin).size() ; i++) {
			 if (! srvt.searchtransacbetweentwodates(datedeb, datefin).get(i).getOperation_type().toString().contentEquals("income")) {
				    BigDecimal bd = BigDecimal.valueOf(srvt.searchtransacbetweentwodates(datedeb, datefin).get(i).getAmount());
					BigDecimal bd2 = BigDecimal.valueOf(srvt.searchtransacbetweentwodates(datedeb, datefin).get(i).getAmount_HT());
					BigDecimal result=bd.subtract(bd2);
					result = result.setScale(2, RoundingMode.DOWN);
					total_tva_deductible=total_tva_deductible+(result.doubleValue());
			 }
		}

		
		BigDecimal bd = BigDecimal.valueOf(totaltvarecoltes);
		BigDecimal bd2 = BigDecimal.valueOf(total_tva_deductible);
		BigDecimal totaltvadu=bd.subtract(bd2);
		totaltvadu = totaltvadu.setScale(2, RoundingMode.DOWN);
	
		context.close();
		tva.put("totaltvarecoltes", totaltvarecoltes);
		tva.put("totaltvadu", totaltvadu.doubleValue());
		return tva;
	}
	@RequestMapping(value = "/Getransactionsbetween/{date1}/{date2}", method = RequestMethod.GET)
	public @ResponseBody List<Transaction> gettransactions(@PathVariable("date1") String datedeb,@PathVariable("date2") String datefin ) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); 
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
	    List<Transaction> listc=srvt.searchtransacbetweentwodates(datedeb, datefin);		
		context.close();
		return listc;
	}
}
