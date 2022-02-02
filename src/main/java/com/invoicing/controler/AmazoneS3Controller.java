package com.invoicing.controler;

import java.io.FileInputStream;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
import com.invoicing.service.TransactionsService;
import com.invoicing.tools.S3Amazonetools;
@Controller
public class AmazoneS3Controller {
	final org.apache.logging.log4j.Logger log =  LogManager.getLogger(this.getClass().getName());
	@GetMapping(value = "/Download_From_Amazone_AS_Bytes/{company}/{typedocument}/{documentname}",produces = "application/pdf")
	 public @ResponseBody byte[] Download_From_Amazone_AS_Bytes(@PathVariable("company") String company ,@PathVariable("typedocument") String typedocument ,@PathVariable("documentname") String documentname) throws Exception {
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
		       s3Object = s3client.getObject(prop.getProperty("AmazoneS3.Bucket"),company+"/"+typedocument+"/"+java.time.Year.now().getValue()+"/"+documentname);      
		    
		       byte[] bytes = IOUtils.toByteArray(s3Object.getObjectContent());
		       return bytes;
		    		   
		   }
	
	
	
	@GetMapping(value = "/Download_From_Amazone_AS_RE/{company}/{typedocument}/{year}/{documentname}",produces = "application/pdf")
	 public  ResponseEntity<byte[]> Download_From_Amazone_AS_RE(@PathVariable("company") String company ,@PathVariable("typedocument") String typedocument ,@PathVariable("year") String year,@PathVariable("documentname") String documentname) throws Exception {
		    S3Object s3Object = null;
		    HttpHeaders headers = new HttpHeaders();
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
		       s3Object = s3client.getObject(prop.getProperty("AmazoneS3.Bucket"),company+"/"+typedocument+"/"+year+"/"+documentname);      
		       byte[] bytes = IOUtils.toByteArray(s3Object.getObjectContent());
		      

		       headers.add("content-disposition", "attachment; filename=" + documentname);
		       ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
		       bytes, headers, HttpStatus.OK);
		       return response;
		    		   
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

	
	
	
	
	@GetMapping(value = "/DownloadStatus",produces = "application/pdf")
	public ResponseEntity<byte[]> DownloadStatus(@CookieValue("invoicing_username") String cookielogin)throws Exception {
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
			   s3Object = s3client.getObject(prop.getProperty("AmazoneS3.Bucket"),srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getRs()+"/ADMINISTRATION/Status/"+srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getStatus_file_name());
		       }catch(Exception e) {
		       log.error("The file "+srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getStatus_file_name()+" is not found in Amazone S3"); 	   
		       context.close();
	           return null;
		       }
		       byte[] bytes = IOUtils.toByteArray(s3Object.getObjectContent());
		       HttpHeaders headers = new HttpHeaders();
		       headers.add("content-disposition", "attachment; filename=" + srvcompany.getcompanybyraison(srvlogins.getinfo(cookielogin).getCompany()).getStatus_file_name());
		       ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
		       bytes, headers, HttpStatus.OK);
		       context.close();
		       return response;
	  		
	}
	@GetMapping(value = "/Download_Invoice/{invoicename}",produces = "application/pdf")
	 public ResponseEntity<byte[]> Download_Invoice(@PathVariable("invoicename") String invoicename , @CookieValue("invoicing_username") String cookielogin) throws Exception {
		    AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		    LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
		    S3Object s3Object = null;
			InputStream input;
			HttpHeaders headers = new HttpHeaders();
			ResponseEntity<byte[]> response=null;
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
			   s3Object = s3client.getObject(prop.getProperty("AmazoneS3.Bucket"),srvlogins.getinfo(cookielogin).getCompany()+"/INVOICES/"+java.time.Year.now().getValue()+"/"+invoicename+".pdf");
               byte[] bytes = IOUtils.toByteArray(s3Object.getObjectContent());
		       headers.add("content-disposition", "attachment; filename=" + invoicename);
		       response = new ResponseEntity<byte[]>(
		       bytes, headers, HttpStatus.OK);		       
		       } catch (Exception e) {
		             
		    	            response = new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);    		         
		                    context.close();
		      		        return response;
		       }
		       context.close();
		       return response;
		   }


		@PostMapping(value = "/DeleteProof/{year}/{documentname}/{settled_at}/{label}/{reference}/{montant}/{type}",produces = "application/pdf")
		 public @ResponseBody ResponseEntity<String>  deleteProof(@CookieValue("invoicing_username") String cookielogin ,@PathVariable("year") String year,@PathVariable("documentname") String documentname,@PathVariable("settled_at") String settled_at,@PathVariable("label") String label,@PathVariable("reference") String reference,@PathVariable("montant") double montant , @PathVariable("type") String  type) throws Exception {
				InputStream input;
				AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
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
				    LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
					TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");		
					srvt.DeleteProofName(srvlogins.getinfo(cookielogin).getCompany(), documentname,settled_at,label,reference,montant,type);	
					s3client.deleteObject(prop.getProperty("AmazoneS3.Bucket"), srvlogins.getinfo(cookielogin).getCompany()+"/PROOF/"+year+"/"+documentname);   
					context.close();
					return ResponseEntity.ok("Le justificatif a été bien supprimé");
				   }catch(Exception e) {
					context.close();  
					System.out.println(ExceptionUtils.getStackTrace(e)); 
					return ResponseEntity.status(505).body("une erreur est survenue lors de la suppression du justificatif");
				   }
			 
		}


		@PostMapping(value = "/UploadProof")
		public  @ResponseBody void uploadproof(@CookieValue("invoicing_username") String cookielogin,@RequestParam(required = true) String  settled_at,@RequestParam(required = true) String  updated_at,@RequestParam(required = true) String  label,@RequestParam(required = true) String  reference,@RequestParam(required = true) double   montant,@RequestParam(required = true) String  proof_file_name,@RequestParam(required = true) MultipartFile proof_file ) throws  Exception{
			AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); 
			LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
			TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
			srvt.updateproof(settled_at, updated_at,label,reference,montant, srvlogins.getinfo(cookielogin).getCompany(),proof_file_name);	
			S3Amazonetools.Putdocument(srvlogins.getinfo(cookielogin).getCompany(),settled_at.substring(0, 4) ,"PROOF", proof_file.getBytes(),proof_file_name);
		    context.close();
		}
		
		
		@PostMapping(value = "/CheckExistProof")
		public  @ResponseBody JSONObject uploadproof(@CookieValue("invoicing_username") String cookielogin,@RequestParam(required = true) String  settled_at,@RequestParam(required = true) String  updated_at) throws  Exception{
			AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class); 
			LoginsService srvlogins = (LoginsService) context.getBean("LoginsService");
			TransactionsService srvt = (TransactionsService) context.getBean("TransactionsService");
		    JSONObject json = new JSONObject();
		    json=srvt.checkeexistproof(settled_at, updated_at,srvlogins.getinfo(cookielogin).getCompany());
		    context.close();
		    return json;
		}



}