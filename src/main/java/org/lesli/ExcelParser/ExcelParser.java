package org.lesli.ExcelParser;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lesli.ExcelParser.model.Company;
import org.lesli.ExcelParser.model.Product;
import org.lesli.ExcelParser.model.Sale;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelParser {

    private static final File tFile = new File("src/main/resources/test.txt");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final Set<Sale> sales = new HashSet<>();
    private static final List<Company> companies = new ArrayList<>();
    private static final List<Product> products = new ArrayList<>();

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InvalidFormatException {
        Iterator<File> it = FileUtils.iterateFiles(new File("src/main/resources/"), new String[]{"xlsx"}, false);
        while (it.hasNext()) {
            sales.addAll(ultimateParser(it.next()));
        }
        System.out.println("Number of companies: " + companies.size());
        System.out.println("Number of products: " + products.size());
        System.out.println("Total number of sales: " + sales.size());
        DataBase.storeData(sales);
    }

    public static Set<Sale> ultimateParser (File file) throws IOException, InvalidFormatException {
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
    public static void testLine (String value) throws IOException {
        FileUtils.writeStringToFile(tFile, value, Charset.defaultCharset(), true);
    }
    public static void testCompanies () throws IOException {
        //System.out.println(companies.size());
        /*for (String c : companies) {
            FileUtils.writeStringToFile(tFile, c + "\n", Charset.defaultCharset(), true);
        }*/
    }
    public static void testProducts (Set<String> products) throws IOException {
        for (String p : products) {
            FileUtils.writeStringToFile(tFile, p + "\n", Charset.defaultCharset(), true);
        }
    }
    public static void testSales (List<Sales> sales) throws IOException {
        for (Sales sale: sales) {
            FileUtils.writeStringToFile(tFile, sale.toString() + "\n", Charset.defaultCharset(), true);
        }
    }
}
