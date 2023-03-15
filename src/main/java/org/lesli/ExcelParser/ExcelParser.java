package org.lesli.ExcelParser;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lesli.ExcelParser.model.Sales;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelParser {

    private static final File tFile = new File("src/main/resources/test.txt");

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final TreeSet<String> clients = new TreeSet<>();
    private static final HashMap<String, String> products = new HashMap<>();
    private static final ArrayList<Sales> sales = new ArrayList<>();

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InvalidFormatException {
        Iterator<File> it = FileUtils.iterateFiles(new File("src/main/resources/"), new String[]{"xlsx"}, false);
        while (it.hasNext()) {
            File file = new File(String.valueOf(it.next()));
            //clientsParser(file);
            //productsParser(file);
            salesParser(file);
        }
        testSales(sales);
        //DataBase.addClients(clients);
        //DataBase.addProducts(products);
        //DataBase.addSales(sales);
    }
    public static void clientsParser (File file) throws IOException, InvalidFormatException {
        TreeSet<String> clients = new TreeSet<>();
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        String value = "";
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            value = sheet.getRow(i).getCell(1).getStringCellValue();
            if (!value.equals("")) clients.add(value);
            else break;
        }
        ExcelParser.clients.addAll(clients);
        workbook.close();
    }
    public static void productsParser (File file) throws IOException, InvalidFormatException {
        HashMap<String, String> products = new HashMap<>();
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(1);
        int counter = 0;
        String value = "";
        for (int i = 3; i < sheet.getLastRowNum(); i++) {
            value = sheet.getRow(i).getCell(0).getStringCellValue();
            if (value.equals("")) break;
            if ((value.equals("итого"))) {
                counter++;
                continue;
            }
            switch (counter) {
                case 0:
                    products.put(value, "Ароматизатор");
                    break;
                case 1:
                    products.put(value, "Краситель");
                    break;
                case 2:
                    products.put(value, "Подсластитель");
                    break;
            }
        }
        ExcelParser.products.putAll(products);
        workbook.close();
    }
    public static void salesParser (File file) throws IOException, InvalidFormatException {
        List<Sales> sales = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        String date;
        String company;
        String product;
        double amount;
        for (int j = 1; !sheet.getRow(j).getCell(0).getCellType().toString().equals("STRING"); j++) {
            date = dateFormat.format(sheet.getRow(j).getCell(0).getDateCellValue());
            company = sheet.getRow(j).getCell(1).getStringCellValue();
            for (int i = 2; !sheet.getRow(0).getCell(i).getStringCellValue().equals("Итого"); i++) {
                amount = sheet.getRow(j).getCell(i).getNumericCellValue();
                if (amount != 0) {
                    product = sheet.getRow(0).getCell(i).getStringCellValue();
                    sales.add(new Sales(date, company, product, amount));
                }
            }
        }
        ExcelParser.sales.addAll(sales);
        workbook.close();
    }
    public static void testLine (String value) throws IOException {
        FileUtils.writeStringToFile(tFile, value, Charset.defaultCharset(), true);
    }
    public static void testClients (Set<String> clients) throws IOException {
        for (String c : clients) {
            FileUtils.writeStringToFile(tFile, c + "\n", Charset.defaultCharset(), true);
        }
    }
    public static void testProducts (HashMap<String, String> products) throws IOException {
        for (String key : products.keySet()) {
            FileUtils.writeStringToFile(tFile, key + ", " + products.get(key) + "\n", Charset.defaultCharset(), true);
        }
    }
    public static void testSales (List<Sales> sales) throws IOException {
        for (Sales sale: sales) {
            FileUtils.writeStringToFile(tFile, sale.toString() + "\n", Charset.defaultCharset(), true);
        }
    }
}
