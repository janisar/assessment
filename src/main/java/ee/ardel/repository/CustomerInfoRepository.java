package ee.ardel.repository;

import ee.ardel.model.CustomerInfo;
import org.springframework.data.repository.CrudRepository;

public interface CustomerInfoRepository extends CrudRepository<CustomerInfo, Long> {
}
