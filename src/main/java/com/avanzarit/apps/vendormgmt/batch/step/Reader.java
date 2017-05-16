package com.avanzarit.apps.vendormgmt.batch.step;

import com.avanzarit.apps.vendormgmt.model.Vendor;
import com.avanzarit.apps.vendormgmt.storage.StorageService;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class Reader {
	public static FlatFileItemReader<Vendor> reader(String path) {
		FlatFileItemReader<Vendor> reader = new FlatFileItemReader<Vendor>();

		reader.setResource(new ClassPathResource(path));
		reader.setLineMapper(new DefaultLineMapper<Vendor>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "vendorId","vendorName1","vendorName2","vendorName3","telephoneNumberExtn","mobileNo","email","faxNumberExtn","buildingNo","address1","address2","address3","address4","address5","city","postCode","region","country","accountHolderName","accountNumber","pan","vatNumber" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Vendor>() {
					{
						setTargetType(Vendor.class);
					}
				});
			}
		});
		return reader;
	}

	public static FlatFileItemReader<Vendor> reader(StorageService storageService) {
		FlatFileItemReader<Vendor> reader = new FlatFileItemReader<Vendor>();

		try {
			reader.setResource(storageService.loadAsResource("vendor-data.csv"));
			reader.setLineMapper(new DefaultLineMapper<Vendor>() {
				{
					setLineTokenizer(new DelimitedLineTokenizer() {
						{
							setNames(new String[]{"vendorId", "vendorName1", "vendorName2", "vendorName3", "telephoneNumberExtn", "mobileNo", "email", "faxNumberExtn", "buildingNo", "address1", "address2", "address3", "address4", "address5", "city", "postCode", "region", "country", "accountHolderName", "accountNumber", "pan", "vatNumber"});
						}
					});
					setFieldSetMapper(new BeanWrapperFieldSetMapper<Vendor>() {
						{
							setTargetType(Vendor.class);
						}
					});
				}
			});
		}catch(Exception exception){

		}
		return reader;
	}

}
