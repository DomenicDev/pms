package de.hfu.pms;

import de.hfu.pms.shared.dto.DoctoralStudentDTO;

public interface ApplicationServices {

    void addDoctoralStudent(DoctoralStudentDTO student);

    void login(String username, String password);



    // Todo add all other needed methods

}
