package de.hfu.pms.shared.dto;

public class AnonymizeResultDTO {

    private Long deletedId;

    private DoctoralStudentDTO newDoctoralStudent;

    public AnonymizeResultDTO() {
    }

    public AnonymizeResultDTO(Long deletedId, DoctoralStudentDTO newDoctoralStudent) {
        this.deletedId = deletedId;
        this.newDoctoralStudent = newDoctoralStudent;
    }

    public Long getDeletedId() {
        return deletedId;
    }

    public void setDeletedId(Long deletedId) {
        this.deletedId = deletedId;
    }

    public DoctoralStudentDTO getNewDoctoralStudent() {
        return newDoctoralStudent;
    }

    public void setNewDoctoralStudent(DoctoralStudentDTO newDoctoralStudent) {
        this.newDoctoralStudent = newDoctoralStudent;
    }
}
