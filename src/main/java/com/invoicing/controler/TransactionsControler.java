package com.invoicing.controler;
import java.io.FileInputStream;

import java.io.InputStream;
import java.math.BigDecimal;


import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.invoicing.tools.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONObject;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.invoicing.context.AppConfig;
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
		List<Transaction> listT=srvt.searchtransacbetweentwodates_with_tva(datedeb, datefin,srvlogins.getinfo(cookielogin).getCompany());	
		
		for (int i=0 ; i<listT.size() ; i++) {
			 if (listT.get(i).getOperation_type().toString().contentEquals("income") ) {
				    // calcul tva income
				    if (listT.get(i).getAmount_HT()>0) {
				    BigDecimal bd = BigDecimal.valueOf(listT.get(i).getAmount());
					BigDecimal bd2 = BigDecimal.valueOf(listT.get(i).getAmount_HT());
					BigDecimal result=bd.subtract(bd2);
					result = result.setScale(2, RoundingMode.DOWN);
					totaltvarecoltes=totaltvarecoltes+(result.doubleValue());
				    }
				    }else {
				    	// calcul tva déduite
				        if (listT.get(i).getAmount_HT() >0) {
				    	BigDecimal bd = BigDecimal.valueOf(listT.get(i).getAmount());
						BigDecimal bd2 = BigDecimal.valueOf(listT.get(i).getAmount_HT());
						BigDecimal result=bd.subtract(bd2);
						result = result.setScale(2, RoundingMode.DOWN);
						total_tva_deductible=total_tva_deductible+(result.doubleValue());	
				        }
				    	
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


	
	
	@RequestMapping(value = "/GetAllTransactionsWithProof", method = RequestMethod.GET)
	public @ResponseBody List<Transaction> gettransactions(@CookieValue("invoicing_username") String cookielogin ) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); 
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");		
		List<Transaction> list = srvt.GetTransactionWithProof(srvlogins.getinfo(cookielogin).getCompany());
		context.close();
		return list;
	}

	@GetMapping(value = "/Download_Log_Invoicing_Tracking")
	 public ResponseEntity<byte[]> Download_Invoice(@CookieValue("invoicing_username") String cookielogin) throws Exception {
			InputStream batchconfig = new FileInputStream(System.getProperty("batch.trigger.config.file"));
			Properties props = new Properties();
			props.load(batchconfig);	 
		    byte[] bytes = Files.readAllBytes(Paths.get(props.getProperty("LOG.PATH")+System.getProperty("file.separator")+props.getProperty("LOG.NAME")));
			HttpHeaders headers = new HttpHeaders();
			ResponseEntity<byte[]> response=null;
		    headers.add("content-disposition", "attachment; filename="+props.getProperty("LOG.NAME"));
		    response = new ResponseEntity<byte[]>(
		    bytes, headers, HttpStatus.OK);		       
		    return response;
		   }


		@RequestMapping(value = "/List_Proofs", method = RequestMethod.GET)
		public ModelAndView viewproofs(@CookieValue("invoicing_username") String cookielogin) {
			AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
			LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
			TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
			ModelAndView mv = new ModelAndView("/bank/list_proofs");
			mv.addObject("list_proofs", srvt.GetTransactionWithProof(srvlogins.getinfo(cookielogin).getCompany()));
			mv.addObject("company", srvlogins.getinfo(cookielogin).getCompany());
			context.close();
			return mv;
		
		}
	 
	 
	 

}
