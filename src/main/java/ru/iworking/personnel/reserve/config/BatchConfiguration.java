package ru.iworking.personnel.reserve.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    @StepScope
    public Resource outputFile(@Value("#{jobParameters[outputFileUrl]}") String pathToFile) {
        return new FileSystemResource(pathToFile);
    }

    @Bean
    @StepScope
    public Resource inputFile(@Value("#{jobParameters[inputFileUrl]}") String pathToFile) {
        return new FileSystemResource(pathToFile);
    }

}
