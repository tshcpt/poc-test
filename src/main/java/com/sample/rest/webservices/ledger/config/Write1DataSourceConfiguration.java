package com.sample.rest.webservices.ledger.config;

import com.sample.rest.webservices.ledger.entity.write1.MessageDetails;
import com.sample.rest.webservices.ledger.entity.write1.TransactionDetails;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "app.datasource.write1")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.sample.rest.webservices.ledger.repository.write1",
        entityManagerFactoryRef = "write1EntityManagerFactory",
        transactionManagerRef= "write1TransactionManager"
)
public class Write1DataSourceConfiguration extends HikariConfig {

    /*@Bean
    @Primary
    @ConfigurationProperties("app.datasource.write1")
    public DataSourceProperties write1DataSourceProperties() {
        return new DataSourceProperties();
    }*/

    @Bean(name="write1DataSource")
    @Primary
    //@ConfigurationProperties("app.datasource.write1.configuration")
    public DataSource write1DataSource() {
        return new HikariDataSource(this);
        /*return write1DataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();*/
    }

    @Primary
    @Bean(name = "write1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean write1EntityManagerFactory(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(this.write1Adaptor());
        entityManagerFactoryBean.setDataSource(write1DataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPersistenceUnitName("mysql");
        entityManagerFactoryBean.setPackagesToScan("com.sample.rest.webservices.ledger.entity.write1");
        entityManagerFactoryBean.setJpaProperties(this.jpaHibernateProperties());
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean;

        /*return builder
                .dataSource(write1DataSource())
                .packages(MessageDetails.class, TransactionDetails.class)
                .
                .build();*/
    }


    @Primary
    @Bean
    @PersistenceContext(name="write1")
    public PlatformTransactionManager write1TransactionManager(
            final @Qualifier("write1EntityManagerFactory") LocalContainerEntityManagerFactoryBean write1EntityManagerFactory) {
        return new JpaTransactionManager(write1EntityManagerFactory.getObject());
    }

    private HibernateJpaVendorAdapter write1Adaptor() {
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