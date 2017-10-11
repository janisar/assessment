package ee.ardel.controller;

import ee.ardel.model.Sector;
import ee.ardel.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SectorController {

  @Autowired
  private SectorService sectorService;

  @RequestMapping(value = "/rootSectors", method = RequestMethod.GET)
  public List<Sector> getRootSectors() {
    return sectorService.getRootSectors();
  }
}
