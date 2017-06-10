package com.avanzarit.apps.gst.batch.job.materialimport;

import com.avanzarit.apps.gst.model.MaterialMaster;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

/**
 * Created by SPADHI on 5/30/2017.
 */
@Component
public class MaterialFieldSetMapper implements FieldSetMapper<MaterialMaster> {


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
        MaterialMaster result = new MaterialMaster();
        result.setVendorId(fieldSet.readString("VENDORID"));
        result.setCode(fieldSet.readString("CODE"));
        result.setDesc(fieldSet.readString("DESC"));
        //   result.setHsn(fieldSet.readString("HSN"));
        return result;
    }
}
