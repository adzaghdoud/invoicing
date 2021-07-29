package com.invoicing.filtre;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebFilter("/AuthentificationFilter")
public class AuthentificationFilter implements Filter {
	private ServletContext context;
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.context = filterConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();
		this.context.log("Requested Resource::"+uri);
		
		//flase to indicate no create new session if session is invalid
		HttpSession session = req.getSession(false);
		
		if( session==null && ! uri.endsWith("login") && ! uri.endsWith("png")  ){
			this.context.log("Unauthorized access request"+uri);
			res.sendRedirect("login");
		}else{
			 res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  
			 res.setHeader("Pragma", "no-cache");  
			 res.setDateHeader("Expires", 0); 
			// pass the request along the filter chain
			chain.doFilter(request, response);
			
		}
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
