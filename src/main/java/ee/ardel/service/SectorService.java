package ee.ardel.service;

import ee.ardel.model.Sector;

import java.util.List;

public interface SectorService {

  List<Sector> getRootSectors();

  List<Sector> findAllByIdIn(List<Long> sectorIds);
}
