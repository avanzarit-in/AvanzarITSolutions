package com.avanzarit.apps.gst.batch.job;

import org.springframework.batch.item.file.transform.ExtractorLineAggregator;

public class CustomExtractorLineAggregator<T> extends ExtractorLineAggregator<T> {
    /**
     * Aggregate provided fields into single String.
     *
     * @param fields An array of the fields that must be aggregated
     * @return aggregated string
     */
    @Override
    protected String doAggregate(Object[] fields) {
        StringBuilder sb = new StringBuilder();
        for (int counter = 0; counter < fields.length; counter++) {
            sb.append("\"").append(fields[counter]).append("\",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
