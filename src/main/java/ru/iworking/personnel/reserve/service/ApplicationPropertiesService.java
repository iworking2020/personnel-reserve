package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.entity.ApplicationProperty;

import java.util.Optional;

public interface ApplicationPropertiesService {

    Optional<ApplicationProperty> findById(Long id);
    Optional<ApplicationProperty> findFirstByName(String name);

    ApplicationProperty update(ApplicationProperty applicationProperty);
    ApplicationProperty create(ApplicationProperty applicationProperty);

}
