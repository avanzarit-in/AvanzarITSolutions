package com.avanzarit.apps.vendormgmt.batch.job.vendorexport;

import com.avanzarit.apps.vendormgmt.model.Vendor;
import com.avanzarit.apps.vendormgmt.storage.StorageService;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;

/**
 * Created by SPADHI on 5/17/2017.
 */
public class VendorDataExportWriter {
    public static FlatFileItemWriter write(StorageService storageService) {
        FlatFileItemWriter writer = new FlatFileItemWriter();

        try {

            writer.setResource(storageService.loadAsResource("vendor-data-export.csv"));
            writer.setLineAggregator(new DelimitedLineAggregator() {
                {
                    setFieldExtractor(createStudentFieldExtractor());
                    setDelimiter(",");
                }
            });
        } catch (Exception exception) {

        }
        return writer;
    }

    private static FieldExtractor<Vendor> createStudentFieldExtractor() {
        BeanWrapperFieldExtractor<Vendor> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[]{"vendorId", "vendorName1", "vendorName2", "vendorName3", "telephoneNumberExtn", "mobileNo", "email", "faxNumberExtn", "buildingNo", "address1", "address2", "address3", "address4", "address5", "city", "postCode", "region", "country", "accountHolderName", "accountNumber", "pan", "vatNumber"});
        return extractor;
    }
}
