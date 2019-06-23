package de.hfu.pms.shared.dto;

public class PhotoDTO {

    private String filename;

    private byte[] photo;

    public PhotoDTO() {
    }

    public PhotoDTO(String filename, byte[] photo) {
        this.filename = filename;
        this.photo = photo;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
