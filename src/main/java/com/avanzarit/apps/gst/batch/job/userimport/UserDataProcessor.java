package com.avanzarit.apps.gst.batch.job.userimport;

import com.avanzarit.apps.gst.auth.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserDataProcessor implements ItemProcessor<User, User> {

    private static final Logger log = LoggerFactory.getLogger(UserDataProcessor.class);

    @Override
    public User process(User user) throws Exception {

        return user;
    }
}
