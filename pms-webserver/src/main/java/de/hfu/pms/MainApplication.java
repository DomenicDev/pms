package de.hfu.pms;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.model.DoctoralStudent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Currently running the main method is enough to
 * start a Tomcat and deploy the app on the servlet container.
 * So at the moment there is no need to deploy the war manually on a Tomcat.
 */
@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    @Autowired
    DoctoralStudentDao doctoralStudentDao;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {

        System.out.println("Hallo Spring!");

    }

    /*
    @Bean
    public CommandLineRunner demo(SessionFactory sessionFactory) {
        return args -> {
            DoctoralStudent doc1 = new DoctoralStudent();
            doc1.getPersonalData().setForename("Domenic");

            Session session = sessionFactory.openSession();
            session.save(doc1);
            session.close();

        };
    }
    */
}
