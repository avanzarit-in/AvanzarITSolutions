package com.avanzarit.apps.gst.batch.job.materialimport;

import com.avanzarit.apps.gst.batch.job.exception.SkippableReadException;
import com.avanzarit.apps.gst.model.MaterialMaster;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.repository.MaterialMasterRepository;
import com.avanzarit.apps.gst.repository.VendorRepository;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.util.List;

@Component
public class MaterialFieldSetMapper implements FieldSetMapper<MaterialMaster> {

    @Autowired
    private MaterialMasterRepository materialMasterRepository;

    @Autowired
    private VendorRepository vendorRepository;

    /**
     * Method used to map data obtained from a {@link FieldSet} into an object.
     *
     * @param fieldSet
     *         the {@link FieldSet} to map
     * @throws BindException
     *         if there is a problem with the binding
     */
    @Override
    public MaterialMaster mapFieldSet(FieldSet fieldSet) throws BindException {
        String vendorId = fieldSet.readString("VENDORID");
        String code = fieldSet.readString("CODE");
        String desc = fieldSet.readString("DESC");

        if (vendorId == null || code == null || desc == null) {
            throw new SkippableReadException("Vendor ID or material Code or Material Description missing not uploading material info");
        }

        Vendor vendor = vendorRepository.findByVendorId(vendorId);

        if (vendor != null) {
            List<MaterialMaster> materialMasterList = materialMasterRepository.findByVendor(vendor);
            for (MaterialMaster materialMaster : materialMasterList) {
                if (materialMaster.getCode().equals(code)) {
                    throw new SkippableReadException("Material with Material Code " + code + " already exist not uploading material info");
                }
            }
        } else {
            throw new SkippableReadException("Vendor ID " + vendorId + " not found in vendor master table not uploading");
        }

        MaterialMaster result = new MaterialMaster();
        result.setVendorId(vendorId);
        result.setCode(code);
        result.setDesc(desc);
        return result;
    }
}
