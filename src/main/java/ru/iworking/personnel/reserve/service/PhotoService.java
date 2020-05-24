package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.dao.PhotoDao;
import ru.iworking.personnel.reserve.entity.Photo;

public class PhotoService extends DaoService<Photo, Long> {

    public static final PhotoService INSTANCE = new PhotoService();

    private PhotoService() {}

    @Override
    public PhotoDao getDao() {
        return PhotoDao.INSTANCE;
    }
}
