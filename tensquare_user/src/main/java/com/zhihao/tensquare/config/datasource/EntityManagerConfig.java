/**
 * 
 */
package com.zhihao.tensquare.config.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author zzh
 * 2018年10月30日
 */
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
public class EntityManagerConfig {

	@Autowired
	private DataSource dataSource;
	
	@Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		//加载hibernate属性文件，创建Properties对象
		InputStream inputStream = EntityManagerConfig.class.getClassLoader().getResourceAsStream("hibernate.properties");
        Properties properties = new Properties();
        try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Map<String, Object> propertyMap = new Hashtable<>();
		propertyMap.put("javax.persistence.schema-generation.database.action",
                "none");

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(adapter);
        factory.setDataSource(dataSource);
        factory.setPackagesToScan("com.zhihao.tensquare.entity");
        factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        factory.setValidationMode(ValidationMode.NONE);
        factory.setJpaPropertyMap(propertyMap);
        factory.setJpaProperties(properties);
        return factory;
    }
	
	@Bean(name="transactionManager")
    public PlatformTransactionManager jpaTransactionManager() {
        return new JpaTransactionManager(
        	this.entityManagerFactoryBean().getObject()
        );
    }
	
	// 通过EntityManagerFactory获取EntityManager对象
	@Bean(name="entityManager")
	public EntityManager entityManager() {
		return this.entityManagerFactoryBean().getObject().createEntityManager();
	}
}
