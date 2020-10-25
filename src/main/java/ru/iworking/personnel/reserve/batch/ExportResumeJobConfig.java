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
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.repository.ResumeRepository;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ExportResumeJobConfig {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;

    private final JobCompletionListener jobCompletionListener;

    private final ResumeRepository resumeRepository;

    @Bean
    public Job exportResumeJob(Step stepExportResume) {
        return jobBuilderFactory.get("exportResumeJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobCompletionListener)
                .start(stepExportResume)
                .build();
    }

    @Bean
    public Step stepExportResume(ItemReader<Resume> resumeReader, ItemWriter<Resume> resumeToJsonItemWriter) {
        return stepBuilderFactory.get("stepExportResume")
                .<Resume, Resume> chunk(1)
                .reader(resumeReader)
                .writer(resumeToJsonItemWriter)
                .build();
    }

    @Bean
    public ItemReader<Resume> resumeReader() {
        RepositoryItemReader<Resume> reader = new RepositoryItemReader<>();
        reader.setRepository(resumeRepository);
        reader.setMethodName("findAll");
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("id", Sort.Direction.ASC);
        reader.setSort(sort);
        return reader;
    }

    @Bean
    public ItemWriter<Resume> resumeToJsonItemWriter(Resource outputFile){
        return new JsonFileItemWriterBuilder<Resume>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(outputFile)
                .name("resumeJsonFileItemWriter")
                .build();
    }

}
