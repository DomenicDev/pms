package de.hfu.pms;

import de.hfu.pms.dao.DoctoralStudentDao;
import de.hfu.pms.dao.UniversityDao;
import de.hfu.pms.dao.UserDao;
import de.hfu.pms.model.*;
import de.hfu.pms.shared.enums.FacultyHFU;
import de.hfu.pms.shared.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        User testAdmin = new User("admin", passwordEncoder.encode("1234"),"test@example.com","Bob","Tester");
        testAdmin.setRole(UserRole.ADMIN);
        userDao.save(testAdmin);

        // add simple doctoral student
        DoctoralStudent student = new DoctoralStudent();

        PersonalData personalData = new PersonalData();
        personalData.setForename("Max");
        personalData.setLastName("Mustermann");
        personalData.setGender(Gender.Male);
        personalData.setNationality("German");
        student.setPersonalData(personalData);

        student.getTargetGraduation().setFacultyHFU(FacultyHFU.Informatik);
        student.getQualifiedGraduation().setGrade("1.3");
        personalData.getAddress().setCountry("Germany");
        personalData.setEmail("test@email.de");


        doctoralStudentDao.save(student);

        // add test university
        University university = new University();
        university.setName("HFU");
        university.setLocation("Furtwangen");
        university.setCountry("Deutschland");
        universityDao.save(university);
    }
}
