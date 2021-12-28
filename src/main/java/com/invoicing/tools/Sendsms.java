package com.invoicing.tools;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
public class Sendsms {
static org.apache.logging.log4j.Logger logger =  LogManager.getLogger(Sendsms.class);
	
public boolean send (String Tophonenumber,String Contain ) throws Exception {
	
	InputStream input;
	Properties prop = new Properties();
	try {
	input = new FileInputStream(System.getProperty("env.file.ext"));	           
	prop.load(input);
	Twilio.init(prop.getProperty("Twilio.AccountSid"), prop.getProperty("Twilio.AuthToken"));
    Message message = Message.creator( 
            new com.twilio.type.PhoneNumber(Tophonenumber), 
            new com.twilio.type.PhoneNumber(prop.getProperty("Twilio.PhoneNumber")),  
            Contain)      
        .create();
    return true;
	}catch(Exception e) {
		System.out.println("***********************************"+ExceptionUtils.getStackTrace(e));
		logger.error(ExceptionUtils.getStackTrace(e));	
		return false;
	}

}
}
