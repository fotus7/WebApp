package org.lesli.WebApp.parsers;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lesli.WebApp.model.Company;
import org.lesli.WebApp.model.Product;
import org.lesli.WebApp.model.Sale;
import org.lesli.WebApp.repository.CompanyRepository;
import org.lesli.WebApp.repository.ProductRepository;
import org.lesli.WebApp.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class NewDataParser {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    public Set<Sale> addData () throws IOException, InvalidFormatException {
        File file = new File("src/main/resources/new/feb.xlsm");
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Set<Sale> sales = new TreeSet<>();
        int startRowNum = getRowNum(sheet);
        if (startRowNum == 0) return sales;
        List<Company> companies = new ArrayList<>();
        List<Product> products = new ArrayList<>();
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
                    if (companyRepository.existsCompanyByName(company)) {
                        if (productRepository.existsProductByName(product)) {
                            sales.add(new Sale(date, companyRepository.findByNameIs(company), productRepository.findByNameIs(product), amount));
                            continue;
                        }
                        sales.add(new Sale(date, companyRepository.findByNameIs(company), p, amount));
                        continue;
                    }
                    if (productRepository.existsProductByName(product)) {
                        sales.add(new Sale(date, c, productRepository.findByNameIs(product), amount));
                        continue;
                    }
                    sales.add(new Sale(date, c, p, amount));
                }
            }
        }
        workbook.close();
        test(sales);
        return sales;
    }

    private int getRowNum (Sheet sheet) {
        Sale lastAddedSale = saleRepository.findTopByOrderByIdDesc();
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

    private void test (Set<Sale> sales) throws IOException {
        File file = new File("src/main/resources/test.txt");
        for (Sale s : sales) {
            FileUtils.write(file, s.toString() + "\n", Charset.defaultCharset() ,true);
        }
    }
}
