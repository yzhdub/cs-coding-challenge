package com.cs.coding.config;

import javax.sql.DataSource;

import com.cs.coding.exception.ResourceNotFoundException;
import com.cs.coding.exception.ResourceNotSpecifiedException;
import com.cs.coding.listener.JobCompletionNotificationListener;
import com.cs.coding.reader.ServerLogJsonLineMapper;
import com.cs.coding.processor.ServerLogProcessor;
import com.cs.coding.model.EventDetail;
import com.cs.coding.model.ServerLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;

@Slf4j
@Configuration
@EnableBatchProcessing
public class CSBatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public ItemStreamReader<ServerLog> serverLogReader(@Value("#{jobParameters[filepath]}") String path) {
        log.info("Starting ServerLogReader");
        if (path == null || path.length() == 0) {
            throw new ResourceNotSpecifiedException("File resource not specified. Please specify with filepath=<full_path>");
        }
        InputStream inputstream = null;
        try {
            inputstream = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("File resource not available");
        }
        FileSystemResource file = new FileSystemResource(path);
        if (!file.exists() || !file.isFile()) {
            throw new ResourceNotFoundException("File resource not exist at " + path);
        }
        FlatFileItemReader<ServerLog> reader = new FlatFileItemReader<>();

        reader.setName("serverLogReader");

        reader.setResource(new InputStreamResource(inputstream));

        ServerLogJsonLineMapper lineMapper = new ServerLogJsonLineMapper();
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public ServerLogJsonLineMapper lineMapper() {
        return new ServerLogJsonLineMapper();
    }

    @Bean
    public ServerLogProcessor serverLogProcessor() {
        log.info("Starting ServerLogProcessor");
        return new ServerLogProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<EventDetail> eventDetailWriter(DataSource dataSource) {
        log.info("Starting EventDetailWriter");
        return new JdbcBatchItemWriterBuilder<EventDetail>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO EVENTDETAIL (id, duration, type, host, alert) VALUES (:id, :duration, :type, :host, :alert)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job parseServerLogJob(JobCompletionNotificationListener listener, Step serverLogParsingStep) {
        log.info("Starting ServerLogParsingJob");
        return jobBuilderFactory.get("parseServerLogJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(serverLogParsingStep)
                .end()
                .build();
    }


    @Bean
    public Step serverLogParsingStep(JdbcBatchItemWriter<EventDetail> eventDetailWriter) {
        log.info("Starting ServerLogParsingStep");
        return stepBuilderFactory.get("serverLogParsingStep")
                .<ServerLog, EventDetail> chunk(100)
                .reader(serverLogReader(null))
                .processor(serverLogProcessor())
                .writer(eventDetailWriter)
                .build();
    }


}