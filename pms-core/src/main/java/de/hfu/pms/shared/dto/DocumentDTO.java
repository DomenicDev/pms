package de.hfu.pms.shared.dto;

public class DocumentDTO {

    private Long id;

    private String filename;

    private byte[] data;

    public DocumentDTO() {
    }

    public DocumentDTO(Long id, String filename, byte[] data) {
        this.id = id;
        this.filename = filename;
        this.data = data;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
