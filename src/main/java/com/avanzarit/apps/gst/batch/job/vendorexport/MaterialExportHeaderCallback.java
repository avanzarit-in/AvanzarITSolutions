package com.avanzarit.apps.gst.batch.job.vendorexport;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by SPADHI on 6/1/2017.
 */
@Component
public class MaterialExportHeaderCallback implements FlatFileHeaderCallback {
    /**
     * Write contents to a file using the supplied {@link Writer}. It is not
     * required to flush the writer inside this method.
     *
     * @param writer
     */
    @Override
    public void writeHeader(Writer writer) throws IOException {

    }
}
