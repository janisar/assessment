package ee.ardel.controller;

import ee.ardel.model.Sector;
import ee.ardel.service.SectorService;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SectorControllerTest {

  @Mock
  private SectorService sectorService;
  @InjectMocks
  private SectorController controller;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldGetRootSectors() throws Exception {
    Sector sector = Sector.builder().name("sector1").id(11L).build();

    when(sectorService.getRootSectors()).thenReturn(singletonList(sector));

    List<Sector> sectorList = controller.getRootSectors();

    assertEquals(1, sectorList.size());
    assertEquals(sector.getName(), sectorList.get(0).getName());
  }

}