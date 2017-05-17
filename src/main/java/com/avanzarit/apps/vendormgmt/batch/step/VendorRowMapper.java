package com.avanzarit.apps.vendormgmt.batch.step;

import com.avanzarit.apps.vendormgmt.model.Vendor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by SPADHI on 5/17/2017.
 */
public class VendorRowMapper implements RowMapper<Vendor> {

    @Override
    public Vendor mapRow(ResultSet rs, int rowNum) throws SQLException {

        Vendor result = new Vendor();


        return result;
    }
}