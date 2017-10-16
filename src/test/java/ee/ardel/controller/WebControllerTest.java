package ee.ardel.controller;

import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static ee.ardel.controller.WebController.HOME_PAGE;
import static org.junit.Assert.assertEquals;

public class WebControllerTest {

  @InjectMocks
  private WebController controller;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldReturnHomePage() {
    String page = controller.index();

    assertEquals(HOME_PAGE, page);
  }
}