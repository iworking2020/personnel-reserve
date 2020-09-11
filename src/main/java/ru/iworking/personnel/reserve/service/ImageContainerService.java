package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.ImageContainerDao;
import ru.iworking.personnel.reserve.entity.ImageContainer;

@Service
public class ImageContainerService extends DaoService<ImageContainer, Long> {

    private final ImageContainerDao imageContainerDao;

    @Autowired
    public ImageContainerService(ImageContainerDao imageContainerDao) {
        this.imageContainerDao = imageContainerDao;
    }

    @Override
    public ImageContainerDao getDao() {
        return imageContainerDao;
    }
}
