package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.entity.ImageContainer;

public interface ImageContainerService {
    ImageContainer findById(Long id);

    void create(ImageContainer imageContainer);
}
