package de.hfu.pms.shared.dto;

public class AnonymizeResultDTO {

    private Long deletedId;

    private PreviewDoctoralStudentDTO newDoctoralStudent;

    public AnonymizeResultDTO() {

    }

    public AnonymizeResultDTO(Long deletedId, PreviewDoctoralStudentDTO newDoctoralStudent) {
        this.deletedId = deletedId;
        this.newDoctoralStudent = newDoctoralStudent;
    }

    public Long getDeletedId() {
        return deletedId;
    }


    public PreviewDoctoralStudentDTO getNewDoctoralStudent() {
        return newDoctoralStudent;
    }

}
