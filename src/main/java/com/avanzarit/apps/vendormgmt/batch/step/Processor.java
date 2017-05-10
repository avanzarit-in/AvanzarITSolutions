package com.avanzarit.apps.vendormgmt.batch.step;

import com.avanzarit.apps.vendormgmt.model.Vendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Random;

public class Processor implements ItemProcessor<Vendor, Vendor> {

    private static final Logger log = LoggerFactory.getLogger(Processor.class);

    @Override
    public Vendor process(Vendor vendor) throws Exception {


        return vendor;
    }
}
