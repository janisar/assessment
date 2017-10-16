package ee.ardel.service.impl;

import ee.ardel.model.Sector;
import ee.ardel.repository.SectorRepository;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.thymeleaf.util.SetUtils.singletonSet;

/**
 * Created by janis on 10/11/17.
 */
public class SectorServiceImplTest {

  @Mock
  private SectorRepository sectorRepository;
  @InjectMocks
  private SectorServiceImpl sectorService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldGetRootSectors() {
    Sector sector = getTestSector();

    when(sectorRepository.findByParentId(null)).thenReturn(singletonList(sector));

    List<Sector> sectors = sectorService.getRootSectors();

    assertEquals(1, sectors.size());
    assertEquals(1L, sectors.get(0).getId().longValue());
    assertEquals(1, sectors.get(0).getChildren().size());
  }

  @Test
  public void shouldFindAllByIdIn() {
    Sector sector = getTestSector();
    List<Long> sectorIds = singletonList(1L);

    when(sectorService.findAllByIdIn(sectorIds)).thenReturn(singletonList(sector));

    List<Sector> sectors = sectorService.findAllByIdIn(sectorIds);

    assertEquals(1, sectors.size());
    assertEquals(1L, sectors.get(0).getId().longValue());
    assertEquals(1, sectors.get(0).getChildren().size());
  }

  private Sector getTestSector() {
    Sector child = Sector.builder().name("childSector").id(2L).build();
    return Sector.builder().id(1L).name("name").children(singletonSet(child)).build();
  }
}