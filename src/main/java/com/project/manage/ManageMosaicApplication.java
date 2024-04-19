package com.project.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class ManageMosaicApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ManageMosaicApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(ManageMosaicApplication.class);

    }

}
