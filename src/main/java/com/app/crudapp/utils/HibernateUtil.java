package com.app.crudapp.utils;


import com.app.crudapp.model.Event;
import com.app.crudapp.model.Media;
import com.app.crudapp.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.net.URI;


public class HibernateUtil {
    private static final SessionFactory sessionFactory = createSessionFactory();

    private HibernateUtil() {}

    public static SessionFactory createSessionFactory() {
        try {
            //URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
            //String username = dbUri.getUserInfo().split(":")[0];
            //String password = dbUri.getUserInfo().split(":")[1];
            //String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
            String dbUrl = "jdbc:mysql://localhost:3308/crudapp";
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", dbUrl);
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "root");
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Media.class);
            configuration.addAnnotatedClass(Event.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
