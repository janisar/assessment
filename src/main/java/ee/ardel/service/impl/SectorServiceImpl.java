package ee.ardel.service.impl;

import ee.ardel.model.Sector;
import ee.ardel.repository.SectorRepository;
import ee.ardel.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorServiceImpl implements SectorService {

  @Autowired
  private SectorRepository sectorRepository;

  @Override
  public List<Sector> getRootSectors() {
    return sectorRepository.findByParentId(null);
  }

  @Override
  public List<Sector> findAllByIdIn(List<Long> sectorIds) {
    return sectorRepository.findAllByIdIn(sectorIds);
  }
}
