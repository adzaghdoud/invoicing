package com.invoicing.tools;



import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONObject;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.common.io.ByteSource;
import com.invoicing.model.Client;
import com.invoicing.model.Company;
import com.invoicing.model.Prestations;
import com.lowagie.text.pdf.codec.Base64.InputStream;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;


public class Generatepdf {

	/**
	 * 
	 */
	static org.apache.logging.log4j.Logger logger =  LogManager.getLogger(Generatepdf.class);
	private static final long serialVersionUID = 1L;
	private String filetype;
	private String filename;
	private Company company;
	private Client client;
	private Prestations prestation;
	

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}


	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	
	 

	public Prestations getPrestation() {
		return prestation;
	}

	public void setPrestation(Prestations prestation) {
		this.prestation = prestation;
	}

	@SuppressWarnings("unchecked")
	public JSONObject generate(HttpServletResponse response) {
		
		
		//regeneration Facture
		   
		   JSONObject jsonresult = new JSONObject();
		   JasperReport jasperReport;
		   JasperDesign jasperDesign;
		   JRDataSource reportSource1;
		   JRDataSource reportSource2;
		   JRDataSource reportSource3;
		   JRDataSource reportSource4;
		   JRDataSource reportSource5;
		   try {
		      ClassLoader classLoader = getClass().getClassLoader();	 
		      File file = new File(classLoader.getResource("invoice.jrxml").getFile());
			  jasperDesign = JRXmlLoader.load(file);
		      jasperReport = JasperCompileManager.compileReport(jasperDesign);
		      
		     /**
		      * Get report DataSource.
		      */
		         Map<String, Object> reportParameters = new HashMap<String, Object>();
		         List<Company> listcompany = Arrays.asList(company);
		         List<Client> listclient = Arrays.asList(this.client);
		         List<Prestations> listprestation = Arrays.asList(this.prestation);
		    	 reportSource1 = new JRBeanCollectionDataSource(listcompany); 
		    	 reportSource2 = new JRBeanCollectionDataSource(listclient); 
		    	 reportSource3 = new JRBeanCollectionDataSource(listprestation);
		    	 reportSource4 = new JRBeanCollectionDataSource(listcompany);
		    	 reportSource5 = new JRBeanCollectionDataSource(listprestation);
		    	 java.io.InputStream logo;
		    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		    	 String condition_paiement=this.prestation.getTotalttc()+" Euros �payer par "+this.prestation.getModepaiement()+" le "+formatter.format(this.prestation.getDatepaiementattendue());
				try {
					logo = ByteSource.wrap(company.getLogo()).openStream();
					BufferedImage logoimage = ImageIO.read(logo);
					reportParameters.put("logo", logoimage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                        
			     reportParameters.put("DS1", reportSource1);
			     reportParameters.put("DS2", reportSource2);
			     reportParameters.put("DS3", reportSource3);
			     reportParameters.put("DS4", reportSource4);
			     reportParameters.put("DSinvoiceheader", reportSource5);
			     reportParameters.put("condition_paiement", condition_paiement);
			     reportParameters.put("total_HT", prestation.getMontantHT()*prestation.getQuantite());
			     reportParameters.put("TVA", prestation.getValtaxe());
			     reportParameters.put("Total_TTC", prestation.getTotalttc());
			     reportParameters.put("tel", company.getTel());	
				 JasperPrint jasperPrint= JasperFillManager.fillReport( jasperReport,reportParameters, new JREmptyDataSource());
				   
	
		         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();	
			     JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
		         try {
			     S3Amazonetools.Putdocument(company.getRs(), "INVOICES", byteArrayOutputStream,filename+".pdf");
		         } catch (Exception e) { 
		        	 logger.error(ExceptionUtils.getStackTrace(e));
		        	 jsonresult.put("msg","Error Uplaod invoice in Amazone S3");
		        	 return jsonresult;
		         }
		         }  catch (Exception e) {
		         logger.error(ExceptionUtils.getStackTrace(e));
			     jsonresult.put("msg","Error Generation invoice "+filename);
	        	 return jsonresult;
		         }		   
		   
		         jsonresult.put("msg","G�n�ration Facture "+filename+".pdf"+"OK \n Upload invoice in Amazone S3 OK"); 
	 return jsonresult;
	}

	
	@SuppressWarnings("unchecked")
	public byte[] GenerateInvoiceAsBytes() {
		
		
		//regeneration Facture
		   ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		   JasperReport jasperReport;
		   JasperDesign jasperDesign;
		   JRDataSource reportSource1;
		   JRDataSource reportSource2;
		   JRDataSource reportSource3;
		   JRDataSource reportSource4;
		   JRDataSource reportSource5;
		   try {
		      ClassLoader classLoader = getClass().getClassLoader();	 
		      File file = new File(classLoader.getResource("invoice.jrxml").getFile());
			  jasperDesign = JRXmlLoader.load(file);
		      jasperReport = JasperCompileManager.compileReport(jasperDesign);
		      
		     /**
		      * Get report DataSource.
		      */
		         Map<String, Object> reportParameters = new HashMap<String, Object>();
		         List<Company> listcompany = Arrays.asList(company);
		         List<Client> listclient = Arrays.asList(this.client);
		         List<Prestations> listprestation = Arrays.asList(this.prestation);
		    	 reportSource1 = new JRBeanCollectionDataSource(listcompany); 
		    	 reportSource2 = new JRBeanCollectionDataSource(listclient); 
		    	 reportSource3 = new JRBeanCollectionDataSource(listprestation);
		    	 reportSource4 = new JRBeanCollectionDataSource(listcompany);
		    	 reportSource5 = new JRBeanCollectionDataSource(listprestation);
		    	 java.io.InputStream logo;
		    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		    	 String condition_paiement=this.prestation.getTotalttc()+" Euros �payer par "+this.prestation.getModepaiement()+" le "+formatter.format(this.prestation.getDatepaiementattendue());
				try {
					logo = ByteSource.wrap(company.getLogo()).openStream();
					BufferedImage logoimage = ImageIO.read(logo);
					reportParameters.put("logo", logoimage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                        
			     reportParameters.put("DS1", reportSource1);
			     reportParameters.put("DS2", reportSource2);
			     reportParameters.put("DS3", reportSource3);
			     reportParameters.put("DS4", reportSource4);
			     reportParameters.put("DSinvoiceheader", reportSource5);
			     reportParameters.put("condition_paiement", condition_paiement);
			     reportParameters.put("total_HT", prestation.getMontantHT()*prestation.getQuantite());
			     reportParameters.put("TVA", prestation.getValtaxe());
			     reportParameters.put("Total_TTC", prestation.getTotalttc());
			     reportParameters.put("tel", company.getTel());	
				 JasperPrint jasperPrint= JasperFillManager.fillReport( jasperReport,reportParameters, new JREmptyDataSource());
			     JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
		     
		         }  catch (Exception e) {
		         logger.error(ExceptionUtils.getStackTrace(e));
		         }		   

	 return byteArrayOutputStream.toByteArray();
	}






}