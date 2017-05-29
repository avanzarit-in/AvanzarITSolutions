package com.avanzarit.apps.gst.batch.job.userimport;

import com.avanzarit.apps.gst.auth.model.User;
import com.avanzarit.apps.gst.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import java.util.List;

public class UserDataListener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(UserDataListener.class);

	private final UserRepository userRepository;

	public UserDataListener(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Finish Job! Check the results");

			List<User> users = userRepository.findAll();

			for (User user : users) {
				log.info("Found <" + user + "> in the database.");
			}
		}
	}
}
