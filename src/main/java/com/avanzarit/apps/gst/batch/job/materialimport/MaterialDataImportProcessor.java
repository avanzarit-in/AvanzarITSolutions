package com.avanzarit.apps.gst.batch.job.materialimport;

import com.avanzarit.apps.gst.model.MaterialMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MaterialDataImportProcessor implements ItemProcessor<MaterialMaster, MaterialMaster> {

    private static final Logger log = LoggerFactory.getLogger(MaterialDataImportProcessor.class);

    @Override
    public MaterialMaster process(MaterialMaster material) throws Exception {

        return material;
    }
}
