package com.grocery.ordering.listener;

import com.grocery.ordering.util.DatabaseManager;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("---------------------------------------------------------");
        System.out.println("WEB-APP STARTUP: Initializing database...");
        DatabaseManager.createTables();
        System.out.println("DATABASE INIT: Database initialization complete.");
        System.out.println("---------------------------------------------------------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Web application is shutting down.");
    }
}
