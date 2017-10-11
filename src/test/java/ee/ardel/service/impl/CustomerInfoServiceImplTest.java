package ee.ardel.service.impl;

import ee.ardel.form.CustomerDataForm;
import ee.ardel.model.CustomerInfo;
import ee.ardel.model.Sector;
import ee.ardel.service.SectorService;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CustomerInfoServiceImplTest {

  @Mock
  private SectorService sectorService;
  @InjectMocks
  private CustomerInfoServiceImpl service;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldCreateCustomerInfo() {
    List<Long> sectorIds = asList(1L, 2L);
    List<Sector> sectorList = asList(Sector.builder().name("1").build(), Sector.builder().name("2").build());
    CustomerDataForm customerDataForm = CustomerDataForm.builder().name("Name").sectors(sectorIds).tc(true).build();

    when(sectorService.findAllByIdIn(sectorIds)).thenReturn(sectorList);

    CustomerInfo customerInfo = service.createCustomerInfo(customerDataForm);

    assertEquals(customerDataForm.getName(), customerInfo.getName());
    assertEquals(sectorList, customerInfo.getSectors());
    assertEquals(customerDataForm.isTc(), customerInfo.isTc());
  }

  @Test
  public void shouldReturnNullIfNoValidSectors() {
    List<Long> sectorIds = asList(1L, 2L);

    CustomerDataForm customerDataForm = CustomerDataForm.builder().name("Name").sectors(sectorIds).tc(true).build();

    when(sectorService.findAllByIdIn(sectorIds)).thenReturn(emptyList());

    CustomerInfo customerInfo = service.createCustomerInfo(customerDataForm);

    assertNull(customerInfo);
  }
}