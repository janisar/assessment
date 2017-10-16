package ee.ardel.service.impl;

import ee.ardel.form.CustomerDataForm;
import ee.ardel.model.CustomerInfo;
import ee.ardel.model.Sector;
import ee.ardel.repository.CustomerInfoRepository;
import ee.ardel.service.CustomerInfoService;
import ee.ardel.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

  @Autowired
  private SectorService sectorService;
  @Autowired
  private CustomerInfoRepository customerInfoRepository;

  @Override
  public CustomerInfo createCustomerInfo(CustomerDataForm customerDataForm) {
    List<Sector> sectors = sectorService.findAllByIdIn(customerDataForm.getSectors());

    if (sectors.size() != customerDataForm.getSectors().size()) {
      return null;
    }
    return CustomerInfo.builder()
      .id(customerDataForm.getId())
      .name(customerDataForm.getName())
      .sectors(sectors)
      .tc(customerDataForm.isTc())
      .build();
  }

  @Override
  public CustomerInfo saveCustomerInfo(CustomerInfo customerInfo) {
    return customerInfoRepository.save(customerInfo);
  }
}
