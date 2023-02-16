/*
@Configuration
@EnableBatchProcessing
public class ApplicationBatchConfig {

    @Autowired
    private CustomerRepository repository;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;


    @Bean
    public FlatFileItemReader<Customer> itemReader() {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("/Users/javatechie/Downloads/study-docs/customers.csv"));
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public CustomerDataProcessor processor() {
        return new CustomerDataProcessor();
    }


    @Bean
    public RepositoryItemWriter<Customer> itemWriter() {
        RepositoryItemWriter<Customer> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(repository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public Step importCustomersStep() {
        return stepBuilderFactory.get("ImportCustomersStep").<Customer, Customer>chunk(10)
                .reader(itemReader())
                .processor(processor())
                .writer(itemWriter())
                .taskExecutor(taskExecutor())
                .build();

    }

    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("importCustomersJob")
                .flow(importCustomersStep())
                .end().build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor taskExecutor=new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }
}*/
