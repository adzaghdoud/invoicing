package com.invoicing.controler;
import java.math.BigDecimal;


import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.invoicing.tools.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONObject;
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
import org.springframework.web.multipart.MultipartFile;
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
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/refresh_transactions")
	public @ResponseBody JSONObject refresh_transactions (@CookieValue("invoicing_username") String cookielogin){
		
		JSONObject json = new JSONObject();
		long nb_credit_before=0;
		long nb_debit_before=0;
		long nb_credit_after=0;
		long nb_debit_after=0;
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		 TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		 LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		try {	
		 ProcessBuilder processBuilder = new ProcessBuilder(System.getProperty("path.script.import."+srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getBankname().toLowerCase()),System.getProperty("path.json.input")+System.getProperty("file.separator")+"transactions_"+srvlogins.getinfo(cookielogin).getCompany()+".json", System.getProperty("path.backend.jar"),srvcompany.getinfo(srvlogins.getinfo(cookielogin).getCompany()).getRib(),srvcompany.getinfo(srvlogins.getinfo(cookielogin).getCompany()).getSlug(),srvcompany.getinfo(srvlogins.getinfo(cookielogin).getCompany()).getToken(),srvcompany.getinfo(srvlogins.getinfo(cookielogin).getCompany()).getRs().toUpperCase());
		 nb_credit_before = srvt.count_credit_trancactions(srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getRs());
		 nb_debit_before = srvt.count_debit_trancactions(srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getRs());
		 processBuilder.redirectErrorStream(true);
		 Process p = processBuilder.start();
		 log.info(new String(IOUtils.toByteArray(p.getInputStream()))); 
		 if( p.getErrorStream().available() >0) {
		 log.error(new String(IOUtils.toByteArray(p.getErrorStream()))); 
	     }
		 p.waitFor();
		 Date date = new Date();
         Timestamp ts=new Timestamp(date.getTime());
         srvcompany.updatetimestamprefresh(ts, srvlogins.getinfo(cookielogin).getCompany());

	} catch (Exception e) {
		 
	log.error(ExceptionUtils.getStackTrace(e));
	}
	  
		nb_credit_after = srvt.count_credit_trancactions(srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getRs());
		nb_debit_after = srvt.count_debit_trancactions(srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getRs());
		json.put("nb_credit", nb_credit_after - nb_credit_before);
		json.put("nb_debit", nb_debit_after - nb_debit_before);
		context.close();
	    return json;	 
	}
   

	@PostMapping(value = "/GestionTVA")
	public @ResponseBody void gestiontva (@RequestParam(required = true) String  typeoperation,@RequestParam(required = true) String  amountttc,@RequestParam(required = true) String  settled_at,@RequestParam(required = true) String  updated_at){
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); 
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
		if (typeoperation.contentEquals("add")) {
		BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(amountttc)/1.2);
		bd = bd.setScale(2, RoundingMode.DOWN);
	    srvt.updatetvatransaction(settled_at,bd.doubleValue(),ts,updated_at);
		}
		if (typeoperation.contentEquals("reduce")) {
			srvt.updatetvatransaction(settled_at, 0,ts,updated_at);
			}
		
	context.close();
	}

	@RequestMapping(value = "/totaltva/{date1}/{date2}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Double> tva_collectee(@PathVariable("date1") String datedeb,@PathVariable("date2") String datefin,@CookieValue("invoicing_username") String cookielogin ) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); 
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		 Map<String, Double> tva  = new HashMap<String, Double>();
		
		double totaltvarecoltes=0;
		double total_tva_deductible=0;
		
		for (int i=0 ; i<srvt.searchtransacbetweentwodates_with_tva(datedeb, datefin,srvlogins.getinfo(cookielogin).getCompany()).size() ; i++) {
			 if (srvt.searchtransacbetweentwodates(datedeb, datefin,srvlogins.getinfo(cookielogin).getCompany()).get(i).getOperation_type().toString().contentEquals("income")) {
				    BigDecimal bd = BigDecimal.valueOf(srvt.searchtransacbetweentwodates_with_tva(datedeb, datefin,srvlogins.getinfo(cookielogin).getCompany()).get(i).getAmount());
					BigDecimal bd2 = BigDecimal.valueOf(srvt.searchtransacbetweentwodates_with_tva(datedeb, datefin,srvlogins.getinfo(cookielogin).getCompany()).get(i).getAmount_HT());
					BigDecimal result=bd.subtract(bd2);
					result = result.setScale(2, RoundingMode.DOWN);
					totaltvarecoltes=totaltvarecoltes+(result.doubleValue());
			 }
		}
		
		
		
		for (int i=0 ; i<srvt.searchtransacbetweentwodates_with_tva(datedeb, datefin,srvlogins.getinfo(cookielogin).getCompany()).size() ; i++) {
			 if (! srvt.searchtransacbetweentwodates(datedeb, datefin,srvlogins.getinfo(cookielogin).getCompany()).get(i).getOperation_type().toString().contentEquals("income")) {
				    BigDecimal bd = BigDecimal.valueOf(srvt.searchtransacbetweentwodates_with_tva(datedeb, datefin,srvlogins.getinfo(cookielogin).getCompany()).get(i).getAmount());
					BigDecimal bd2 = BigDecimal.valueOf(srvt.searchtransacbetweentwodates_with_tva(datedeb, datefin,srvlogins.getinfo(cookielogin).getCompany()).get(i).getAmount_HT());
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
		tva.put("totaltvadu", java.lang.Math.abs(totaltvadu.doubleValue()));
		return tva;
	}
	@RequestMapping(value = "/Getransactionsbetween_with_tva/{date1}/{date2}", method = RequestMethod.GET)
	public @ResponseBody List<Transaction> gettransactions(@PathVariable("date1") String datedeb,@PathVariable("date2") String datefin,@CookieValue("invoicing_username") String cookielogin ) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); 
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
	    List<Transaction> listc=srvt.searchtransacbetweentwodates_with_tva(datedeb, datefin,srvlogins.getinfo(cookielogin).getCompany());		
		context.close();
		return listc;
	}

	@PostMapping(value = "/UploadProof")
	public  @ResponseBody void uploadproof(@CookieValue("invoicing_username") String cookielogin,@RequestParam(required = true) String  settled_at,@RequestParam(required = true) String  updated_at,@RequestParam(required = true) String  proof_file_name,@RequestParam(required = true) MultipartFile proof_file ) throws  Exception{
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); 
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		srvt.updateproof(settled_at, updated_at, proof_file_name);	
		S3Amazonetools.Putdocument(srvlogins.getinfo(cookielogin).getCompany(), "PROOF", proof_file.getBytes(),proof_file_name);
	    context.close();
	}
	
	
	@PostMapping(value = "/CheckExistProof")
	public  @ResponseBody JSONObject uploadproof(@CookieValue("invoicing_username") String cookielogin,@RequestParam(required = true) String  settled_at,@RequestParam(required = true) String  updated_at) throws  Exception{
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); 
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
	    JSONObject json = new JSONObject();
	    json=srvt.checkeexistproof(settled_at, updated_at,srvlogins.getinfo(cookielogin).getCompany());
	    context.close();
	    return json;
	}
	
	
	@RequestMapping(value = "/GetAllTransactionsWithProof", method = RequestMethod.GET)
	public @ResponseBody List<Transaction> gettransactions(@CookieValue("invoicing_username") String cookielogin ) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); 
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");		
		List<Transaction> list = srvt.GetTransactionWithProof(srvlogins.getinfo(cookielogin).getCompany());
		context.close();
		return list;
	}
}
