package fr.xhackax47.MyLittlePony.app;

import javax.persistence.EntityManagerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@SpringBootApplication(scanBasePackages = { "fr.xhackax47" })
@EnableJpaRepositories(basePackages = { "fr.xhackax47" })
@EntityScan(basePackages = "fr.xhackax47")
@EnableJpaAuditing
public class MyLittlePony {

	public static void main(String... args) {
		SpringApplication.run(MyLittlePony.class, args);
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
		bean.setDatabase(Database.POSTGRESQL);
		bean.setGenerateDdl(true);
		bean.setShowSql(true);
		return bean;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
}
