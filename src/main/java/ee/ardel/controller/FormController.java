package ee.ardel.controller;

import ee.ardel.form.CustomerDataForm;
import ee.ardel.model.CustomerInfo;
import ee.ardel.model.Sector;
import ee.ardel.service.CustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ee.ardel.controller.FormController.CUSTOMER_DATA_FORM_KEY;

@RestController
@SessionAttributes({CUSTOMER_DATA_FORM_KEY})
public class FormController extends HttpServlet {

  static final String CUSTOMER_DATA_FORM_KEY = "customerDataForm";

  @Autowired
  private CustomerInfoService customerInfoService;

  @RequestMapping(value = "/getCustomerForm", method = RequestMethod.GET)
  @ResponseBody
  public CustomerDataForm getCustomerDataForm(HttpServletRequest request) {

    return (CustomerDataForm) request.getSession().getAttribute(CUSTOMER_DATA_FORM_KEY);
  }

  @RequestMapping(value = "/saveCustomerForm", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Long> saveCustomerForm(HttpServletRequest request,
                                               @RequestBody
                                               @Valid CustomerDataForm customerForm,
                                               BindingResult bindingResult) {

    if (!bindingResult.hasErrors()) {
      CustomerInfo customerInfo = customerInfoService.createCustomerInfo(customerForm);

      if (customerInfo != null) {
        CustomerDataForm sessionCustomerForm = (CustomerDataForm) WebUtils.getSessionAttribute(request, CUSTOMER_DATA_FORM_KEY);

        if (sessionCustomerForm != null) {
          customerInfo.setId(sessionCustomerForm.getId());
        }
        CustomerInfo savedCustomerInfo = customerInfoService.saveCustomerInfo(customerInfo);

        request.getSession().setAttribute(CUSTOMER_DATA_FORM_KEY, mapCustomerInfoToSessionObject(savedCustomerInfo));

        return new ResponseEntity<>(savedCustomerInfo.getId(), HttpStatus.ACCEPTED);
      }
    }

    return new ResponseEntity<>(0L, HttpStatus.BAD_REQUEST);
  }

  private CustomerDataForm mapCustomerInfoToSessionObject(CustomerInfo customerInfo) {
    List<Long> sectors = customerInfo.getSectors().stream().map(Sector::getId).collect(Collectors.toList());

    return CustomerDataForm.builder()
      .id(customerInfo.getId())
      .name(customerInfo.getName())
      .sectors(sectors)
      .tc(customerInfo.isTc())
      .build();
  }
}