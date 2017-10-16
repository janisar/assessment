package ee.ardel.service;

import ee.ardel.form.CustomerDataForm;
import ee.ardel.model.CustomerInfo;
import org.springframework.stereotype.Service;

@Service
public interface CustomerInfoService {

  CustomerInfo createCustomerInfo(CustomerDataForm customerDataForm);

  CustomerInfo saveCustomerInfo(CustomerInfo customerInfo);
}
