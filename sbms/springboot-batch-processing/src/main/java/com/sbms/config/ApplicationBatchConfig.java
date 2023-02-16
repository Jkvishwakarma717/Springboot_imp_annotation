package com.sbms.config;

import com.sbms.entity.Employee;
import com.sbms.repository.EmployeeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.Serializable;

@Configuration
@EnableBatchProcessing
public class ApplicationBatchConfig  {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public FlatFileItemReader<Employee> itemReader() {
        FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("C:/Users/jeete/Downloads/employee.csv"));
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());

        return itemReader;
    }

    @Bean
    public EmployeeDataProcessor itemProcessor() {
        return new EmployeeDataProcessor();
    }

    @Bean
    public RepositoryItemWriter<Employee> itemWriter() {
        RepositoryItemWriter<Employee> itemWriter = new RepositoryItemWriter<>();
        //  repository.save("")
        itemWriter.setRepository(repository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }
    @SuppressWarnings("unchecked")
    @Bean
    public Step createEmployeeStep() {
        //any name you can pass inside get() method
        return stepBuilderFactory.get("createEmployeeStep").<Employee, Employee>chunk(10000)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("createEmployeeJob")
                .flow(createEmployeeStep())
                //there is defined single step, if you want multiple step then you can use next and pass the next step
                .end().build();
    }
    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor taskExecutor=new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }

    private LineMapper<Employee> lineMapper() {
        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("date","appAccountId","appId","appVersion","appChannel","campaignChannelId","model",
                "brand","network","mcc","mnc","country","city","state","language","campaignId","userIsFraud","publisher");
        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employee.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

}
