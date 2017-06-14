package com.avanzarit.apps.gst.batch.job.customerexport;

import com.avanzarit.apps.gst.batch.job.annotations.Export;
import com.avanzarit.apps.gst.batch.job.comparator.FieldComparator;
import com.avanzarit.apps.gst.model.CustomerContactPersonMaster;
import com.avanzarit.apps.gst.utils.Utils;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CustomerContactPersonExportHeaderCallback implements FlatFileHeaderCallback {
    /**
     * Write contents to a file using the supplied {@link Writer}. It is not
     * required to flush the writer inside this method.
     *
     * @param writer
     */
    @Override
    public void writeHeader(Writer writer) throws IOException {
        List<Field> fields = Utils.findFields(CustomerContactPersonMaster.class, Export.class);
        List<String> fieldTitles = new ArrayList<>();
        Collections.sort(fields, new FieldComparator());
        for (Field field : fields) {
            Export export = field.getAnnotation(Export.class);
            writer.append(export.title() + ",");
        }
    }
}
