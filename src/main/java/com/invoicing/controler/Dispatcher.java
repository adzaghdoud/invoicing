package com.invoicing.controler;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.model.Logins;
import com.invoicing.model.Transaction;
import com.invoicing.service.ArticleService;
import com.invoicing.service.ClientService;
import com.invoicing.service.CompanyService;
import com.invoicing.service.LoginsService;
import com.invoicing.service.PrestationsService;
import com.invoicing.service.TransactionsService;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
@Controller
public class Dispatcher {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/accueil/login");
		return mv;
	}
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView forwordlogin(HttpServletRequest request, HttpServletResponse response , @RequestParam(required = true) String login,@RequestParam(required = true) String password) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");	
	  if (!srvlogins.checkloginpassword(request.getParameter("login"),request.getParameter("password"))) {
		ModelAndView mv = new ModelAndView("/accueil/login");
		mv.addObject("erromsg", "Login ou password invalide");
		context.close();
		return mv;
		}
		ModelAndView mv = new ModelAndView("/accueil/main");
		mv.addObject("welcome","Bonjour "+login);
		mv.addObject("company_name",srvlogins.getinfo(login).getCompany());
		mv.addObject("company_bank_name",srvcompany.getcompanybyraison(srvlogins.getinfo(login).getCompany()).getBankname());
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
	public ModelAndView newinvoice() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		ClientService srvclient = (ClientService) context.getBean("ClientService");	
		ArticleService srvarticle = (ArticleService) context.getBean("ArticleService");	
		ModelAndView mv = new ModelAndView("/actions/createinvoice");
		mv.addObject("listeclients", srvclient.getallclients());
		mv.addObject("listearticles", srvarticle.getlistarticles());
		context.close();
		return mv;
	}
	
	
	@RequestMapping(value = "/Companysettings", method = RequestMethod.GET)
	public ModelAndView getCompanysettings() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");	
		ModelAndView mv = new ModelAndView("/settings/company_settings");
		
		String encodedimage = Base64Utils.encodeToString(srvcompany.getinfo().getLogo());
		mv.addObject("info", srvcompany.getinfo());
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
		mv.addObject("prenom", p.getPrenom());
		mv.addObject("nom", p.getNom());
		mv.addObject("email", p.getEmail());
		mv.addObject("login", p.getLogin());
		mv.addObject("tel", p.getTel());
		mv.addObject("company", p.getCompany());
		context.close();	 
		return mv;
	
	}
	
	
	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	public ModelAndView charts(@CookieValue("invoicing_username") String cookielogin) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		PrestationsService srvprestations = (PrestationsService) context.getBean("PrestationsService");
		ClientService srvclient = (ClientService) context.getBean("ClientService");
		TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		double in=0;
		double out=0;
		for (int i=0 ; i<srvt.getlist().size() ; i++) {
			if (srvt.getlist().get(i).getSide().contentEquals("debit")) {
				out = out +srvt.getlist().get(i).getAmount();
			}
			
			if (srvt.getlist().get(i).getSide().contentEquals("credit")) {
				in =in +srvt.getlist().get(i).getAmount();
			}
			
		}
		BigDecimal bd = BigDecimal.valueOf(in);
		BigDecimal bd2 = BigDecimal.valueOf(out);
		BigDecimal result=bd.subtract(bd2);
		result = result.setScale(2, RoundingMode.DOWN);
		ModelAndView mv = new ModelAndView("/dash/dashbord");
		mv.addObject("ca", srvprestations.chiffre_affaire());
		mv.addObject("nb_paiement_to_validate", srvprestations.number_paiement_to_validate());
		mv.addObject("nb_paiement_validated", srvprestations.number_paiement_validate());
		mv.addObject("nb_clients", srvclient.numberclient());
		mv.addObject("liste_prestations", srvprestations.getlistprestations());
		mv.addObject("tresorie", result.doubleValue());
		mv.addObject("bankname", srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getBankname().toUpperCase());
		context.close();
		return mv;
	
	}
	
	
	
	@RequestMapping(value = "/prestations", method = RequestMethod.GET)
	public ModelAndView getprestations() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		PrestationsService srvprestations = (PrestationsService) context.getBean("PrestationsService");	
		ModelAndView mv = new ModelAndView("/prestations/liste_prestations");
		mv.addObject("Liste_prestations", srvprestations.getlistprestations());
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
		ModelAndView mv = new ModelAndView("/bank/transactions_bank");
		mv.addObject("List_transactions", srvtransaction.getlist());
			
		double in=0;
		double out=0;
		for (int i=0 ; i<srvtransaction.getlist().size() ; i++) {
			if (srvtransaction.getlist().get(i).getSide().contentEquals("debit")) {
				out = out +srvtransaction.getlist().get(i).getAmount();
			}
			
			if (srvtransaction.getlist().get(i).getSide().contentEquals("credit")) {
				in =in +srvtransaction.getlist().get(i).getAmount();
			}
			
		}
		DecimalFormat decimalFormat= new DecimalFormat("#.##");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);
		
		
		mv.addObject("amount_out", decimalFormat.format(out));
		mv.addObject("amount_in", decimalFormat.format(in));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		mv.addObject("last_refresh_transaction", formatter.format(srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getLast_refresh_transaction()));
		context.close();
		return mv;
	
	}

	@RequestMapping(value = "/tva_collectee", method = RequestMethod.GET)
	public ModelAndView tva_collectee() {
		ModelAndView mv = new ModelAndView("/bank/detail_tva");
		return mv;
	
	}

}