package com.avanzarit.apps.gst.batch.job.materialimport;

import com.avanzarit.apps.gst.model.MaterialMaster;
import com.avanzarit.apps.gst.repository.MaterialMasterRepository;
import com.avanzarit.apps.gst.repository.VendorRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialDataImportWriter implements ItemWriter<MaterialMaster> {


    private VendorRepository vendorRepository;
    private MaterialMasterRepository materialMasterRepository;


    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Autowired
    public void setMaterialMasterRepository(MaterialMasterRepository materialMasterRepository) {
        this.materialMasterRepository = materialMasterRepository;
    }

    @Override
    public void write(List<? extends MaterialMaster> materialMasters) throws Exception {
        for (MaterialMaster materialMaster : materialMasters) {


        }
    }
}
