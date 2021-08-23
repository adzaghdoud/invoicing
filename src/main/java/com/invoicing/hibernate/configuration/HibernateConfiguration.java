package com.invoicing.hibernate.configuration;

	import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


	 
	import javax.sql.DataSource;
	 
	import org.hibernate.SessionFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.ComponentScan;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.context.annotation.PropertySource;
	import org.springframework.core.env.Environment;
	import org.springframework.jdbc.datasource.DriverManagerDataSource;
	import org.springframework.orm.hibernate5.HibernateTransactionManager;
	import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
	import org.springframework.transaction.annotation.EnableTransactionManagement;
	 
	@Configuration
	@EnableTransactionManagement
	@ComponentScan({ "com.invoicing.hibernate.configuration" })
	@PropertySource(value = { "classpath:application.properties" })
	public class HibernateConfiguration {
	 
	    @Autowired
	    private Environment environment;
	 
	    @Bean
	    public LocalSessionFactoryBean sessionFactory() {
	        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource());
	        sessionFactory.setPackagesToScan(new String[] { "com.invoicing.model" });
	        sessionFactory.setHibernateProperties(hibernateProperties());
	        return sessionFactory;
	     }
	     
	    @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
	        
	       
					
					if (System.getProperty("invoicing.env").toString().contentEquals("DEV")) {
				    dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
				    dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
				    dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));	
					} 
					
					if (System.getProperty("invoicing.env").toString().contentEquals("PROD")) {
					    dataSource.setUsername(environment.getRequiredProperty("prod.jdbc.username"));
					    dataSource.setUrl(environment.getRequiredProperty("prod.jdbc.url"));
					    dataSource.setPassword(environment.getRequiredProperty("prod.jdbc.password"));	
					}  
	        return dataSource;
	    }
	     
	    private Properties hibernateProperties() {
	        Properties properties = new Properties();
	        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
	        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
	        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
	        return properties;        
	    }
	     
	    @Bean
	    @Autowired
	    public HibernateTransactionManager transactionManager(SessionFactory s) {
	       HibernateTransactionManager txManager = new HibernateTransactionManager();
	       txManager.setSessionFactory(s);
	       return txManager;
	    }
}
