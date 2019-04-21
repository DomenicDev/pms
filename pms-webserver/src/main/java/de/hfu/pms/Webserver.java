package de.hfu.pms;

import de.hfu.pms.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Webserver {

    public static void main(String[] args) {


        DoctoralStudent student = new DoctoralStudent();
   //     student.setForename("Domenic");
   //     student.setLastName("Cassisi");

        SessionFactory factory = buildSessionFactory();

        Session session = factory.openSession();

        session.save(student);

        DoctoralStudent domenic = session.get(DoctoralStudent.class, 1);
     //   System.out.println(domenic.getForename() +" " + domenic.getLastName());

        session.close();
        factory.close();

    }

    private static SessionFactory buildSessionFactory() {
        return new Configuration()
                .configure()
                .addAnnotatedClass(DoctoralStudent.class)
                .addAnnotatedClass(University.class)
                .addAnnotatedClass(TravelCostConference.class)
                .addAnnotatedClass(TravelCostUniversity.class)
                .addAnnotatedClass(VisitedQualification.class)
                .addAnnotatedClass(ConsultingSupport.class)
                .buildSessionFactory();
    }

}
