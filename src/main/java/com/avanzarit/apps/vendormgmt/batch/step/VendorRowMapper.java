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


        result.setVendorId(rs.getString("vendorid"));
        result.setVendorName1(rs.getString("vendorname1"));
        result.setVendorName2(rs.getString("vendorname2"));
        result.setVendorName3(rs.getString("vendorname3"));
        result.setTelephoneNumberExtn(rs.getString("telephonenumberextn"));
        result.setMobileNo(rs.getString("mobileno"));
        result.setEmail(rs.getString("email"));
        result.setFaxNumberExtn(rs.getString("faxnumberextn"));
        result.setBuildingNo(rs.getString("buildingno"));
        result.setAddress1(rs.getString("address1"));
        result.setAddress2(rs.getString("address2"));
        result.setAddress3(rs.getString("address3"));
        result.setAddress4(rs.getString("address4"));
        result.setAddress5(rs.getString("address5"));
        result.setCity(rs.getString("city"));
        result.setPostCode(rs.getString("postcode"));
        result.setRegion(rs.getString("region"));
        result.setCountry(rs.getString("country"));
        result.setAccountHolderName(rs.getString("accountholdername"));
        result.setAccountNumber(rs.getString("accountnumber"));
        result.setVatNumber(rs.getString("vatnumber"));
        return result;
    }
}