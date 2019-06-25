package de.hfu.pms;

import de.hfu.pms.dao.UserDao;
import de.hfu.pms.exceptions.UniversityNotFoundException;
import de.hfu.pms.exceptions.UserNotFoundException;
import de.hfu.pms.model.University;
import de.hfu.pms.service.UniversityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UniversityUnitTest {



    @Autowired
    private UniversityService universityService;

    @Autowired
    private UserDao userDao;




    @Test
    public void checkCreateUniversity(){

        University university = new University();

        university.setName("Hochschule Furtwangen University");
        university.setLocation("Furtwangen");
        university.setCountry("Deutschland");
        university.setAbbreviation("HFU");
        university.setContact("Herr Fitzon");

        universityService.createUniversity(university);

        University saved = universityService.getUniversity(university.getId());

        assertEquals(saved.getName(),"Hochschule Furtwangen University");
        assertEquals(saved.getLocation(),"Furtwangen");
        assertEquals(saved.getCountry(),"Deutschland");
        assertEquals(saved.getAbbreviation(),"HFU");
        assertEquals(saved.getContact(),"Herr Fitzon");

    }
    @Test
    public void checkDeleteUniversity(){

        University university = new University();

        university.setName("Hochschule Furtwangen University");
        university.setLocation("Furtwangen");
        university.setCountry("Deutschland");
        university.setAbbreviation("HFU");
        university.setContact("Herr Fitzon");

        universityService.createUniversity(university);

        universityService.deleteUniversity(university.getId());
        assertThatThrownBy(() -> universityService.getUniversity( university.getId())).isInstanceOf(UniversityNotFoundException.class);
    }

    @Test
    public void checkUpdateUniversity(){

        University university = new University();

        university.setName("Hochschule Furtwangen");
        university.setLocation("Freiburg");
        university.setCountry("England");
        university.setAbbreviation("HFOO");
        university.setContact("Herr X");

        universityService.createUniversity(university);

        Long id = university.getId();

        University updatedUniversity = new University();

        updatedUniversity.setName("Hochschule Furtwangen University");
        updatedUniversity.setLocation("Furtwangen");
        updatedUniversity.setCountry("Deutschland");
        updatedUniversity.setAbbreviation("HFU");
        updatedUniversity.setContact("Herr Fitzon");

        universityService.updateUniversity(id,updatedUniversity);

        University savedUniversity = universityService.getUniversity(id);

        assertEquals(savedUniversity.getName(),updatedUniversity.getName());
        assertEquals(savedUniversity.getLocation(),updatedUniversity.getLocation());
        assertEquals(savedUniversity.getCountry(),updatedUniversity.getCountry());
        assertEquals(savedUniversity.getAbbreviation(),updatedUniversity.getAbbreviation());
        assertEquals(savedUniversity.getContact(),updatedUniversity.getContact());



    }

    @Test
    public void checkListUniversity(){

        University university1 = new University();
        university1.setName("Hochschule Furtwangen University");
        university1.setLocation("Furtwangen");
        university1.setCountry("Deutschland");
        university1.setAbbreviation("HFU");
        university1.setContact("Herr Fitzon");

        University university2 = new University();
        university2.setName("Hochschule Furtwangen University");
        university2.setLocation("Villingen");
        university2.setCountry("Deutschland");
        university2.setAbbreviation("HFU");
        university2.setContact("Herr Mustermann");

        University university3 = new University();
        university3.setName("Hochschule Furtwangen University");
        university3.setLocation("Tuttlingen");
        university3.setCountry("Deutschland");
        university3.setAbbreviation("HFU");
        university3.setContact("Frau Müller");

        universityService.createUniversity(university1);
        universityService.createUniversity(university2);
        universityService.createUniversity(university3);

        List<University> list = universityService.getUniversityList();
        //3 + 1 für die vor generierte HFU
        //TODO: Discuss this test
        assertEquals(4,list.size());


    }

}
