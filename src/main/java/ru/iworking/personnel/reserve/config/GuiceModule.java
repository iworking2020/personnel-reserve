package ru.iworking.personnel.reserve.config;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import ru.iworking.personnel.reserve.service.CurrencyService;
import ru.iworking.personnel.reserve.service.DaoService;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DaoService.class)
                .annotatedWith(Names.named("CurrencyService"))
                .to(CurrencyService.class);
    }

}
