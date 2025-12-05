package com.example.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * کلاس اصلی Spring Boot Application
 * نکته: @SpringBootApplication شامل @Configuration, @EnableAutoConfiguration, @ComponentScan می‌شود
 */
@SpringBootApplication
public class BookstoreApplication {

    // متد main - نقطه شروع برنامه جاوا
    public static void main(String[] args) {
        // انواع متغیرها در جاوا:

        // primitive types
        int port = 8080;                    // 32-bit integer
        double version = 1.0;               // 64-bit floating point
        boolean isRunning = true;           // boolean (true/false)
        char grade = 'A';                   // single character

        // reference types
        String appName = "Bookstore API";   // String object
        Integer maxBooks = 1000;            // Integer object (wrapper class)

        // array
        String[] features = {"REST API", "CRUD Operations", "Database Integration"};

        System.out.println("🚀 Starting " + appName + " on port " + port);
        System.out.println("📚 Version: " + version);
        System.out.println("✅ Application is running: " + isRunning);

        // حلقه for - از ساختارهای کنترل پایه جاوا
        for (String feature : features) {
            System.out.println("✨ Feature: " + feature);
        }

        SpringApplication.run(BookstoreApplication.class, args);
    }
}