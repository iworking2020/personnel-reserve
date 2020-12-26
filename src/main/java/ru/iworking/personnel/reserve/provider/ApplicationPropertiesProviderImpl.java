package ru.iworking.personnel.reserve.provider;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.ApplicationPropertiesProvider;
import ru.iworking.personnel.reserve.entity.ApplicationProperty;
import ru.iworking.personnel.reserve.service.ApplicationPropertiesService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplicationPropertiesProviderImpl implements ApplicationPropertiesProvider {

    private static final Logger logger = LogManager.getLogger(ApplicationPropertiesProviderImpl.class);

    private final ApplicationPropertiesService applicationPropertiesService;

    @Override
    public String getValueByName(String s) {
        Optional<ApplicationProperty> applicationProperty = applicationPropertiesService.findFirstByName(s);
        return applicationProperty.map(ApplicationProperty::getValue).orElse(null);
    }

    @Override
    public void saveValue(String name, String value) {
        Optional<ApplicationProperty> applicationPropertyOptional = applicationPropertiesService.findFirstByName(name);
        if (applicationPropertyOptional.isPresent()) {
            ApplicationProperty applicationProperty = applicationPropertyOptional.get();
            applicationProperty.setValue(value);
            applicationPropertiesService.update(applicationProperty);
        } else {
            ApplicationProperty applicationProperty = new ApplicationProperty();
            applicationProperty.setName(name);
            applicationProperty.setValue(value);
            applicationPropertiesService.create(applicationProperty);
        }
    }

}
