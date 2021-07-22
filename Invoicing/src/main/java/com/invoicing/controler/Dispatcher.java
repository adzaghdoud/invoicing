package com.invoicing.controler;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.service.ArticleService;
import com.invoicing.service.ClientService;
import com.invoicing.service.CompanyService;
import com.invoicing.service.PrestationsService;
@Controller
public class Dispatcher {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/accueil/login");
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
		mv.addObject("info", srvcompany.getinfo());
		context.close();
		return mv;
	}
	
	
	@RequestMapping(value = "/Generalsettings", method = RequestMethod.GET)
	public ModelAndView getgeneralsettings() {
		ModelAndView mv = new ModelAndView("/settings/general_settings");
		return mv;
	}
	
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile() {
		ModelAndView mv = new ModelAndView("/settings/profil");
		return mv;
	
	}
	
	
	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	public ModelAndView charts() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		PrestationsService srvprestations = (PrestationsService) context.getBean("PrestationsService");
		ClientService srvclient = (ClientService) context.getBean("ClientService");
		ModelAndView mv = new ModelAndView("/dash/dashbord");
		mv.addObject("ca", srvprestations.chiffre_affaire());
		mv.addObject("nb_paiement_to_validate", srvprestations.number_paiement_to_validate());
		mv.addObject("nb_paiement_validated", srvprestations.number_paiement_validate());
		mv.addObject("number_clients"+srvclient.numberclient());
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
	
	
	
	
}
