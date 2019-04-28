package de.hfu.pms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Currently running the main method is enough to
 * start a Tomcat and deploy the app on the servlet container.
 * So at the moment there is no need to deploy the war manually on a Tomcat.
 */
@SpringBootApplication
public class MainApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }


    @Override
    public void run(String... args) {
        System.out.println("Hallo Spring!");
    }

}
