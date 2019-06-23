package de.hfu.pms.service;

import de.hfu.pms.model.Photo;

public interface PhotoService {

    Photo getPhotoById(Long id);

    void updatePhoto(Long id, Photo photo);

    void deletePhoto(Long id);

}
