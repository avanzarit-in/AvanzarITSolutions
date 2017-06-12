package com.avanzarit.apps.gst.batch.job.vendorimport;

import com.avanzarit.apps.gst.batch.job.exception.SkippableReadException;
import com.avanzarit.apps.gst.batch.job.report.BatchLog;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.model.VendorStatusEnum;
import com.avanzarit.apps.gst.repository.VendorRepository;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.thymeleaf.util.StringUtils;

import java.util.Date;

/**
 * Created by SPADHI on 5/30/2017.
 */
@Component
public class VendorFieldSetMapper implements FieldSetMapper<Vendor> {

    private String user;
    @Autowired
    private VendorRepository vendorRepository;
    private BatchLog logger;

    /**
     * Method used to map data obtained from a {@link FieldSet} into an object.
     *
     * @param fieldSet the {@link FieldSet} to map
     * @throws BindException if there is a problem with the binding
     */
    @Override
    public Vendor mapFieldSet(FieldSet fieldSet) throws BindException {

        String vendorId = fieldSet.readString("vendorid");
        String email = fieldSet.readString("email");
        if (StringUtils.isEmpty(email)) {
            logger.log("WARNING: Email ID missing for vendorId: " + vendorId);
        }
        if (StringUtils.isEmpty(vendorId)) {
            throw new SkippableReadException("Vendor ID missing not uploading");
        }
        Vendor savedVendor = vendorRepository.findByVendorId(vendorId);
        if (savedVendor != null) {
            throw new SkippableReadException("Vendor with ID " + savedVendor.getVendorId() + " already exist not uploading again");
        }

        Vendor result = new Vendor();
        result.setVendorId(vendorId);
        result.setVendorName1(fieldSet.readString("vendorname1"));
        result.setVendorName2(fieldSet.readString("vendorname2"));
        result.setVendorName3(fieldSet.readString("vendorname3"));
        result.setTelephoneNumberExtn(fieldSet.readString("telephonenumberextn"));
        result.setMobileNo(fieldSet.readString("mobileno"));
        result.setEmail(fieldSet.readString("email"));
        result.setFaxNumberExtn(fieldSet.readString("faxnumberextn"));
        result.setBuildingNo(fieldSet.readString("buildingno"));
        result.setAddress1(fieldSet.readString("address1"));
        result.setAddress2(fieldSet.readString("address2"));
        result.setAddress3(fieldSet.readString("address3"));
        result.setAddress4(fieldSet.readString("address4"));
        result.setAddress5(fieldSet.readString("address5"));
        result.setCity(fieldSet.readString("city"));
        result.setPostCode(fieldSet.readString("postcode"));
        result.setRegion(fieldSet.readString("region"));
        result.setCountry(fieldSet.readString("country"));
        result.setAccountHolderName(fieldSet.readString("accountholdername"));
        result.setAccountNumber(fieldSet.readString("accountnumber"));
        result.setVatNumber(fieldSet.readString("vatnumber"));
        result.setCreatedBy(user);
        result.setCreatedOn(new Date());
        result.setVendorStatus(VendorStatusEnum.NEW);
        return result;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.user = stepExecution.getJobParameters().getString("user");
        logger = (BatchLog) stepExecution.getJobExecution().getExecutionContext().get("log");

    }


}
