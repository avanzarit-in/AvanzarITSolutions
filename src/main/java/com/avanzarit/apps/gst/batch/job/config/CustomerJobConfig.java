package com.avanzarit.apps.gst.batch.job.config;

import com.avanzarit.apps.gst.batch.job.CustomArrayExtractorLineAggregator;
import com.avanzarit.apps.gst.batch.job.CustomExtractorLineAggregator;
import com.avanzarit.apps.gst.batch.job.customercontactpersonimport.CustomerContactPersonDataImportProcessor;
import com.avanzarit.apps.gst.batch.job.customercontactpersonimport.CustomerContactPersonDataImportWriter;
import com.avanzarit.apps.gst.batch.job.customercontactpersonimport.CustomerContactPersonFieldSetMapper;
import com.avanzarit.apps.gst.batch.job.customercontactpersonimport.CustomerContactPersonImportJobListener;
import com.avanzarit.apps.gst.batch.job.customercontactpersonimport.CustomerContactPersonImportReaderStepListener;
import com.avanzarit.apps.gst.batch.job.customercontactpersonimport.CustomerContactPersonImportWriterStepListener;
import com.avanzarit.apps.gst.batch.job.customerexport.CustomerContactPersonDataFieldExtractor;
import com.avanzarit.apps.gst.batch.job.customerexport.CustomerContactPersonExportHeaderCallback;
import com.avanzarit.apps.gst.batch.job.customerexport.CustomerDataExportProcessor;
import com.avanzarit.apps.gst.batch.job.customerexport.CustomerDataFieldExtractor;
import com.avanzarit.apps.gst.batch.job.customerexport.CustomerExportHeaderCallback;
import com.avanzarit.apps.gst.batch.job.customerexport.CustomerExportJobListener;
import com.avanzarit.apps.gst.batch.job.customerexport.CustomerExportReaderStepListener;
import com.avanzarit.apps.gst.batch.job.customerexport.CustomerExportWriterStepListener;
import com.avanzarit.apps.gst.batch.job.customerimport.CustomerDataImportProcessor;
import com.avanzarit.apps.gst.batch.job.customerimport.CustomerDataImportWriter;
import com.avanzarit.apps.gst.batch.job.customerimport.CustomerFieldSetMapper;
import com.avanzarit.apps.gst.batch.job.customerimport.CustomerImportJobListener;
import com.avanzarit.apps.gst.batch.job.customerimport.CustomerImportReaderStepListener;
import com.avanzarit.apps.gst.batch.job.customerimport.CustomerImportWriterStepListener;
import com.avanzarit.apps.gst.batch.job.policy.SkipPolicy;
import com.avanzarit.apps.gst.batch.job.properties.BatchProperties;
import com.avanzarit.apps.gst.model.Customer;
import com.avanzarit.apps.gst.model.CustomerContactPersonMaster;
import com.avanzarit.apps.gst.storage.StorageService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class CustomerJobConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    BatchProperties batchProperties;

    @Autowired
    StorageService storageService;

    @Autowired
    CustomerFieldSetMapper customerFieldSetMapper;

    @Autowired
    CustomerDataImportProcessor customerDataImportProcessor;

    @Autowired
    CustomerDataImportWriter customerDataImportWriter;

    @Autowired
    CustomerImportJobListener customerImportJobListener;

    @Autowired
    CustomerImportReaderStepListener customerImportReaderStepListener;

    @Autowired
    CustomerImportWriterStepListener customerImportWriterStepListener;

    @Autowired
    CustomerContactPersonFieldSetMapper customerContactPersonFieldSetMapper;

    @Autowired
    CustomerContactPersonDataImportProcessor customerContactPersonDataImportProcessor;

    @Autowired
    CustomerContactPersonDataImportWriter customerContactPersonDataImportWriter;

    @Autowired
    CustomerContactPersonImportJobListener customerContactPersonImportJobListener;

    @Autowired
    CustomerContactPersonImportReaderStepListener customerContactPersonImportReaderStepListener;

    @Autowired
    CustomerContactPersonImportWriterStepListener customerContactPersonImportWriterStepListener;

    @Autowired
    CustomerExportJobListener customerExportJobListener;

    @Autowired
    CustomerExportReaderStepListener customerExportReaderStepListener;

    @Autowired
    CustomerExportWriterStepListener customerExportWriterStepListener;

    @Autowired
    CustomerDataExportProcessor customerDataExportProcessor;

    @Autowired
    CustomerExportHeaderCallback customerExportHeaderCallback;

    @Autowired
    CustomerContactPersonExportHeaderCallback customerContactPersonExportHeaderCallback;

    @Autowired
    CustomerDataFieldExtractor customerDataFieldExtractor;

    @Autowired
    CustomerContactPersonDataFieldExtractor customerContactPersonDataFieldExtractor;


    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Bean(name = "customerContactPersonImportJob")
    @Scope(scopeName = "prototype")
    public Job importContactPersonJob(Resource resource) {
        return jobBuilderFactory.get("importCustomerContactPersonjob").incrementer(new RunIdIncrementer()).listener(customerContactPersonImportJobListener)
                .flow(importCustomerContactPersonStep(resource)).end().build();
    }

    public Step importCustomerContactPersonStep(Resource resource) {
        return stepBuilderFactory.get("importCustomerContactPersonStep").<CustomerContactPersonMaster, CustomerContactPersonMaster>chunk(1)
                .reader(customerContactPersonImportReader(resource)).listener(customerContactPersonImportReaderStepListener)
                .processor(customerContactPersonDataImportProcessor)
                .writer(customerContactPersonDataImportWriter).listener(customerContactPersonImportWriterStepListener).build();
    }

    public ItemReader<CustomerContactPersonMaster> customerContactPersonImportReader(Resource resource) {
        FlatFileItemReader<CustomerContactPersonMaster> reader = new FlatFileItemReader<CustomerContactPersonMaster>();
        reader.setLinesToSkip(1);
        reader.setResource(resource);
        reader.setLineMapper(contactPersonLineMapper());
        return reader;
    }

    @Bean
    public LineMapper<CustomerContactPersonMaster> contactPersonLineMapper() {
        DefaultLineMapper<CustomerContactPersonMaster> lineMapper = new DefaultLineMapper<CustomerContactPersonMaster>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"CUSTOMERID", "LASTNAME", "FIRSTNAME", "DEPARTMENT", "TELEPHONE", "MOBILE", "EMAIL"});

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(customerContactPersonFieldSetMapper);

        return lineMapper;
    }


    public ItemReader<Customer> importReader(Resource resource) {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();
        reader.setLinesToSkip(1);
        reader.setResource(resource);
        reader.setLineMapper(customerLineMapper());
        return reader;
    }

    @Bean
    public LineMapper<Customer> customerLineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<Customer>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setNames(new String[]{"CUSTOMERID", "NAME1", "NAME2", "NAME3", "TELEPHONENO1",
                "TELEPHONENO2", "FAXNO", "BUILDING_NO", "STREET1", "STREET2", "STREET3", "STREET4",
                "LOCATION", "POSTCODE", "CITY", "REGION", "COUNTRY", "EMAIL_ID", "VATNO"});

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(customerFieldSetMapper);


        return lineMapper;
    }

    @Bean(name = "customerImportJob")
    @Scope(scopeName = "prototype")
    public Job importCustomerJob(Resource resource) {
        return jobBuilderFactory.get("importCustomerjob").incrementer(new RunIdIncrementer()).listener(customerImportJobListener)
                .flow(importCustomerStep(resource)).end().build();
    }


    public Step importCustomerStep(Resource resource) {
        return stepBuilderFactory.get("importCustomerStep").<Customer, Customer>chunk(1)
                .faultTolerant().skipPolicy(new SkipPolicy())
                .reader(importReader(resource)).listener(customerImportReaderStepListener)
                .listener(customerFieldSetMapper)
                .processor(customerDataImportProcessor)
                .writer(customerDataImportWriter).listener(customerImportWriterStepListener).build();
    }

    @Bean(name = "customerExportJob")
    @Scope(scopeName = "prototype")
    public Job exportCustomerJob(Map<String, String> resourceMap) {
        return jobBuilderFactory.get("exportCustomerjob").incrementer(new RunIdIncrementer()).listener(customerExportJobListener)
                .flow(exportCustomerStep(resourceMap)).end().build();
    }

    public Step exportCustomerStep(Map<String, String> resourceMap) {
        return stepBuilderFactory.get("exportCustomerStep").<Customer, Customer>chunk(1)
                .reader(exportReader()).listener(customerExportReaderStepListener)
                .processor(customerDataExportProcessor)
                .writer(compositItemWriter(resourceMap)).listener(customerExportWriterStepListener).build();
    }

    public JpaPagingItemReader<Customer> exportReader() {
        JpaPagingItemReader<Customer> jpaPagingItemReader = new JpaPagingItemReader<>();
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setQueryString("SELECT c FROM Customer c where c.customerStatus='APPROVED'");
        jpaPagingItemReader.setPageSize(100);
        return jpaPagingItemReader;
    }

    public CompositeItemWriter<Customer> compositItemWriter(Map<String, String> resourceMap) {
        CompositeItemWriter<Customer> compositeItemWriter = new CompositeItemWriter<>();
        List<ItemWriter<? super Customer>> itemWriterList = new ArrayList<>();
        itemWriterList.add(customerDataExportWriter(storageService.loadAsResource("export-customer", resourceMap.get("CUSTOMER"))));
        itemWriterList.add(customerContactPersonExportWriter(storageService.loadAsResource("export-customer", resourceMap.get("CUSTOMERCONTACTPERSON"))));
        compositeItemWriter.setDelegates(itemWriterList);
        return compositeItemWriter;
    }

    public ItemWriter<Customer> customerDataExportWriter(Resource resource) {
        FlatFileItemWriter<Customer> flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setHeaderCallback(customerExportHeaderCallback);
        flatFileItemWriter.setLineAggregator((new CustomExtractorLineAggregator<Customer>() {
            {
                setFieldExtractor(customerDataFieldExtractor);
            }
        }));
        flatFileItemWriter.setResource(resource);
        return flatFileItemWriter;
    }

    public ItemWriter<Customer> customerContactPersonExportWriter(Resource resource) {
        FlatFileItemWriter<Customer> flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setHeaderCallback(customerContactPersonExportHeaderCallback);
        flatFileItemWriter.setLineAggregator((new CustomArrayExtractorLineAggregator<Customer>() {
            {
                setFieldExtractor(customerContactPersonDataFieldExtractor);
            }
        }));
        flatFileItemWriter.setResource(resource);
        return flatFileItemWriter;
    }


}
