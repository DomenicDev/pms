package de.hfu.pms.events;


import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;

import java.util.Collection;

/**
 * This event is used to show the doctoral students
 * which tend to exceed their promotion time.
 */
public class ShowAlertedDoctoralStudentsEvent {

    private Collection<PreviewDoctoralStudentDTO> alertedStudents;

    public ShowAlertedDoctoralStudentsEvent(Collection<PreviewDoctoralStudentDTO> alertedStudents) {
        this.alertedStudents = alertedStudents;
    }

    public Collection<PreviewDoctoralStudentDTO> getAlertedStudents() {
        return alertedStudents;
    }
}
