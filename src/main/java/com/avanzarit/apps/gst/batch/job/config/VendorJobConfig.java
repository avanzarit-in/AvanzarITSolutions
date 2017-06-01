package com.avanzarit.apps.gst.batch.job.config;

import com.avanzarit.apps.gst.batch.job.properties.BatchProperties;
import com.avanzarit.apps.gst.batch.job.vendorimport.VendorDataImportProcessor;
import com.avanzarit.apps.gst.batch.job.vendorimport.VendorDataImportWriter;
import com.avanzarit.apps.gst.batch.job.vendorimport.VendorFieldSetMapper;
import com.avanzarit.apps.gst.batch.job.vendorimport.VendorImportJobListener;
import com.avanzarit.apps.gst.batch.job.vendorimport.VendorImportReaderStepListener;
import com.avanzarit.apps.gst.batch.job.vendorimport.VendorImportWriterStepListener;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.storage.StorageService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;

@Configuration
public class VendorJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    BatchProperties batchProperties;

    @Autowired
    StorageService storageService;

    @Autowired
    VendorFieldSetMapper vendorFieldSetMapper;

    @Autowired
    VendorDataImportProcessor vendorDataImportProcessor;

    @Autowired
    VendorDataImportWriter vendorDataImportWriter;

    @Autowired
    VendorImportJobListener vendorImportJobListener;

    @Autowired
    VendorImportReaderStepListener vendorImportReaderStepListener;

    @Autowired
    VendorImportWriterStepListener vendorImportWriterStepListener;

    public ItemReader<Vendor> reader(Resource resource) {
        FlatFileItemReader<Vendor> reader = new FlatFileItemReader<Vendor>();
        reader.setLinesToSkip(1);
        reader.setResource(resource);
        reader.setLineMapper(vendorLineMapper());
        return reader;
    }

    @Bean
    public LineMapper<Vendor> vendorLineMapper() {
        DefaultLineMapper<Vendor> lineMapper = new DefaultLineMapper<Vendor>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"vendorid", "vendorname1", "vendorname2", "vendorname3", "telephonenumberextn",
                "mobileno", "email", "faxnumberextn", "buildingno", "address1", "address2", "address3",
                "address4", "address5", "city", "postcode", "region", "country", "accountholdername",
                "accountnumber", "pan", "vatnumber"});

        BeanWrapperFieldSetMapper<Vendor> fieldSetMapper = new BeanWrapperFieldSetMapper<Vendor>();
        fieldSetMapper.setTargetType(Vendor.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(vendorFieldSetMapper);

        return lineMapper;
    }

    @Bean(name = "vendorImportJob")
    @Scope(scopeName = "prototype")
    public Job importVendorJob(Resource resource) {
        return jobBuilderFactory.get("importVendorjob").incrementer(new RunIdIncrementer()).listener(vendorImportJobListener)
                .flow(importVendorStep(resource)).end().build();
    }


    public Step importVendorStep(Resource resource) {
        return stepBuilderFactory.get("importVendorStep").<Vendor, Vendor>chunk(1)
                .reader(reader(resource)).listener(vendorImportReaderStepListener)
                .processor(vendorDataImportProcessor)
                .writer(vendorDataImportWriter).listener(vendorImportWriterStepListener).build();
    }
}
