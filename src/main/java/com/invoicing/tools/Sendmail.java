package com.invoicing.tools;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.multipart.MultipartFile;

public class Sendmail {
	
	private String mailto;
	private String subject;
	private String contain;
	private MultipartFile file;
	private String filename;
	private String filetype;

	
	public MultipartFile getFile() {
		return file;
	}


	public void setFile(MultipartFile file) {
		this.file = file;
	}


	public String getFilename() {
		return filename;
	}




	public void setFilename(String filename) {
		this.filename = filename;
	}


	public String getMailto() {
		return mailto;
	}




	public void setMailto(String mailto) {
		this.mailto = mailto;
	}




	public String getSubject() {
		return subject;
	}




	public void setSubject(String subject) {
		this.subject = subject;
	}




	public String getContain() {
		return contain;
	}




	public void setContain(String contain) {
		this.contain = contain;
	}
 
	
	
	public String getFiletype() {
		return filetype;
	}


	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}


	public   File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
	    File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
	    multipart.transferTo(convFile);
	    return convFile;
	}
	
	
	public boolean send(){
		 
		      try {
				InputStream input = new FileInputStream(System.getProperty("env.file.ext"));
				

				final Properties prop = new Properties();

	            // load a properties file
	            try {
					prop.load(input);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
	            
	  
	    		Properties props = new Properties();
	    		props.put("mail.smtp.auth", "true");
	    		props.put("mail.smtp.starttls.enable","true");
	    		props.put("mail.smtp.host",prop.getProperty("SMTP.HOST"));
	    		props.put("mail.smtp.port",prop.getProperty("SMTP.PORT"));
	    		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	    		Session session = Session.getInstance(props,
	    		new javax.mail.Authenticator() {
	    		protected PasswordAuthentication getPasswordAuthentication() {
	    		return new PasswordAuthentication(prop.getProperty("SMTP.USERNAME"), prop.getProperty("SMTP.PASSWORD"));
	    		}
	    		});
	    		try {
	    		if (this.file != null) {		
	    	    multipartToFile(this.file, this.filename);
	    		}	
	    	    Message message = new MimeMessage(session);
	    		message.setFrom(new InternetAddress(prop.getProperty("COMPANY.EMAIL")));
	    		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(this.mailto));	
	    		message.setSubject(this.subject);
	    		
	    		
	    		Multipart multipart = new MimeMultipart();

	            MimeBodyPart textBodyPart = new MimeBodyPart();
	            textBodyPart.setContent(this.contain, "text/html; charset=utf-8"); 
	            textBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
	            if ( this.filename !=null) {
	            MimeBodyPart attachmentBodyPart= new MimeBodyPart();
	            FileDataSource source = new FileDataSource(System.getProperty("java.io.tmpdir")+FileSystems.getDefault().getSeparator()+this.filename);
	            attachmentBodyPart.setDataHandler(new DataHandler(source));
	            attachmentBodyPart.setFileName(this.filename);
	            multipart.addBodyPart(attachmentBodyPart);
	            }
	            multipart.addBodyPart(textBodyPart);
	            message.setContent(multipart);
	    		Transport.send(message);
	    		} catch (MessagingException e) {
	    			System.out.print(ExceptionUtils.getStackTrace(e));
	    		return false;
	    		} 
		       } catch (Exception e) {
				System.out.print(ExceptionUtils.getStackTrace(e));
				return false;
			   }	
	
	           return true;
	
	}
}
