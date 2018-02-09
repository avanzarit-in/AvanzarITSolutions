package com.avanzarit.apps.gst.batch.job.userimport;

import com.avanzarit.apps.gst.auth.db.model.DbUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserDataProcessor implements ItemProcessor<DbUser, DbUser> {

    private static final Logger log = LoggerFactory.getLogger(UserDataProcessor.class);

    @Override
    public DbUser process(DbUser dbUser) throws Exception {

        return dbUser;
    }
}
