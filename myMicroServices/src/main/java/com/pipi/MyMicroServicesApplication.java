package com.pipi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class MyMicroServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyMicroServicesApplication.class, args);
	}

	/** locale with Locale Resolver*/
	@Bean
	public LocaleResolver localeResolver(){
//		SessionLocaleResolver localeResolver = new SessionLocaleResolver(); // remove
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver(); // add
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

//	We can remove this and move the configuration to application.properties.
//	@Bean
//	public ResourceBundleMessageSource messageSource() {
//		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//		messageSource.setBasename("message");
//		return messageSource;
//	}
}
