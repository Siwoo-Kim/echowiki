package org.echowiki.core.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Import(value = CoreConfiguration.class)
@Configuration
@EnableJpaRepositories("org.echowiki.core.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Value("${db.driver.class}")
    private String DB_DRIVER_CLASS;

    @Value("${db.url}")
    private String DB_URL;

    @Value("${db.username}")
    private String DB_USERNAME;

    @Value("${db.password}")
    private String DB_PASSWORD;

    @Value("${hibernate.entity.package}")
    private String HIBERNATE_ENTITY_PACKAGE;

    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;

    @Value("${hibernate.hbm2ddl.auto}")
    private String HIBERNATE_HBM2DDL_AUTO;

    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;

    @Value("${hibernate.format_sql}")
    private String HIBERNATE_FORMAT_SQL;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DB_DRIVER_CLASS);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setMaxOpenPreparedStatements(100);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(HIBERNATE_ENTITY_PACKAGE);
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", HIBERNATE_DIALECT);
        properties.setProperty("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        properties.setProperty("hibernate.format_sql", HIBERNATE_FORMAT_SQL);
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public TransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
