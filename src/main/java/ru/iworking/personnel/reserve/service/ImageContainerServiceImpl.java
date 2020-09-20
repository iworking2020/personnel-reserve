package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.entity.ImageContainer;
import ru.iworking.personnel.reserve.repository.ImageContainerRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ImageContainerServiceImpl implements ImageContainerService {

    private final ImageContainerRepository imageContainerRepository;

    @Override
    public ImageContainer findById(Long id) {
        return imageContainerRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void create(ImageContainer imageContainer) {
        imageContainerRepository.save(imageContainer);
    }
}
