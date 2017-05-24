package com.avanzarit.apps.vendormgmt.batch.job.userimport;

import com.avanzarit.apps.vendormgmt.auth.model.User;
import com.avanzarit.apps.vendormgmt.storage.StorageService;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class UserDataReader {
	public static FlatFileItemReader<User> reader(String path) {
		FlatFileItemReader<User> reader = new FlatFileItemReader<User>();

		reader.setResource(new ClassPathResource(path));
		reader.setLineMapper(new DefaultLineMapper<User>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "username","roleString"});
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {
					{
						setTargetType(User.class);
					}
				});
			}
		});
		return reader;
	}

	public static FlatFileItemReader<User> reader(StorageService storageService) {
		FlatFileItemReader<User> reader = new FlatFileItemReader<User>();

		try {
			reader.setResource(storageService.loadAsResource("user-data.csv"));
			reader.setLineMapper(new DefaultLineMapper<User>() {
				{
					setLineTokenizer(new DelimitedLineTokenizer() {
						{
							setNames(new String[]{"username", "rolesString"});
						}
					});
					setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {
						{
							setTargetType(User.class);
						}
					});
				}
			});
		}catch(Exception exception){

		}
		return reader;
	}

}
