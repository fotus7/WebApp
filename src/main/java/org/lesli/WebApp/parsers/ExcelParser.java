package org.lesli.WebApp.parsers;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lesli.WebApp.model.Company;
import org.lesli.WebApp.model.Product;
import org.lesli.WebApp.model.Sale;
import org.lesli.WebApp.repository.CompanyRepository;
import org.lesli.WebApp.repository.ProductRepository;
import org.lesli.WebApp.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class ExcelParser {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;
    private final List<Company> companies = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();

    //Used to populate the database with already existing data from past years
    public Set<Sale> getInitialData() throws IOException, InvalidFormatException {
        Set<Sale> sales = new TreeSet<>();
        Iterator<File> it = FileUtils.iterateFiles(new File("src/main/resources/"), new String[]{"xlsx", "xlsm"}, false);
        while (it.hasNext()) {
            sales.addAll(ultimateParser(it.next()));
        }
        System.out.println("Number of companies: " + companies.size());
        System.out.println("Number of products: " + products.size());
        System.out.println("Total number of sales: " + sales.size());
        return sales;
    }

    //Used to get new data from daily updated current year file
    public Set<Sale> updateData() throws IOException, InvalidFormatException {
        Set<Sale> sales = new TreeSet<>();
        Iterator<File> it = FileUtils.iterateFiles(new File("src/main/resources/new"), new String[]{"xlsx", "xlsm"}, false);
        while (it.hasNext()) {
            sales.addAll(ultimateParser(it.next()));
        }
        return sales;
    }

    //Used once for rewriting data for GitHub placement of the project
    public void rewriteData(String path) throws IOException {
        String excelPath;
        Iterator<File> it = FileUtils.iterateFiles(new File(path), new String[]{"xlsx", "xlsm"}, false);
        while (it.hasNext()) {
            excelPath = it.next().getPath();
            rewrite(excelPath);
        }
    }

    private void rewrite(String excelPath) throws IOException {
        Cell cell;
        FileInputStream inputStream = new FileInputStream(excelPath);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 1; !sheet.getRow(i).getCell(0).getCellType().toString().equals("STRING"); i++) {
            cell = sheet.getRow(i).getCell(1);
            cell.setCellValue("Компания #" + companyRepository.findByNameIs(cell.getStringCellValue()).getId());
        }
        for (int i = 2; !sheet.getRow(0).getCell(i).getStringCellValue().equals("Итого"); i++) {
            cell = sheet.getRow(0).getCell(i);
            try {
                cell.setCellValue("Продукт #" + productRepository.findByNameIs(cell.getStringCellValue()).getId());
            } catch (NullPointerException ex) {
                System.out.println("Product " + cell.getStringCellValue() + " was never bought");
                cell.setCellValue("Некупленный продукт");
            }
        }
        inputStream.close();
        FileOutputStream outputStream = new FileOutputStream(excelPath);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
        System.out.println("Rewriting for file " + excelPath + " done");
    }

    private Set<Sale> ultimateParser(File file) throws IOException, InvalidFormatException {
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Set<Sale> sales = new TreeSet<>();
        int startRowNum = getRowNum(sheet);
        if (startRowNum == 0) {
            workbook.close();
            return sales;
        }
        Date date;
        String company;
        String product;
        double amount;
        for (int j = startRowNum; !sheet.getRow(j).getCell(0).getCellType().toString().equals("STRING"); j++) {
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
                    sales.add(saleFactory(date, c, p, amount, j));
                }
            }
        }
        workbook.close();
        return sales;
    }

    private Sale saleFactory(Date date, Company c, Product p, double amount, int rowNum) {
        if (companyRepository.existsCompanyByName(c.getName())) {
            if (productRepository.existsProductByName(p.getName())) {
                return new Sale(date, companyRepository.findByNameIs(c.getName()), productRepository.findByNameIs(p.getName()), amount, rowNum);
            }
            return new Sale(date, companyRepository.findByNameIs(c.getName()), p, amount, rowNum);
        }
        if (productRepository.existsProductByName(p.getName())) {
            return new Sale(date, c, productRepository.findByNameIs(p.getName()), amount, rowNum);
        }
        return new Sale(date, c, p, amount, rowNum);
    }

    private int getRowNum(Sheet sheet) {
        Sale lastAddedSale = saleRepository.findTopByOrderByIdDesc();
        if (lastAddedSale == null) return 1;
        Date date;
        String company;
        String product;
        double amount;
        for (int j = 1; !sheet.getRow(j).getCell(0).getCellType().toString().equals("STRING"); j++) {
            date = sheet.getRow(j).getCell(0).getDateCellValue();
            if (date.before(lastAddedSale.getDate())) continue;
            if (date.after(lastAddedSale.getDate())) return 1;
            company = sheet.getRow(j).getCell(1).getStringCellValue();
            if (!company.equals(lastAddedSale.getCompany().getName())) continue;
            for (int i = 2; !sheet.getRow(0).getCell(i).getStringCellValue().equals("Итого"); i++) {
                amount = sheet.getRow(j).getCell(i).getNumericCellValue();
                if (amount == lastAddedSale.getAmount()) {
                    product = sheet.getRow(0).getCell(i).getStringCellValue();
                    if (!product.equals(lastAddedSale.getProduct().getName())) continue;
                    return j + 1;
                }
            }
        }
        return 0;
    }

    private void test(Set<Sale> sales) throws IOException {
        File file = new File("src/main/resources/test.txt");
        for (Sale s : sales) {
            FileUtils.write(file, s.toString() + "\n", Charset.defaultCharset(), true);
        }
    }
}
