package com.avanzarit.apps.gst.batch.job.config;

import com.avanzarit.apps.gst.batch.job.materialimport.MaterialDataImportProcessor;
import com.avanzarit.apps.gst.batch.job.materialimport.MaterialDataImportWriter;
import com.avanzarit.apps.gst.batch.job.materialimport.MaterialFieldSetMapper;
import com.avanzarit.apps.gst.batch.job.materialimport.MaterialImportJobListener;
import com.avanzarit.apps.gst.batch.job.materialimport.MaterialImportReaderStepListener;
import com.avanzarit.apps.gst.batch.job.materialimport.MaterialImportWriterStepListener;
import com.avanzarit.apps.gst.batch.job.properties.BatchProperties;
import com.avanzarit.apps.gst.batch.job.vendorexport.ContactPersonDataFieldExtractor;
import com.avanzarit.apps.gst.batch.job.vendorexport.ContactPersonExportHeaderCallback;
import com.avanzarit.apps.gst.batch.job.vendorexport.CustomExtractorLineAggregator;
import com.avanzarit.apps.gst.batch.job.vendorexport.MaterialDataFieldExtractor;
import com.avanzarit.apps.gst.batch.job.vendorexport.MaterialExportHeaderCallback;
import com.avanzarit.apps.gst.batch.job.vendorexport.VendorDataExportProcessor;
import com.avanzarit.apps.gst.batch.job.vendorexport.VendorDataFieldExtractor;
import com.avanzarit.apps.gst.batch.job.vendorexport.VendorExportHeaderCallback;
import com.avanzarit.apps.gst.batch.job.vendorexport.VendorExportJobListener;
import com.avanzarit.apps.gst.batch.job.vendorexport.VendorExportReaderStepListener;
import com.avanzarit.apps.gst.batch.job.vendorexport.VendorExportWriterStepListener;
import com.avanzarit.apps.gst.batch.job.vendorimport.VendorDataImportProcessor;
import com.avanzarit.apps.gst.batch.job.vendorimport.VendorDataImportWriter;
import com.avanzarit.apps.gst.batch.job.vendorimport.VendorFieldSetMapper;
import com.avanzarit.apps.gst.batch.job.vendorimport.VendorImportJobListener;
import com.avanzarit.apps.gst.batch.job.vendorimport.VendorImportReaderStepListener;
import com.avanzarit.apps.gst.batch.job.vendorimport.VendorImportWriterStepListener;
import com.avanzarit.apps.gst.model.MaterialMaster;
import com.avanzarit.apps.gst.model.Vendor;
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
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
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
public class VendorJobConfig {

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


    @Autowired
    MaterialFieldSetMapper materialFieldSetMapper;

    @Autowired
    MaterialDataImportProcessor materialDataImportProcessor;

    @Autowired
    MaterialDataImportWriter materialDataImportWriter;

    @Autowired
    MaterialImportJobListener materialImportJobListener;

    @Autowired
    MaterialImportReaderStepListener materialImportReaderStepListener;

    @Autowired
    MaterialImportWriterStepListener materialImportWriterStepListener;


    @Autowired
    VendorExportJobListener vendorExportJobListener;

    @Autowired
    VendorExportReaderStepListener vendorExportReaderStepListener;

    @Autowired
    VendorExportWriterStepListener vendorExportWriterStepListener;

    @Autowired
    VendorDataExportProcessor vendorDataExportProcessor;

    @Autowired
    VendorExportHeaderCallback vendorExportHeaderCallback;

    @Autowired
    MaterialExportHeaderCallback materialExportHeaderCallback;

    @Autowired
    ContactPersonExportHeaderCallback contactPersonExportHeaderCallback;

    @Autowired
    VendorDataFieldExtractor vendorDataFieldExtractor;

    @Autowired
    ContactPersonDataFieldExtractor contactPersonDataFieldExtractor;

    @Autowired
    MaterialDataFieldExtractor materialDataFieldExtractor;

    @Autowired
    EntityManagerFactory entityManagerFactory;


    public ItemReader<MaterialMaster> materialImportReader(Resource resource) {
        FlatFileItemReader<MaterialMaster> reader = new FlatFileItemReader<MaterialMaster>();
        reader.setLinesToSkip(1);
        reader.setResource(resource);
        reader.setLineMapper(materialLineMapper());
        return reader;
    }

    @Bean
    public LineMapper<MaterialMaster> materialLineMapper() {
        DefaultLineMapper<MaterialMaster> lineMapper = new DefaultLineMapper<MaterialMaster>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"VENDORID", "CODE", "DESC", "HSN"});

        BeanWrapperFieldSetMapper<MaterialMaster> fieldSetMapper = new BeanWrapperFieldSetMapper<MaterialMaster>();
        fieldSetMapper.setTargetType(MaterialMaster.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(materialFieldSetMapper);

        return lineMapper;
    }

    @Bean(name = "materialImportJob")
    @Scope(scopeName = "prototype")
    public Job importMaterialJob(Resource resource) {
        return jobBuilderFactory.get("importMaterialjob").incrementer(new RunIdIncrementer()).listener(materialImportJobListener)
                .flow(importMaterialStep(resource)).end().build();
    }


    public Step importMaterialStep(Resource resource) {
        return stepBuilderFactory.get("importMaterialStep").<MaterialMaster, MaterialMaster>chunk(1)
                .reader(materialImportReader(resource)).listener(materialImportReaderStepListener)
                .processor(materialDataImportProcessor)
                .writer(materialDataImportWriter).listener(materialImportWriterStepListener).build();
    }




    public ItemReader<Vendor> importReader(Resource resource) {
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
                .reader(importReader(resource)).listener(vendorImportReaderStepListener)
                .processor(vendorDataImportProcessor)
                .writer(vendorDataImportWriter).listener(vendorImportWriterStepListener).build();
    }

    @Bean(name = "vendorExportJob")
    @Scope(scopeName = "prototype")
    public Job exportVendorJob(Map<String, String> resourceMap) {
        return jobBuilderFactory.get("exportVendorjob").incrementer(new RunIdIncrementer()).listener(vendorExportJobListener)
                .flow(exportVendorStep(resourceMap)).end().build();
    }

    public Step exportVendorStep(Map<String, String> resourceMap) {
        return stepBuilderFactory.get("exportVendorStep").<Vendor, Vendor>chunk(1)
                .reader(exportReader()).listener(vendorExportReaderStepListener)
                .processor(vendorDataExportProcessor)
                .writer(compositItemWriter(resourceMap)).listener(vendorExportWriterStepListener).build();
    }

    public JpaPagingItemReader<Vendor> exportReader() {
        JpaPagingItemReader<Vendor> jpaPagingItemReader = new JpaPagingItemReader<>();
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setQueryString("SELECT v FROM Vendor v");
        jpaPagingItemReader.setPageSize(100);
        return jpaPagingItemReader;
    }

    public CompositeItemWriter<Vendor> compositItemWriter(Map<String, String> resourceMap) {
        CompositeItemWriter<Vendor> compositeItemWriter = new CompositeItemWriter<>();
        List<ItemWriter<? super Vendor>> itemWriterList = new ArrayList<>();
        itemWriterList.add(vendorDataExportWriter(storageService.loadAsResource(resourceMap.get("VENDOR"))));
        itemWriterList.add(contactPersonExportWriter(storageService.loadAsResource(resourceMap.get("CONTACTPERSON"))));
        itemWriterList.add(materialExportWriter(storageService.loadAsResource(resourceMap.get("MATERIAL"))));
        compositeItemWriter.setDelegates(itemWriterList);
        return compositeItemWriter;
    }

    public ItemWriter<Vendor> vendorDataExportWriter(Resource resource) {
        FlatFileItemWriter<Vendor> flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setHeaderCallback(vendorExportHeaderCallback);
        flatFileItemWriter.setLineAggregator((new DelimitedLineAggregator() {
            {
                setFieldExtractor(vendorDataFieldExtractor);
                setDelimiter(",");
            }
        }));
        flatFileItemWriter.setResource(resource);
        return flatFileItemWriter;
    }

    public ItemWriter<Vendor> contactPersonExportWriter(Resource resource) {
        FlatFileItemWriter<Vendor> flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setHeaderCallback(contactPersonExportHeaderCallback);
        flatFileItemWriter.setLineAggregator((new CustomExtractorLineAggregator<Vendor>() {
            {
                setFieldExtractor(contactPersonDataFieldExtractor);

            }
        }));
        flatFileItemWriter.setResource(resource);
        return flatFileItemWriter;
    }

    public ItemWriter<Vendor> materialExportWriter(Resource resource) {
        FlatFileItemWriter<Vendor> flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setHeaderCallback(materialExportHeaderCallback);
        flatFileItemWriter.setLineAggregator((new CustomExtractorLineAggregator<Vendor>() {
            {
                setFieldExtractor(materialDataFieldExtractor);
            }
        }));
        flatFileItemWriter.setResource(resource);
        return flatFileItemWriter;
    }
}
