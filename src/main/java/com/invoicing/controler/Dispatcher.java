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
@Controller
public class Dispatcher {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/accueil/login");
		return mv;
	}
	
	@RequestMapping(value = "/dashbord", method = RequestMethod.GET)
	public ModelAndView dash() {
		ModelAndView mv = new ModelAndView("/dash/dash");
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
	
	
	
	
}
