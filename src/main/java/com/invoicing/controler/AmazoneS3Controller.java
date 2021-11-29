package com.invoicing.controler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.invoicing.hibernate.configuration.AppConfig;
import com.invoicing.service.CompanyService;
import com.invoicing.service.LoginsService;
@Controller
public class AmazoneS3Controller {
	@GetMapping(value = "/Download_Invoice_From_Amazone/{company}/{typedocument}/{invoicename}",produces = "application/pdf")
	 public @ResponseBody byte[] Download_Invoice_From_Amazone(@PathVariable("company") String company ,@PathVariable("typedocument") String typedocument ,@PathVariable("invoicename") String invoicename) throws Exception {
		    S3Object s3Object = null;
			InputStream input;
			final Properties prop = new Properties();
			input = new FileInputStream(System.getProperty("env.file.ext"));	           
			prop.load(input);  
		    /*Retrieve file as object from S3*/
		    AWSCredentials credentials = new BasicAWSCredentials(prop.getProperty("AmazoneS3.KeyID"),prop.getProperty("AmazoneS3.SecretKey"));
			   AmazonS3 s3client = AmazonS3ClientBuilder
						  .standard()
						  .withCredentials(new AWSStaticCredentialsProvider(credentials))
						  .withRegion(Regions.EU_WEST_2)
						  .build();
		       s3Object = s3client.getObject(prop.getProperty("AmazoneS3.Bucket"),company+"/"+typedocument+"/"+java.time.Year.now().getValue()+"/"+invoicename);

		       byte[] bytes = IOUtils.toByteArray(s3Object.getObjectContent());
		       return bytes;
		    		   
		   }
	@GetMapping(value = "/Download_Kbis",produces = "application/pdf")
	public ResponseEntity<byte[]> Downloadkbis(@CookieValue("invoicing_username") String cookielogin)throws Exception {
		    AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		    CompanyService srvcompany = (CompanyService) context.getBean("CompanyService");
		    LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		    S3Object s3Object = null;
			InputStream input;
			final Properties prop = new Properties();
			input = new FileInputStream(System.getProperty("env.file.ext"));	           
			prop.load(input);  
		    /*Retrieve file as object from S3*/
		    AWSCredentials credentials = new BasicAWSCredentials(prop.getProperty("AmazoneS3.KeyID"),prop.getProperty("AmazoneS3.SecretKey"));
			   AmazonS3 s3client = AmazonS3ClientBuilder
						  .standard()
						  .withCredentials(new AWSStaticCredentialsProvider(credentials))
						  .withRegion(Regions.EU_WEST_2)
						  .build();
		       try {
			   s3Object = s3client.getObject(prop.getProperty("AmazoneS3.Bucket"),srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getRs()+"/ADMINISTRATION/KBIS/"+srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getKbis_file_name());
		       }catch(Exception e) {
		       context.close(); 
		       return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		       }
		       byte[] bytes = IOUtils.toByteArray(s3Object.getObjectContent());
		       HttpHeaders headers = new HttpHeaders();
		       headers.add("content-disposition", "attachment; filename=" + srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getKbis_file_name());
		       ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
		       bytes, headers, HttpStatus.OK);
		       context.close();
		       return response;
		    		   	
		
	}
}