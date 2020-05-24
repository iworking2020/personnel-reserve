package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.annotation.GuiceComponent;
import ru.iworking.personnel.reserve.dao.PhotoDao;
import ru.iworking.personnel.reserve.entity.Photo;

@GuiceComponent
public class PhotoService extends DaoService<Photo, Long> {
    @Override
    public PhotoDao getDao() {
        return PhotoDao.INSTANCE;
    }
}
