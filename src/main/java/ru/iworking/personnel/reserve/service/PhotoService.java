package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.PhotoDao;
import ru.iworking.personnel.reserve.entity.Photo;

@Service
public class PhotoService extends DaoService<Photo, Long> {

    private final PhotoDao photoDao;

    @Autowired
    public PhotoService(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    @Override
    public PhotoDao getDao() {
        return photoDao;
    }
}
