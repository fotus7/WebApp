package org.lesli.WebApp.parsers;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lesli.WebApp.model.Company;
import org.lesli.WebApp.model.Product;
import org.lesli.WebApp.model.Sale;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class InitialParser {
    private final Set<Sale> sales = new TreeSet<>();
    private final List<Company> companies = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();

    public Set<Sale> process () throws IOException, InvalidFormatException {
        Iterator<File> it = FileUtils.iterateFiles(new File("src/main/resources/"), new String[]{"xlsx"}, false);
        while (it.hasNext()) {
            sales.addAll(ultimateParser(it.next()));
        }
        System.out.println("Number of companies: " + companies.size());
        System.out.println("Number of products: " + products.size());
        System.out.println("Total number of sales: " + sales.size());
        return sales;
    }

    public Set<Sale> process1 () throws IOException, InvalidFormatException {
        Iterator<File> it = FileUtils.iterateFiles(new File("F:\\Test"), new String[]{"xlsx"}, false);
        while (it.hasNext()) {
            sales.addAll(ultimateParser(it.next()));
        }
        System.out.println("Number of companies: " + companies.size());
        System.out.println("Number of products: " + products.size());
        System.out.println("Total number of sales: " + sales.size());
        return sales;
    }

    public Set<Sale> ultimateParser (File file) throws IOException, InvalidFormatException {
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Set<Sale> sales = new HashSet<>();
        Date date;
        String company;
        String product;
        double amount;
        for (int j = 1; !sheet.getRow(j).getCell(0).getCellType().toString().equals("STRING"); j++) {
            date = sheet.getRow(j).getCell(0).getDateCellValue();
            company = sheet.getRow(j).getCell(1).getStringCellValue();
            for (int i = 2; !sheet.getRow(0).getCell(i).getStringCellValue().equals("Итого"); i++) {
                amount = sheet.getRow(j).getCell(i).getNumericCellValue();
                if (amount != 0) {
                    product = sheet.getRow(0).getCell(i).getStringCellValue();
                    Company c = new Company(company);
                    if (companies.contains(c)) c = companies.get(companies.indexOf(c));
                    else companies.add(c);
                    Product p = new Product(product);
                    if (products.contains(p)) p = products.get(products.indexOf(p));
                    else products.add(p);
                    sales.add(new Sale(date, c, p, amount));
                }
            }
        }
        workbook.close();
        return sales;
    }
}
