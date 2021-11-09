package com.invoicing.controler;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.model.Logins;
import com.invoicing.model.Prestations;
import com.invoicing.model.Transaction;
import com.invoicing.service.ArticleService;
import com.invoicing.service.ClientService;
import com.invoicing.service.CompanyService;
import com.invoicing.service.LoginsService;
import com.invoicing.service.PrestationsService;
import com.invoicing.service.TrackingService;
import com.invoicing.service.TransactionsService;
import com.invoicing.tools.Ldaptools;
import com.invoicing.tools.Sendmail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
@Controller
public class Dispatcher {
	final org.apache.logging.log4j.Logger logger =  LogManager.getLogger(this.getClass().getName());

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/accueil/login");
		return mv;
	}
	
	
	public static boolean checkidldap(String login , String password) {
		try {
		
	        Properties env = new Properties();
	        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.PROVIDER_URL, "ldap://vmi537338.contaboserver.net:389/ou=people,dc=vmi537338,dc=contaboserver,dc=net");
	        env.put(Context.SECURITY_PRINCIPAL, "uid="+login+",ou=people,dc=vmi537338,dc=contaboserver,dc=net");
	        env.put(Context.SECURITY_CREDENTIALS, password);
	        DirContext ctx = new InitialDirContext(env); 
	        ctx.close();
	        return true;
			} catch (Exception e) {
			 System.out.println(ExceptionUtils.getStackTrace(e));
			 return false;
			}
				
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView forwordlogin(HttpServletRequest request, HttpServletResponse response , @RequestParam(required = true) String login,@RequestParam(required = true) String password) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");		
		ModelAndView mv ;
		if (!checkidldap(login,password)) {
		 mv = new ModelAndView("/accueil/login");
		mv.addObject("erromsg", "Login ou password invalide");
		context.close();
		return mv;
		}
		;
	
		mv = new ModelAndView("/accueil/main");
		mv.addObject("welcome","Bonjour "+login);
		mv.addObject("company_name",srvlogins.getinfo(login).getCompany());
		mv.addObject("company_bank_name",srvcompany.getcompanybyraison(srvlogins.getinfo(login).getCompany()).getBankname());
        mv.addObject("flag_reset_password", srvlogins.getinfo(login).getResetpassword());
		Cookie userName = new Cookie("invoicing_username", login);
		userName.setMaxAge(-1);
		response.addCookie(userName);
		context.close();
		return mv;
	} 
	
	
	
	
	
	@RequestMapping(value = "/dashbord", method = RequestMethod.GET)
	public ModelAndView dash() {
		ModelAndView mv = new ModelAndView("/accueil/main");
		return mv;
	}
	
	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public ModelAndView getlclients() {
		ModelAndView mv = new ModelAndView("/clients/liste_client");
		return mv;
	}
	
	
	@RequestMapping(value = "/newinvoice", method = RequestMethod.GET)
	public ModelAndView newinvoice(@CookieValue("invoicing_username") String cookielogin) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		ClientService srvclient = (ClientService) context.getBean("ClientService");	
		ArticleService srvarticle = (ArticleService) context.getBean("ArticleService");	
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		ModelAndView mv = new ModelAndView("/actions/createinvoice");
		mv.addObject("listeclients", srvclient.getallclients(srvlogins.getinfo(cookielogin).getCompany()));
		mv.addObject("listearticles", srvarticle.getlistarticles(srvlogins.getinfo(cookielogin).getCompany()));
		context.close();
		return mv;
	}
	
	
	@RequestMapping(value = "/Companysettings", method = RequestMethod.GET)
	public ModelAndView getCompanysettings(@CookieValue("invoicing_username") String cookielogin) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		ModelAndView mv = new ModelAndView("/settings/company_settings");
		
		String encodedimage = Base64Utils.encodeToString(srvcompany.getinfo(srvlogins.getinfo(cookielogin).getCompany()).getLogo());
		mv.addObject("info", srvcompany.getinfo(srvlogins.getinfo(cookielogin).getCompany()));
		mv.addObject("encodedimage", encodedimage);
		context.close();
		return mv;
	}
	
	
	@RequestMapping(value = "/Generalsettings", method = RequestMethod.GET)
	public ModelAndView getgeneralsettings() {
		ModelAndView mv = new ModelAndView("/settings/general_settings");
		return mv;
	}
	
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile(@CookieValue("invoicing_username") String cookielogin) throws IOException, ParseException {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");	
		Logins p = srvlogins.getinfo(cookielogin);		
		ModelAndView mv = new ModelAndView("/settings/profil");
		Map<String, String> map = Ldaptools.getvalueattibute("uid="+cookielogin+",ou=people,dc=vmi537338,dc=contaboserver,dc=net");
		mv.addObject("CN", map.get("cn"));
		mv.addObject("email",map.get("mail"));
		mv.addObject("login", cookielogin);
		mv.addObject("tel", map.get("tel"));
		mv.addObject("company", map.get("o"));
		if (!(p.getAvatar() == null) ) {
		String encodedimage = Base64Utils.encodeToString(p.getAvatar());
		mv.addObject("avatar",encodedimage);
		}
		context.close();	 
		return mv;
	
	}
	
	
	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	public ModelAndView charts(@CookieValue("invoicing_username") String cookielogin) throws java.text.ParseException {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		PrestationsService srvprestations = (PrestationsService) context.getBean("PrestationsService");
		ClientService srvclient = (ClientService) context.getBean("ClientService");
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		double in=0;
		double out=0;
		for (int i=0 ; i<srvt.getlist(srvlogins.getinfo(cookielogin).getCompany()).size() ; i++) {
			if (srvt.getlist(srvlogins.getinfo(cookielogin).getCompany()).get(i).getSide().contentEquals("debit")) {
				out = out +srvt.getlist(srvlogins.getinfo(cookielogin).getCompany()).get(i).getAmount();
			}
			
			if (srvt.getlist(srvlogins.getinfo(cookielogin).getCompany()).get(i).getSide().contentEquals("credit")) {
				in =in +srvt.getlist(srvlogins.getinfo(cookielogin).getCompany()).get(i).getAmount();
			}
			
		}
		BigDecimal bd = BigDecimal.valueOf(in);
		BigDecimal bd2 = BigDecimal.valueOf(out);
		BigDecimal result=bd.subtract(bd2);
		result = result.setScale(2, RoundingMode.DOWN);
		ModelAndView mv = new ModelAndView("/dash/dashbord");
		double ca=0;
	    List<Prestations>listp=srvprestations.getlistprestations_until_date_cloture(srvlogins.getinfo(cookielogin).getCompany(),srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getDate_cloture_comptable());
	    for(int i=0 ; i<listp.size(); i++) {    	
	    ca+=listp.get(i).getTotalttc();	
	    }
		mv.addObject("ca",ca);
		mv.addObject("nb_paiement_to_validate", srvprestations.number_paiement_to_validate());
		mv.addObject("nb_paiement_validated", srvprestations.number_paiement_validate());
		mv.addObject("nb_clients", srvclient.numberclient(srvlogins.getinfo(cookielogin).getCompany()));
		mv.addObject("liste_prestations", srvprestations.getlistprestations(srvlogins.getinfo(cookielogin).getCompany()));
		mv.addObject("tresorie", result.doubleValue());
		mv.addObject("date_cloture", srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getDate_cloture_comptable());
		mv.addObject("bankname", srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getBankname());
		context.close();
		return mv;
	
	}
	
	
	
	@RequestMapping(value = "/prestations", method = RequestMethod.GET)
	public ModelAndView getprestations(@CookieValue("invoicing_username") String cookielogin) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		PrestationsService srvprestations = (PrestationsService) context.getBean("PrestationsService");	
		ModelAndView mv = new ModelAndView("/prestations/liste_prestations");
		mv.addObject("Liste_prestations", srvprestations.getlistprestations(srvlogins.getinfo(cookielogin).getCompany()));
		context.close();
		return mv;
	
	}
	
	@RequestMapping(value = "/validate_paiement", method = RequestMethod.GET)
	public ModelAndView validatepaiement() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		PrestationsService srvprestations = (PrestationsService) context.getBean("PrestationsService");	
		ModelAndView mv = new ModelAndView("/paiement/validate_paiement");
		mv.addObject("list_pending_paiements", srvprestations.getpendingpaiement());
		context.close();
		return mv;
	}
	
	
	@RequestMapping(value = "/notifyclient", method = RequestMethod.GET)
	public ModelAndView notifyclient() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		PrestationsService srvprestations = (PrestationsService) context.getBean("PrestationsService");	
		ModelAndView mv = new ModelAndView("/paiement/relance_paiement");
		mv.addObject("list_pending_paiements", srvprestations.getpendingpaiement());
		context.close();
		return mv;
	
	}
	
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void  deconnect(HttpServletRequest request, HttpServletResponse response)  {
		HttpSession session = request.getSession(false);
		if(session != null){
			try {
				response.sendRedirect("login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.invalidate();
			
	}
	}	


	@RequestMapping(value = "/liste_transactions_bank", method = RequestMethod.GET)
	public ModelAndView getlisttransactions(@CookieValue("invoicing_username") String cookielogin) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		TransactionsService srvtransaction = (TransactionsService) context.getBean("TransactionsService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ModelAndView mv = new ModelAndView("/bank/transactions_bank");
		mv.addObject("List_transactions", srvtransaction.getlist(srvlogins.getinfo(cookielogin).getCompany()));		
		// amout in and out
		LocalDate todaydate = LocalDate.now();
		int currentMonth = todaydate.getMonthValue();
		int currentyear = todaydate.getYear();
		ArrayList<Transaction> arraylisttransactions = new ArrayList<Transaction>();	
		if (currentMonth<10) {
		for (int i=0 ; i<srvtransaction.searchtransacbetweentwodates(currentyear+"-"+"0"+currentMonth+"-01", currentyear+"-"+"0"+currentMonth+"-31", srvlogins.getinfo(cookielogin).getCompany()).size() ; i++) {
			arraylisttransactions.add(srvtransaction.searchtransacbetweentwodates(currentyear+"-"+"0"+currentMonth+"-01", currentyear+"-"+"0"+currentMonth+"-31", srvlogins.getinfo(cookielogin).getCompany()).get(i));
			}	
		}
		else {
			   for (int i=0 ; i<srvtransaction.searchtransacbetweentwodates(currentyear+"-"+currentMonth+"-01", currentyear+"-"+currentMonth+"-31", srvlogins.getinfo(cookielogin).getCompany()).size() ; i++) {
				arraylisttransactions.add(srvtransaction.searchtransacbetweentwodates(currentyear+"-"+currentMonth+"-01", currentyear+"-"+currentMonth+"-31", srvlogins.getinfo(cookielogin).getCompany()).get(i));
				}	
			
		}
		double in=0;
		double out=0;
		for (int i=0 ; i<arraylisttransactions.size() ; i++) {
			if (arraylisttransactions.get(i).getSide().contentEquals("debit")) {
				out = out +arraylisttransactions.get(i).getAmount();
			}
			
			if (arraylisttransactions.get(i).getSide().contentEquals("credit")) {
				in =in +arraylisttransactions.get(i).getAmount();
			}
			
		}
		DecimalFormat decimalFormat= new DecimalFormat("#.##");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);	
		mv.addObject("amount_out", decimalFormat.format(out));
		mv.addObject("amount_in", decimalFormat.format(in));
	    mv.addObject("last_refresh_transaction", formatter.format(srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getLast_refresh_transaction()));
		context.close();
		return mv;
	
	}
	
	@RequestMapping(value = "/Get_Tracking_Batch", method = RequestMethod.GET)
	public ModelAndView GetTracking(@CookieValue("invoicing_username") String cookielogin) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		TrackingService srvt = (TrackingService) context.getBean("TrackingService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
	    ModelAndView mv = new ModelAndView("/bank/tracking_import"); 
		mv.addObject("list_tracked_batch", srvt.GetTracking(srvlogins.getinfo(cookielogin).getCompany()));
		mv.addObject("bank_name", srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getBankname());
		context.close();
		return mv;
	}
	
	
	
	
	
	

	@RequestMapping(value = "/tva_collectee", method = RequestMethod.GET)
	public ModelAndView tva_collectee() {
		ModelAndView mv = new ModelAndView("/bank/detail_tva");
		return mv;
	
	}

	@RequestMapping(value = "/General_settings", method = RequestMethod.GET)
	public ModelAndView GS() {
		ModelAndView mv = new ModelAndView("/settings/general_settings");
		try {
		InputStream input = new FileInputStream(System.getProperty("env.file.ext"));
		
		Properties props = new Properties();
		props.load(input);
		mv.addObject("smtphost", props.getProperty("SMTP.HOST"));
		mv.addObject("smptport", props.getProperty("SMTP.PORT"));
		mv.addObject("smtpusername", props.getProperty("SMTP.USERNAME"));
		mv.addObject("smtppassword", props.getProperty("SMTP.PASSWORD"));
		mv.addObject("companyemail", props.getProperty("COMPANY.EMAIL"));
		mv.addObject("ldaphost", props.getProperty("LDAP.HOST"));
		mv.addObject("ldapport", props.getProperty("LDAP.PORT"));
		mv.addObject("ldapadmin", props.getProperty("LDAP.ADMIN"));
		mv.addObject("ldappassword", props.getProperty("LDAP.ADMIN.PASSWORD"));
		}catch (Exception e) {	
		logger.error(ExceptionUtils.getStackTrace(e));
		}
		
		try {
		InputStream inputbatch = new FileInputStream(System.getProperty("batch.trigger.config.file"));
		Properties props = new Properties();
		props.load(inputbatch);
		mv.addObject("Scheduledtime", props.getProperty("heure")+":"+props.getProperty("minutes")+":"+props.getProperty("seconde"));
			
		}catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		
		
		
		
		return mv;
	}
	
	@RequestMapping(value = "/updateavatar", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> updateavatar(HttpServletRequest request,@CookieValue("invoicing_username") String cookielogin ,@RequestParam(required = true) MultipartFile avatar,@RequestParam(required = true) String applied_to) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		byte[] newavatar;
		String msg="";
		try {
			  newavatar = avatar.getBytes();	 
		      if (applied_to.contentEquals("avatar")) {
			  srvlogins.updateavatar(cookielogin, newavatar);
			  msg="L'avatar a été bien mis à jour";
		      }	
		      
		      if (applied_to.contentEquals("logo")) {
		    	  srvcompany.updatlogo(srvlogins.getinfo(cookielogin).getCompany(), newavatar); 
		    	  msg="Le logo a été bien mis à jour";
			      }	
		      
		} catch (Exception e) {
			context.close();
			logger.error(ExceptionUtils.getStackTrace(e));
		}
			
		context.close();
	    return  ResponseEntity.ok(msg);
	
	}
	@PostMapping(value = "/sendmail")
	public  @ResponseBody ResponseEntity<String> sendmail(@RequestParam(required = true) String mailto,@RequestParam(required = true) String subject,@RequestParam(required = true) String contain,@RequestParam(required = false) MultipartFile attached_file,@RequestParam (required = false)String attached_file_name) {
	Sendmail s= new Sendmail();
	s.setContain(contain);
	s.setSubject(subject);
	s.setMailto(mailto);
	if (attached_file !=null ) {
	s.setFile(attached_file);
	s.setFilename(attached_file_name);
	}
	if (! s.send() ) {
	 return ResponseEntity.status(505).body("une erreur est survenue lors de l'envoi du mail");	
	}
	return ResponseEntity.ok("Le mail a été bien envoyée à "+mailto);	
	} 
}
