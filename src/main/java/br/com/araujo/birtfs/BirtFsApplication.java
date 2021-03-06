package br.com.araujo.birtfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class, ErrorMvcAutoConfiguration.class})
public class BirtFsApplication extends SpringBootServletInitializer {

	private static Class<BirtFsApplication> applicationClass = BirtFsApplication.class;
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}
}
