package com.invoicing.controler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.model.Logins;
import com.invoicing.model.Prestations;
import com.invoicing.service.LoginsService;
import com.invoicing.service.PrestationsService;

@Controller
public class LoginsControler {
	@RequestMapping(value = "/getuserinfo", method = RequestMethod.POST)
	public @ResponseBody Logins getinforuser(HttpServletRequest request,@CookieValue("invoicing_username") String cookielogin) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");	
		Logins p = srvlogins.getinfo(cookielogin);
	    context.close();
	    return p;
	
	}
}
