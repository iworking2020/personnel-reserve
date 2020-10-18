package ru.iworking.personnel.reserve.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfiguration { }
