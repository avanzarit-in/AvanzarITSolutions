package com.avanzarit.apps.gst.batch.job.customerimport;

import com.avanzarit.apps.gst.model.Customer;
import com.avanzarit.apps.gst.storage.StorageService;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

public class CustomerDataImportReader {

    public static FlatFileItemReader<Customer> reader(StorageService storageService) {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();
        reader.setLinesToSkip(1);
        try {
            reader.setResource(storageService.loadAsResource("upload", "customer-data.csv"));
            reader.setLineMapper(new DefaultLineMapper<Customer>() {
                {
                    setLineTokenizer(new DelimitedLineTokenizer() {
                        {
                            setNames(new String[]{"customerId", "customerName1", "customerName2", "customerName3", "telephoneNumberExtn", "mobileNo", "email", "faxNumberExtn", "buildingNo", "address1", "address2", "address3", "address4", "address5", "city", "postCode", "region", "country"});
                        }
                    });
                    setFieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {
                        {
                            setTargetType(Customer.class);
                        }
                    });

                }
            });
        } catch (Exception exception) {

        }
        return reader;
    }

}
