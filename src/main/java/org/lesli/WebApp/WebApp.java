package org.lesli.WebApp;

import org.lesli.WebApp.parsers.ExcelParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class WebApp {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(WebApp.class, args);
        //Following lines of code, exception throw and import were used to rewrite actual data with placeholders for the purpose of confidentiality
        //ExcelParser excelParser = (ExcelParser) context.getBean("excelParser");
        //excelParser.rewriteData("src/main/resources");
        //excelParser.rewriteData("src/main/resources/new");
    }
}
