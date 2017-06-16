package com.avanzarit.apps.gst.batch.job.customerimport;

import com.avanzarit.apps.gst.batch.job.exception.SkippableReadException;
import com.avanzarit.apps.gst.batch.job.report.BatchLog;
import com.avanzarit.apps.gst.model.Customer;
import com.avanzarit.apps.gst.model.CustomerStatusEnum;
import com.avanzarit.apps.gst.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.thymeleaf.util.StringUtils;

import java.util.Date;

@Component
public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerFieldSetMapper.class);
    private String user;
    @Autowired
    private CustomerRepository customerRepository;
    private BatchLog logger;
    private int itemCount;

    /**
     * Method used to map data obtained from a {@link FieldSet} into an object.
     *
     * @param fieldSet
     *         the {@link FieldSet} to map
     * @throws BindException
     *         if there is a problem with the binding
     */
    @Override
    public Customer mapFieldSet(FieldSet fieldSet) throws BindException {

        String customerid = fieldSet.readString("CUSTOMERID");
        String email = fieldSet.readString("EMAIL_ID");
        if (StringUtils.isEmpty(email)) {
            logger.log("WARNING: Email ID missing for customerId: " + customerid);
        }
        if (StringUtils.isEmpty(customerid)) {
            throw new SkippableReadException("Customer ID missing not uploading");
        }
        Customer savedCustomer = customerRepository.findByCustomerId(customerid);
        if (savedCustomer != null) {
            throw new SkippableReadException("Customer with ID " + savedCustomer.getCustomerId() + " already exist not uploading again");
        }

        Customer result = new Customer();
        result.setCustomerId(customerid);
        result.setCustomerName1(fieldSet.readString("NAME1"));
        result.setCustomerName2(fieldSet.readString("NAME2"));
        result.setCustomerName3(fieldSet.readString("NAME3"));
        result.setTelephoneNumberExtn(fieldSet.readString("TELEPHONENO1"));
        result.setMobileNo(fieldSet.readString("TELEPHONENO2"));
        result.setEmail(email);
        result.setFaxNumberExtn(fieldSet.readString("FAXNO"));
        result.setBuildingNo(fieldSet.readString("BUILDING_NO"));
        result.setAddress1(fieldSet.readString("STREET1"));
        result.setAddress2(fieldSet.readString("STREET2"));
        result.setAddress3(fieldSet.readString("STREET3"));
        result.setAddress4(fieldSet.readString("STREET4"));
        result.setAddress5(fieldSet.readString("LOCATION"));
        result.setCity(fieldSet.readString("CITY"));
        result.setPostCode(fieldSet.readString("POSTCODE"));
        result.setRegion(fieldSet.readString("REGION"));
        result.setCountry(fieldSet.readString("COUNTRY"));
        result.setVatNumber(fieldSet.readString("VATNO"));
        result.setCreatedBy(user);
        result.setCreatedOn(new Date());
        result.setCustomerStatus(CustomerStatusEnum.NEW);
        return result;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.user = stepExecution.getJobParameters().getString("user");
        logger = (BatchLog) stepExecution.getJobExecution().getExecutionContext().get("log");

    }

}
