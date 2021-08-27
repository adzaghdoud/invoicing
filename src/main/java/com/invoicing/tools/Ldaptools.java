package com.invoicing.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;

public class Ldaptools {

	public static Map<String, String> getvalueattibute(String uid) {
		   Attribute mail;
		   Attribute cn;
		   Attribute tel;
		   Attribute o;
		   Map<String, String> map = new HashMap<String, String>();
		   try {
			Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://vmi537338.contaboserver.net:389");
			env.put(Context.SECURITY_PRINCIPAL, "cn=Directory Manager");
			env.put(Context.SECURITY_CREDENTIALS, "09142267");
		    DirContext ctx = new InitialDirContext(env);
		    Attributes attrs;
		    attrs = ctx.getAttributes(uid);
		    mail= attrs.get("mail");
		    cn = attrs.get("cn");
		    tel = attrs.get("telephonenumber");
		    o = attrs.get("o");
		    map.put("mail", (String)mail.get());
		    map.put("cn", (String)cn.get());
		    map.put("tel", (String)tel.get());
		    map.put("o", (String)o.get());
			} catch (Exception e) {				
				System.out.println(ExceptionUtils.getStackTrace(e));
				return null;
			}
		
	           return map;
	}

}
