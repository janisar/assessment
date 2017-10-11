package ee.ardel.form;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
@Component("customerDataForm")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDataForm implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Size(min = 2, max = 100)
  private String name;
  @NotEmpty
  private List<Long> sectors;
  @AssertTrue
  private boolean tc;

  private Long id;

}
