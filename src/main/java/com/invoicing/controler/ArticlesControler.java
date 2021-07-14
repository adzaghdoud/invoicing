package com.invoicing.controler;

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
	
	
}
