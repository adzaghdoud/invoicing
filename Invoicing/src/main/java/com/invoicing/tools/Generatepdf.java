package com.invoicing.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.invoicing.model.Company;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class Generatepdf extends HttpServlet{

	private String filename;
	private String filetype;
	private String typedocument;
	private Company company;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getTypedocument() {
		return typedocument;
	}

	public void setTypedocument(String typedocument) {
		this.typedocument = typedocument;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void generate(HttpServletResponse response) {
		
		
		//regeneration Facture
		
		   String reportPath;
		   OutputStream outStream;
		   JasperReport jasperReport;
		   JasperDesign jasperDesign;
		   JRDataSource reportSource;
		   JRDataSource reportSource2;
		   JRDataSource reportSource3;
		   
		  
		   try {
		   	 
			 jasperDesign = JRXmlLoader.load(realpath);
		     jasperReport = JasperCompileManager.compileReport(jasperDesign);

		     /**
		      * Get report DataSource.
		      */
		     Map<String, Object> reportParameters = new HashMap<String, Object>();
		     if (this.typedocument.contains("extract")) {
		     
		     reportSource = new JRBeanCollectionDataSource(listcommande); 
		     
		     reportParameters.put("DS1", reportSource);
		     }
		     else {
		    	 
		    	 reportSource = new JRBeanCollectionDataSource(commande); 
			     reportSource2 = new JRBeanCollectionDataSource(globalcommande);
			     List<Site> list = Arrays.asList(site);
			     reportSource3 = new JRBeanCollectionDataSource(list);
			     
			     reportParameters.put("DS1", reportSource);
			     reportParameters.put("DS2", reportSource2);
			     reportParameters.put("DS3", reportSource3);
		     }
		     //load form the classpath
		     
		     InputStream input =this.getClass().getClassLoader().getResourceAsStream("application.properties");

		            Properties prop = new Properties();

		            if (input == null) {
		                System.out.println("Sorry, unable to find application.properties");
		            }

		            //load a properties file from class path, inside static method
		            prop.load(input);
		 
		      BufferedImage image = ImageIO.read(new URL(prop.getProperty("URL.imageinvoice")));
		      if (this.typedocument == "invoice") {	     
		      reportParameters.put("QRcontain",numfacture);
		      }
		      reportParameters.put("myimage",image);
		     /**
		      * Get byteStream for generated Stream.
		      */
		   //  byte[] byteStream;
		   //  byteStream = JasperRunManager.runReportToPdf(jasperReport,reportParameters, new JREmptyDataSource());  
		      JasperPrint jasperPrint= JasperFillManager.fillReport( jasperReport,reportParameters, new JREmptyDataSource());
		      //if (printFileName != null) {
		            /**
		             * 1- export to PDF
		             */
		      if (this.typedocument == "invoice") {
		          JasperExportManager.exportReportToPdfFile(jasperPrint,prop.getProperty("path.pdf")+numfacture+".pdf");
		      }
		      else {
		    	  
		    	  JasperExportManager.exportReportToPdfFile(jasperPrint,prop.getProperty("path.pdf")+filename+".pdf");  
		      }
		               
		     // }
		      
		      
		      
		     /**
		      * Set output Stream in response.
		      */
		  //   outStream = response.getOutputStream();
		  //   response.setHeader("Content-Disposition", "inline, filename=" + filename);
		   //  response.setContentType(filetype);
		   //  response.setContentLength(byteStream.length);
		  //   outStream.write(byteStream, 0, byteStream.length);
		  //   outStream.flush();
		    // outStream.close();
		     
		   } catch (JRException ex) {
		     Logger.getLogger(Generatepdf.class.getName()).log(Level.SEVERE, null, ex);
		   } catch (Exception e) {
		     e.printStackTrace();
		   } 	



}

	