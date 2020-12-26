package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.entity.ApplicationProperty;
import ru.iworking.personnel.reserve.repository.ApplicationPropertiesRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationPropertiesServiceImpl implements ApplicationPropertiesService {

    private final ApplicationPropertiesRepository applicationPropertiesRepository;

    @Override
    public Optional<ApplicationProperty> findById(Long id) {
        return applicationPropertiesRepository.findById(id);
    }

    @Override
    public Optional<ApplicationProperty> findFirstByName(String name) {
        List<ApplicationProperty> list = applicationPropertiesRepository.findAllByName(name);
        if (list.isEmpty()) return Optional.empty();
        return Optional.ofNullable(list.get(0));
    }

    @Override
    public ApplicationProperty update(ApplicationProperty applicationProperty) {
        return applicationPropertiesRepository.save(applicationProperty);
    }

    @Override
    public ApplicationProperty create(ApplicationProperty applicationProperty) {
        return applicationPropertiesRepository.save(applicationProperty);
    }
}
