/**
 * 
 */
package com.simple.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring configuration.
 * 
 * 
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.simple")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "com.simple.repository")
public class RootConfig {

   private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
   private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
   private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
   private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

   private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
   private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
   private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
   private static final String PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH = "hibernate.max_fetch_depth";
   private static final String PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE = "hibernate.jdbc.fetch_size";
   private static final String PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE = "hibernate.jdbc.batch_size";

   @Resource
   private Environment env;

   @Bean
   public DataSource dataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
      dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
      dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
      dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
      return dataSource;
   }

   /**
    * Use JPA Transaction Manager.
    * 
    * @param emf entity manager
    * @return a JPA transaction manager
    */
   @Bean
   public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(emf);
      return transactionManager;
   }

   /**
    * Use entity manager.
    * 
    * @return a entity manager
    */
   @Bean
   @Primary
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
      entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
      entityManagerFactoryBean.setDataSource(dataSource());
      entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
      entityManagerFactoryBean.setPackagesToScan(env.getProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
      entityManagerFactoryBean.setJpaProperties(hibernateProperties());
      return entityManagerFactoryBean;
   }

   /**
    * Use message resource bundle.
    * 
    * @return a message bundle
    */
   @Bean
   public ResourceBundleMessageSource messageSource() {
      ResourceBundleMessageSource source = new ResourceBundleMessageSource();
      source.setBasename(env.getRequiredProperty("message.source.basename"));
      source.setUseCodeAsDefaultMessage(true);
      return source;
   }

   @Bean
   public com.fasterxml.jackson.databind.ObjectMapper objectMapper() {
      return new com.fasterxml.jackson.databind.ObjectMapper();
   }

   private Properties hibernateProperties() {
      Properties properties = new Properties();
      properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
      properties.put(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH, env.getProperty(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH));
      properties.put(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE, env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE));
      properties.put(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE, env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE));
      properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
      properties.put(AvailableSettings.HBM2DDL_DATABASE_ACTION, "none");
      return properties;
   }

   private HibernateJpaVendorAdapter vendorAdaptor() {
      HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      vendorAdapter.setShowSql(true);
      return vendorAdapter;
   }
}
