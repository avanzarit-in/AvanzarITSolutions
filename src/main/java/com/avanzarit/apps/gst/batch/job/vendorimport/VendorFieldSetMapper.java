package com.avanzarit.apps.gst.batch.job.vendorimport;

import com.avanzarit.apps.gst.batch.job.exception.SkippableReadException;
import com.avanzarit.apps.gst.batch.job.report.BatchLog;
import com.avanzarit.apps.gst.model.Vendor;
import com.avanzarit.apps.gst.model.VendorStatusEnum;
import com.avanzarit.apps.gst.repository.VendorRepository;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemCountAware;
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
public class VendorFieldSetMapper implements FieldSetMapper<Vendor>, ItemCountAware {

    private String user;
    @Autowired
    private VendorRepository vendorRepository;
    private BatchLog logger;
    private int itemCount;

    /**
     * Method used to map data obtained from a {@link FieldSet} into an object.
     *
     * @param fieldSet the {@link FieldSet} to map
     * @throws BindException if there is a problem with the binding
     */
    @Override
    public Vendor mapFieldSet(FieldSet fieldSet) throws BindException {

        String vendorId = fieldSet.readString("VENDORID");
        String email = fieldSet.readString("EMAIL_ID");
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
        result.setVendorName1(fieldSet.readString("NAME1"));
        result.setVendorName2(fieldSet.readString("NAME2"));
        result.setVendorName3(fieldSet.readString("NAME3"));
        result.setTelephoneNumberExtn(fieldSet.readString("TELEPHONENO1"));
        result.setMobileNo(fieldSet.readString("TELEPHONENO2"));
        result.setEmail(email);
        result.setFaxNumberExtn(fieldSet.readString("FAXNO"));
        result.setBuildingNo(fieldSet.readString("BUILDING_NO"));
        result.setAddress1(fieldSet.readString("STREET"));
        result.setAddress2(fieldSet.readString("STREET2"));
        result.setAddress3(fieldSet.readString("STREET3"));
        result.setAddress4(fieldSet.readString("STREET4"));
        result.setAddress5(fieldSet.readString("LOCATION"));
        result.setCity(fieldSet.readString("CITY"));
        result.setPostCode(fieldSet.readString("POSTCODE"));
        result.setRegion(fieldSet.readString("REGION"));
        result.setCountry(fieldSet.readString("COUNTRY"));
        result.setAccountHolderName(fieldSet.readString("ACCOUNT_HOLDER_NAME"));
        result.setAccountNumber(fieldSet.readString("ACCOUNT_NO"));
        result.setVatNumber(fieldSet.readString("VATNO"));
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


    @Override
    public void setItemCount(int i) {
        this.itemCount = i;
    }
}
