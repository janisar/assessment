package ee.ardel.controller;

import ee.ardel.form.CustomerDataForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("customerDataForm")
public class WebController {

  static final String HOME_PAGE = "index";

  @RequestMapping(value = "/")
  public String index() {
    return HOME_PAGE;
  }

  @ModelAttribute("customerDataForm")
  public CustomerDataForm setUpUserForm() {
    return new CustomerDataForm();
  }
}
