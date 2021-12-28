package com.invoicing.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;



import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;


public class S3Amazonetools {
	
	static org.apache.logging.log4j.Logger logger =  LogManager.getLogger(S3Amazonetools.class);
	public static  void Putdocument(String Company,String year,String Typedocument,byte[] bytes,String filename)  throws  Exception{
				
		        InputStream input;
				final Properties prop = new Properties();
				ObjectMetadata metadata = new ObjectMetadata();
		        metadata.setContentType("application/pdf");
		        metadata.setContentLength((long) bytes.length);
		        ByteArrayInputStream inputstream = new ByteArrayInputStream(bytes);
				
					input = new FileInputStream(System.getProperty("env.file.ext"));	           
					prop.load(input);    
				
			   AWSCredentials credentials = new BasicAWSCredentials(prop.getProperty("AmazoneS3.KeyID"),prop.getProperty("AmazoneS3.SecretKey"));
			   AmazonS3 s3client = AmazonS3ClientBuilder
						  .standard()
						  .withCredentials(new AWSStaticCredentialsProvider(credentials))
						  .withRegion(Regions.EU_WEST_2)
						  .build();
				s3client.putObject(prop.getProperty("AmazoneS3.Bucket"), Company+"/"+Typedocument+"/"+year+"/"+filename, (InputStream) inputstream, metadata);		
				
	}
	
   }
