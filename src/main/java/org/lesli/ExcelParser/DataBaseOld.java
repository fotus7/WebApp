package org.lesli.ExcelParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class DataBaseOld {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "test";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/sales";
    private static final Connection connection;
    private static final Statement statement;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new RuntimeException();
        }
    }
    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new RuntimeException();
        }
    }
    public static void addClients (TreeSet<String> clients) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        for (String c : clients) {
            statement.executeUpdate("INSERT INTO clients (name) VALUE ('" + c + "')");
        }
        connection.close();
    }
    public static void addProducts (HashMap<String, String> products) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        statement.executeUpdate(
                "ALTER TABLE `sales`.`products` \n" +
                    "ADD COLUMN `typename` VARCHAR(45) NOT NULL AFTER `name`");
        for (String key : products.keySet()) {
            statement.executeUpdate("INSERT INTO products (name, typename) " +
                                        "VALUE (" + "'" + key + "'" + ", " + "'" + products.get(key) + "'" + ")");
        }
        statement.executeUpdate(
                "UPDATE products \n" +
                    "SET type = \n" +
                    "(SELECT id\n" +
                    "FROM product_type\n" +
                    "WHERE typename = product_type.type)");
        statement.executeUpdate(
                "ALTER TABLE products \n" +
                    "DROP COLUMN typename");
        connection.close();
    }
    public static void addSales (ArrayList<Sales> sales) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        for (Sales sale : sales) {
            statement.executeUpdate(
                    "INSERT INTO shipments VALUES ('" + sale.getDate() + "', \n" +
                            "(SELECT id \n" +
                            "FROM clients c\n" +
                            "WHERE c.name = '" + sale.getCompany() +"'), \n" +
                            "(SELECT id \n" +
                            "FROM products p\n" +
                            "WHERE p.name = '" + sale.getProduct() + "'),"  + sale.getAmount() +");");
        }
        connection.close();
    }
}
