package com.bridgelabz.fundoonotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@EnableCaching

public class FundoonotesApplication {

	public static void main(String[] args) {
		 System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(FundoonotesApplication.class, args);
	
	}

}
