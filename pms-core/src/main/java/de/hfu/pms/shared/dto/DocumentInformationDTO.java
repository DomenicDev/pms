package de.hfu.pms.shared.dto;

public class DocumentInformationDTO {

    private Long id;

    private String filename;

    public DocumentInformationDTO() {
    }

    public DocumentInformationDTO(Long id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return filename;
    }
}
