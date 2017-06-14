package com.avanzarit.apps.gst.batch.job.customercontactpersonimport;

import com.avanzarit.apps.gst.model.Customer;
import com.avanzarit.apps.gst.model.CustomerContactPersonMaster;
import com.avanzarit.apps.gst.repository.CustomerContactPersonMasterRepository;
import com.avanzarit.apps.gst.repository.CustomerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerContactPersonDataImportWriter implements ItemWriter<CustomerContactPersonMaster> {

    private CustomerRepository customerRepository;
    private CustomerContactPersonMasterRepository customerContactPersonMasterRepository;


    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setCustomerContactPersonMasterRepository(CustomerContactPersonMasterRepository customerContactPersonMasterRepository) {
        this.customerContactPersonMasterRepository = customerContactPersonMasterRepository;
    }

    @Override
    public void write(List<? extends CustomerContactPersonMaster> customerContactPersonMasters) throws Exception {
        for (CustomerContactPersonMaster customerContactPersonMaster : customerContactPersonMasters) {
            Customer customer = customerRepository.findByCustomerId(customerContactPersonMaster.getCustomerId());
            customerContactPersonMaster.setCustomer(customer);
            customerContactPersonMasterRepository.save(customerContactPersonMaster);
        }
    }
}
