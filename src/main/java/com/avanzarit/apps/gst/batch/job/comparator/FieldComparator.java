package com.avanzarit.apps.gst.batch.job.comparator;

import com.avanzarit.apps.gst.batch.job.annotations.Export;

import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * Created by AVANZAR on 6/3/2017.
 */
public class FieldComparator implements Comparator<Field> {

    @Override
    public int compare(Field field1, Field field2) {
        Export export1 = field1.getAnnotation(Export.class);
        Export export2 = field2.getAnnotation(Export.class);
        int order1 = export1.order();
        int order2 = export2.order();
        if (order1 > order2) {
            return 1;
        } else if (order1 < order2) {
            return -1;
        }
        return 0;
    }
}
