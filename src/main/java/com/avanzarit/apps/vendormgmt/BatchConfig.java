package com.avanzarit.apps.vendormgmt;

import com.avanzarit.apps.vendormgmt.auth.service.UserService;
import com.avanzarit.apps.vendormgmt.batch.step.Listener;
import com.avanzarit.apps.vendormgmt.batch.step.Processor;
import com.avanzarit.apps.vendormgmt.batch.step.Reader;
import com.avanzarit.apps.vendormgmt.batch.step.Writer;
import com.avanzarit.apps.vendormgmt.model.Vendor;
import com.avanzarit.apps.vendormgmt.repository.VendorRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public VendorRepository vendorRepository;

    @Autowired
    private UserService userService;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(new Listener(vendorRepository))
                .flow(step1()).end().build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Vendor, Vendor>chunk(2)
                .reader(Reader.reader("vendor-data.csv"))
                .processor(new Processor()).writer(new Writer(vendorRepository,userService)).build();
    }
}
