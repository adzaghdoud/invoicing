package com.invoicing.tools;



import java.io.File;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.invoicing.model.Client;
import com.invoicing.model.Company;
import com.invoicing.model.Prestations;

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

import net.sf.jasperreports.engine.xml.JRXmlLoader;


public class Generatepdf extends HttpServlet{

	/**
	 * 
	 */
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

	public boolean generate(HttpServletResponse response,String numfacture) {
		
		
		//regeneration Facture
		   JasperReport jasperReport;
		   JasperDesign jasperDesign;
		   JRDataSource reportSource1;
		   JRDataSource reportSource2;
		   JRDataSource reportSource3;
		   JRDataSource reportSource4;
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
			     reportParameters.put("DS1", reportSource1);
			     reportParameters.put("DS2", reportSource2);
			     reportParameters.put("DS3", reportSource3);
			     reportParameters.put("DS4", reportSource4);
			     JasperPrint jasperPrint= JasperFillManager.fillReport( jasperReport,reportParameters, new JREmptyDataSource());
			     JasperExportManager.exportReportToPdfFile(jasperPrint,System.getProperty("pdf.stor")+FileSystems.getDefault().getSeparator()+this.filename+".pdf");	     
		   } catch (JRException ex) {
			      
			      Logger.getLogger(Generatepdf.class.getName()).log(Level.SEVERE, null, ex);
			     
			     
			     return false;
		        }
	
	
               return true;
}
}

	