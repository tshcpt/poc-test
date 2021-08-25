package com.sample.rest.webservices.ledger.config;

import com.sample.rest.webservices.ledger.entity.write2.Write2MessageDetails;
import com.sample.rest.webservices.ledger.entity.write2.Write2TransactionDetails;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
//import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "app.datasource.write2")
@EnableJpaRepositories(basePackages = "com.sample.rest.webservices.ledger.repository.write2",
        entityManagerFactoryRef = "write2EntityManagerFactory",
        transactionManagerRef= "write2TransactionManager")
public class Write2SourceConfiguration  extends HikariConfig {

    /*@Bean
    @ConfigurationProperties("app.datasource.write2")
    public DataSourceProperties write2DataSourceProperties() {
        return new DataSourceProperties();
    }*/

    @Bean(name="write2DataSource")
    //@ConfigurationProperties("app.datasource.write2.configuration")
    public DataSource write2DataSource() {
        return new HikariDataSource(this);
        //return write2DataSourceProperties().initializeDataSourceBuilder()
          //      .type(BasicDataSource.class).build();
    }

    @Bean(name = "write2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean write2EntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(this.write2Adaptor());
        entityManagerFactoryBean.setDataSource(write2DataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPersistenceUnitName("mysql");
        entityManagerFactoryBean.setPackagesToScan("com.sample.rest.webservices.ledger.entity.write2");
        entityManagerFactoryBean.setJpaProperties(this.jpaHibernateProperties());
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean;

        /*return builder
                .dataSource(write2DataSource())
                .packages(Write2MessageDetails.class, Write2TransactionDetails.class)
                .build();*/
    }

    @Bean
    @PersistenceContext(name="write2")
    public PlatformTransactionManager write2TransactionManager(
            final @Qualifier("write2EntityManagerFactory") LocalContainerEntityManagerFactoryBean write2EntityManagerFactory) {
        return new JpaTransactionManager(write2EntityManagerFactory.getObject());
    }

    private HibernateJpaVendorAdapter write2Adaptor() {
        final HibernateJpaVendorAdapter write1Adaptor = new HibernateJpaVendorAdapter();
        write1Adaptor.setGenerateDdl(true);
        write1Adaptor.setShowSql(true);
        write1Adaptor.setDatabase(Database.MYSQL);
        write1Adaptor.setPrepareConnection(true);
        // put all the adapter properties here, such as show sql
        return write1Adaptor;
    }

    private Properties jpaHibernateProperties() {
        final Properties properties = new Properties();
        // put all required jpa propeties here
        properties.put("hibernate.dialect",org.hibernate.dialect.MySQL5InnoDBDialect.class);
        return properties;
    }

}