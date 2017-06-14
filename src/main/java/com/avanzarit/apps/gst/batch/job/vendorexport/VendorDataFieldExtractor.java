package com.avanzarit.apps.gst.batch.job.vendorexport;

import com.avanzarit.apps.gst.batch.job.annotations.Export;
import com.avanzarit.apps.gst.batch.job.comparator.FieldComparator;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.utils.Utils;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class VendorDataFieldExtractor implements FieldExtractor<Vendor> {
    /**
     * @param item
     * @return an array containing item's parts
     */
    @Override
    public Object[] extract(Vendor item) {
        BeanWrapperFieldExtractor<Vendor> extractor = new BeanWrapperFieldExtractor<>();
        List<Field> fields = Utils.findFields(Vendor.class, Export.class);
        List<String> fieldNames = new ArrayList<>();
        Collections.sort(fields, new FieldComparator());
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        String[] fieldNameArray = new String[fieldNames.size()];
        extractor.setNames(fieldNames.toArray(fieldNameArray));
        return extractor.extract(item);
    }
}
