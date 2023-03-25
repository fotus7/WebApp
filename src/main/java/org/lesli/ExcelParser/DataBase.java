package org.lesli.ExcelParser;

import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.lesli.ExcelParser.model.Sale;

import java.util.Set;

public class DataBase {
    public static void storeData(Set<Sale> sales) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        for (Sale s : sales) {
            session.persist(s);
        }
        t.commit();
        session.close();
        System.out.println("Done");
    }

}
