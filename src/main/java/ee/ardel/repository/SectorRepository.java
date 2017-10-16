package ee.ardel.repository;

import ee.ardel.model.Sector;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SectorRepository extends CrudRepository<Sector, Long> {

  List<Sector> findByParentId(Long parentId);

  List<Sector> findAllByIdIn(List<Long> ids);
}
