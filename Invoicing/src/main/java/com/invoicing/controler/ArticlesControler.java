package com.invoicing.controler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.model.Article;
import com.invoicing.service.ArticleService;
@Controller
public class ArticlesControler {
	@RequestMapping(value = "/articles", method = RequestMethod.GET)
	public ModelAndView getarticles() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		ArticleService srvarticles = (ArticleService) context.getBean("ArticleService");
		ModelAndView mv = new ModelAndView("/articles/liste_article");
		mv.addObject("Listarticles", srvarticles.getlistarticles());
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
	public @ResponseBody boolean createnew(@RequestParam (required = true)String designation,@RequestParam (required = true)String famille,
			@RequestParam (required = true) double pvht , @RequestParam (required = true) double paht,	@RequestParam (required = true) String taxe,
			@RequestParam (required = true) int valtaxe, @RequestParam (required = true) double pvttc ) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);	
		ArticleService srvarticles = (ArticleService) context.getBean("ArticleService");
		Article a = new Article();
		a.setDesignation(designation);
		a.setFamille(famille);
		a.setPaht(paht);
		a.setPvht(pvht);
		a.setPvttc(pvttc);
		a.setTaxe(taxe);
		a.setValtax(valtaxe);
		try {
			srvarticles.addarticle(a);	
			
		}catch(Exception e) {
			context.close();
			ExceptionUtils.getStackTrace(e);
			return false;
		}
		
	
		context.close();
		return true;
	}
	
	
	
	
}
