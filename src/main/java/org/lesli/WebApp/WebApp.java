package org.lesli.WebApp;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.lesli.WebApp.services.SaleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class WebApp {

	public static void main(String[] args) throws IOException, InvalidFormatException {
		ConfigurableApplicationContext context = SpringApplication.run(WebApp.class, args);
		SaleService saleService = (SaleService) context.getBean("saleService");
		//saleService.save();
		//saleService.saveNew();
		System.out.println("Done");
	}
}
