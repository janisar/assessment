package ee.ardel.controller;

import ee.ardel.form.CustomerDataForm;
import ee.ardel.model.CustomerInfo;
import ee.ardel.model.Sector;
import ee.ardel.service.CustomerInfoService;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FormControllerTest {

  @Mock
  private CustomerInfoService customerInfoService;
  @InjectMocks
  private FormController controller;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldSaveCustomerFormWhenNoErrors() {
    Long customerInfoId = 1L;

    CustomerDataForm customerDataForm = CustomerDataForm.builder().build();
    List<Sector> sectors = singletonList(new Sector(1L, null, null, "SectorName"));

    BindingResult bindingResult = Mockito.mock(BindingResult.class);
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    CustomerInfo customerInfo = CustomerInfo.builder().id(customerInfoId).name("name").sectors(sectors).build();

    when(bindingResult.hasErrors()).thenReturn(false);
    when(customerInfoService.createCustomerInfo(customerDataForm)).thenReturn(customerInfo);
    when(customerInfoService.saveCustomerInfo(customerInfo)).thenReturn(customerInfo);
    when(request.getSession()).thenReturn(session);

    ResponseEntity<CustomerDataForm> response = controller.saveCustomerForm(customerDataForm, bindingResult);

    assertNotNull(response.getBody());
    assertEquals(customerInfoId, response.getBody().getId());
    assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
  }

  @Test
  public void shouldNotSaveCustomerFormWhenHasErrors() {
    CustomerDataForm customerDataForm = CustomerDataForm.builder().build();

    BindingResult bindingResult = Mockito.mock(BindingResult.class);

    when(bindingResult.hasErrors()).thenReturn(true);

    ResponseEntity<CustomerDataForm> response = controller.saveCustomerForm(customerDataForm, bindingResult);

    assertNotNull(response.getBody());
    assertEquals(null, response.getBody().getId());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void shouldNotSaveCustomerFormWhenCannotCreateCustomerInfoObject() {
    CustomerDataForm customerDataForm = CustomerDataForm.builder().build();

    BindingResult bindingResult = mock(BindingResult.class);

    when(bindingResult.hasErrors()).thenReturn(false);
    when(customerInfoService.createCustomerInfo(customerDataForm)).thenReturn(null);

    ResponseEntity<CustomerDataForm> response = controller.saveCustomerForm(customerDataForm, bindingResult);

    assertNotNull(response.getBody());
    assertEquals(null, response.getBody().getId());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

}