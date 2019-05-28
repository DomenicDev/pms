package de.hfu.pms;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.dao.UniversityDao;
import de.hfu.pms.dao.UserDao;
import de.hfu.pms.model.UserRole;
import de.hfu.pms.model.*;
import de.hfu.pms.shared.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * This service adds some test (sample) entities to the database.
 */
@Service
public class DatabaseInit implements CommandLineRunner {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    private final DoctoralStudentDao doctoralStudentDao;
    private final UniversityDao universityDao;

    @Autowired
    public DatabaseInit(UserDao userDao, PasswordEncoder passwordEncoder, DoctoralStudentDao doctoralStudentDao, UniversityDao universityDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.doctoralStudentDao = doctoralStudentDao;
        this.universityDao = universityDao;
    }

    @Override
    public void run(String... args) {
        // add test user with admin rights
        User testAdmin = new User("admin", passwordEncoder.encode("1234"),"Bob","Baumeister","test@example.com");
        testAdmin.setRole(UserRole.ADMIN);
        userDao.save(testAdmin);

        // add test university
        University university = new University();
        university.setName("HFU");
        university.setLocation("Furtwangen");
        university.setCountry("Deutschland");
        university = universityDao.save(university);

        // add simple doctoral student
        DoctoralStudent student = new DoctoralStudent();

        PersonalData personalData = new PersonalData();
        personalData.setSalutation(Salutation.Mr);
        personalData.setForename("Max");
        personalData.setLastName("Mustermann");
        personalData.setGender(Gender.Male);
        personalData.setNationality("German");
        personalData.setFamilyStatus(FamilyStatus.Single);
        personalData.setNumberOfChildren(0);
        personalData.setEmail("max@mustermann.de");
        personalData.setTelephone("0174 456789");
        personalData.setDateOfBirth(LocalDate.of(1997, 12, 19));
        personalData.getAddress().setStreet("Musterstraße 1");
        personalData.getAddress().setLocation("Musterort");
        personalData.getAddress().setPlz("12345");
        personalData.getAddress().setCountry("Germany");
        student.setPersonalData(personalData);

        // qualified graduation
        student.getQualifiedGraduation().setGradedUniversity(university);
        student.getQualifiedGraduation().setGrade("1.3");
        student.getQualifiedGraduation().setGraduation(Graduation.Master_of_Science);
        student.getQualifiedGraduation().setSubjectArea("Thema meiner Wahl");

        // target graduation
        student.getTargetGraduation().setExternalUniversity(university);
        student.getTargetGraduation().setExternalSupervisor("Müller");
        student.getTargetGraduation().setFacultyHFU(FacultyHFU.Informatik);
        student.getTargetGraduation().setNameOfDissertation("Thema der Dissertation");
        student.getTargetGraduation().setTargetDegree(DoctoralGraduation.Dr_sc_nat.name());


        student.getQualifiedGraduation().setGradedUniversity(university);




        doctoralStudentDao.save(student);
    }
}
