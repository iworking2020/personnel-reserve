package ru.iworking.personnel.reserve.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.repository.ResumeRepository;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ResumeToCsvFileJobConfig {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;

    private final JobCompletionListener jobCompletionListener;

    private final ResumeRepository resumeRepository;

    @Bean
    public Job exportResumeJob(Step step1) {
        return jobBuilderFactory.get("exportResumeJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobCompletionListener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(ItemWriter<Resume> writer) {
        return stepBuilderFactory.get("step1")
                .<Resume, Resume> chunk(1)
                .reader(reader())
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<Resume> reader() {
        RepositoryItemReader<Resume> reader = new RepositoryItemReader<>();
        reader.setRepository(resumeRepository);
        reader.setMethodName("findAll");
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("id", Sort.Direction.ASC);
        reader.setSort(sort);
        return reader;
    }

    @Bean
    @StepScope
    public Resource outputFile(@Value("#{jobParameters[outputFileUrl]}") String pathToFile) {
        return new FileSystemResource(pathToFile);
    }

    @Bean
    public ItemWriter<Resume> resumeToCsvItemWriter(Resource outputFile){
        FlatFileItemWriter<Resume> csvFileWriter = new FlatFileItemWriter<>();

        String exportFileHeader = "id;profession;email";
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        csvFileWriter.setHeaderCallback(headerWriter);

        csvFileWriter.setAppendAllowed(false);

        csvFileWriter.setResource(outputFile);

        LineAggregator<Resume> lineAggregator = createResumeLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    private LineAggregator<Resume> createResumeLineAggregator() {
        DelimitedLineAggregator<Resume> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<Resume> fieldExtractor = createResumeFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<Resume> createResumeFieldExtractor() {
        BeanWrapperFieldExtractor<Resume> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"id", "profession", "email"});
        return extractor;
    }

}
