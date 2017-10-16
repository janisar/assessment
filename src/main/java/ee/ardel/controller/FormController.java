package ee.ardel.controller;

import ee.ardel.form.CustomerDataForm;
import ee.ardel.model.CustomerInfo;
import ee.ardel.service.CustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;

import static ee.ardel.controller.FormController.CUSTOMER_DATA_FORM_KEY;

@RestController
@SessionAttributes({CUSTOMER_DATA_FORM_KEY})
public class FormController extends HttpServlet {

  static final String CUSTOMER_DATA_FORM_KEY = "customerDataForm";

  @Autowired
  private CustomerInfoService customerInfoService;

  @RequestMapping(value = "/saveCustomerForm", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<CustomerDataForm> saveCustomerForm(@RequestBody
                                                           @Valid CustomerDataForm customerForm,
                                                           BindingResult bindingResult) {
    if (!bindingResult.hasErrors()) {
      CustomerInfo customerInfo = customerInfoService.createCustomerInfo(customerForm);

      if (customerInfo != null) {
        CustomerInfo savedCustomerInfo = customerInfoService.saveCustomerInfo(customerInfo);
        customerForm.setId(savedCustomerInfo.getId());

        return new ResponseEntity<>(customerForm, HttpStatus.ACCEPTED);
      }
    }

    return new ResponseEntity<>(customerForm, HttpStatus.BAD_REQUEST);
  }

}