package ee.ardel.controller;

import ee.ardel.form.CustomerDataForm;
import ee.ardel.model.CustomerInfo;
import ee.ardel.model.Sector;
import ee.ardel.service.CustomerInfoService;
import org.junit.*;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static ee.ardel.controller.FormController.CUSTOMER_DATA_FORM_KEY;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
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
    ArgumentCaptor<CustomerDataForm> customerDataFormArgumentCaptor = ArgumentCaptor.forClass(CustomerDataForm.class);

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

    ResponseEntity<Long> response = controller.saveCustomerForm(request, customerDataForm, bindingResult);

    verify(session).setAttribute(any(String.class), customerDataFormArgumentCaptor.capture());

    assertNotNull(response.getBody());
    assertEquals(customerInfoId, response.getBody());
    assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

    CustomerDataForm sessionCustomerData = customerDataFormArgumentCaptor.getValue();

    assertEquals("name", sessionCustomerData.getName());
    assertEquals(customerInfoId, sessionCustomerData.getId());
    assertEquals(sectors.get(0).getId(), sessionCustomerData.getSectors().get(0));
  }

  @Test
  public void shouldNotSaveCustomerFormWhenHasErrors() {
    Long expectedResult = 0L;

    CustomerDataForm customerDataForm = CustomerDataForm.builder().build();

    BindingResult bindingResult = Mockito.mock(BindingResult.class);
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    when(bindingResult.hasErrors()).thenReturn(true);

    ResponseEntity<Long> response = controller.saveCustomerForm(request, customerDataForm, bindingResult);

    assertNotNull(response.getBody());
    assertEquals(expectedResult, response.getBody());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void shouldNotSaveCustomerFormWhenCannotCreateCustomerInfoObject() {
    Long expectedResult = 0L;

    CustomerDataForm customerDataForm = CustomerDataForm.builder().build();

    BindingResult bindingResult = mock(BindingResult.class);
    HttpServletRequest request = mock(HttpServletRequest.class);

    when(bindingResult.hasErrors()).thenReturn(false);
    when(customerInfoService.createCustomerInfo(customerDataForm)).thenReturn(null);

    ResponseEntity<Long> response = controller.saveCustomerForm(request, customerDataForm, bindingResult);

    assertNotNull(response.getBody());
    assertEquals(expectedResult, response.getBody());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void shouldGetCustomerDataForm() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpSession session = mock(HttpSession.class);

    CustomerDataForm customerDataForm = CustomerDataForm.builder().name("test").id(1L).tc(true).build();

    when(request.getSession()).thenReturn(session);
    when(session.getAttribute(CUSTOMER_DATA_FORM_KEY)).thenReturn(customerDataForm);

    CustomerDataForm result = controller.getCustomerDataForm(request);

    assertEquals(customerDataForm.getName(), result.getName());
    assertEquals(customerDataForm.getId(), result.getId());
  }

}