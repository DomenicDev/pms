package de.hfu.pms;

import de.hfu.pms.dao.UserDao;
import de.hfu.pms.model.Faculty;
import de.hfu.pms.service.FacultyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FacultyUnitTest {



    @Autowired
    private FacultyService facultyService;

    @Autowired
    private UserDao userDao;




    @Test
    public void checkCreateUniversity(){


        Faculty faculty = new Faculty();

        faculty.setFacultyName("Digitale Medien");

        facultyService.addFaculty(faculty);

        Collection<Faculty> list = facultyService.getAll();
        Boolean isFound = false;
        for(Faculty f : list){
            System.err.println(f.getFacultyName());
            if (f.getFacultyName().equals("Digitale Medien")) {
                facultyService.removeFaculty(f.getId());
                isFound = true;
            }
        }
        assertTrue(isFound);
    }
    @Test
    public void checkDeleteUniversity(){


        Faculty faculty = new Faculty();

        faculty.setFacultyName("Digitale Medien");

        facultyService.addFaculty(faculty);

        facultyService.removeFaculty(faculty.getId());
    }

    @Test
    public void checkUpdateUniversity(){

        Faculty faculty = new Faculty();

        faculty.setFacultyName("Infomate");

        facultyService.addFaculty(faculty);

        Faculty updateFaculty = new Faculty();

        updateFaculty.setFacultyName("Digitale Medien");

        facultyService.editFaculty(faculty.getId(),updateFaculty);

        Collection<Faculty> list = facultyService.getAll();
        for(Faculty f : list){
            System.err.println(f.getFacultyName());
            if (f.getFacultyName().equals("Infomate"))
                assert false;
        }

        facultyService.removeFaculty(faculty.getId());

    }

    @Test
    public void checkListUniversity(){

        Faculty faculty1 = new Faculty();
        faculty1.setFacultyName("Informatik");
        facultyService.addFaculty(faculty1);

        Faculty faculty2 = new Faculty();
        faculty2.setFacultyName("Digitale Medien");
        facultyService.addFaculty(faculty2);

        Faculty faculty3 = new Faculty();
        faculty3.setFacultyName("Wirtschaft");
        facultyService.addFaculty(faculty3);

        Collection<Faculty> list = facultyService.getAll();
        //3 + 2 für die vor generierte Facultaeten?
        //TODO: Discuss this test
        assertEquals(4,list.size());


    }

}
