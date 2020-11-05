package ru.iworking.personnel.reserve.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.repository.CompanyRepository;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ExportCompanyJobConfig {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;

    private final JobCompletionListener jobCompletionListener;

    private final CompanyRepository companyRepository;

    @Bean
    public Job exportCompanyJob(Step stepExportCompany) {
        return jobBuilderFactory.get("exportCompanyJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobCompletionListener)
                .start(stepExportCompany)
                .build();
    }

    @Bean
    public Step stepExportCompany(ItemReader<Company> companyReader, ItemWriter<Company> companyToJsonItemWriter) {
        return stepBuilderFactory.get("stepExportCompany")
                .<Company, Company> chunk(1)
                .reader(companyReader)
                .writer(companyToJsonItemWriter)
                .build();
    }

    @Bean
    public ItemReader<Company> companyReader() {
        RepositoryItemReader<Company> reader = new RepositoryItemReader<>();
        reader.setRepository(companyRepository);
        reader.setMethodName("findAll");
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("id", Sort.Direction.ASC);
        reader.setSort(sort);
        return reader;
    }

    @Bean
    public ItemWriter<Company> companyToJsonItemWriter(Resource outputFile){
        return new JsonFileItemWriterBuilder<Company>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(outputFile)
                .name("companyJsonFileItemWriter")
                .build();
    }

}
