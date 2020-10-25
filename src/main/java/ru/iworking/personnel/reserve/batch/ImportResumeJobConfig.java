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
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.repository.ResumeRepository;

@Configuration
@RequiredArgsConstructor
public class ImportResumeJobConfig {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;

    private final JobCompletionListener jobCompletionListener;

    private final ResumeRepository resumeRepository;

    @Bean
    public Job importResumeJob(Step stepImportResume) {
        return jobBuilderFactory.get("importResumeJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobCompletionListener)
                .start(stepImportResume)
                .build();
    }

    @Bean
    public Step stepImportResume(ItemReader<Resume> jsonResumeItemReader, ItemWriter<Resume> resumeToJpaItemWriter) {
        return stepBuilderFactory.get("stepExportResume")
                .<Resume, Resume> chunk(1)
                .reader(jsonResumeItemReader)
                .writer(resumeToJpaItemWriter)
                .build();
    }

    @Bean
    public JsonItemReader<Resume> jsonResumeItemReader(Resource inputFile) {
        return new JsonItemReaderBuilder<Resume>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Resume.class))
                .resource(inputFile)
                .name("resumeJsonItemReader")
                .build();
    }

    @Bean
    public ItemWriter<Resume> resumeToJpaItemWriter() {
        RepositoryItemWriter<Resume> writer = new RepositoryItemWriter<>();
        writer.setRepository(resumeRepository);
        writer.setMethodName("save");
        return writer;
    }

}
