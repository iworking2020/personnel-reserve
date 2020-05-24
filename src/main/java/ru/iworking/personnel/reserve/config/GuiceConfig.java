package ru.iworking.personnel.reserve.config;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceConfig {

    public static volatile Injector INJECTOR = Guice.createInjector(new GuiceModule());

}
