package com.invoicing.controler;

import java.io.FileInputStream;

import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.model.Article;
import com.invoicing.service.ArticleService;
import com.invoicing.service.LoginsService;
@Controller
public class ArticlesControler {
	final org.apache.logging.log4j.Logger log =  LogManager.getLogger(this.getClass().getName());
	@RequestMapping(value = "/articles", method = RequestMethod.GET)
	public ModelAndView getarticles(@CookieValue("invoicing_username") String cookielogin) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		ArticleService srvarticles = (ArticleService) context.getBean("ArticleService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");	
		ModelAndView mv = new ModelAndView("/articles/liste_article");
		mv.addObject("Listarticles", srvarticles.getlistarticles(srvlogins.getinfo(cookielogin).getCompany()));
		context.close();
		return mv;
	}
	
	
	@RequestMapping(value = "/GetArticlebyname", method = RequestMethod.POST)
	public @ResponseBody Article GetArticlebyname(@RequestParam (required = true)String designation) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		ArticleService srvarticles = (ArticleService) context.getBean("ArticleService");
		Article a = srvarticles.getarticlebydesignation(designation);
		context.close();
		return a ;
	}
	
	
	@RequestMapping(value = "/createnewarticle", method = RequestMethod.POST)
	public ResponseEntity<String>createnew(@RequestBody Article a ,@CookieValue("invoicing_username") String cookielogin) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		ArticleService srvarticles = (ArticleService) context.getBean("ArticleService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		a.setRs(srvlogins.getinfo(cookielogin).getCompany());
		a.setBywho(cookielogin);
		try {
           
			srvarticles.addarticle(a);	
			
		}catch(Exception e) {
			context.close();
			log.error(ExceptionUtils.getStackTrace(e));
			if (ExceptionUtils.getStackTrace(e).contains("Duplicate entry")) {
			return ResponseEntity.status(550).body("Le produit "+a.getDesignation() +" exite déja en base de données");
			}else {
			return ResponseEntity.status(550).body("Erreur création produit ,Consultez la log");
			}
		}
		context.close();
		return ResponseEntity.ok().body("Le nouveau Article "+a.getDesignation() +"a été bien créé");
	}
	
	
	
	@RequestMapping(value = "/updatearticle", method = RequestMethod.POST)
	   public ResponseEntity<String> updatearticle(@RequestBody Article a , @CookieValue("invoicing_username") String cookielogin) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		ArticleService srvarticles = (ArticleService) context.getBean("ArticleService");
		try {
	    Date date = new Date();
	    Timestamp ts=new Timestamp(date.getTime());
	    a.setLast_modification(ts);
		a.setBywho(cookielogin);
		srvarticles.updatearticle(a);
		} catch (Exception e ) {
		log.error(ExceptionUtils.getStackTrace(e));
		context.close();
		return ResponseEntity.status(550).body("Erreur modification en base de données");
		}
		
		context.close();
		return ResponseEntity.ok().body("La modification a été faite avec succés");
	}
	
	
	@RequestMapping(value = "/deletearticle/{designation}", method = RequestMethod.POST)
	   public ResponseEntity<String> deletearticle(@PathVariable("designation") String designation) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		ArticleService srvarticles = (ArticleService) context.getBean("ArticleService");
		try {
			srvarticles.deletearticle(designation);
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			context.close();
			return ResponseEntity.status(550).body("Erreur suppression en base de données");
			
		}
		context.close();
		return ResponseEntity.ok().body("Le produit a été bien supprimé");
	}
	


	
	@GetMapping(value = "/DowloadProductsCSV",produces = "application/csv")
	 public ResponseEntity<byte[]> DownloadProducts(@CookieValue("invoicing_username") String cookielogin) throws Exception {
			AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
			ArticleService srvarticles = (ArticleService) context.getBean("ArticleService");
			LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
			List<Article> listA= srvarticles.getlistarticles(srvlogins.getinfo(cookielogin).getCompany());
		    	StringWriter sw = new StringWriter();
		    	sw.append("Designation;Famille;PV_HT;PA_HT;Taxe;Val_Taxe;PV_TTC;Last Modification;By Who;Company\n");
		    	for(int i=0 ; i<listA.size(); i++) {
		    		sw.append(listA.get(i).getDesignation()+";"+listA.get(i).getFamille()+";"+listA.get(i).getPvht()+";"+listA.get(i).getPaht()+";"
		    			
		    		+listA.get(i).getTaxe()+";"+listA.get(i).getValtaxe()+";"+listA.get(i).getPvttc()+";"+listA.get(i).getLast_modification()+";"
		    		+listA.get(i).getBywho()+";"+listA.get(i).getRs()+"\n");
		    	}
		    	HttpHeaders headers = new HttpHeaders();
				ResponseEntity<byte[]> response=null;
			    headers.add("content-disposition", "attachment; filename=ListProducts.csv");
			    response = new ResponseEntity<byte[]>(
			    		sw.toString().getBytes(StandardCharsets.ISO_8859_1), headers, HttpStatus.OK);		       
			    context.close();
			    return response;	 
		 
		   }
	



}



