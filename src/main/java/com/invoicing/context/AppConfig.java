package com.invoicing.context;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
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

@ComponentScan(basePackages = "com.invoicing")
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class AppConfig {
	
	final org.apache.logging.log4j.Logger log =  LogManager.getLogger(this.getClass().getName());
   
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
           try {	
           if (System.getProperty("invoicing.env").toUpperCase().contentEquals("DEV")) {
			    dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
			    dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
			    dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));	
				} 
				
				if (System.getProperty("invoicing.env").toUpperCase().contentEquals("PROD")) {
				    dataSource.setUsername(environment.getRequiredProperty("prod.jdbc.username"));
				    dataSource.setUrl(environment.getRequiredProperty("prod.jdbc.url"));
				    dataSource.setPassword(environment.getRequiredProperty("prod.jdbc.password"));	
				}
           } catch (Exception e) {
        	log.error(ExceptionUtils.getStackTrace(e));
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
