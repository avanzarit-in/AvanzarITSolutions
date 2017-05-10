package com.avanzarit.apps.vendormgmt.batch.step;

import com.avanzarit.apps.vendormgmt.model.Vendor;
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
						setNames(new String[] { "name", "accno" });
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
}
