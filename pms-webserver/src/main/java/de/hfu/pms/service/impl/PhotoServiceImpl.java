package de.hfu.pms.service.impl;

import de.hfu.pms.dao.PhotoDao;
import de.hfu.pms.model.Photo;
import de.hfu.pms.service.PhotoService;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoDao photoDao;

    public PhotoServiceImpl(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    @Override
    public Photo getPhotoById(Long id) {
        return photoDao.findById(id).orElseThrow();
    }

    @Override
    public void updatePhoto(Long id, Photo photo) {
        Photo p = getPhotoById(id);
        p.setFilename(photo.getFilename());
        p.setPhoto(photo.getPhoto());
        photoDao.save(p);
    }

    @Override
    public void deletePhoto(Long id) {
        photoDao.deleteById(id);
    }
}
