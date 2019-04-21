package de.hfu.pms;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.model.DoctoralStudent;
import de.hfu.pms.model.PersonalData;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DoctoralStudentDao doctoralStudentDao;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {

        System.out.println("Hallo Spring!");


        // add a test student, save it and read it again
        DoctoralStudent testDoc = new DoctoralStudent();
        testDoc.getPersonalData().setForename("Max");
        testDoc.getPersonalData().setLastName("Mustermann");

        // store entity to database
        doctoralStudentDao.save(testDoc);

        // we search all students and print out their id and names
        for (DoctoralStudent savedDoc : doctoralStudentDao.findAll()) {
            PersonalData pd = savedDoc.getPersonalData();
            System.out.println(savedDoc.getId() + ": " + pd.getForename() + " " + pd.getLastName());
        }

    }

}
