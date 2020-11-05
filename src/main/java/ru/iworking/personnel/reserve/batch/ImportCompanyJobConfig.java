package ru.iworking.personnel.reserve.batch;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.repository.CompanyRepository;

@Configuration
@RequiredArgsConstructor
public class ImportCompanyJobConfig {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;

    private final JobCompletionListener jobCompletionListener;
    private final CompanyJobListener companyJobListener;

    private final CompanyRepository companyRepository;

    @Bean
    public Job importCompanyJob(Step stepImportCompany) {
        return jobBuilderFactory.get("importCompanyJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobCompletionListener)
                .listener(companyJobListener)
                .start(stepImportCompany)
                .build();
    }

    @Bean
    public Step stepImportCompany(ItemReader<Company> jsonCompanyItemReader, ItemWriter<Company> companyToJpaItemWriter) {
        return stepBuilderFactory.get("stepExportCompany")
                .<Company, Company> chunk(1)
                .reader(jsonCompanyItemReader)
                .writer(companyToJpaItemWriter)
                .build();
    }

    @Bean
    public JsonItemReader<Company> jsonCompanyItemReader(Resource inputFile) {
        return new JsonItemReaderBuilder<Company>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Company.class))
                .resource(inputFile)
                .name("companyJsonItemReader")
                .build();
    }

    @Bean
    public ItemWriter<Company> companyToJpaItemWriter() {
        RepositoryItemWriter<Company> writer = new RepositoryItemWriter<>();
        writer.setRepository(companyRepository);
        writer.setMethodName("save");
        return writer;
    }

}
